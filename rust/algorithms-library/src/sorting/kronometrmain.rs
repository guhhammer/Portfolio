use std::{
    io::{self, Write},
    ops::Range,
    time::Instant,
};

use crate::{algorithms::randarray, sorting};

#[allow(dead_code)]
fn timing<F>(arr: &mut [i128], sort_fn: F, time_units: &str) -> i128
where
    F: FnOnce(&mut [i128]),
{
    let start = Instant::now();

    sort_fn(arr);

    let mark = start.elapsed();

    match time_units {
        "nano" => mark.as_nanos() as i128,
        "micro" => mark.as_micros() as i128,
        "milli" => mark.as_millis() as i128,
        _ => mark.as_secs() as i128,
    }
}

#[allow(dead_code)]
fn time_annotation(time_units: &str) -> &str {
    match time_units {
        "nano" => "ns",
        "micro" => "μs",
        "milli" => "ms",
        _ => "s",
    }
}

#[allow(dead_code)]
fn display_sample(sample: i128) -> String {
    let s = sample.to_string();

    if s.len() <= 3 {
        return s;
    }

    let mut ret: String = String::new();
    let mut count: i32 = 0;

    for (_i, c) in s.chars().rev().enumerate() {
        if count == 3 {
            ret.push('_');
            count = 0;
        }

        ret.push(c);
        count += 1;
    }

    ret.chars().rev().collect()
}

#[allow(dead_code)]
fn round_executioner(
    method: fn(&mut [i128]),
    name: &str,
    max_sample: i128,
    rounds: i128,
    time_units: &str,
) -> () {
    print!("|\n|--- [round_executioner (method:{}) (sample:{}) (rounds:{})]\n|\n|--- executing rounds: [ ", name, display_sample(max_sample), rounds);
    io::stdout().flush().unwrap();

    let mut sum_timing: i128 = 0;

    let arr: Vec<i128> = randarray::rand_array(max_sample);

    for r in 0..rounds {
        print!("{:?} ", r + 1);
        io::stdout().flush().unwrap();

        sum_timing += timing(&mut arr.clone(), method, time_units);
    }

    sum_timing /= rounds;

    print!(
        "]\n|\n|--- average execution time: {} {}.\n",
        sum_timing,
        time_annotation(time_units)
    );
    io::stdout().flush().unwrap();
}

#[allow(dead_code)]
fn category_controller(
    method_class_name: &str,
    matrix: &[(fn(&mut [i128]), i128, &str); 10],
    items_sampling: &[i128; 9],
    category_range: Range<usize>,
    spec_time: &[&str; 9],
    rounds: i128,
) -> () {
    println!("| {} Methods Execution Time>\n|", method_class_name);

    for c_range in category_range {
        let &(method, max_limit, name) = &matrix[c_range];

        print!("| {} execution time>\n|\n", name);

        let mut sample_max = 0;

        for s in 0..items_sampling.len() {
            sample_max = s;

            if items_sampling[s] > max_limit {
                break;
            }
        }

        for sample in 0..sample_max {
            if items_sampling[sample as usize] > max_limit {
                break;
            }

            let mut arr: Vec<i128> = randarray::rand_array_spread(items_sampling[sample], 1);

            let ex_time: i128 = timing(&mut arr, method, spec_time[sample]);

            print!(
                "|--- execution time: {:?} {} (sample:{})\n",
                ex_time,
                time_annotation(spec_time[sample]),
                display_sample(items_sampling[sample as usize])
            );
        } // limit in max_limit for each method.

        let one_exponant_easier_round = max_limit / 10;

        round_executioner(
            method,
            name,
            one_exponant_easier_round,
            rounds,
            spec_time[sample_max],
        );

        println!("|");
    }
}

#[allow(dead_code)]
fn controller() -> () {
    let matrix: [(fn(&mut [i128]), i128, &str); 10] = [
        // best methods.
        (sorting::quicksort::quicksort, 10_000_000, "quicksort"),
        (sorting::mergesort::mergesort, 10_000_000, "mergesort"),
        (sorting::heapsort::heapsort, 10_000_000, "heapsort"),
        // mid methods.
        (sorting::radixsort::radixsort, 1_000_000, "radixsort"),
        (
            sorting::countingsort::countingsort,
            1_000_000,
            "countingsort",
        ),
        (sorting::bucketsort::bucketsort, 1_000_000, "bucketsort"),
        (sorting::shellsort::shellsort, 1_000_000, "shellsort"),
        // basic methods.
        (
            sorting::selectionsort::selectionsort,
            10_000,
            "selectionsort",
        ),
        (
            sorting::insertionsort::insertionsort,
            10_000,
            "insertionsort",
        ),
        (sorting::bubblesort::bubblesort, 10_000, "bubblesort"),
    ]; // (method, max_limit of items in array to be tested in timing fn, name of method).

    let items_sampling: [i128; 9] = [
        10,
        100,
        1_000, // a thousand.
        10_000,
        100_000,
        1_000_000, // a million.
        10_000_000,
        100_000_000,
        1_000_000_000, // a billion.
    ];

    let spec_time_annotation: [&str; 9] = [
        "nano", "nano", "micro", "micro", "milli", "milli", "milli", "milli", "s",
    ];

    let _category_rounds = [15, 30, 30];

    category_controller(
        "Basic",
        &matrix,
        &items_sampling,
        7..10,
        &spec_time_annotation,
        40,
    );

    category_controller(
        "Mid",
        &matrix,
        &items_sampling,
        3..7,
        &spec_time_annotation,
        30,
    );

    category_controller(
        "Best",
        &matrix,
        &items_sampling,
        0..3,
        &spec_time_annotation,
        15,
    );
}

#[allow(dead_code)]
pub fn main() -> () {
    println!("\nkronometrmain.rs>\n");

    controller();

    println!("\n-----\n");
}
