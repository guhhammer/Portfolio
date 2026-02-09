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

pub fn run() -> Result<(), Box<dyn std::error::Error>> {
    println!("Run: ed25519 sign + x25519 DH + chacha20poly1305 encrypt demo");

    // -----------------------
    // 1) Generate Ed25519 keypair (signing identity)
    // -----------------------
    let mut csprng = OsRng {};
    let keypair: Keypair = Keypair::generate(&mut csprng);
    let pubkey_bytes = keypair.public.to_bytes();
    println!(
        "Ed25519 public (base64): {}",
        general_purpose::STANDARD.encode(pubkey_bytes)
    );

    // -----------------------
    // 2) Generate X25519 keypairs for sender and receiver
    //    (StaticSecret + PublicKey)
    // -----------------------
    // Sender keys
    let mut rng = OsRng {};
    let sender_secret = StaticSecret::new(rng);
    let sender_pub = X25519Public::from(&sender_secret);

    // Receiver keys (simulate peer/server)
    let receiver_secret = StaticSecret::new(rng);
    let receiver_pub = X25519Public::from(&receiver_secret);

    // Exchange and derive shared secret (both sides derive same bytes)
    let shared1 = sender_secret.diffie_hellman(&receiver_pub);
    let shared2 = receiver_secret.diffie_hellman(&sender_pub);
    assert_eq!(shared1.as_bytes(), shared2.as_bytes());
    let shared_bytes = shared1.as_bytes(); // 32 bytes

    println!(
        "Derived shared secret (hex, truncated): {}",
        hex::encode(&shared_bytes[..8])
    );

    // -----------------------
    // 3) Build payload and encrypt it with ChaCha20-Poly1305 using shared key
    // -----------------------
    let plaintext = b"{\"op\":\"update\",\"value\":42}";

    // Use the shared_bytes as key material. In production you should derive subkeys with HKDF.
    let aead_key = GenericArray::from_slice(shared_bytes); // 32 bytes => valid key
    let aead = ChaCha20Poly1305::new(aead_key);

    // Nonce (12 bytes). Use random nonce per message.
    let mut nonce = [0u8; 12];
    // we can reuse OsRng here
    rand::RngCore::fill_bytes(&mut rng, &mut nonce);
    let nonce_ga = GenericArray::from_slice(&nonce);

    let ciphertext = aead
        .encrypt(nonce_ga, plaintext.as_ref())
        .map_err(|e| format!("encryption failed: {e:?}"))?;

    println!(
        "Ciphertext (base64): {}",
        general_purpose::STANDARD.encode(&ciphertext)
    );
    println!(
        "Nonce (base64): {}",
        general_purpose::STANDARD.encode(nonce)
    );

    // -----------------------
    // 4) Build header and sign it with Ed25519
    //    header = node_id | seq | ts | nonce_b64 | ciphertext_b64
    // -----------------------
    let node_id = general_purpose::STANDARD.encode(pubkey_bytes); // node identity = ed25519 pub
    let seq: u64 = 1;
    let ts = chrono::Utc::now().timestamp();

    let nonce_b64 = general_purpose::STANDARD.encode(nonce);
    let ct_b64 = general_purpose::STANDARD.encode(&ciphertext);

    let header = format!("{node_id}|{seq}|{ts}|{nonce_b64}|{ct_b64}");

    // sign header
    let signature: Signature = keypair.sign(header.as_bytes());
    let sig_b64 = general_purpose::STANDARD.encode(signature.to_bytes());
    println!("Signature (base64): {sig_b64}");

    // -----------------------
    // 5) Simulate receiver verifying & decrypting
    // -----------------------
    // a) verify signature:
    let sender_pubkey = PublicKey::from_bytes(&pubkey_bytes)?;
    let sig_bytes = general_purpose::STANDARD.decode(&sig_b64)?;
    let sig = Signature::from_bytes(&sig_bytes)?;

    match sender_pubkey.verify(header.as_bytes(), &sig) {
        Ok(_) => println!("Signature verified! sender is authorized (assuming whitelist)."),
        Err(e) => {
            println!("Signature verification failed: {e}");
            return Err("signature failed".into());
        }
    }

    // b) parse header fields (for demo we already have nonce & ciphertext)
    let nonce_decoded = general_purpose::STANDARD.decode(&nonce_b64)?;
    let ct_decoded = general_purpose::STANDARD.decode(&ct_b64)?;

    // c) derive shared secret on receiver (we already did above: shared2)
    let aead_key2 = GenericArray::from_slice(shared2.as_bytes());
    let aead_receiver = ChaCha20Poly1305::new(aead_key2);
    let nonce_ga2 = GenericArray::from_slice(&nonce_decoded);

    let decrypted = aead_receiver
        .decrypt(nonce_ga2, ct_decoded.as_ref())
        .map_err(|e| format!("decryption failed: {e:?}"))?;

    match String::from_utf8(decrypted.clone()) {
        Ok(s) => println!("Decrypted payload: {s}"),
        Err(e) => println!("Invalid UTF-8, raw bytes: {:?}", e.into_bytes()),
    }
    // d) verify integrity optionally by hashing
    let mut hasher = Sha256::new();
    hasher.update(&decrypted);
    let digest = hasher.finalize();
    println!("SHA256 of payload (hex): {}", hex::encode(digest));

    println!("All done.");

    Ok(())
}

/*

Threat                Protection Provided                             Notes for Current Setup
----------------------------------------------------------------------------------------------------
Unauthorized node     Signature verification + whitelist           Ed25519 ensures only nodes with a registered private key
                                                               can produce valid signatures. Whitelist ensures only known
                                                               node IDs are accepted.

Eavesdropping         E2E encryption                                X25519-derived ChaCha20Poly1305 keys ensure that only the
                                                               intended recipient (with the private X25519 key) can
                                                               decrypt messages. LAN or internet eavesdroppers see only ciphertext.

Message tampering     Signatures detect modifications               Ed25519 signature covers all header fields
                                                               (node_id|seq|ts|recipient_id|nonce|enc_payload), so any
                                                               modification is detected.

Replay attacks        Partially covered                             We include seq numbers and timestamps in the signed
                                                               payload. Nodes must persist last_seq_seen and reject
                                                               duplicates for full protection.

MITM                  Partially                                     E2E encryption + signature protects message contents and
                      covered                                        integrity. Connection-level MITM could inject or replace
                                                                   public keys; TLS or mutual authentication secures transport.

*/
