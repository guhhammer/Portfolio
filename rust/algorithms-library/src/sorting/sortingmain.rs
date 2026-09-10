use crate::{algorithms::randarray, sorting};

#[allow(dead_code)]
fn sorting(vec: &mut Vec<i128>, ok: bool) -> () {
    if !ok {
        return;
    }

    let methods: Vec<fn(&mut [i128])> = vec![
        sorting::quicksort::quicksort,
        sorting::mergesort::mergesort,
        sorting::bubblesort::bubblesort,
        sorting::insertionsort::insertionsort,
        sorting::selectionsort::selectionsort,
        sorting::heapsort::heapsort,
        sorting::shellsort::shellsort,
        sorting::countingsort::countingsort,
        sorting::radixsort::radixsort,
        sorting::bucketsort::bucketsort,
    ];

    let method_name: Vec<&str> = vec![
        "quicksort",
        "mergesort",
        "bubblesort",
        "insertionsort",
        "selectionsort",
        "heapsort",
        "shellsort",
        "countingsort",
        "radixsort",
        "bucketsort",
    ];

    let mut index: usize = 0;
    for m in methods {
        let mut m_vec: Vec<i128> = vec.clone();

        print!(
            "Using {:?} method to sort>\n|\n",
            method_name[index].to_owned()
        );

        print!("| normal: {:?}\n|\n", &mut m_vec);

        m(&mut m_vec);

        println!("| sorted: {:?}\n", &mut m_vec);

        index += 1;
    }
}

#[allow(dead_code)]
fn tester(ok: bool) -> () {
    if ok {
        let mut testing: Vec<i128> = randarray::rand_array_spread(10, 100);

        print!("| normal: {:?}\n|\n", &mut testing);

        sorting::bucketsort::bucketsort(&mut testing);

        println!("| sorted: {:?}\n", &mut testing);
    }
}

#[allow(dead_code)]
pub fn main() -> () {
    let mut random_vec: Vec<i128> = randarray::rand_array(20);

    sorting(&mut random_vec, true);

    tester(false);
}
