enum Color {
    Red,
    Yellow,
    Blue,
}

impl Color {
    fn print(&self) {
        let c: &str = match self {
            Color::Red => "Red",
            Color::Yellow => "Yellow",
            Color::Blue => "Blue",
        };
        println!("Color: {:?}", c);
    }
}

struct Dimensions {
    depth: f64,
    height: f64,
    width: f64,
}

impl Dimensions {
    fn print(&self) {
        println!("Depth: {:?}", self.depth);
        println!("Height: {:?}", self.height);
        println!("Width: {:?}", self.width);
    }
}

struct Box {
    weight: f64,
    color: Color,
    dimensions: Dimensions,
}

impl Box {
    fn new(weight: f64, color: Color, dimensions: Dimensions) -> Self {
        Self {
            weight,
            color,
            dimensions,
        }
    }

    fn print(&self) {
        self.color.print();
        self.dimensions.print();
        println!("Weight: {:?}", self.weight);
    }
}

pub fn main() {
    let small_dimensions = Dimensions {
        depth: 1.0,
        height: 2.0,
        width: 2.0,
    };
    let mid_dimensions = Dimensions {
        depth: 2.0,
        height: 3.0,
        width: 4.0,
    };
    let big_dimensions = Dimensions {
        depth: 3.0,
        height: 4.0,
        width: 5.0,
    };

    let shipping_box = Box::new(10.0, Color::Red, small_dimensions);

    let _shipping_box_2 = Box::new(10.0, Color::Yellow, mid_dimensions);
    let _shipping_box_3 = Box::new(10.0, Color::Blue, big_dimensions);

    shipping_box.print();
}
