#[allow(dead_code)]
pub fn sieve_of_eratosthenes(limit: usize) -> Vec<usize> {
    let mut prime: Vec<bool> = vec![true; limit + 1];

    let mut p = 2;

    while p * p <= limit {
        if prime[p] {
            for i in (p * p..=limit).step_by(p) {
                prime[i] = false;
            }
        }

        p += 1;
    }

    (2..=limit).filter(|&x| prime[x]).collect()
}

#[allow(dead_code)]
pub fn main() -> () {
    println!(
        "Prime numbers up to {}: {:?}",
        100,
        sieve_of_eratosthenes(100)
    );
}
