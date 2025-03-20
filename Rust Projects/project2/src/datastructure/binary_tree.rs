use std::{cell::RefCell, rc::Rc};

#[allow(dead_code)]
#[derive(Debug, Clone)]
pub struct TreeNode<T> {
    left: Option<Rc<RefCell<TreeNode<T>>>>,
    right: Option<Rc<RefCell<TreeNode<T>>>>,

    value: T,
}

#[allow(dead_code)]
#[derive(Debug, Clone)]
pub struct BinaryTree<T> {
    pub root: Option<Rc<RefCell<TreeNode<T>>>>,
}

impl<T: Ord + Copy + std::fmt::Debug> BinaryTree<T> {
    #[allow(dead_code)]
    pub fn new() -> Self {
        BinaryTree { root: None }
    }

    #[allow(dead_code)]
    pub fn insert(&mut self, value: T)
    where
        T: PartialOrd<T>,
    {
        let new_node = Rc::new(RefCell::new(TreeNode {
            value,
            left: None,
            right: None,
        }));

        if let Some(root) = self.root.clone() {
            Self::insert_node(root, new_node);
        } else {
            self.root = Some(new_node);
        }
    }

    #[allow(dead_code)]
    fn insert_node(current: Rc<RefCell<TreeNode<T>>>, new_node: Rc<RefCell<TreeNode<T>>>)
    where
        T: PartialOrd<T>,
    {
        let mut current_borrow = current.borrow_mut();
        if new_node.borrow().value < current_borrow.value {
            if let Some(left) = current_borrow.left.clone() {
                drop(current_borrow); // Release the borrow before the recursive call
                Self::insert_node(left, new_node);
            } else {
                current_borrow.left = Some(new_node);
            }
        } else {
            if let Some(right) = current_borrow.right.clone() {
                drop(current_borrow); // Release the borrow before the recursive call
                Self::insert_node(right, new_node);
            } else {
                current_borrow.right = Some(new_node);
            }
        }
    }

    #[allow(dead_code)]
    fn search(&self, value: T) -> bool
    where
        T: PartialEq<T> + PartialOrd<T>,
    {
        Self::search_node(self.root.clone(), value)
    }

    #[allow(dead_code)]
    fn search_node(node: Option<Rc<RefCell<TreeNode<T>>>>, value: T) -> bool
    where
        T: PartialEq<T> + PartialOrd<T>,
    {
        match node {
            Some(n) => {
                if value == n.borrow().value {
                    true
                } else if value < n.borrow().value {
                    Self::search_node(n.borrow().left.clone(), value)
                } else {
                    Self::search_node(n.borrow().right.clone(), value)
                }
            }
            None => false,
        }
    }

    #[allow(dead_code)]
    fn find_min(&self) -> Option<T> {
        Self::find_min_node(self.root.clone())
    }

    #[allow(dead_code)]
    fn find_min_node(node: Option<Rc<RefCell<TreeNode<T>>>>) -> Option<T> {
        match node {
            Some(n) => {
                if n.borrow().left.is_some() {
                    Self::find_min_node(n.borrow().left.clone())
                } else {
                    Some(n.borrow().value)
                }
            }
            None => None,
        }
    }

    #[allow(dead_code)]
    fn find_max(&self) -> Option<T> {
        Self::find_max_node(self.root.clone())
    }

    #[allow(dead_code)]
    fn find_max_node(node: Option<Rc<RefCell<TreeNode<T>>>>) -> Option<T> {
        match node {
            Some(n) => {
                if n.borrow().right.is_some() {
                    Self::find_max_node(n.borrow().right.clone())
                } else {
                    Some(n.borrow().value)
                }
            }
            None => None,
        }
    }

    #[allow(dead_code)]
    fn in_order_traversal(&self) -> Vec<T> {
        let mut result = Vec::new();
        Self::in_order_traversal_node(self.root.clone(), &mut result);
        result
    }

    #[allow(dead_code)]
    fn in_order_traversal_node(node: Option<Rc<RefCell<TreeNode<T>>>>, result: &mut Vec<T>) {
        if let Some(n) = node {
            Self::in_order_traversal_node(n.borrow().left.clone(), result);
            result.push(n.borrow().value);
            Self::in_order_traversal_node(n.borrow().right.clone(), result);
        }
    }

    #[allow(dead_code)]
    fn pre_order_traversal(&self) -> Vec<T> {
        let mut result = Vec::new();
        Self::pre_order_traversal_node(self.root.clone(), &mut result);
        result
    }

    #[allow(dead_code)]
    fn pre_order_traversal_node(node: Option<Rc<RefCell<TreeNode<T>>>>, result: &mut Vec<T>) {
        if let Some(n) = node {
            result.push(n.borrow().value);
            Self::pre_order_traversal_node(n.borrow().left.clone(), result);
            Self::pre_order_traversal_node(n.borrow().right.clone(), result);
        }
    }

    // Method to traverse the tree post-order
    #[allow(dead_code)]
    fn post_order_traversal(&self) -> Vec<T> {
        let mut result = Vec::new();
        Self::post_order_traversal_node(self.root.clone(), &mut result);
        result
    }

    #[allow(dead_code)]
    fn post_order_traversal_node(node: Option<Rc<RefCell<TreeNode<T>>>>, result: &mut Vec<T>) {
        if let Some(n) = node {
            Self::post_order_traversal_node(n.borrow().left.clone(), result);
            Self::post_order_traversal_node(n.borrow().right.clone(), result);
            result.push(n.borrow().value);
        }
    }

    #[allow(dead_code)]
    fn height(&self) -> usize {
        Self::height_node(self.root.clone())
    }

    #[allow(dead_code)]
    fn height_node(node: Option<Rc<RefCell<TreeNode<T>>>>) -> usize {
        match node {
            Some(n) => {
                let left_height = Self::height_node(n.borrow().left.clone());
                let right_height = Self::height_node(n.borrow().right.clone());
                usize::max(left_height, right_height) + 1
            }
            None => 0,
        }
    }

    #[allow(dead_code)]
    fn count_nodes(&self) -> usize {
        Self::count_nodes_node(self.root.clone())
    }

    #[allow(dead_code)]
    fn count_nodes_node(node: Option<Rc<RefCell<TreeNode<T>>>>) -> usize {
        match node {
            Some(n) => {
                1 + Self::count_nodes_node(n.borrow().left.clone())
                    + Self::count_nodes_node(n.borrow().right.clone())
            }
            None => 0,
        }
    }

    #[allow(dead_code)]
    fn is_valid_bst(&self) -> bool
    where
        T: PartialOrd<T> + PartialEq<T>,
    {
        Self::is_valid_bst_node(self.root.clone(), None, None)
    }

    #[allow(dead_code)]
    fn is_valid_bst_node(
        node: Option<Rc<RefCell<TreeNode<T>>>>,
        min: Option<T>,
        max: Option<T>,
    ) -> bool
    where
        T: PartialOrd<T> + PartialEq<T>,
    {
        match node {
            Some(n) => {
                let value = n.borrow().value;
                if let Some(min) = min {
                    if value <= min {
                        return false;
                    }
                }
                if let Some(max) = max {
                    if value >= max {
                        return false;
                    }
                }
                Self::is_valid_bst_node(n.borrow().left.clone(), min, Some(value))
                    && Self::is_valid_bst_node(n.borrow().right.clone(), Some(value), max)
            }
            None => true,
        }
    }

    /*
    Height of a Tree: The length of the longest path from the root to a leaf.
    Depth of a Node: The length of the path from the root to the node.
    Level of a Node: The number of edges on the path from the root to the node.
    Size of a Tree: The total number of nodes in the tree.
    Balanced Factor: For any node, it is the difference between the heights of the left and right subtrees.


    */
}

#[allow(dead_code)]
pub fn main() {
    let mut tree = BinaryTree::new();
    tree.insert(5);
    tree.insert(3);
    tree.insert(7);
    tree.insert(2);
    tree.insert(4);
    tree.insert(6);
    tree.insert(8);

    println!("In-order traversal: {:?}", tree.in_order_traversal());
    println!("Pre-order traversal: {:?}", tree.pre_order_traversal());
    println!("Post-order traversal: {:?}", tree.post_order_traversal());
    println!("Search for 4: {}", tree.search(4));
    println!("Search for 9: {}", tree.search(9));
    println!("Minimum value: {:?}", tree.find_min());
    println!("Maximum value: {:?}", tree.find_max());
    println!("Height: {}", tree.height());
    println!("Node count: {}", tree.count_nodes());
    println!("Is valid BST: {}", tree.is_valid_bst());
}
