#[allow(dead_code)]
pub fn fibonacci(n: u32) -> u32 {
    if n == 0 {
        return 0;
    }

    if n == 1 {
        return 1;
    }

    let mut a = 0;
    let mut b = 1;

    let mut temp;

    for _ in 2..n + 1 {
        temp = a;
        a = b;
        b += temp;
    }

    b
}

#[allow(dead_code)]
pub fn main() -> () {
    let n = 8;
    let f = fibonacci(n);

    println!("fibonacci({:?}) = {:?}", n, f);
}
