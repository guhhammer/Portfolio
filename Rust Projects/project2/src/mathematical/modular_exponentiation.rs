#[allow(dead_code)]
pub fn modular_exponentiation(a: u64, b: u64, n: u64) -> u64 {
    if n == 1 {
        return 0;
    }

    let mut base: u64 = a % n;

    let mut exp: u64 = b;

    let mut result: u64 = 1;

    while exp > 0 {
        if exp % 2 == 1 {
            result = (result * base) % n;
        }

        exp >>= 1;

        base = (base * base) % n;
    }

    result
}

#[allow(dead_code)]
pub fn main() -> () {
    println!("3^17 mod 17 = {:?}", modular_exponentiation(3, 13, 17));
}
