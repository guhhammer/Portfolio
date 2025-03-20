#[allow(dead_code)]
pub fn heapsort<T>(arr: &mut [T]) -> ()
where
    T: PartialOrd<T>,
{
    max_heap(arr, arr.len());

    for i in (1..arr.len()).rev() {
        arr.swap(0, i);

        heapify(arr, 0, i);
    }
}

fn max_heap<T>(arr: &mut [T], len: usize) -> ()
where
    T: PartialOrd<T>,
{
    let mut i: isize = (len / 2) as isize - 1;

    while i >= 0 {
        heapify(arr, i as usize, len);
        i -= 1;
    }
}

fn heapify<T>(arr: &mut [T], i: usize, len: usize) -> ()
where
    T: PartialOrd<T>,
{
    let mut largest: usize = i;
    let left: usize = 2 * i + 1;
    let right: usize = 2 * i + 2;

    if left < len && arr[left] > arr[largest] {
        largest = left;
    }

    if right < len && arr[right] > arr[largest] {
        largest = right;
    }

    if largest != i {
        arr.swap(i, largest);

        heapify(arr, largest, len);
    }
}
