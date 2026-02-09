use super::deque::{Deque, DequeIterator};

#[allow(dead_code)]
#[derive(Debug, Clone)]
pub struct Stack<T> {
    items: Deque<T>,
}

impl<T> Stack<T> {
    #[allow(dead_code)]
    pub fn new() -> Self {
        Stack {
            items: Deque::new(),
        }
    }

    #[allow(dead_code)]
    pub fn set_capacity(&mut self, capacity: usize) -> () {
        self.items.with_capacity(capacity);
    }

    #[allow(dead_code)]
    pub fn get_capacity(&mut self) -> usize {
        self.items.get_capacity()
    }

    #[allow(dead_code)]
    pub fn show(&mut self) -> String
    where
        T: std::fmt::Display,
    {
        self.items.show()
    }

    #[allow(dead_code)]
    pub fn push(&mut self, item: T) -> () {
        self.items.enqueue(item)
    }

    #[allow(dead_code)]
    pub fn pop(&mut self) -> Option<T> {
        self.items.pop()
    }

    #[allow(dead_code)]
    pub fn peek(&mut self) -> Option<&T> {
        self.items.peek_back()
    }

    #[allow(dead_code)]
    pub fn is_empty(&mut self) -> bool {
        self.items.is_empty()
    }

    #[allow(dead_code)]
    pub fn len(&mut self) -> usize {
        self.items.len()
    }

    #[allow(dead_code)]
    pub fn clear(&mut self) -> () {
        self.items.clear()
    }

    #[allow(dead_code)]
    pub fn contains(&mut self, x: &T) -> bool
    where
        T: PartialEq<T>,
    {
        self.items.contains(x)
    }

    #[allow(dead_code)]
    pub fn iter(&mut self) -> DequeIterator<T> {
        self.items.iter()
    }

    #[allow(dead_code)]
    pub fn swap(&mut self) -> () {
        if self.len() < 2 {
            return;
        }

        let z: T = self.pop().unwrap();
        let y: T = self.pop().unwrap();

        self.push(z);
        self.push(y);
    }

    #[allow(dead_code)]
    pub fn duplicate(&mut self) -> ()
    where
        T: Copy,
    {
        if let Some(&top) = self.peek() {
            self.push(top)
        }
    }

    #[allow(dead_code)]
    fn rotate(&mut self, side: bool) -> () {
        if side {
            if let Some(top) = self.pop() {
                self.items.enqueue_front(top)
            }
        } else {
            if let Some(bottom) = self.items.dequeue() {
                self.items.enqueue(bottom);
            }
        }
    }

    #[allow(dead_code)]
    pub fn rotate_left(&mut self) -> () {
        self.rotate(true)
    }

    #[allow(dead_code)]
    pub fn rotate_right(&mut self) -> () {
        self.rotate(false)
    }

    #[allow(dead_code)]
    pub fn from_vec(&mut self, v: Vec<T>) -> ()
    where
        T: Copy,
    {
        self.items.from_vec(v)
    }

    #[allow(dead_code)]
    pub fn to_vec(&mut self) -> Vec<T>
    where
        T: Copy,
    {
        self.items.to_vec()
    }

    #[allow(dead_code)]
    pub fn drain(&mut self) -> std::collections::vec_deque::Drain<T>
    where
        T: Clone,
    {
        self.items.drain()
    }

    #[allow(dead_code)]
    pub fn append(&mut self, q: &mut Stack<T>) -> ()
    where
        T: Copy,
    {
        let additional: usize = q.len();

        self.items.reserve(additional);

        for i in q.clone().iter() {
            self.push(*i);
        }
    }

    #[allow(dead_code)]
    pub fn shrink_to_fit(&mut self) -> () {
        self.items.shrink_to_fit()
    }

    #[allow(dead_code)]
    pub fn resize(&mut self, capacity: usize) -> () {
        self.items.resize(capacity)
    }
}

#[allow(dead_code)]
fn stack_tester() -> () {
    let mut s: Stack<i32> = Stack::new();
    println!(
        "|-- Stack tester>\n|\n|---- s: Stack<i32>::new() -> {:?}\n|",
        s
    );

    s.set_capacity(5);
    println!(
        "|---- s.set_capacity(5) & s.get_capacity() -> {:?}\n|",
        s.get_capacity()
    );
    println!("|---- s.show() -> {:?}\n|", s.show());

    s.push(4);
    s.push(5);
    s.push(6);
    s.push(7);

    println!(
        "|---- pushing 4,5,6,7 to s (s.push(x: T)) -> {:?}\n|",
        s.show()
    );
    println!("|---- s.pop() -> {:?}\n|", s.pop());
    println!("|---- s.peek() -> {:?}\n|", s.peek());
    println!("|---- s.is_empty() -> {:?}\n|", s.is_empty());
    println!("|---- s.len() -> {:?}\n|", s.len());
    println!("|---- s.contains(&5) -> {:?}\n|", s.contains(&5));

    print!("|---- itering through s and printing (s.iter()) -> ");
    for i in s.iter() {
        print!("{:?} ", i)
    }
    print!("\n|\n");

    s.swap();
    println!("|---- s.swap() -> {:?}\n|", s.show());

    s.duplicate();
    println!("|---- s.duplicate() -> {:?}\n|", s.show());

    let mut s_clear = s.clone();
    s_clear.clear();
    println!("|---- s.clone().clear() -> {:?}\n|", s_clear.show());

    s.rotate_left();
    println!("|---- s.rotate_left() -> {:?}\n|", s.show());

    s.rotate_right();
    println!("|---- s.rotate_right() -> {:?}\n|", s.show());

    let mut s_vec: Stack<i32> = Stack::new();
    s_vec.from_vec(vec![1, 2, 7, 9]);
    println!(
        "|---- s_vec: Stack::new() & s_vec.from_vec(vec![1,2,7,9]) -> {:?}\n|",
        s_vec.show()
    );
    println!("|---- s.to_vec() -> {:?}\n|", s.to_vec());

    let old_cap = s.get_capacity();
    s.resize(8);
    println!(
        "|---- s.resize(8) & s.get_capacity() -> {:?} (old_cap:{:?})\n|",
        s.get_capacity(),
        old_cap
    );

    s.shrink_to_fit();
    println!(
        "|---- s.shrink_to_fit() & s.get_capacity() -> {:?}\n|",
        s.get_capacity()
    );

    s.append(&mut s_vec);
    println!("|---- s.append(&mut s_vec) -> {:?}\n|", s.show());

    print!("|---- printing from s.drain() -> ");
    for i in s.drain() {
        print!("{:?} ", i);
    }
    println!("\n|\n|---- s after s.drain() -> {:?}\n|", s.show());
}

#[allow(dead_code)]
pub fn main() -> () {
    stack_tester();
}
