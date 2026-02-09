#[allow(dead_code)]
fn compute_prefix_function(pattern: &str) -> Vec<i32> {
    let mut pi: Vec<i32> = (0..pattern.len()).into_iter().map(|_| 0).collect();
    let mut k: usize = 0;

    let pattern_bytes = pattern.as_bytes();

    for i in 1..pattern.len() {
        while k > 0 && pattern_bytes[i] != pattern_bytes[k] {
            k = pi[k - 1] as usize;
        }

        if pattern_bytes[i] == pattern_bytes[k] {
            k += 1;
        }

        pi[i] = k as i32;
    }

    pi
}

pub fn kmp_search(text: &str, pattern: &str) -> Vec<usize> {
    let (text_bytes, pattern_bytes) = (text.as_bytes(), pattern.as_bytes());

    let mut occurrences: Vec<usize> = Vec::new();

    let (pi, mut j) = (compute_prefix_function(pattern), 0);

    for i in 0..text.len() {
        while j > 0 && text_bytes[i] != pattern_bytes[j] {
            j = pi[j - 1] as usize;
        }

        if text_bytes[i] == pattern_bytes[j] {
            j += 1;
        }

        if j == pattern.len() {
            occurrences.push(i - pattern.len() + 1);
            j = pi[j - 1] as usize;
        }
    }

    occurrences
}

#[allow(dead_code)]
pub fn main() -> () {
    let text = "ababcababcabc";
    let pattern = "abc";
    let matches = kmp_search(text, pattern);

    println!("Pattern found at indices: {:?}", matches);
}
