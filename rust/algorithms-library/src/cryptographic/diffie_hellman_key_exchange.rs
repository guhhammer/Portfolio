use rand::Rng;

use crate::mathematical::{
    fast_exponentiation::mod_exp, random_number_generation::random_number_limit,
    sieve_of_eratosthenes_for_finding_prime_numbers::sieve_of_eratosthenes,
};

#[allow(dead_code)]
pub fn gen_private_key(p: u64) -> u64 {
    let mut rng = rand::thread_rng();
    rng.gen_range(2..p)
}

#[allow(dead_code)]
pub fn gen_parameter() -> u64 {
    random_number_limit(20, 100)
}

#[allow(dead_code)]
pub fn gen_g() -> u64 {
    let sieve = sieve_of_eratosthenes(10);

    let x = random_number_limit(0, sieve.len() as u64);

    sieve[x as usize] as u64
}

#[allow(dead_code)]
pub fn gen_public_key(private_key: u64, g: u64, p: u64) -> u64 {
    mod_exp(g, private_key, p)
}

#[allow(dead_code)]
pub fn gen_shared_secret(their_public_key: u64, your_private_key: u64, p: u64) -> u64 {
    mod_exp(their_public_key, your_private_key, p)
}

#[allow(dead_code)]
pub fn main() -> () {
    let (p, g) = (gen_parameter(), gen_g());

    // Party A
    let private_key_a = gen_private_key(p);
    let public_key_a = gen_public_key(private_key_a, g, p);

    // Party B
    let private_key_b = gen_private_key(p);
    let public_key_b = gen_public_key(private_key_b, g, p);

    // Exchange public keys
    let shared_secret_a = gen_shared_secret(public_key_b, private_key_a, p);
    let shared_secret_b = gen_shared_secret(public_key_a, private_key_b, p);

    // Print results
    println!("Party A's Private Key: {}", private_key_a);
    println!("Party A's Public Key: {}", public_key_a);
    println!("Party B's Private Key: {}", private_key_b);
    println!("Party B's Public Key: {}", public_key_b);
    println!("Shared Secret (computed by Party A): {}", shared_secret_a);
    println!("Shared Secret (computed by Party B): {}", shared_secret_b);

    // Ensure the shared secrets are the same
    assert_eq!(
        shared_secret_a, shared_secret_b,
        "Shared secrets do not match!"
    );
    println!("Shared secrets match!");
}
