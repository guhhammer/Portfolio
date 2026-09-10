#[allow(dead_code)]
pub fn fast_exponentiation(a: u64, b: u64) -> u64 {
    let mut base: u64 = a;
    let mut exp: u64 = b;
    let mut result: u64 = 1;

    while exp > 0 {
        if exp % 2 == 1 {
            result *= base;
        }
        base *= base;
        exp /= 2;
    }

    result
}

#[allow(dead_code)]
pub fn mod_exp(base: u64, exp: u64, modulus: u64) -> u64 {
    let mut result = 1;
    let mut base = base % modulus;
    let mut exp = exp;

    while exp > 0 {
        if exp % 2 == 1 {
            result = (result * base) % modulus;
        }
        exp = exp >> 1;
        base = (base * base) % modulus;
    }
    result
}

#[allow(dead_code)]
pub fn main() -> () {
    println!("3^13 = {:?}", fast_exponentiation(3, 13));
}
