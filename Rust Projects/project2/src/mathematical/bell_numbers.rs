#[allow(dead_code)]
pub fn pascoal_triangle_row(n: usize) -> Vec<u32> {
    let mut row = Vec::new();

    row.push(1);

    if n == 0 {
        return row;
    }

    row.push(1);

    if n == 1 {
        return row;
    }

    for r in 2..n + 1 {
        let mut nrow = Vec::new();

        nrow.push(1);

        for i in 0..r - 1 {
            nrow.push(row[i] + row[i + 1]);
        }

        nrow.push(1);

        row = nrow;
    }

    row
}

#[allow(dead_code)]
pub fn bell_number(n: usize) -> u32 {
    if n == 0 {
        return 1;
    }

    let mut bell_n = Vec::new();

    bell_n.push(1);

    for i in 1..n + 1 {
        let left_multi = pascoal_triangle_row(i - 1);

        let mut bell_i = 0;

        for j in 0..left_multi.len() {
            bell_i += left_multi[j] * bell_n[j];
        }

        bell_n.push(bell_i);
    }

    bell_n[n]
}

#[allow(dead_code)]
pub fn main() -> () {
    let x = 15;

    println!("bell_number({:?}) = {:?}", x, bell_number(x));
}
