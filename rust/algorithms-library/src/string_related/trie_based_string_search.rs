use crate::datastructure::trie::Trie;

#[allow(dead_code)]
pub fn main() -> () {
    let mut trie = Trie::new();

    trie.insert("hello");
    trie.insert("world");

    println!("trie.search(\"hello\") = {:?}", trie.search("hello"));
    println!("trie.search(\"hell\") =  {:?}", trie.search("hell"));
    println!("trie.search(\"world\") = {:?}", trie.search("world"));

    trie.delete("hello");
    println!("trie.search(\"hello\") = {:?}", trie.search("hello"));
}
