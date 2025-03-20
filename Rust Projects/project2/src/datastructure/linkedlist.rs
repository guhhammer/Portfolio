use crate::{datastructure::node::Node, sorting::mergesort};

#[derive(Debug, Clone)]
pub struct Linkedlist<T> {
    pub head: Option<Box<Node<T>>>,
    pub size: usize,
}

impl<T> Linkedlist<T> {
    #[allow(dead_code)]
    pub fn new() -> Self {
        Linkedlist {
            head: None,
            size: 0,
        }
    }

    #[allow(dead_code)]
    pub fn new_with_singlenode(n: Node<T>) -> Self {
        Linkedlist {
            head: Some(Box::new(n)),
            size: 1,
        }
    }

    #[allow(dead_code)]
    pub fn new_with_nodearray(arr: &[Node<T>]) -> Self
    where
        T: Copy,
    {
        let mut l: Linkedlist<T> = Linkedlist::new();

        for n in arr {
            l.append(n.clone())
        }

        l
    }

    #[allow(dead_code)]
    pub fn show(&mut self) -> String
    where
        T: std::fmt::Display + Copy,
    {
        let mut curr = &mut self.head;

        let mut list: Vec<T> = Vec::new();

        while let Some(ref mut node) = curr {
            list.push(node.content);

            curr = &mut node.pointer;
        }

        "Linkedlist [ ".to_owned()
            + &list
                .iter()
                .map(|&content| content.to_string())
                .collect::<Vec<String>>()
                .join(" -> ")
                .to_owned()
            + &format!("; size: {}])", self.size)
    }

    #[allow(dead_code)]
    pub fn is_empty(&self) -> bool {
        self.head.is_none()
    }

    #[allow(dead_code)]
    pub fn clear(&mut self) -> () {
        self.head = None;
        self.size = 0;
    }

    #[allow(dead_code)]
    pub fn append(&mut self, n: Node<T>) -> () {
        let mut curr = &mut self.head;

        while let Some(ref mut next) = curr {
            curr = &mut next.pointer;
        }

        self.size += 1;
        *curr = Some(Box::new(n));
    }

    #[allow(dead_code)]
    pub fn insert_head(&mut self, n: Node<T>) -> ()
    where
        T: Copy,
    {
        if self.is_empty() {
            self.append(n);
            return;
        }

        let mut new_head = n;

        let head = *self.head.as_ref().unwrap().clone();

        new_head.pointer = Some(Box::new(head));

        self.head = Some(Box::new(new_head));
        self.size += 1;
    }

    #[allow(dead_code)]
    pub fn insert_last(&mut self, n: Node<T>) -> () {
        self.append(n);
    }

    #[allow(dead_code)]
    pub fn remove(&mut self) -> () {
        if !self.head.is_none() {
            let mut curr = &mut self.head;

            while let Some(ref mut node) = curr {
                if node.pointer.is_none() || node.pointer.as_ref().unwrap().pointer.is_none() {
                    node.pointer = None;
                    self.size -= 1;
                    return;
                }

                curr = &mut node.pointer;
            }
        }
    }

    #[allow(dead_code)]
    pub fn remove_head(&mut self) -> () {
        if self.is_empty() {
            return;
        }

        if let Some(old_head) = self.head.take() {
            self.head = old_head.pointer;
            self.size -= 1;
        }
    }

    #[allow(dead_code)]
    pub fn remove_node(&mut self, node_content: T) -> ()
    where
        T: PartialEq<T>,
    {
        if self.is_empty() {
            return;
        }

        let mut curr = &mut self.head;

        if let Some(ref mut head_node) = curr {
            if head_node.content == node_content {
                self.remove_head();
                return;
            }
        }

        while let Some(ref mut node) = curr {
            if let Some(ref mut next_node) = node.pointer {
                if next_node.content == node_content {
                    node.pointer = next_node.pointer.take();
                    self.size -= 1;
                    return;
                }
            }

            curr = &mut node.pointer;
        }
    }

    #[allow(dead_code)]
    pub fn get_size(&self) -> usize {
        self.size
    }

    #[allow(dead_code)]
    pub fn find(&mut self, content: T) -> Option<usize>
    where
        T: PartialEq<T>,
    {
        let mut curr = &mut self.head;
        let mut pos: usize = 0;

        while let Some(ref mut i) = curr {
            if i.content == content {
                return Some(pos);
            }

            pos += 1;

            curr = &mut i.pointer;
        }

        None
    }

    #[allow(dead_code)]
    pub fn get_node(&mut self, index: usize) -> Option<&Node<T>> {
        if index >= self.size {
            return None;
        }

        let mut curr = &mut self.head;
        let mut pos: usize = 0;

        while let Some(node) = curr {
            let i = &mut **node;

            if pos == index {
                return Some(i);
            }

            curr = &mut i.pointer;
            pos += 1;
        }

        None
    }

    #[allow(dead_code)]
    pub fn has(&mut self, content: T) -> bool
    where
        T: PartialEq<T>,
    {
        if !self.is_empty() {
            let mut curr = &mut self.head;

            while let Some(ref mut node) = curr {
                if node.content == content {
                    return true;
                }

                curr = &mut node.pointer;
            }
        }

        false
    }

    #[allow(dead_code)]
    pub fn first_node(&self) -> Option<&Node<T>> {
        self.head.as_ref().map(|node_box| &**node_box)
    }

    #[allow(dead_code)]
    pub fn last_node(&mut self) -> Option<&Node<T>> {
        if self.is_empty() {
            return None;
        }

        let mut curr = &mut self.head;

        while let Some(ref mut node) = curr {
            if node.pointer.is_none() {
                return Some(node);
            }

            curr = &mut node.pointer;
        }

        None
    }

    #[allow(dead_code)]
    pub fn count(&mut self, occurrence: T) -> i32
    where
        T: PartialEq<T>,
    {
        if self.is_empty() {
            return 0;
        }

        let mut curr = &mut self.head;
        let mut counter: i32 = 0;

        while let Some(ref mut node) = curr {
            if node.content == occurrence {
                counter += 1;
            }

            curr = &mut node.pointer;
        }

        counter
    }

    #[allow(dead_code)]
    pub fn max_value(&mut self) -> Option<T>
    where
        T: PartialOrd<T> + Copy,
    {
        if self.is_empty() {
            return None;
        }

        let mut curr = &mut self.head;
        let mut max = curr.as_ref().unwrap().content;

        while let Some(ref mut node) = curr {
            if node.content > max {
                max = node.content;
            }

            curr = &mut node.pointer;
        }

        Some(max)
    }

    #[allow(dead_code)]
    pub fn min_value(&mut self) -> Option<T>
    where
        T: PartialOrd<T> + Copy,
    {
        if self.is_empty() {
            return None;
        }

        let mut curr = &mut self.head;
        let mut min = curr.as_ref().unwrap().content;

        while let Some(ref mut node) = curr {
            if node.content < min {
                min = node.content;
            }

            curr = &mut node.pointer;
        }

        Some(min)
    }

    #[allow(dead_code)]
    pub fn reverse(&mut self) -> ()
    where
        T: Clone + Copy,
    {
        if self.is_empty() {
            return;
        }

        let mut curr = &mut self.head;

        let mut list: Vec<T> = Vec::new();

        while let Some(ref mut node) = curr {
            list.push(node.content);

            curr = &mut node.pointer;
        }

        self.clear();

        for i in &list {
            self.insert_head(Node::new(*i));
        }
    }

    #[allow(dead_code)]
    pub fn copy(&mut self) -> Linkedlist<T>
    where
        T: Clone,
    {
        self.clone()
    }

    #[allow(dead_code)]
    pub fn concatenate(&mut self, linkedlist: &Linkedlist<T>) -> ()
    where
        T: Copy,
    {
        let mut curr = &linkedlist.head;

        while let Some(ref node) = curr {
            self.append(Node::new(node.content));

            curr = &node.pointer;
        }
    }

    #[allow(dead_code)]
    pub fn sort(&mut self) -> ()
    where
        T: std::cmp::Ord + Copy,
    {
        if self.is_empty() {
            return;
        }

        let mut curr = &mut self.head;

        let mut list: Vec<T> = Vec::new();

        while let Some(ref mut node) = curr {
            list.push(node.content);

            curr = &mut node.pointer;
        }

        self.clear();

        mergesort::mergesort(&mut list);

        for i in &list {
            self.append(Node::new(*i));
        }
    }

    #[allow(dead_code)]
    pub fn merge(&mut self, linkedlist: &Linkedlist<T>) -> ()
    where
        T: std::cmp::Ord + Copy,
    {
        self.concatenate(&linkedlist);

        self.sort();
    }

    #[allow(dead_code)]
    pub fn remove_at_index(&mut self, index: usize) -> () {
        if self.is_empty() || index >= self.size {
            return;
        }

        let mut curr = &mut self.head;
        let mut pos: usize = 0;

        while let Some(ref mut node) = curr {
            if let Some(ref mut next_node) = node.pointer {
                if pos + 1 == index {
                    node.pointer = next_node.pointer.take();
                    self.size -= 1;
                    return;
                }
            }

            pos += 1;

            curr = &mut node.pointer;
        }
    }

    #[allow(dead_code)]
    pub fn remove_by_value(&mut self, value: T) -> ()
    where
        T: PartialEq<T>,
    {
        self.remove_node(value);
    }

    #[allow(dead_code)]
    pub fn split(&mut self) -> [Option<Linkedlist<T>>; 2]
    where
        T: Copy,
    {
        if self.size < 2 {
            return [None, None];
        }

        let half = (if self.size % 2 == 0 {
            self.size
        } else {
            self.size + 1
        }) / 2;

        let mut curr = &mut self.head;
        let mut pos: usize = 0;

        let mut split_left = Linkedlist::new();
        let mut split_right = Linkedlist::new();

        while let Some(ref mut node) = curr {
            if pos < half {
                split_left.append(Node::new(node.content));
            } else {
                split_right.append(Node::new(node.content))
            }

            pos += 1;
            curr = &mut node.pointer;
        }

        [Some(split_left), Some(split_right)]
    }

    #[allow(dead_code)]
    pub fn split_parts(&mut self, parts: usize) -> Vec<Option<Linkedlist<T>>>
    where
        T: Clone + Copy,
    {
        if self.is_empty() {
            return Vec::new();
        }

        if self.size < parts || parts < 1 {
            return vec![Some(self.clone())];
        }

        let mut list = Vec::new();

        let mut curr = &mut self.head;

        let mut index: usize = 0;
        let mut container: usize = 0;

        let split_size = self.size / parts;

        let mut i_ll = Linkedlist::new();

        while let Some(ref mut node) = curr {
            i_ll.append(Node::new(node.content));

            if index % split_size == split_size - 1 && container < parts - 1 {
                list.push(Some(i_ll.clone()));

                i_ll.clear();

                container += 1;
            }

            index += 1;

            curr = &mut node.pointer;
        }

        if !i_ll.is_empty() {
            list.push(Some(i_ll));
        }

        list
    }

    #[allow(dead_code)]
    pub fn iter(&self) -> LinkedListIterator<T> {
        LinkedListIterator {
            current: self.head.as_deref(),
        }
    }

    #[allow(dead_code)]
    pub fn map<F>(&self, mut f: F) -> Linkedlist<T>
    where
        F: FnMut(&T) -> T,
        T: Copy,
    {
        let mut new_list: Linkedlist<T> = Linkedlist::new();

        for value in self.iter() {
            new_list.append(Node::new(f(value)));
        }

        new_list
    }

    #[allow(dead_code)]
    pub fn filter<F>(&self, mut f: F) -> Linkedlist<T>
    where
        F: FnMut(&T) -> bool,
        T: Copy,
    {
        let mut new_list: Linkedlist<T> = Linkedlist::new();

        for value in self.iter() {
            if f(value) {
                new_list.append(Node::new(*value));
            }
        }

        new_list
    }

    #[allow(dead_code)]
    pub fn reduce<F>(&self, mut f: F, initial: T) -> T
    where
        F: FnMut(T, &T) -> T,
    {
        let mut acc = initial;

        for value in self.iter() {
            acc = f(acc, value);
        }

        acc
    }
}

pub struct LinkedListIterator<'a, T> {
    current: Option<&'a Node<T>>,
}

impl<'a, T> Iterator for LinkedListIterator<'a, T> {
    type Item = &'a T;

    fn next(&mut self) -> Option<Self::Item> {
        self.current.map(|node| {
            self.current = node.pointer.as_deref();

            &node.content
        })
    }
}

#[allow(dead_code)]
pub fn main() {
    println!("\nlinkedlist.rs>\n");

    // ###################################

    let mut empty_ll: Linkedlist<i32> = Linkedlist::new();

    println!("Empty List created>\n|\n| {:?}\n", empty_ll.show());

    // ###################################

    let mut ll_singlenode = Linkedlist::new_with_singlenode(Node::new(50));

    println!("List with head created>\n|\n| {:?}\n", ll_singlenode.show());

    // ###################################

    let vec_nodes: Vec<Node<i32>> = (0..3).map(|x| Node::new(x)).collect();

    let mut vecnode_ll = Linkedlist::new_with_nodearray(&vec_nodes);

    println!(
        "List with node vec created>\n|\n| {:?}\n",
        vecnode_ll.show()
    );

    // ###################################

    println!(
        "Check if empty_ll is empty>\n|\n| {:?}\n",
        empty_ll.is_empty()
    );

    // ###################################

    println!(
        "Check if vecnode_ll is empty>\n|\n| {:?}\n",
        vecnode_ll.is_empty()
    );

    // ###################################

    let vec_clear: Vec<Node<i32>> = (0..2).map(|x| Node::new(x)).collect();

    let mut ll_clear = Linkedlist::new_with_nodearray(&vec_clear);

    print!(
        "List with node vec created>\n|\n| Is empty: {:?}\n",
        ll_clear.is_empty()
    );
    print!("|\n| List ll_clear now: {:?}\n", ll_clear.show());
    print!("|\n| Applying clear method...\n");

    ll_clear.clear();

    print!("|\n| Is empty now: {:?}\n", ll_clear.is_empty());
    println!("|\n| List ll_clear now: {:?}\n", ll_clear.show());

    // ###################################

    let mut ll_all = Linkedlist::new_with_nodearray(&vec_nodes);

    println!("List ll_all for testing following methods>");
    print!("|\n|--ll_all = {:?}\n", ll_all.show());

    print!("|\n|--Appending 10 to ll_all>\n");
    ll_all.append(Node::new(10));
    print!("|\n|--| {:?}\n", ll_all.show());

    print!("|\n|--Inserting 8 to head to ll_all>\n");
    ll_all.insert_head(Node::new(8));
    print!("|\n|--| {:?}\n", ll_all.show());

    print!("|\n|--Inserting 15 to end (last) to ll_all>\n");
    ll_all.insert_last(Node::new(15));
    print!("|\n|--| {:?}\n", ll_all.show());

    print!("|\n|--Removing last node from ll_all>\n");
    ll_all.remove();
    print!("|\n|--| {:?}\n", ll_all.show());

    print!("|\n|--Removing head node from ll_all>\n");
    ll_all.remove_head();
    print!("|\n|--| {:?}\n", ll_all.show());

    print!("|\n|--Removing node content:2 from ll_all>\n");
    ll_all.remove_node(2);
    print!("|\n|--| {:?}\n", ll_all.show());

    print!("|\n|--Getting size from ll_all>\n");
    print!("|\n|--| {:?}\n", ll_all.get_size());

    print!("|\n|--Finding node content:1 from ll_all>\n");
    print!("|\n|--| Index: {:?}\n", ll_all.find(1));

    print!("|\n|--Getting a ll_all node from index:2>\n");
    print!("|\n|--| Node: {:?}\n", ll_all.get_node(2));

    print!("|\n|--Seeing if ll_all has node with content:10>\n");
    print!("|\n|--| {:?}\n", ll_all.has(10));

    print!("|\n|--Getting first node from ll_all>\n");
    print!("|\n|--| {:?}\n", ll_all.first_node());

    print!("|\n|--Getting last node from ll_all>\n");
    print!("|\n|--| {:?}\n", ll_all.last_node());

    print!("|\n|--Counting occurrencies of 10 in ll_all (adding another two 10 node)>\n");
    ll_all.append(Node::new(10));
    ll_all.append(Node::new(10));

    let x = ll_all.count(10);
    print!("|\n|--| Occurrencies: {:?}\n", x);

    print!("|\n|--Getting max value from ll_all>\n");
    print!("|\n|--| {:?}\n", ll_all.max_value());

    print!("|\n|--Getting min value from ll_all>\n");
    print!("|\n|--| {:?}\n", ll_all.min_value());

    print!("|\n|--Reversing ll_all>\n");
    ll_all.reverse();
    print!("|\n|--| {:?}\n", ll_all.show());

    print!("|\n|--Copying ll_all to ll_copy and adding 4 to ll_copy>\n");
    let mut ll_copy = ll_all.copy();
    ll_copy.append(Node::new(4));
    print!("|\n|--| {:?}\n", ll_copy.show());
    print!("|\n|--| {:?}\n", ll_all.show());

    print!("|\n|--Concatenating ll_all with one node 7 to ll_all>\n");
    ll_all.concatenate(&Linkedlist::new_with_singlenode(Node::new(7)));
    print!("|\n|--| {:?}\n", ll_all.show());

    print!("|\n|--Sorting ll_all>\n");
    ll_all.sort();
    print!("|\n|--| {:?}\n", ll_all.show());

    print!("|\n|--Merging ll_all with a clone of itself>\n");
    ll_all.merge(&ll_all.clone());
    print!("|\n|--| {:?}\n", ll_all.show());

    print!("|\n|--Removing at index 1 from ll_all>\n");
    ll_all.remove_at_index(1);
    print!("|\n|--| {:?}\n", ll_all.show());

    print!("|\n|--Removing by value 7 from ll_all>\n");
    ll_all.remove_by_value(7);
    print!("|\n|--| {:?}\n", ll_all.show());

    print!("|\n|--Splitting ll_all in two halves>\n");
    ll_all.split().iter().for_each(|maybe_list| {
        if let Some(list) = maybe_list {
            print!("|\n|--| {:?}\n", list.clone().show());
        } else {
            print!("Empty list\n");
        }
    });

    // ###################################

    // implementing splitting by other numbers.

    let split_nodes: Vec<Node<i32>> = (0..20).map(|x| Node::new(x)).collect();

    let mut split_ll = Linkedlist::new_with_nodearray(&split_nodes);

    print!("|\n|--Splitting split_ll in 4 halves>\n");
    print!("|\n|--| split_ll: {:?}\n", split_ll.show());
    split_ll.split_parts(4).iter().for_each(|maybe_list| {
        if let Some(list) = maybe_list {
            print!("|\n|--| {:?}\n", list.clone().show());
        } else {
            print!("Empty list\n");
        }
    });

    print!("|\n|--Splitting split_ll in 7 halves>\n");
    print!("|\n|--| split_ll: {:?}\n", split_ll.show());
    split_ll.split_parts(7).iter().for_each(|maybe_list| {
        if let Some(list) = maybe_list {
            print!("|\n|--| {:?}\n", list.clone().show());
        } else {
            print!("Empty list\n");
        }
    });

    // ###################################

    print!("|\n|--Mapping to double each element in ll_copy>\n");
    print!("|\n|--| {:?}\n", ll_copy.map(|x| x * 2).show());

    print!("|\n|--Filtering evens from ll_copy>\n");
    print!("|\n|--| {:?}\n", ll_copy.filter(|x| x % 2 == 0).show());

    print!("|\n|--Summing all elements in ll_copy>\n");
    print!("|\n|--| {:?}\n", ll_copy.reduce(|acc, x| acc + x, 0));

    // ###################################

    println!("\n-----\n");
}
