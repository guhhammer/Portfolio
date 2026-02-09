use crate::{
    algorithms::randarray::rand_array, sorting::randomized_quicksort::randomized_quicksort,
};

#[allow(dead_code)]
pub fn main() -> () {
    let mut arr = rand_array(10);

    println!("Original array: {:?}", arr);

    randomized_quicksort(&mut arr);

    println!("Sorted array: {:?}", arr);
}
