use std::collections::HashMap;

// fibonacci memoization (top-down approach).
#[allow(dead_code)]
fn fib_m(n: usize, memo: &mut HashMap<usize, usize>) -> usize {
    if n == 0 {
        return 0;
    }

    if n == 1 {
        return 1;
    }

    if let Some(&ret) = memo.get(&n) {
        return ret;
    }

    let result = fib_m(n - 1, memo) + fib_m(n - 2, memo);

    memo.insert(n, result);

    result
}

#[allow(dead_code)]
pub fn fib_memo(n: usize) -> usize {
    let mut memo = HashMap::new();

    fib_m(n, &mut memo)
}

//fibonacci tabulation (bottom-up approach).
#[allow(dead_code)]
pub fn fib_tab(n: usize) -> usize {
    if n == 0 {
        return 0;
    }

    if n == 1 {
        return 1;
    }

    let mut arr = vec![0; n + 1];

    arr[0] = 0;
    arr[1] = 1;

    for i in 2..=n {
        arr[i] = arr[i - 1] + arr[i - 2];
    }

    arr[n]
}

#[allow(dead_code)]
pub fn fibonnacci_sequence(n: usize) -> usize {
    fib_tab(n)
}

#[allow(dead_code)]
pub fn fib(n: usize) -> usize {
    fib_tab(n)
}

#[allow(dead_code)]
pub fn main() -> () {
    let mut n = 10;

    let mut result = fib_memo(n);

    println!("Fibonacci memoization of {} is {}", n, result);

    n = 15;

    result = fib_tab(n);

    println!("Fibonacci tabulation of {} is {}", n, result);
}
