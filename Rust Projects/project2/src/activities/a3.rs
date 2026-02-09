#[derive(Debug)]
enum Access {
    Backstage,
    Vip,
    Standard,
}

#[derive(Debug)]
struct Ticket {
    name: String,
    access: Access,
    price: f64,
}

pub fn main() {
    let ticket_a: Ticket = Ticket {
        name: String::from("Anthony"),
        access: Access::Backstage,
        price: 500.00,
    };

    let ticket_b: Ticket = Ticket {
        name: String::from("Jess"),
        access: Access::Vip,
        price: 400.00,
    };

    let ticket_c: Ticket = Ticket {
        name: String::new(),
        access: Access::Standard,
        price: 120.00,
    };

    let v_tickets = vec![ticket_a, ticket_b, ticket_c];

    for t in &v_tickets {
        match t.access {
            Access::Backstage => println!("Backstage ticket @ ${:?} Owner: {:?}", t.price, t.name),
            Access::Vip => println!("Vip ticket @ ${:?} Owner: {:?}", t.price, t.name),
            Access::Standard => println!("Standard ticket @ ${:?}", t.price),
        }
    }
}
