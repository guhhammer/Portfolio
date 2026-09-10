use crate::datastructures::fuel::Fuel;
use crate::datastructures::grid::Grid;
use crate::datastructures::message_buf::MessageBuffer;
use crate::simulations::price_ruler;
use rand::Rng;

use serde::{Deserialize, Serialize};

#[allow(unused_imports)]
use serde_json::Value;
use std::fs::{self, OpenOptions};
use std::io::{Read, Write};
use std::path::Path;

use std::sync::{Arc, Mutex};
use std::thread;
use std::time::Duration;

fn get_new() -> Vec<Entry> {
  let mut rng = rand::thread_rng();

  let g70: u32 = rng.gen_range(price_ruler::G70_MIN..=price_ruler::G70_MAX);

  let g85: u32 = g70 + rng.gen_range(price_ruler::G85_MIN..=price_ruler::G85_MAX);

  let g95: u32 = g70 + rng.gen_range(price_ruler::G95_MIN..=price_ruler::G95_MAX);

  let e70: u32 = rng.gen_range(price_ruler::E70_MIN..=price_ruler::E70_MAX);

  let d50: u32 = rng.gen_range(price_ruler::D50_MIN..=price_ruler::D50_MAX);

  let d500: u32 = rng.gen_range(price_ruler::D500_MIN..=price_ruler::D500_MAX);

  vec![
    Entry {
      fuel: Fuel::Gasoline70,
      amount: g70,
    },
    Entry {
      fuel: Fuel::Gasoline85,
      amount: g85,
    },
    Entry {
      fuel: Fuel::Gasoline95,
      amount: g95,
    },
    Entry {
      fuel: Fuel::Ethanol70,
      amount: e70,
    },
    Entry {
      fuel: Fuel::Diesel50,
      amount: d50,
    },
    Entry {
      fuel: Fuel::Diesel500,
      amount: d500,
    },
  ]
}

#[derive(Serialize, Deserialize, Clone)]
struct Entry {
  fuel: Fuel,
  amount: u32,
}

#[derive(Serialize, Deserialize)]
struct Data {
  entries: Vec<Vec<Entry>>,
}

fn write_changes(new_entries: &[Entry]) -> Result<(), Box<dyn std::error::Error>> {
  let path = Path::new("src/etc/price_records.json");

  if let Some(parent) = path.parent() {
    fs::create_dir_all(parent)?;
  }

  // read existing file or create empty
  let mut data: Data = if path.exists() {
    let mut file = fs::File::open(path)?;
    let mut content = String::new();
    file.read_to_string(&mut content)?;
    if content.trim().is_empty() {
      Data { entries: vec![] }
    } else {
      serde_json::from_str(&content).unwrap_or(Data { entries: vec![] })
    }
  } else {
    Data { entries: vec![] }
  };

  // append new entries
  data.entries.push(new_entries.to_vec());

  // write back to file
  let mut file = OpenOptions::new()
    .create(true)
    .write(true) // overwrite
    .truncate(true) // clear file
    .open(path)?;
  let json = serde_json::to_string_pretty(&data).unwrap();
  writeln!(file, "{json}")?;

  Ok(())
}

pub fn follow(
  grid: Arc<Mutex<Grid>>,
  display: bool,
  price_changes_time_secs: u64,
  api_return: bool,
) {
  thread::spawn(move || {
    loop {
      let new_entries = get_new();

      let _ = write_changes(&new_entries);

      let updates: Vec<(Fuel, u32)> = new_entries.iter().map(|x| (x.fuel, x.amount)).collect();

      {
        let mut grid = grid.lock().unwrap();

        let mut m: MessageBuffer = MessageBuffer::new();

        grid.update_prices(updates);

        grid.display_prices(&mut m, api_return);

        m.log("log/price-maker/", "price-maker");

        if display {
          print!("\n[PRICE TRACKER UPDATE]");
          m.print();
        }
      }

      thread::sleep(Duration::from_secs(price_changes_time_secs));
    }
  });
}
