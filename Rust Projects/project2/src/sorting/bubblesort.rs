#[allow(dead_code)]
pub fn bubblesort<T>(arr: &mut [T]) -> ()
where
    T: PartialOrd<T>,
{
    for i in 0..arr.len() {
        for j in i + 1..arr.len() {
            if arr[i] > arr[j] {
                arr.swap(i, j);
            }
        }
    }
}
