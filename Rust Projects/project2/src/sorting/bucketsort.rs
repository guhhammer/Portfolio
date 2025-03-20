#[allow(dead_code)]
pub fn bucketsort(arr: &mut [i128]) -> () {
    if arr.len() <= 1 {
        return;
    }

    let min_value = *arr.iter().min().unwrap_or(&0);
    let max_value = *arr.iter().max().unwrap_or(&0);

    let range = max_value - min_value + 1;
    let num_buckets = (range as usize).min(arr.len());

    let mut buckets: Vec<Vec<i128>> = vec![Vec::new(); num_buckets];

    for &num in arr.iter() {
        let bucket_index = ((num - min_value) as usize * num_buckets) / range as usize;

        buckets[bucket_index].push(num);
    }

    for bucket in buckets.iter_mut() {
        bucket.sort(); // insertion sort.
    }

    let mut index = 0;
    for bucket in buckets.into_iter() {
        for num in bucket {
            arr[index] = num;
            index += 1;
        }
    }
}
