fn max_branchless(x: i8, y: i8) -> i8 {
    let diff = x ^ y;

    let mask = (x < y) as i8;
    
    println!("diff: {diff:?}, mask: {mask:?}");

    x ^ (diff & (0i8.wrapping_sub(mask)))
}

fn main() {

    let x: i8 = 7;
    let y: i8 = -4;

    let z = max_branchless(x, y);

    println!("z: {z:?}");

}
