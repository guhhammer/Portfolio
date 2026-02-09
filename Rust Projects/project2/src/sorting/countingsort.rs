#[allow(dead_code)]
pub fn countingsort(arr: &mut [i128]) -> () {
    let max_value = *arr.iter().max().unwrap_or(&0) as usize;

    let mut count = vec![0; max_value + 1];

    for &num in arr.iter() {
        count[num as usize] += 1;
    }

    for i in 1..=max_value {
        count[i] += count[i - 1];
    }

    let mut output = vec![0; arr.len()];

    for &num in arr.iter().rev() {
        let idx = count[num as usize] - 1;

        output[idx] = num;

        count[num as usize] -= 1;
    }

    arr.copy_from_slice(&output);
}
