#[allow(dead_code)]
pub fn factorial(n: u32) -> u32 {
    if n < 2 {
        return 1;
    }

    let mut ret: u32 = 1;
    for i in 2..n + 1 {
        ret *= i;
    }

    ret
}

#[allow(dead_code)]
pub fn fact(n: u32) -> u32 {
    factorial(n)
}

#[allow(dead_code)]
pub fn main() -> () {
    let n = 6;
    let f = factorial(n);

    println!("factorial({:?}) = {:?}", n, f);
}
