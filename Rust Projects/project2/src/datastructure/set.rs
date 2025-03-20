#[allow(dead_code)]
#[derive(Debug, Clone)]
pub struct Set<T> {
    content: Vec<T>,
}

impl<T> Set<T>
where
    T: PartialEq<T> + Copy,
{
    #[allow(dead_code)]
    pub fn new() -> Self {
        Set {
            content: Vec::new(),
        }
    }

    #[allow(dead_code)]
    pub fn add_element(&mut self, e: T) -> () {
        if !self.content.contains(&e) {
            self.content.push(e);
        }
    }

    #[allow(dead_code)]
    pub fn remove_element(&mut self, e: T) -> () {
        if self.content.contains(&e) {
            self.content.retain(|x| *x != e);
        }
    }

    #[allow(dead_code)]
    pub fn contains_element(&mut self, e: T) -> bool {
        self.content.contains(&e)
    }

    #[allow(dead_code)]
    pub fn union(&mut self, set_b: &mut Set<T>) -> () {
        for &e in &set_b.content {
            self.add_element(e);
        }
    }

    #[allow(dead_code)]
    pub fn intersection(&mut self, set_b: &mut Set<T>) -> () {
        self.content.retain(|x| set_b.contains_element(*x));
    }

    #[allow(dead_code)]
    pub fn difference(&mut self, set_b: &mut Set<T>) -> () {
        self.content.retain(|x| !set_b.contains_element(*x));
    }
}

#[allow(dead_code)]
pub fn main() -> () {
    // Creating two sets and initializing elements
    let mut set_a: Set<i32> = Set::new();
    let mut set_b: Set<i32> = Set::new();

    set_a.add_element(1);
    set_a.add_element(2);
    set_a.add_element(3);

    set_b.add_element(3);
    set_b.add_element(4);
    set_b.add_element(5);

    println!("Set A: {:?}", set_a);
    println!("Set B: {:?}", set_b);

    // Testing union of sets A and B
    set_a.union(&mut set_b);
    println!("Union of Set A and Set B: {:?}", set_a);

    // Testing intersection of sets A and B
    let mut set_a_intersection = Set::new();
    set_a_intersection.content = set_a.content.clone(); // Clone set A to intersect with it
    set_a_intersection.intersection(&mut set_b);
    println!("Intersection of Set A and Set B: {:?}", set_a_intersection);

    // Testing difference of sets A and B
    let mut set_a_difference = Set::new();
    set_a_difference.content = set_a.content.clone(); // Clone set A to find its difference with B
    set_a_difference.difference(&mut set_b);
    println!("Difference of Set A and Set B: {:?}", set_a_difference);

    // Removing an element from Set A
    set_a.remove_element(1);
    println!("Set A after removing element 1: {:?}", set_a);

    // Checking if elements exist in Set A
    println!("Set A contains 2: {}", set_a.contains_element(2));
    println!("Set A contains 5: {}", set_a.contains_element(5));
}
