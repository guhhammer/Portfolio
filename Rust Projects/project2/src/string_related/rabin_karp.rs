#[allow(dead_code)]
pub fn rabin_karp(text: &str, pattern: &str) -> () {
    let prime: i32 = 101;
    let base: i32 = 256;
    let n = text.len();
    let m = pattern.len();

    if m > n {
        return;
    }

    let text_bytes = text.as_bytes();
    let pattern_bytes = pattern.as_bytes();

    let mut pattern_hash: i32 = 0;
    let mut text_hash: i32 = 0;
    let mut h: i32 = 1;

    for _ in 0..m - 1 {
        h = (h * base) % prime;
    }

    for i in 0..m {
        pattern_hash = (pattern_hash * base + pattern_bytes[i] as i32) % prime;
        text_hash = (text_hash * base + text_bytes[i] as i32) % prime;
    }

    for i in 0..=n - m {
        if pattern_hash == text_hash {
            if &text[i..i + m] == pattern {
                println!("Pattern found at index {}.", i);
            }
        }

        if i < n - m {
            text_hash =
                ((text_hash - text_bytes[i] as i32 * h) * base + text_bytes[i + m] as i32) % prime;

            if text_hash < 0 {
                text_hash += prime;
            }
        }
    }
}

#[allow(dead_code)]
pub fn main() -> () {
    let text = "abracadabraab";
    let pattern = "abra";
    rabin_karp(text, pattern);
}
