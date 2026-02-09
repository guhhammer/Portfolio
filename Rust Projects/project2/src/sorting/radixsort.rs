#[allow(dead_code)]
pub fn radixsort(arr: &mut [i128]) -> () {
    if arr.len() == 0 {
        return;
    }

    let max_value = *arr.iter().max().unwrap_or(&0);
    let mut place: i128 = 1;

    while place <= max_value {
        let mut output = vec![0; arr.len()];
        let mut count = vec![0; 10];

        for &num in arr.iter() {
            let digit = getdigit(num, place as usize);
            count[digit] += 1;
        }

        for i in 1..10 {
            count[i] += count[i - 1];
        }

        for &num in arr.iter().rev() {
            let digit = getdigit(num, place as usize);
            let idx = count[digit] - 1;

            output[idx] = num;
            count[digit] -= 1;
        }

        arr.copy_from_slice(&output);

        place *= 10;
    }
}

fn getdigit(num: i128, place: usize) -> usize {
    (num as usize / place) % 10
}
