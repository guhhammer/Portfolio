pub fn quicksort<T>(arr: &mut [T]) -> ()
where
    T: PartialOrd<T>,
{
    if arr.len() <= 1 {
        return;
    }

    let pivot_index: usize = partition(arr);

    let (left, right) = arr.split_at_mut(pivot_index);

    quicksort(left);
    quicksort(&mut right[1..]);
}

fn partition<T>(arr: &mut [T]) -> usize
where
    T: PartialOrd<T>,
{
    let pivot_index: usize = arr.len() / 2;

    arr.swap(pivot_index, arr.len() - 1);

    let mut index = 0;

    for i in 0..arr.len() - 1 {
        if arr[i] < arr[arr.len() - 1] {
            arr.swap(i, index);

            index += 1;
        }
    }

    arr.swap(index, arr.len() - 1);
    index
}
