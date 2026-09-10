use std::time::{SystemTime, UNIX_EPOCH};

#[allow(dead_code)]
fn linear_congruential_generator(state: u64, prev_state: bool) -> u64 {
    let start: SystemTime = SystemTime::now();
    let duration_since_epoch: std::time::Duration = start
        .duration_since(UNIX_EPOCH)
        .expect("Time went backwards");

    let timestamp_ns: u128 = duration_since_epoch.as_nanos();

    let seed: u64 = if prev_state {
        state
    } else {
        (timestamp_ns % 100) as u64
    };

    let primes: [u64; 10] = [7, 11, 13, 31, 37, 47, 67, 71, 83, 97];

    let mersennes: [u32; 3] = [7, 31, 42];

    let m: u64 = 2u64.pow(mersennes[(timestamp_ns % 10 % 3) as usize]) - 1;

    let a: u64 = primes[(timestamp_ns % 10) as usize];

    let c: u64 =
        mersennes[(timestamp_ns % 3) as usize] as u64 + primes[(timestamp_ns % 10) as usize] + 429;

    (a * seed + c) % m
}

#[allow(dead_code)]
struct Lcg {
    state: u64,
}

impl Lcg {
    #[allow(dead_code)]
    fn new(seed: u64) -> Self {
        Lcg { state: seed }
    }
}

impl Iterator for Lcg {
    type Item = u64;

    fn next(&mut self) -> Option<Self::Item> {
        self.state = linear_congruential_generator(self.state, true);
        Some(self.state)
    }
}

#[allow(dead_code)]
fn lcg(sequence_size: usize, limit_a: u64, limit_b: u64, no_limit: bool) -> Vec<u64> {
    let start: SystemTime = SystemTime::now();

    let duration_since_epoch: std::time::Duration = start
        .duration_since(UNIX_EPOCH)
        .expect("Time went backwards");

    let timestamp_ns: u128 = duration_since_epoch.as_nanos();

    let mut lcg: Lcg = Lcg::new((timestamp_ns % 42) as u64);

    let mut ret: Vec<u64> = Vec::new();

    if sequence_size < 2 {
        if sequence_size == 1 {
            if no_limit {
                ret.push(lcg.next().unwrap());
            } else {
                while ret.len() < 1 {
                    let x = lcg.next().unwrap() % limit_b;

                    if x >= limit_a {
                        ret.push(x);
                    }
                }
            }
        }

        return ret;
    }

    while ret.len() < sequence_size {
        if no_limit {
            ret.push(lcg.next().unwrap());
            continue;
        }

        let x = lcg.next().unwrap() % limit_b;

        if x >= limit_a {
            ret.push(x);
        }
    }

    ret
}

#[allow(dead_code)]
pub fn random_number() -> u64 {
    lcg(1, 0, 10, true)[0]
}

#[allow(dead_code)]
pub fn random_number_limit(min: u64, max: u64) -> u64 {
    lcg(1, min, max, false)[0]
}

#[allow(dead_code)]
pub fn random_array(size: usize) -> Vec<u64> {
    lcg(size, 0, 10, true)
}

#[allow(dead_code)]
pub fn random_array_limit(size: usize, min: u64, max: u64) -> Vec<u64> {
    lcg(size, min, max, false)
}

#[allow(dead_code)]
pub fn main() -> () {
    let rn = random_number();

    let rnl = random_number_limit(10, 50);

    let ra = random_array(10);

    let ral = random_array_limit(10, 100, 1000);

    println!("random_number() = {:?}", rn);

    println!("random_number_limit(10, 50) = {:?}", rnl);

    println!("random_number(10) = {:?}", ra);

    println!("random_number(10, 100, 1000) = {:?}", ral);
}
