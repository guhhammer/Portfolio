// cargo run -q --bin <<project-name>> for no flags on output.

#[warn(dead_code)]
fn sub(a: i32, b: i32) -> i32 {
    a - b
}

#[warn(dead_code)]
enum Color {
    Red,
    Yellow,
    Blue,
}

fn get_color(c: Color) -> () {
    let ans: &str = match c {
        Color::Red => "red",
        Color::Yellow => "yellow",
        Color::Blue => "blue",
    };

    println!("{}", ans);
}

pub fn main() {
    let _x = sub(2, 1);

    println!("Hello, world!");

    get_color(Color::Red);
    get_color(Color::Yellow);
    get_color(Color::Blue);
}
