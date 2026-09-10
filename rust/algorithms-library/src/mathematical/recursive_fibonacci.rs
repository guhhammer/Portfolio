#[allow(dead_code)]
pub fn fibonacci(n: u32) -> u32 {
    if n == 0 {
        return 0;
    }

    if n == 1 {
        return 1;
    }

    fibonacci(n - 1) + fibonacci(n - 2)
}

#[allow(dead_code)]
pub fn main() -> () {
    let n = 8;
    let f = fibonacci(n);

    println!("fibonacci({:?}) = {:?}", n, f);
}
