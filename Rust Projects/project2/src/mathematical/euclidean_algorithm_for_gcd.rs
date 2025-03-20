#[allow(dead_code)]
pub fn gcd(a: u64, b: u64) -> u64 {
    let mut x = a;

    let mut y = b;

    let mut temp: u64;

    if y > x {
        temp = x;
        x = y;
        y = temp;
    }

    while y != 0 {
        temp = x;
        x = y;
        y = temp % y;
    }

    x
}

#[allow(dead_code)]
pub fn main() -> () {
    println!("GCD({:?}, {:?}) = {:?}", 48, 18, gcd(48, 18));
}
