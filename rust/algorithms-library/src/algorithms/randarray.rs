use rand::Rng;

#[warn(dead_code)]
pub fn rand_array(size: i128) -> Vec<i128> {
    let mut rng: rand::prelude::ThreadRng = rand::thread_rng();

    (0..size).map(|_| rng.gen_range(0..(size * 100))).collect()
}

#[warn(dead_code)]
pub fn rand_array_spread(size: i128, spread: i128) -> Vec<i128> {
    let mut rng: rand::prelude::ThreadRng = rand::thread_rng();

    (0..size)
        .map(|_| rng.gen_range(0..(size * spread)))
        .collect()
}
