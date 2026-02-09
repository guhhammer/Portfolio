use base64::{engine::general_purpose, Engine as _};
use chacha20poly1305::aead::generic_array::GenericArray;
use chacha20poly1305::{
    aead::{Aead, KeyInit},
    ChaCha20Poly1305,
};
use rand::rngs::OsRng;
use rand::RngCore;
use x25519_dalek::{PublicKey as X25519Public, StaticSecret};

// Convert a base64 string into a StaticSecret (private key)
pub fn static_secret_from_b64(b64: &str) -> StaticSecret {
    let clean = b64.trim_matches('"').trim();

    // Decode base64
    let bytes = general_purpose::STANDARD
        .decode(clean)
        .expect("invalid base64");

    // Check length
    if bytes.len() != 32 {
        panic!("expected 32 bytes, got {}", bytes.len());
    }

    // Convert to array
    let bytes_array: [u8; 32] = bytes.try_into().unwrap();

    StaticSecret::from(bytes_array)
}

// Convert a base64 string into X25519Public (public key)
pub fn x25519_pub_from_b64(b64: &str) -> X25519Public {
    let bytes = general_purpose::STANDARD.decode(b64).unwrap();
    let bytes_array_pub: [u8; 32] = bytes.as_slice().try_into().expect("expected 32 bytes");
    X25519Public::from(bytes_array_pub)
}

/// Compute shared key using X25519
fn derive_shared_key(sender_priv: &StaticSecret, recipient_pub: &X25519Public) -> [u8; 32] {
    let shared = sender_priv.diffie_hellman(recipient_pub);
    *shared.as_bytes()
}

/// Encrypt a message for a peer
pub fn encrypt_for_peer(
    sender_priv: &StaticSecret,
    recipient_pub: &X25519Public,
    plaintext: &[u8],
) -> (Vec<u8>, Vec<u8>) {
    let key_bytes = derive_shared_key(sender_priv, recipient_pub);
    let aead = ChaCha20Poly1305::new(GenericArray::from_slice(&key_bytes));

    let mut nonce = [0u8; 12];
    OsRng.fill_bytes(&mut nonce);

    let ciphertext = aead
        .encrypt(GenericArray::from_slice(&nonce), plaintext)
        .unwrap();
    (nonce.to_vec(), ciphertext)
}

/// Decrypt a message from a peer
pub fn decrypt_from_peer(
    recipient_priv: &StaticSecret,
    sender_pub: &X25519Public,
    nonce: &[u8],
    ciphertext: &[u8],
) -> Vec<u8> {
    let key_bytes = derive_shared_key(recipient_priv, sender_pub);
    let aead = ChaCha20Poly1305::new(GenericArray::from_slice(&key_bytes));
    aead.decrypt(GenericArray::from_slice(nonce), ciphertext)
        .unwrap()
}
