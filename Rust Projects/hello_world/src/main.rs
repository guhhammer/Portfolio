
// commenting 

/* block 

*/

#[allow(dead_code)]
fn adder(x: i32, y: i32) -> i32{
    x + y
}

#[allow(unused_variables)]
#[allow(unused_assignments)]
#[allow(dead_code)]
fn main() {

    let x: i32 = 1;
    

    let y: i32 = 2i32;

    let f: f64 = 3.2f64;

    let implicit_x = 1;
    let implicit_f = 3.5; // implicit infer.


    println!("{} {} {} {} {}", x, y, f, implicit_x, implicit_f);

    let sum = x + y + 44;

    let mut mutable = 1;
    mutable = 4;
    mutable += 2;

    let s: &str = "hey";
    
    //A `String` – a heap-allocated string
    // Stored as a `Vec<u8>` and always hold a valid UTF-8 sequence, not null terminated.
    let ss: String = "hey".to_string();

    // The string slice is like a view `&[u8]` into `Vec<T>.
    let ss_slice: &str = &ss;


    println!("adder is {}\n", ss_slice);

    
    
    let arr1: [i32; 4] = [2,3,4,5]; //fixed-size array.

    println!("{:?}", arr1); //debug-style print for array.


    let mut vector: Vec<i32> = vec![5,6,7,8];
    vector.push(9); // vectors as dynamic arrays

    let vslice = &vector;

    println!("v:{:?} vs:{:?}\n", vector, vslice);


    let t: (i32, &str, f64, Vec<i32>) = (1, "hh", 3.14, vec![1,2]); // tuple.

    let (_a, _b, _c, _d) = t; //destructuring let.

    println!("{:?}", t.1);


    struct Point {

        x: i32,
        y: i32,

    } // structs.


    let origin: Point = Point { x: 0, y: 0 };


    struct Point2(i32, i32); // A tuple struct, a struct with unnamed fields.

    let origin2 = Point2(0, 0);


    enum Direction {
        Up,
        Left,
        Down,
        Right,
    } // Basic c-like enum.

    let up = Direction::Up;


    enum OptionalI32 {
        Ani32(i32),
        Nothing,
    } // enum with fields.

    let two: OptionalI32 = OptionalI32::Ani32(2);
    let nothing: OptionalI32 = OptionalI32::Nothing;

    

    // Generics //

    struct Foo<T> { bar : T }


    enum Optional<T> {
        Someval(T),
        Noval,
    } // This is defined in the standard library as 'Option'.

    // Methods //    

    impl<T> Foo<T> {

        // Methods take an explicit parameter called &self.

        fn bar(&self) -> &T {
            &self.bar // self is borrowed.
        }

        fn bar_mut(&mut self) -> &mut T { 
            &mut self.bar // self is mutably borrowed.
        }

        fn into_bar(self) -> T {
            self.bar // here self is consumed.
        }

    }

    let a_foo = Foo {bar: 1};

    println!("foo bar: {}", a_foo.bar());



    // Traits (known as interfaces or typeclasses in other languages) //
    
    trait Frobnicate<T> {
        fn frobnicate(self) -> Option<T>;
    }

    impl<T> Frobnicate<T> for Foo<T> {
        fn frobnicate(self) -> Option<T> {
            Some(self.bar)
        }
    }

    let another_foo = Foo {bar : 2};
    println!("{:?}", another_foo.frobnicate());
    

    

    // Function Pointer types: //

    fn fibonacci(n: u32) -> u32 {
        match n {
            0 => 1,
            1 => 1,
            _ => fibonacci(n - 1) + fibonacci(n - 2),
        }
    }

    type FunctionPointer = fn(u32) -> u32;

    let fib: FunctionPointer = fibonacci;

    println!("Fib({}): {}", 4, fib(4));



    // Control flow.

    let arr = [1,2,4,5];
    for i in arr {
        println!("{}", i);
    }

    for i in 0u32..10 {
        print!("{} ", i);
    }
    println!("");


    if 1 == 1 {
        println!("Maths is working!");
    } else {
        println!("Oh no...");
    }

    // `if` as expression
    let value = if true {
        "good"
    } else {
        "bad"
    };

    println!("{}", value);

    // `while` loop
    while 1 == 1 {
        println!("The universe is operating normally.");
        // break statement gets out of the while loop.
        //  It avoids useless iterations.
        break
    }

    let mut count: u32 = 0;
    loop {  
        
        if count > 10 {
            break
        } else {
            println!("count {}", count);
            count += 1;
        }


    }

     let mut ss = 0;
     let sss = ss;

    ss = 2;
    
    println!("{ss:?}");
    print!("ss:{}| sss:{}", ss, sss);



}
