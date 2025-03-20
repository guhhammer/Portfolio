use std::collections::VecDeque;

#[allow(dead_code)]
#[derive(Debug, Clone)]
pub struct Deque<T> {
    items: VecDeque<T>,
    capacity: usize,
    flexible_size: bool,
}

impl<T> Deque<T> {
    #[allow(dead_code)]
    pub fn new() -> Self {
        Deque {
            items: VecDeque::new(),
            capacity: 0,
            flexible_size: false,
        }
    }

    #[allow(dead_code)]
    pub fn new_with_capacity(capacity: usize) -> Self {
        Deque {
            items: VecDeque::new(),
            capacity: capacity,
            flexible_size: false,
        }
    }

    #[allow(dead_code)]
    pub fn new_with_flexibility() -> Self {
        Deque {
            items: VecDeque::new(),
            capacity: 0,
            flexible_size: true,
        }
    }

    #[allow(dead_code)]
    pub fn with_capacity(&mut self, capacity: usize) -> () {
        self.capacity = capacity;
    }

    #[allow(dead_code)]
    pub fn get_capacity(&mut self) -> usize {
        self.capacity
    }

    #[allow(dead_code)]
    pub fn show(&mut self) -> String
    where
        T: std::fmt::Display,
    {
        let mut ret = "[ ".to_owned();

        for item in self.items.iter() {
            ret.push_str(&item.to_string());
            ret.push(' ');
        }

        ret.push(']');

        ret
    }

    #[allow(dead_code)]
    pub fn set_flexible(&mut self, flexible: bool) -> () {
        self.flexible_size = flexible;
    }

    #[allow(dead_code)]
    pub fn get_flexibility(&mut self) -> bool {
        self.flexible_size
    }

    #[allow(dead_code)]
    pub fn enqueue_front(&mut self, item: T) -> () {
        if self.items.len() > self.capacity - 1 && !self.flexible_size {
            return;
        }

        self.items.push_front(item)
    }

    #[allow(dead_code)]
    pub fn enqueue(&mut self, item: T) -> () {
        if self.items.len() > self.capacity - 1 && !self.flexible_size {
            return;
        }

        self.items.push_back(item);
    }

    #[allow(dead_code)]
    pub fn dequeue(&mut self) -> Option<T> {
        self.items.pop_front()
    }

    #[allow(dead_code)]
    pub fn pop(&mut self) -> Option<T> {
        self.items.pop_back()
    }

    #[allow(dead_code)]
    pub fn peek_front(&mut self) -> Option<&T> {
        self.items.front()
    }

    #[allow(dead_code)]
    pub fn peek_back(&mut self) -> Option<&T> {
        self.items.back()
    }

    #[allow(dead_code)]
    pub fn clear(&mut self) -> () {
        self.items.clear()
    }

    #[allow(dead_code)]
    pub fn len(&mut self) -> usize {
        self.items.len()
    }

    #[allow(dead_code)]
    pub fn is_empty(&mut self) -> bool {
        self.items.is_empty()
    }

    #[allow(dead_code)]
    pub fn reserve(&mut self, additional: usize) -> () {
        self.capacity += additional;
    }

    #[allow(dead_code)]
    pub fn shrink_to_fit(&mut self) -> () {
        if self.capacity > self.items.len() as usize || self.flexible_size {
            self.capacity = self.items.len() as usize
        }
    }

    #[allow(dead_code)]
    pub fn drain(&mut self) -> std::collections::vec_deque::Drain<T> {
        let r: std::ops::Range<usize> = 0..self.len();
        self.items.drain(r)
    }

    #[allow(dead_code)]
    pub fn iter(&self) -> DequeIterator<T> {
        DequeIterator {
            deque: self,
            index: 0,
        }
    }

    #[allow(dead_code)]
    pub fn contains(&self, x: &T) -> bool
    where
        T: PartialEq<T>,
    {
        self.items.contains(x)
    }

    #[allow(dead_code)]
    pub fn swap_remove_front(&mut self, index: usize) -> Option<T> {
        self.items.remove(index)
    }

    #[allow(dead_code)]
    pub fn swap_remove_back(&mut self, index: usize) -> Option<T> {
        if index > self.items.len() - 1 {
            return None;
        }

        let rev_index = (self.items.len() - 1) - index;

        self.items.remove(rev_index)
    }

    #[allow(dead_code)]
    pub fn split_off(&mut self, at: usize) -> Deque<T> {
        if at >= self.items.len() {
            return Deque::new();
        }

        let ret = self.items.split_off(at);
        let cap = ret.len();

        Deque {
            items: ret,
            capacity: cap,
            flexible_size: false,
        }
    }

    #[allow(dead_code)]
    pub fn resize(&mut self, capacity: usize) -> () {
        if capacity == 0 {
            self.items.clear();
        } else if capacity >= self.get_capacity() {
            let r = capacity - self.get_capacity();

            self.reserve(r);
        } else {
            for _ in 0..(self.len() - capacity) {
                self.dequeue();
            }

            self.shrink_to_fit()
        }
    }

    #[allow(dead_code)]
    pub fn from_vec(&mut self, v: Vec<T>) -> ()
    where
        T: Copy,
    {
        self.capacity = v.len();

        for item in v.iter() {
            self.enqueue(*item)
        }
    }

    #[allow(dead_code)]
    pub fn to_vec(&mut self) -> Vec<T>
    where
        T: Copy,
    {
        let mut ret: Vec<T> = Vec::new();

        for i in self.iter() {
            ret.push(*i);
        }

        ret
    }
}

#[allow(dead_code)]
pub struct DequeIterator<'a, T> {
    deque: &'a Deque<T>,
    index: usize,
}

impl<'a, T> Iterator for DequeIterator<'a, T> {
    type Item = &'a T;

    fn next(&mut self) -> Option<Self::Item> {
        if self.index < self.deque.items.len() {
            let result = &self.deque.items[self.index];
            self.index += 1;
            Some(result)
        } else {
            None
        }
    }
}

impl<'a, T> IntoIterator for &'a Deque<T> {
    type Item = &'a T;

    type IntoIter = DequeIterator<'a, T>;

    fn into_iter(self) -> Self::IntoIter {
        self.iter()
    }
}

#[allow(dead_code)]
fn deque_tester() -> () {
    let mut d: Deque<i32> = Deque::new();
    println!(
        "|-- Deque tester>\n|\n|---- d: Deque<i32>::new() -> {:?}\n|",
        d
    );

    let d_capacity: Deque<i32> = Deque::new_with_capacity(7);
    println!(
        "|---- d_capacity: Deque<i32>::new_with_capacity(7) -> {:?}\n|",
        d_capacity
    );

    let d_flex: Deque<i32> = Deque::new_with_flexibility();
    println!(
        "|---- d_flex: Deque<i32>::new_with_flexibility() -> {:?}\n|",
        d_flex
    );

    d.with_capacity(5);
    println!(
        "|---- d.with_capacity(5) & d.get_capacity() -> {:?}\n|",
        d.get_capacity()
    );
    println!("|---- d.show() -> {:?}\n|", d.show());

    d.set_flexible(true);
    println!(
        "|---- d.set_flexible() & d.get_flexibility -> {:?} (setting to false after)\n|",
        d.get_flexibility()
    );
    d.set_flexible(false);

    d.enqueue(4);
    d.enqueue(5);
    d.enqueue(6);
    d.enqueue(7);

    println!(
        "|---- enqueueing 4,5,6,7 to d (d.enqueue(x: T)) -> {:?}\n|",
        d.show()
    );

    d.enqueue_front(8);
    println!(
        "|---- enqueueing 8 to front of d (d.enqueue_front(x: T)) -> {:?}\n|",
        d.show()
    );
    println!("|---- d.dequeue() -> {:?}\n|", d.dequeue());
    println!("|---- d.pop() -> {:?}\n|", d.pop());
    println!("|---- d.peek_front() -> {:?}\n|", d.peek_front());
    println!("|---- d.peek_back() -> {:?}\n|", d.peek_back());

    let mut d_clear = d.clone();
    d_clear.clear();
    println!("|---- d.clone().clear() -> {:?}\n|", d_clear.show());
    println!("|---- d.len() -> {:?}\n|", d.len());
    println!("|---- d.is_empty() -> {:?}\n|", d.is_empty());
    println!("|---- d.contains(&5) -> {:?}\n|", d.contains(&5));

    let mut d_vec: Deque<i32> = Deque::new();
    d_vec.from_vec(vec![1, 2, 7, 9]);
    println!(
        "|---- d_vec: Deque::new() & d_vec.from_vec(vec![1,2,7,9]) -> {:?}\n|",
        d_vec.show()
    );
    println!(
        "|---- d.swap_remove_front(1) -> {:?}\n|",
        d.swap_remove_front(1)
    );
    println!(
        "|---- d.swap_remove_back(0) -> {:?}\n|",
        d.swap_remove_back(0)
    );

    let old_cap = d.get_capacity();
    d.reserve(8);
    println!(
        "|---- d.reserve(8) & d.get_capacity() -> {:?} (old_cap:{:?})\n|",
        d.get_capacity(),
        old_cap
    );

    d.shrink_to_fit();
    println!(
        "|---- d.shrink_to_fit() & d.get_capacity() -> {:?}\n|",
        d.get_capacity()
    );

    d.resize(3);
    println!(
        "|---- d.resize(3) & d.get_capacity() -> {:?} (old_cap:{:?})\n|",
        d.get_capacity(),
        old_cap
    );
    println!("|---- d.to_vec() -> {:?}\n|", d.to_vec());

    d.enqueue(11);
    d.enqueue(15);
    d.enqueue(16);
    println!(
        "|---- enqueueing 11,15,16 to d (d.enqueue(x: T)) -> {:?}\n|",
        d.show()
    );

    print!("|---- itering through d and printing (d.iter()) -> ");
    for i in d.iter() {
        print!("{:?} ", i)
    }
    print!("\n|\n");

    let mut d_clone: Deque<i32> = d.clone();
    let mut split_d_clone: Deque<i32> = d_clone.split_off(2);
    println!("|---- d.clone().split_off(2) (signing to split_d_clone) -> {:?}(d_clone) {:?}(split_d_clone)\n|", d_clone.show(), split_d_clone.show());

    print!("|---- printing from d.drain() -> ");
    for i in d.drain() {
        print!("{:?} ", i);
    }
    println!("\n|\n|---- d after d.drain() -> {:?}\n|", d.show());
}

#[allow(dead_code)]
pub fn main() -> () {
    deque_tester();
}
