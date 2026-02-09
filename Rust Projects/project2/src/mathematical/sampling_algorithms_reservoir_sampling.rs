use crate::algorithms::randarray::rand_array;
use crate::mathematical::random_number_generation::random_number_limit;

#[allow(dead_code)]
pub fn reservoir_sampling<T: Ord + Copy>(stream: Vec<T>, sample_size: usize) -> Vec<T> {
    let mut reservoir = Vec::new();

    for (ix, item) in stream.iter().enumerate() {
        if ix < sample_size {
            reservoir.push(*item);
        } else {
            let j: usize = random_number_limit(0, ix as u64) as usize;

            if j < sample_size {
                reservoir[j] = *item;
            }
        }
    }

    reservoir
}

#[allow(dead_code)]
pub fn main() -> () {
    let stream: Vec<i128> = rand_array(30);

    let sample_size: usize = 20;

    println!(
        "reservoir_sampling(stream:stream, sample_size:{:?}) = {:?}",
        sample_size,
        reservoir_sampling(stream, sample_size)
    );
}
