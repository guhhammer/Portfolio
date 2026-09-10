#[allow(dead_code)]
pub fn linear_search<T>(arr: &Vec<T>, elem: T) -> i32
where
    T: PartialEq<T>,
{
    for i in 0..arr.len() {
        if arr[i] == elem {
            return i as i32;
        }
    }

    -1
}

#[allow(dead_code)]
pub fn main() -> () {
    let numbers = vec![1, 3, 5, 7, 9, 11];
    let target = 7;

    let result = linear_search(&numbers, target);

    if result != -1 {
        println!("Element {} found at index {}.", target, result);
    } else {
        println!("Element {} not found in the list.", target);
    }

    let words = vec!["apple", "banana", "cherry", "date"];
    let target_word = "cherry";

    let result_word = linear_search(&words, target_word);

    if result_word != -1 {
        println!("Element {} found at index {}.", target_word, result_word);
    } else {
        println!("Element {} not found in the list.", target_word);
    }
}
