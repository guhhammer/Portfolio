use crate::mathematical::using_memoizaiton_factorial::fact;

#[allow(dead_code)]
pub fn is_prime_wilson(p: u32) -> bool {
    if p <= 1 {
        return false;
    }

    if p == 2 {
        return true;
    }

    fact(p - 1) % p == p - 1
}

#[allow(dead_code)]
pub fn main() -> () {
    println!("is_prime_wilson({:?}) = {:?}", 5, is_prime_wilson(5));
    println!("is_prime_wilson({:?}) = {:?}", 4, is_prime_wilson(4));
    println!("is_prime_wilson({:?}) = {:?}", 7, is_prime_wilson(7));
    println!("is_prime_wilson({:?}) = {:?}", 9, is_prime_wilson(9));
}
