pub fn mergesort<T>(arr: &mut [T]) -> ()
where
    T: Clone + PartialOrd<T> + PartialEq<T> + Copy,
{
    if arr.len() <= 1 {
        return;
    }

    let mid: usize = arr.len() / 2;

    let mut left = arr[..mid].to_vec();
    let mut right = arr[mid..].to_vec();

    mergesort(&mut left);
    mergesort(&mut right);

    merge(arr, &left, &right);
}

pub fn merge<T>(arr: &mut [T], left: &[T], right: &[T]) -> ()
where
    T: PartialOrd<T> + PartialEq<T> + Copy,
{
    let mut left_index = 0;
    let mut right_index = 0;
    let mut index = 0;

    while left_index < left.len() && right_index < right.len() {
        if left[left_index] <= right[right_index] {
            arr[index] = left[left_index];
            left_index += 1;
        } else {
            arr[index] = right[right_index];
            right_index += 1;
        }

        index += 1;
    }

    while left_index < left.len() {
        arr[index] = left[left_index];
        left_index += 1;
        index += 1;
    }

    while right_index < right.len() {
        arr[index] = right[right_index];
        right_index += 1;
        index += 1;
    }
}
