use crate::datastructure::linkedlist::Linkedlist;

use super::node::Node;

#[allow(dead_code)]
#[derive(Debug, Clone)]
pub struct HashMap<'a, K, V> {
    index: Vec<K>,
    table: Vec<Linkedlist<&'a V>>,
    size: usize,
}

impl<'a, K, V> HashMap<'a, K, V>
where
    K: PartialEq<K> + Copy,
    V: PartialEq<V> + Copy,
{
    #[allow(dead_code)]
    pub fn new() -> Self {
        HashMap {
            index: Vec::new(),
            table: Vec::new(),
            size: 0,
        }
    }

    #[allow(dead_code)]
    pub fn insert(&mut self, key: &K, value: &'a V) -> () {
        for i in 0..self.size {
            if self.index[i] == *key {
                self.table[i].append(Node::new(value));
                return;
            }
        }

        self.index.push(key.clone());
        self.table.push(Linkedlist::new());

        let pos = if self.size == 0 { 0 } else { self.size };

        self.table[pos].append(Node::new(value));
        self.size += 1;
    }

    #[allow(dead_code)]
    pub fn get(&mut self, key: &K) -> Option<Vec<&'a V>> {
        for i in 0..self.size {
            if self.index[i] == *key {
                return Some(self.table[i].iter().map(|x| *x).collect());
            }
        }

        None
    }

    #[allow(dead_code)]
    pub fn remove_value_from_key(&mut self, key: &K, value: &'a V) -> Option<&'a V> {
        for i in 0..self.size {
            if self.index[i] != *key {
                continue;
            }

            if let Some(pos) = self.table[i].find(value) {
                self.table[i].remove_at_index(pos);

                if self.table[i].is_empty() {
                    self.table.remove(i);
                    self.index.remove(i);

                    self.size -= 1;
                }

                return Some(value);
            }
        }

        None
    }

    #[allow(dead_code)]
    pub fn remove(&mut self, key: &K) -> Option<Linkedlist<&'a V>> {
        for i in 0..self.size {
            if self.index[i] != *key {
                continue;
            }

            let ret = self.table[i].clone();

            self.table.remove(i);
            self.index.remove(i);

            return Some(ret);
        }

        None
    }
}

#[allow(dead_code)]
pub fn main() {
    let mut hashmap = HashMap::new();

    // Insert some key-value pairs
    hashmap.insert(&1, &"Value1");
    hashmap.insert(&2, &"Value2");
    hashmap.insert(&1, &"Value3");

    // Test get method
    if let Some(values) = hashmap.get(&1) {
        println!("Values for key 1: {:?}", values);
    } else {
        println!("Key 1 not found");
    }

    // Test remove_value_from_key method
    if let Some(removed_value) = hashmap.remove_value_from_key(&2, &"Value2") {
        println!("Removed value: {:?}", removed_value);
    } else {
        println!("Value not found or key not present");
    }

    // Test remove method
    if let Some(removed_list) = hashmap.remove(&1) {
        println!("Removed list: {:?}", removed_list);
    } else {
        println!("Key not found");
    }
}
