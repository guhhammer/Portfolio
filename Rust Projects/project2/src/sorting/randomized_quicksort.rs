use rand::Rng;

#[allow(dead_code)]
pub fn randomized_quicksort<T: Ord + Clone>(arr: &mut Vec<T>) -> () {
    if arr.len() <= 1 {
        return;
    }

    let pivot_index = rand::thread_rng().gen_range(0..arr.len());
    let pivot = arr[pivot_index].clone();

    let mut less_than_pivot = Vec::new();
    let mut greater_than_pivot = Vec::new();

    for item in arr.iter() {
        if item < &pivot {
            less_than_pivot.push(item.clone());
        } else if item > &pivot {
            greater_than_pivot.push(item.clone());
        }
    }

    randomized_quicksort(&mut less_than_pivot);
    randomized_quicksort(&mut greater_than_pivot);

    arr.clear();
    arr.append(&mut less_than_pivot);
    arr.push(pivot);
    arr.append(&mut greater_than_pivot);
}
