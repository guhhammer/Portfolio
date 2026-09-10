#[warn(dead_code)]
pub fn insertionsort<T>(arr: &mut [T]) -> ()
where
    T: PartialOrd<T>,
{
    for i in 1..arr.len() {
        let mut j = i;

        while j > 0 && arr[j - 1] > arr[j] {
            arr.swap(j - 1, j);
            j -= 1;
        }
    }
}
