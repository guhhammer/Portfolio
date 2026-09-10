#[allow(dead_code)]
pub fn factorial(n: u32) -> u32 {
    if n == 0 {
        return 1;
    }

    n * factorial(n - 1)
}

#[allow(dead_code)]
pub fn fact(n: u32) -> u32 {
    factorial(n)
}

#[allow(dead_code)]
pub fn main() -> () {
    let n = 5;
    let f = factorial(n);

    println!("factorial({:?}) = {:?}", n, f);
}
