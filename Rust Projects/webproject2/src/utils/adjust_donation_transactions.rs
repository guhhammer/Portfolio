use rand::Rng;

use crate::{config::db_config, db::simulate_transactions::{SIMULATED_ADDRESSES, SIMULATED_TRANSACTIONS}, logger::log_simple_info};
use crate::{models::donation::Donation, utils::writer_to_file::write_json_to_file};

const ONE_ETHER: u128 = 1_000_000_000_000_000_000;

fn generate_random_amount() -> i64 {
    
    let r = rand::thread_rng().gen_range(1..=100);
   
    r

}

pub async fn transactions_updater() -> Result<(), Box<dyn std::error::Error>> {
    
    let file_path = db_config::POPULATE_DB_DONATIONS_PATH;

    let mut vec_d: Vec<Donation> = Vec::new();

    let mut switcher = true;

    for tx_i in 0..SIMULATED_TRANSACTIONS.len() {

        let random_amount = generate_random_amount();

        let eth_amount: String = if switcher {

            switcher = false;
            (random_amount as u128 * ONE_ETHER).to_string() 

        } else {

            switcher = true;
            ((random_amount as u128 * ONE_ETHER)/100).to_string()

        };

        let d = Donation {

            from: SIMULATED_ADDRESSES[ SIMULATED_TRANSACTIONS[tx_i][0] as usize ].1.to_string(), 
            to: SIMULATED_ADDRESSES[ SIMULATED_TRANSACTIONS[tx_i][1] as usize ].1.to_string(), 
            amount: eth_amount.to_string(), 
            date: 0,
        
        };

        vec_d.push(d);

    }

    write_json_to_file(file_path, &vec_d)?;

    log_simple_info("Updated JSON Data Written to file.".to_string());

    Ok(())

}
