use std::fmt::Debug;

use super::deque::{Deque, DequeIterator};

#[allow(dead_code)]
#[derive(Debug, Clone)]
pub struct Queue<T> {
    items: Deque<T>,
}

impl<T> Queue<T> {
    #[allow(dead_code)]
    pub fn new() -> Self {
        Queue {
            items: Deque::new_with_flexibility(),
        }
    }

    #[allow(dead_code)]
    pub fn new_with_capacity(capacity: usize) -> Self {
        Queue {
            items: Deque::new_with_capacity(capacity),
        }
    }

    #[allow(dead_code)]
    pub fn set_capacity(&mut self, capacity: usize) -> () {
        self.items.with_capacity(capacity)
    }

    #[allow(dead_code)]
    pub fn get_capacity(&mut self) -> usize {
        self.items.get_capacity()
    }

    #[allow(dead_code)]
    pub fn set_flexible(&mut self, flexibility: bool) -> () {
        self.items.set_flexible(flexibility)
    }

    #[allow(dead_code)]
    pub fn get_flexibility(&mut self) -> bool {
        self.items.get_flexibility()
    }

    #[allow(dead_code)]
    pub fn show(&mut self) -> String
    where
        T: std::fmt::Display,
    {
        self.items.show()
    }

    #[allow(dead_code)]
    pub fn enqueue(&mut self, item: T) -> () {
        self.items.enqueue(item)
    }

    #[allow(dead_code)]
    pub fn dequeue(&mut self) -> Option<T> {
        self.items.dequeue()
    }

    #[allow(dead_code)]
    pub fn peek(&mut self) -> Option<&T> {
        self.items.peek_front()
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
        self.items.contains(&x)
    }

    #[allow(dead_code)]
    pub fn iter(&mut self) -> DequeIterator<T> {
        self.items.iter()
    }

    #[allow(dead_code)]
    pub fn front(&mut self) -> Option<&T> {
        self.peek()
    }

    #[allow(dead_code)]
    pub fn back(&mut self) -> Option<&T> {
        self.items.peek_back()
    }

    #[allow(dead_code)]
    pub fn reserve(&mut self, additional: usize) -> () {
        self.items.reserve(additional)
    }

    #[allow(dead_code)]
    pub fn resize(&mut self, capacity: usize) -> () {
        self.items.resize(capacity)
    }

    #[allow(dead_code)]
    pub fn shrink_to_fit(&mut self) -> () {
        self.items.shrink_to_fit()
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
    pub fn append(&mut self, q: &mut Queue<T>) -> ()
    where
        T: Copy,
    {
        let additional: usize = q.len();

        self.reserve(additional);

        for i in q.clone().iter() {
            self.enqueue(*i);
        }
    }

    #[allow(dead_code)]
    pub fn split_off(&mut self, at: usize) -> Queue<T> {
        if at >= self.items.len() {
            return Queue::new();
        }

        let ret_cap: usize = self.len() - at;

        let mut ret_q: Queue<T> = Queue::new_with_capacity(ret_cap);

        let ret_deque: Deque<T> = self.items.split_off(at);

        ret_q.items = ret_deque;

        ret_q
    }
}

#[allow(dead_code)]
fn queue_tester() -> () {
    let mut q: Queue<i32> = Queue::new();
    println!(
        "|-- Queue tester>\n|\n|---- q: Queue<i32>::new() -> {:?}\n|",
        q
    );

    let q_with: Queue<i32> = Queue::new_with_capacity(5);
    println!(
        "|---- q_with: Queue<i32>::new_with_capacity(5) -> {:?}\n|",
        q_with
    );

    q.set_capacity(5);
    println!(
        "|---- q.set_capacity(5) & q.get_capacity() -> {:?}\n|",
        q.get_capacity()
    );
    println!("|---- q.show() -> {:?}\n|", q.show());

    q.set_flexible(true);
    println!(
        "|---- q.set_flexible() & q.get_flexibility -> {:?} (setting to false after)\n|",
        q.get_flexibility()
    );
    q.set_flexible(false);

    q.enqueue(4);
    q.enqueue(5);
    q.enqueue(6);
    q.enqueue(7);

    println!(
        "|---- enqueueing 4,5,6,7 to q (q.push(x: T)) -> {:?}\n|",
        q.show()
    );
    println!("|---- q.dequeue() -> {:?}\n|", q.dequeue());
    println!("|---- q.peek() -> {:?}\n|", q.peek());
    println!("|---- q.is_empty() -> {:?}\n|", q.is_empty());
    println!("|---- q.len() -> {:?}\n|", q.len());

    let mut q_clear = q.clone();
    q_clear.clear();

    println!("|---- q.clone().clear() -> {:?}\n|", q_clear.show());

    let mut q_vec: Queue<i32> = Queue::new();
    q_vec.from_vec(vec![1, 2, 7, 9]);

    println!(
        "|---- q_vec: Queue::new() & q_vec.from_vec(vec![1,2,7,9]) -> {:?}\n|",
        q_vec.show()
    );
    println!("|---- q.contains(&5) -> {:?}\n|", q.contains(&5));
    println!("|---- q.front() -> {:?}\n|", q.front());
    println!("|---- q.back() -> {:?}\n|", q.back());

    print!("|---- itering through q and printing (q.iter()) -> ");
    for i in q.iter() {
        print!("{:?} ", i)
    }
    print!("\n|\n");

    let old_cap = q.get_capacity();

    q.reserve(8);
    println!(
        "|---- q.reserve(8) & q.get_capacity() -> {:?} (old_cap:{:?})\n|",
        q.get_capacity(),
        old_cap
    );

    q.resize(3);
    println!(
        "|---- q.resize(3) & q.get_capacity() -> {:?} (old_cap:{:?})\n|",
        q.get_capacity(),
        old_cap
    );
    println!("|---- q.to_vec() -> {:?}\n|", q.to_vec());

    q.append(&mut q_vec);
    println!("|---- q.append(&mut q_vec) -> {:?}\n|", q.show());

    q.shrink_to_fit();
    println!(
        "|---- q.shrink_to_fit() & q.get_capacity() -> {:?}\n|",
        q.get_capacity()
    );

    let mut q_clone: Queue<i32> = q.clone();
    let mut split_q_clone: Queue<i32> = q_clone.split_off(2);
    println!("|---- q.clone().split_off(2) (signing to split_q_clone) -> {:?}(q_clone) {:?}(split_q_clone)\n|", q_clone.show(), split_q_clone.show());

    print!("|---- printing from q.drain() -> ");
    for i in q.drain() {
        print!("{:?} ", i);
    }
    println!("\n|\n|---- q after q.drain() -> {:?}\n|", q.show());
}

#[allow(dead_code)]
pub fn main() -> () {
    queue_tester();
}
