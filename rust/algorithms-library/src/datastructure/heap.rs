use std::fmt::Display;

#[allow(dead_code)]
#[derive(Debug, Clone)]
pub struct Heap<T> {
    content: Vec<T>,
}

impl<T: PartialOrd<T> + Clone + ToString + Display> Heap<T> {
    #[allow(dead_code)]
    pub fn new() -> Self {
        Heap {
            content: Vec::new(),
        }
    }

    #[allow(dead_code)]
    fn heapify_up(&mut self, index: usize) -> () {
        if index == 0 {
            return;
        }

        let parent = (index - 1) / 2;

        if self.content[index] > self.content[parent] {
            self.content.swap(index, parent);

            self.heapify_up(parent);
        }
    }

    #[allow(dead_code)]
    fn heapify_down(&mut self, index: usize) -> () {
        let left_child = 2 * index + 1;
        let right_child = 2 * index + 2;

        let mut largest = index;

        if left_child < self.content.len() && self.content[left_child] > self.content[largest] {
            largest = left_child;
        }

        if right_child < self.content.len() && self.content[right_child] > self.content[largest] {
            largest = left_child;
        }

        if largest != index {
            self.content.swap(index, largest);

            self.heapify_down(largest);
        }
    }

    #[allow(dead_code)]
    pub fn build_heap(&mut self, elems: &mut Vec<T>) -> () {
        self.content.append(elems);

        for i in (0..self.content.len() / 2).rev() {
            self.heapify_down(i);
        }
    }

    #[allow(dead_code)]
    pub fn show(&mut self) -> String {
        let mut ret = String::from("heap [ ");

        for i in &self.content {
            ret.push_str(&format!("{} ", i.to_string()));
        }

        ret.push_str("]");

        ret
    }

    #[allow(dead_code)]
    pub fn insert(&mut self, elem: T) -> ()
    where
        T: std::fmt::Display,
    {
        self.content.push(elem);

        let i = self.content.len() - 1;

        self.heapify_up(i);
    }

    #[allow(dead_code)]
    pub fn remove(&mut self, index: usize) -> T {
        let last = self.content.len() - 1;

        self.content.swap(index, last);

        let ret = self.content.pop().unwrap();

        self.heapify_down(index);

        ret
    }

    #[allow(dead_code)]
    pub fn peek(&mut self) -> Option<&T> {
        self.content.get(0)
    }
}

#[allow(dead_code)]
pub fn main() -> () {
    let mut heap = Heap::new();

    let mut elements = vec![3, 2, 8, 5, 1];
    heap.build_heap(&mut elements);
    println!("Heap after build: {}", heap.show());

    heap.insert(10);
    println!("Heap after inserting 10: {}", heap.show());

    heap.insert(7);
    println!("Heap after inserting 7: {}", heap.show());

    if let Some(max_value) = heap.peek() {
        println!("Max value (peek): {}", max_value);
    } else {
        println!("Heap is empty");
    }

    let removed_value = heap.remove(0);
    println!("Removed max value: {}", removed_value);
    println!("Heap after removing max: {}", heap.show());

    heap.insert(4);
    println!("Heap after inserting 4: {}", heap.show());

    heap.insert(6);
    println!("Heap after inserting 6: {}", heap.show());

    if let Some(max_value) = heap.peek() {
        println!("Max value (peek): {}", max_value);
    } else {
        println!("Heap is empty");
    }

    while let Some(_) = heap.peek() {
        let removed_value = heap.remove(0);
        println!("Removed value: {}", removed_value);
        println!("Heap after removal: {}", heap.show());
    }
}
