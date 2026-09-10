use std::collections::HashMap;

#[allow(dead_code)]
#[derive(Debug)]
pub struct TrieNode {
    character: char,
    children: HashMap<char, TrieNode>,
    is_end_of_word: bool,
}

#[allow(dead_code)]
#[derive(Debug)]
pub struct Trie {
    root: TrieNode,
}

impl TrieNode {
    #[allow(dead_code)]
    pub fn new(character: char) -> Self {
        TrieNode {
            character,
            children: HashMap::new(),
            is_end_of_word: false,
        }
    }
}

impl Trie {
    #[allow(dead_code)]
    pub fn new() -> Self {
        Trie {
            root: TrieNode::new('\0'),
        }
    }

    #[allow(dead_code)]
    pub fn insert(&mut self, key: &str) -> () {
        let mut curr = &mut self.root;

        for char in key.chars() {
            curr = curr
                .children
                .entry(char)
                .or_insert_with(|| TrieNode::new(char));
        }

        curr.is_end_of_word = true;
    }

    #[allow(dead_code)]
    pub fn search(&self, key: &str) -> bool {
        let mut curr = &self.root;

        for char in key.chars() {
            match curr.children.get(&char) {
                Some(node) => curr = node,
                None => return false,
            }
        }

        curr.is_end_of_word
    }

    pub fn delete(&mut self, key: &str) -> bool {
        fn delete_recursive(curr: &mut TrieNode, key: &str, depth: usize) -> bool {
            if depth == key.len() {
                if curr.is_end_of_word {
                    curr.is_end_of_word = false;
                }

                return curr.children.is_empty();
            }

            let char = key.chars().nth(depth).unwrap();
            if let Some(child_node) = curr.children.get_mut(&char) {
                let should_delete_child = delete_recursive(child_node, key, depth + 1);

                if should_delete_child {
                    curr.children.remove(&char);
                    return curr.children.is_empty() && !curr.is_end_of_word;
                }
            }

            false
        }

        delete_recursive(&mut self.root, key, 0)
    }

    pub fn print_trie(&self, node: &TrieNode, level: usize) {
        let indent = "    ".repeat(level);
        println!(
            "{}{}: TrieNode {{ character: '{}', is_end_of_word: {} }}",
            indent, level, node.character, node.is_end_of_word
        );
        for child in node.children.values() {
            self.print_trie(child, level + 1);
        }
    }
}

#[allow(dead_code)]
pub fn main() -> () {
    let mut trie = Trie::new();

    // Insert some keys into the trie
    println!("Inserting 'hello' into trie.");
    trie.insert("hello");
    println!("Inserting 'world' into trie.");
    trie.insert("world");

    // Search for keys in the trie
    println!("\nSearching for 'hello' in trie: {}", trie.search("hello")); // Output: true
    println!("Searching for 'world' in trie: {}", trie.search("world")); // Output: true
    println!("Searching for 'bye' in trie: {}", trie.search("bye")); // Output: false

    // Detailed output of trie structure
    println!("\nTrie structure after inserts:");
    trie.print_trie(&trie.root, 0);

    // Delete a key from the trie
    println!("\nDeleting 'hello' from trie.");
    trie.delete("hello");

    // Search for deleted key
    println!(
        "Searching for 'hello' in trie after delete: {}",
        trie.search("hello")
    ); // Output: false
    println!("Searching for 'world' in trie: {}", trie.search("world")); // Output: true

    // Detailed output of trie structure
    println!("\nTrie structure after delete:");
    trie.print_trie(&trie.root, 0);

    // Insert additional keys
    println!("\nInserting 'help' into trie.");
    trie.insert("help");
    println!("Inserting 'helix' into trie.");
    trie.insert("helix");

    // Search for additional keys
    println!("Searching for 'help' in trie: {}", trie.search("help")); // Output: true
    println!("Searching for 'helix' in trie: {}", trie.search("helix")); // Output: true

    // Detailed output of trie structure
    println!("\nTrie structure after additional inserts:");
    trie.print_trie(&trie.root, 0);
}
