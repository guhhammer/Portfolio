use std::error::Error;

#[allow(dead_code)]
fn extended_gcd(a: u64, b: u64) -> (u64, i64, i64) {
    if a == 0 {
        return (b, 0, 1);
    }

    let (gcd, x1, y1) = extended_gcd(b % a, a);

    (gcd, y1 - (b / a) as i64 * x1, x1)
}

#[allow(dead_code)]
fn mod_inverse(a: u64, m: u64) -> Result<u64, Box<dyn Error>> {
    let (gcd, x, _) = extended_gcd(a, m);

    if gcd != 1 {
        Err("No modular inverse exists".into())
    } else {
        Ok(((x % m as i64 + m as i64) % m as i64) as u64)
    }
}

#[allow(dead_code)]
pub fn chinese_remainder_theorem(a_list: &[u64], m_list: &[u64]) -> Result<u64, Box<dyn Error>> {
    assert_eq!(
        a_list.len(),
        m_list.len(),
        "Lists must be of the same length"
    );

    let m: u64 = m_list.into_iter().fold(1, |acc, x| acc * x);

    let mut acc: u64 = 0;
    for (&a_i, &m_i) in a_list.iter().zip(m_list.iter()) {
        acc = (acc + a_i * (m / m_i) * mod_inverse(m / m_i, m_i)?) % m
    }

    Ok(acc)
}

#[allow(dead_code)]
pub fn main() -> () {
    let a_list: [u64; 3] = [2, 3, 2];
    let m_list: [u64; 3] = [3, 5, 7];

    let _ = match chinese_remainder_theorem(&a_list, &m_list) {
        Ok(result) => println!("The solution is x = {}", result),
        Err(e) => eprintln!("Error: {}", e),
    };
}
