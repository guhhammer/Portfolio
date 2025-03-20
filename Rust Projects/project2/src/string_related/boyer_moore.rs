use std::collections::HashMap;

#[allow(dead_code)]
fn build_bad_character_table(pattern: &str) -> HashMap<char, usize> {
    let mut table = HashMap::new();

    for (i, c) in pattern.chars().enumerate() {
        table.insert(c, i);
    }

    table
}

#[allow(dead_code)]
pub fn boyer_moore_search(text: &str, pattern: &str) -> Vec<usize> {
    let n = text.len();
    let m = pattern.len();

    if m == 0 {
        return Vec::new();
    }

    let bad_char_table = build_bad_character_table(pattern);
    let mut matches = Vec::new();

    let mut s = 0;
    while s <= n - m {
        let mut j = m - 1;

        while j < m && pattern.chars().nth(j).unwrap() == text.chars().nth(s + j).unwrap() {
            if j == 0 {
                matches.push(s);
                break;
            }
            j -= 1;
        }

        let bad_char_index = bad_char_table
            .get(&text.chars().nth(s + j).unwrap())
            .unwrap_or(&usize::MAX);

        let shift_amount = if *bad_char_index >= j {
            j + 1
        } else {
            usize::max(1, j - *bad_char_index)
        };

        s += shift_amount;
    }

    matches
}

#[allow(dead_code)]
pub fn main() -> () {
    let text = "ABABDABACDABABCABAB";
    let pattern = "ABABCABAB";
    let matches = boyer_moore_search(text, pattern);
    println!("Pattern found at positions: {:?}", matches);
}
