#[allow(dead_code)]
pub fn selectionsort<T>(arr: &mut [T]) -> ()
where
    T: PartialOrd<T>,
{
    for i in 0..arr.len() {
        let mut min = i;
        for j in i..arr.len() {
            if arr[j] < arr[min] {
                min = j;
            }
        }

        arr.swap(i, min);
    }
}
