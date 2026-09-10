//! X25519 key exchange + ChaCha20-Poly1305 authenticated encryption.

use crate::core::error::ApplicationError;
use crate::errors::crypt::{
    DECRYPTION_FAILED, ENCRYPTION_FAILED, INVALID_KEY_ENCODING, INVALID_KEY_LENGTH,
};
use base64::{Engine as _, engine::general_purpose};
use chacha20poly1305::aead::generic_array::GenericArray;
use chacha20poly1305::{
    ChaCha20Poly1305,
    aead::{Aead, KeyInit},
};
use rand::RngCore;
use rand::rngs::OsRng;
use x25519_dalek::{PublicKey as X25519Public, StaticSecret};

/// Decode a base64 string into raw 32-byte key material.
fn key_bytes_from_b64(b64: &str) -> Result<[u8; 32], ApplicationError> {
    let clean = b64.trim_matches('"').trim();

    let bytes = general_purpose::STANDARD
        .decode(clean)
        .map_err(|e| INVALID_KEY_ENCODING.with_details(e.to_string()))?;

    bytes
        .try_into()
        .map_err(|b: Vec<u8>| INVALID_KEY_LENGTH.with_details(format!("got {} bytes", b.len())))
}

/// Parse a base64-encoded X25519 private key.
pub fn static_secret_from_b64(b64: &str) -> Result<StaticSecret, ApplicationError> {
    Ok(StaticSecret::from(key_bytes_from_b64(b64)?))
}

/// Parse a base64-encoded X25519 public key.
pub fn x25519_pub_from_b64(b64: &str) -> Result<X25519Public, ApplicationError> {
    Ok(X25519Public::from(key_bytes_from_b64(b64)?))
}

/// Derive the shared symmetric key for a pair of peers (X25519 Diffie-Hellman).
fn derive_shared_key(local_priv: &StaticSecret, remote_pub: &X25519Public) -> [u8; 32] {
    *local_priv.diffie_hellman(remote_pub).as_bytes()
}

/// Encrypt a message for a peer. Returns `(nonce, ciphertext)`.
pub fn encrypt_for_peer(
    sender_priv: &StaticSecret,
    recipient_pub: &X25519Public,
    plaintext: &[u8],
) -> Result<(Vec<u8>, Vec<u8>), ApplicationError> {
    let key_bytes = derive_shared_key(sender_priv, recipient_pub);
    let aead = ChaCha20Poly1305::new(GenericArray::from_slice(&key_bytes));

    // A fresh random nonce per message.
    let mut nonce = [0u8; 12];
    OsRng.fill_bytes(&mut nonce);

    let ciphertext = aead
        .encrypt(GenericArray::from_slice(&nonce), plaintext)
        .map_err(|e| ENCRYPTION_FAILED.with_details(format!("{e:?}")))?;

    Ok((nonce.to_vec(), ciphertext))
}

/// Decrypt (and authenticate) a message from a peer.
pub fn decrypt_from_peer(
    recipient_priv: &StaticSecret,
    sender_pub: &X25519Public,
    nonce: &[u8],
    ciphertext: &[u8],
) -> Result<Vec<u8>, ApplicationError> {
    let key_bytes = derive_shared_key(recipient_priv, sender_pub);
    let aead = ChaCha20Poly1305::new(GenericArray::from_slice(&key_bytes));

    aead.decrypt(GenericArray::from_slice(nonce), ciphertext)
        .map_err(|e| DECRYPTION_FAILED.with_details(format!("{e:?}")))
}
