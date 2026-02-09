use super::bell_numbers::pascoal_triangle_row;

#[allow(dead_code)]
pub fn permutation(n: u128, k: u128) -> u128 {
    (1..=n).fold(1, |acc, x| acc * x) / (1..n - k + 1).fold(1, |acc, x| acc * x)
}

#[allow(dead_code)]
pub fn combination(n: u128, k: u128) -> u32 {
    let row = pascoal_triangle_row(n as usize);

    row[k as usize]
}

#[allow(dead_code)]
pub fn main() -> () {
    let n = 5;
    let k = 2;

    let p = permutation(n, k);
    let c = combination(n, k);

    println!("permutation({:?}, {:?}) = {:?}", n, k, p);

    println!("combination({:?}, {:?}) = {:?}", n, k, c);
}
