#[allow(dead_code)]
pub struct MainWrapper;

impl MainWrapper {
    #[allow(dead_code)]
    pub fn call<F, T>(func: F, file_name: &str, execute: bool) -> ()
    where
        F: Fn() -> T,
    {
        if execute {
            println!("\n{:?}.rs>\n", file_name);

            func();

            println!("\n-----\n")
        }
    }
}
