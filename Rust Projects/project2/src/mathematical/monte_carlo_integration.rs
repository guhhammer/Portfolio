use rand::Rng;

#[allow(dead_code)]
pub fn monte_carlo_integration(f: fn(f64) -> f64, a: f64, b: f64, n: usize) -> f64 {
    let mut rng = rand::thread_rng();

    let mut sum = 0.0;

    for _ in 0..n {
        sum += f(rng.gen_range(a..b));
    }

    (b - a) * (sum / n as f64)
}

#[allow(dead_code)]
pub fn main() -> () {
    let f = |x: f64| x * x;

    let a = 0.0;
    let b = 1.0;

    let n = 10000;

    println!(
        "Estimated integral: {}",
        monte_carlo_integration(f, a, b, n)
    );
}
