use std::collections::HashMap;

use crate::algorithms::alphabets::*;

use once_cell::sync::Lazy;
use rand::seq::SliceRandom;
use rand::thread_rng;

static ALPHABET: Lazy<Vec<char>> = Lazy::new(|| alphabet());

#[allow(dead_code)]
pub fn alphabet() -> Vec<char> {
    let mut rng = thread_rng();

    let mut alpha = [
        LOWERCASE_LETTERS.to_vec(),
        UPPERCASE_LETTERS.to_vec(),
        DIGITS.to_vec(),
        PUNCTUATION.to_vec(),
        GREEK_LOWERCASE.to_vec(),
        GREEK_UPPERCASE.to_vec(),
        RUSSIAN_LOWERCASE.to_vec(),
        RUSSIAN_UPPERCASE.to_vec(),
        HEBREW.to_vec(),
        ARABIC.to_vec(),
        WESTERN_ADDITIONAL.to_vec(),
        EXTENDED_LATIN.to_vec(),
        MISC_SYMBOLS.to_vec(),
        JAPANESE.to_vec(),
        CHINESE.to_vec(),
        KOREAN.to_vec(),
        // SPECIAL_CHARS.to_vec(),// MATH_SYMBOLS.to_vec(),//ADDITIONAL_EAST_CHARACTERS.to_vec(),
    ]
    .concat();

    alpha.shuffle(&mut rng);

    alpha
}

static ALPHABET_MAP: Lazy<HashMap<char, usize>> = Lazy::new(|| {
    let mut map = HashMap::new();
    for (i, &c) in ALPHABET.iter().enumerate() {
        map.insert(c, i);
    }
    map
});

// Your Caesar cipher function
#[allow(dead_code)]
pub fn caesar_cipher(text: &str, shift: i32) -> String {
    let mut result: String = String::new();
    let alphabet = &*ALPHABET;
    let alphabet_map = &*ALPHABET_MAP;
    let m = alphabet.len() as i32;
    let shift_amount = (shift % m + m) % m; // Ensures shift_amount is positive

    for t in text.chars() {
        if let Some(&t_pos) = alphabet_map.get(&t) {
            result.push(alphabet[((t_pos as i32 + shift_amount) % m) as usize]);
        } else {
            result.push(t);
        }
    }

    result
}
#[allow(dead_code)]
pub fn encrypt_caesar_cipher(message: &str, shift: i32) -> String {
    caesar_cipher(&message, shift)
}

#[allow(dead_code)]
pub fn decrypt_caesar_cipher(message: &str, shift: i32) -> String {
    caesar_cipher(&message, -shift)
}

#[allow(dead_code)]
pub fn example() -> () {
    let alphabet = alphabet();

    println!("{:?}", alphabet.len());

    let message = "hello";

    let shift = 100;

    let encrypted = caesar_cipher(&message, shift);
    let decrypted = caesar_cipher(&encrypted, -shift);

    println!("{:?}> {:?}", message, encrypted);
    println!("{:?}> {:?}", encrypted, decrypted);
}

const SHIFT: i32 = 1000;

#[allow(dead_code)]
pub fn main() -> () {
    let message = r#"Philosophy delves into the essence of existence, pondering 
    concepts like éthique (ethics) and dasein (being) from various traditions. 
    It explores raison d'être (reason for being) and schönheit (beauty), blending 
    logos (reason) with existenz (existence). Philosophers grapple with sagacité (wisdom) and the sens of life."#;

    println!("### Message\n\n{:?}\n\n###\n", message);

    let encrypted: String = encrypt_caesar_cipher(&message, SHIFT);

    println!("### Encrypted Message\n\n{:?}\n\n###\n", encrypted);

    let decrypted: String = decrypt_caesar_cipher(&encrypted, SHIFT);

    println!("### Decrypted Message\n\n{:?}\n\n###\n", decrypted);
}
