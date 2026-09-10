#[allow(dead_code)]
pub fn binary_search<T>(arr: &Vec<T>, elem: T) -> i32
where
    T: PartialEq<T> + PartialOrd<T>,
{
    let mut low = 0;
    let mut high = arr.len() - 1;

    while low <= high {
        let mid = (low + high) / 2;

        if arr[mid] == elem {
            return mid as i32;
        } else if arr[mid] < elem {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }

    -1
}

#[allow(dead_code)]
pub fn main() -> () {
    let numbers = vec![1, 3, 5, 7, 9, 11];
    let target = 7;

    let result = binary_search(&numbers, target);

    if result != -1 {
        println!("Element {} found at index {}.", target, result);
    } else {
        println!("Element {} not found in the list.", target);
    }

    let words = vec!["apple", "banana", "cherry", "date"];
    let target_word = "cherry";

    let result_word = binary_search(&words, target_word);

    if result_word != -1 {
        println!("Element {} found at index {}.", target_word, result_word);
    } else {
        println!("Element {} not found in the list.", target_word);
    }
}
