//! End-to-end walkthrough of the messenger's cryptography:
//! Ed25519 signing + X25519 key exchange + ChaCha20-Poly1305 encryption.
//!
//! Run with: `cargo run --example crypto_demo`

use ed25519_dalek::{Keypair, PublicKey, Signature, Signer, Verifier};
use rand::rngs::OsRng;

use x25519_dalek::{PublicKey as X25519Public, StaticSecret};

use chacha20poly1305::aead::generic_array::GenericArray;
use chacha20poly1305::{
    ChaCha20Poly1305,
    aead::{Aead, KeyInit},
};

use base64::{Engine as _, engine::general_purpose};
use sha2::{Digest, Sha256};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    println!("Demo: ed25519 sign + x25519 DH + chacha20poly1305 encrypt");

    // 1) Generate an Ed25519 keypair — the node's signing identity.
    let mut csprng = OsRng {};
    let keypair: Keypair = Keypair::generate(&mut csprng);
    let pubkey_bytes = keypair.public.to_bytes();
    println!(
        "Ed25519 public (base64): {}",
        general_purpose::STANDARD.encode(pubkey_bytes)
    );

    // 2) Generate X25519 keypairs for sender and receiver.
    let mut rng = OsRng {};
    let sender_secret = StaticSecret::new(rng);
    let sender_pub = X25519Public::from(&sender_secret);

    let receiver_secret = StaticSecret::new(rng);
    let receiver_pub = X25519Public::from(&receiver_secret);

    // Both sides derive the same shared secret via Diffie-Hellman.
    let shared1 = sender_secret.diffie_hellman(&receiver_pub);
    let shared2 = receiver_secret.diffie_hellman(&sender_pub);
    assert_eq!(shared1.as_bytes(), shared2.as_bytes());
    let shared_bytes = shared1.as_bytes();

    println!(
        "Derived shared secret (hex, truncated): {}",
        hex::encode(&shared_bytes[..8])
    );

    // 3) Encrypt a payload with ChaCha20-Poly1305 using the shared key.
    //    In production, subkeys should be derived with HKDF instead of using
    //    the raw DH output.
    let plaintext = b"{\"op\":\"update\",\"value\":42}";

    let aead = ChaCha20Poly1305::new(GenericArray::from_slice(shared_bytes));

    // A fresh random 12-byte nonce per message.
    let mut nonce = [0u8; 12];
    rand::RngCore::fill_bytes(&mut rng, &mut nonce);

    let ciphertext = aead
        .encrypt(GenericArray::from_slice(&nonce), plaintext.as_ref())
        .map_err(|e| format!("encryption failed: {e:?}"))?;

    println!(
        "Ciphertext (base64): {}",
        general_purpose::STANDARD.encode(&ciphertext)
    );
    println!("Nonce (base64): {}", general_purpose::STANDARD.encode(nonce));

    // 4) Build a header and sign it with Ed25519.
    //    header = node_id | seq | ts | nonce_b64 | ciphertext_b64
    let node_id = general_purpose::STANDARD.encode(pubkey_bytes);
    let seq: u64 = 1;
    let ts = chrono::Utc::now().timestamp();

    let nonce_b64 = general_purpose::STANDARD.encode(nonce);
    let ct_b64 = general_purpose::STANDARD.encode(&ciphertext);

    let header = format!("{node_id}|{seq}|{ts}|{nonce_b64}|{ct_b64}");

    let signature: Signature = keypair.sign(header.as_bytes());
    let sig_b64 = general_purpose::STANDARD.encode(signature.to_bytes());
    println!("Signature (base64): {sig_b64}");

    // 5) Simulate the receiver verifying and decrypting.
    // a) Verify the signature.
    let sender_pubkey = PublicKey::from_bytes(&pubkey_bytes)?;
    let sig_bytes = general_purpose::STANDARD.decode(&sig_b64)?;
    let sig = Signature::from_bytes(&sig_bytes)?;

    match sender_pubkey.verify(header.as_bytes(), &sig) {
        Ok(_) => println!("Signature verified! Sender is authorized (assuming whitelist)."),
        Err(e) => {
            println!("Signature verification failed: {e}");
            return Err("signature failed".into());
        }
    }

    // b) Decode the header fields.
    let nonce_decoded = general_purpose::STANDARD.decode(&nonce_b64)?;
    let ct_decoded = general_purpose::STANDARD.decode(&ct_b64)?;

    // c) Decrypt with the receiver's derived shared key.
    let aead_receiver = ChaCha20Poly1305::new(GenericArray::from_slice(shared2.as_bytes()));

    let decrypted = aead_receiver
        .decrypt(GenericArray::from_slice(&nonce_decoded), ct_decoded.as_ref())
        .map_err(|e| format!("decryption failed: {e:?}"))?;

    match String::from_utf8(decrypted.clone()) {
        Ok(s) => println!("Decrypted payload: {s}"),
        Err(e) => println!("Invalid UTF-8, raw bytes: {:?}", e.into_bytes()),
    }

    // d) Hash the payload as an integrity spot-check.
    let mut hasher = Sha256::new();
    hasher.update(&decrypted);
    println!("SHA256 of payload (hex): {}", hex::encode(hasher.finalize()));

    println!("All done.");

    Ok(())
}

/*
Threat model summary:

Threat              Protection                       Notes for the current setup
--------------------------------------------------------------------------------
Unauthorized node   Signature verification +         Ed25519 ensures only nodes with a registered
                    whitelist                        private key can produce valid signatures.

Eavesdropping       End-to-end encryption            X25519-derived ChaCha20-Poly1305 keys mean
                                                     eavesdroppers only ever see ciphertext.

Message tampering   Signatures detect modification   The Ed25519 signature covers every header
                                                     field, so any modification is detected.

Replay attacks      Partially covered                Sequence numbers and timestamps are signed;
                                                     nodes must persist last_seq_seen and reject
                                                     duplicates for full protection.

MITM                Partially covered                Encryption + signatures protect contents and
                                                     integrity; TLS or mutual authentication would
                                                     secure key distribution itself.
*/
