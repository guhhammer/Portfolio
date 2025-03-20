#[allow(dead_code)]
pub fn shellsort<T>(arr: &mut [T]) -> ()
where
    T: PartialOrd<T> + Copy,
{
    let mut gap: usize = arr.len() / 2;

    while gap > 0 {
        for i in gap..arr.len() {
            let mut j = i;

            let temp = arr[i];

            while j >= gap && arr[j - gap] > temp {
                arr[j] = arr[j - gap];

                j -= gap;
            }

            arr[j] = temp;
        }

        gap /= 2;
    }
}
