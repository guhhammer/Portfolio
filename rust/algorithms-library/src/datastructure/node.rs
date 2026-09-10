#[derive(Debug, Clone)]
#[allow(dead_code)]
pub struct Node<T> {
    pub content: T,
    pub pointer: Option<Box<Node<T>>>,
}

impl<T: Copy> Node<T> {
    #[allow(dead_code)]
    pub fn new(content: T) -> Self {
        Node {
            content,
            pointer: None,
        }
    }

    #[allow(dead_code)]
    pub fn set_pointer(&mut self, new_pointer: Option<Box<Node<T>>>) -> () {
        self.pointer = new_pointer;
    }

    #[allow(dead_code)]
    pub fn get_pointer(&mut self) -> &mut Option<Box<Node<T>>> {
        &mut self.pointer
    }

    #[allow(dead_code)]
    pub fn get_content(&mut self) -> T {
        self.content
    }
}
