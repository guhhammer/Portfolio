use crate::mathematical::wilsons_theorem::is_prime_wilson;
use std::collections::HashSet;

#[allow(dead_code)]
pub fn prime_factors(n: u32) -> Vec<u32> {
    let mut factors: Vec<u32> = Vec::new();

    let mut num = n;

    while num % 2 == 0 {
        factors.push(2);
        num /= 2;
    }

    for i in (3..((num as f64).sqrt() as i32)).step_by(2) {
        while num % i as u32 == 0 {
            factors.push(i as u32);
            num /= i as u32;
        }
    }

    if num > 2 {
        factors.push(num);
    }

    factors
}

#[allow(dead_code)]
pub fn eulers_totient_function(p: u32) -> u32 {
    if p == 1 {
        return 1;
    }

    if is_prime_wilson(p) {
        return p - 1;
    }

    let set: HashSet<u32> = prime_factors(p).into_iter().collect();

    let unique_vec: Vec<u32> = set.into_iter().collect();

    let mut result: f64 = p as f64;

    for i in 0..unique_vec.len() {
        result *= 1f64 - (1f64 / unique_vec[i] as f64);
    }

    result as u32
}

#[allow(dead_code)]
pub fn main() -> () {
    let _ = [6, 9, 10, 12]
        .into_iter()
        .map(|x| {
            println!("ϕ({:?}) = {:?}", x, eulers_totient_function(x));
        })
        .collect::<Vec<_>>();
}
