#[allow(dead_code)]
fn binomial_coeficcient(n: u128, k: u128) -> u128 {
    (k + 1..=n).fold(1, |acc, x| acc * x) / (1..=k).fold(1, |acc, x| acc * x)
}

#[allow(dead_code)]
pub fn catalan_number(n: u128) -> u128 {
    ((1 as f64 / (n + 1) as f64) * binomial_coeficcient(2 * n, n) as f64) as u128
}

#[allow(dead_code)]
pub fn main() -> () {
    let n = 8;
    let f = catalan_number(n);

    println!("catalan_number({:?}) = {:?}", n, f);
}
