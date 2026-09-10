fn fac(n: u32, acc: u32, i: u32) -> u32 {
    if n == i {
        return acc * n;
    }

    fac(n, acc * i, i + 1)
}

#[allow(dead_code)]
pub fn factorial(n: u32) -> u32 {
    if n < 2 {
        return 1;
    }

    fac(n, 1, 1)
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
