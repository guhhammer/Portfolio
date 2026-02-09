
#[derive(Debug)]
pub struct Person {
 pub name: String,
}

impl Person {
 
 pub const change_name = |&mut self, new_name: String| self.name = new_name;
	

}

fn main() {
 
 let p = Person { name: "jake".to_string(), };

 println!("p:{p:?}");

 p.change_name("jack".to_string());


 println!("p:{p:?}");

}
