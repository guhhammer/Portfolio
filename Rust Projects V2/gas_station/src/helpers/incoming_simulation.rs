use crate::datastructures::fuel::Fuel;
use crate::datastructures::grid::Grid;
use crate::datastructures::message_buf::MessageBuffer;
use crate::simulations::flow;
use num_format::{Locale, ToFormattedString};
use rand::Rng;
use rand::prelude::SliceRandom;
use rand::thread_rng;
use std::collections::HashMap;
use std::sync::{Arc, Mutex};
use std::thread;
use std::time::Duration;

#[derive(Debug, Copy, Clone)]
enum Vehicle {
  Car,
  Bike,
  Truck,
}

impl Vehicle {
  fn fuel_tank(x: Vehicle) -> u32 {
    let min_max_tank = match x {
      Vehicle::Car => flow::SIZE_TANK_CAR,
      Vehicle::Bike => flow::SIZE_TANK_BIKE,
      Vehicle::Truck => flow::SIZE_TANK_TRUCK,
    };

    let mut rng = rand::thread_rng();

    rng.gen_range(min_max_tank.0..=min_max_tank.1) as u32
  }
}

fn generate_binary_array(len: usize, percent_ones: f64) -> Vec<u8> {
  let mut rng = rand::thread_rng();

  (0..len)
    .map(|_| {
      if rng.r#gen::<f64>() < percent_ones / 100.0 {
        1
      } else {
        0
      }
    })
    .collect()
}

fn generate_traffic() -> Vec<(Fuel, Vec<Vehicle>)> {
  let for_diesel50: Vec<Vehicle> =
    generate_binary_array(flow::DIESEL50_INCOME.0, flow::DIESEL50_INCOME.1)
      .iter()
      .map(|x| {
        if *x == (0_u8) {
          Vehicle::Truck
        } else {
          Vehicle::Car
        }
      })
      .collect();

  let mut for_diesel500: Vec<Vehicle> = for_diesel50.clone();

  let mut rng = thread_rng();
  for_diesel500.shuffle(&mut rng);

  let for_ethanol70: Vec<Vehicle> =
    generate_binary_array(flow::ETHANOL70_INCOME.0, flow::ETHANOL70_INCOME.1)
      .iter()
      .map(|x| {
        if *x == (0_u8) {
          Vehicle::Bike
        } else {
          Vehicle::Car
        }
      })
      .collect();

  let for_gasoline70: Vec<Vehicle> =
    generate_binary_array(flow::GASOLINE70_INCOME.0, flow::GASOLINE70_INCOME.1)
      .iter()
      .map(|x| {
        if *x == (0_u8) {
          Vehicle::Bike
        } else {
          Vehicle::Car
        }
      })
      .collect();

  let for_gasoline85: Vec<Vehicle> = (0..flow::GASOLINE85_INCOME.0)
    .map(|_| Vehicle::Car)
    .collect();

  let for_gasoline95: Vec<Vehicle> = (0..flow::GASOLINE95_INCOME.0)
    .map(|_| Vehicle::Car)
    .collect();

  vec![
    (Fuel::Diesel50, for_diesel50),
    (Fuel::Diesel500, for_diesel500),
    (Fuel::Ethanol70, for_ethanol70),
    (Fuel::Gasoline70, for_gasoline70),
    (Fuel::Gasoline85, for_gasoline85),
    (Fuel::Gasoline95, for_gasoline95),
  ]
}

pub fn run(grid: &Arc<Mutex<Grid>>, display: bool, run: bool) {
  if !run {
    return;
  }

  let grid_clone = Arc::clone(grid); // clone the Arc

  let _handle = thread::spawn(move || {
    runner(&grid_clone, display);
  });
}

fn runner(grid: &Arc<Mutex<Grid>>, display: bool) {
  let all_types: Vec<Fuel> = grid.lock().unwrap().fuel_catalog();

  let mut redirect: HashMap<Fuel, Vec<u16>> = HashMap::new();

  let mut m_buf: Arc<Mutex<MessageBuffer>> = Arc::new(Mutex::new(MessageBuffer::new()));

  let ref_f: &str = &m_buf
    .lock()
    .unwrap()
    .ref_file("log/incoming-simulation/", "incoming");

  all_types.iter().for_each(|n| {
    redirect.insert(*n, Vec::new());
  });

  grid.lock().unwrap().get_isles().iter().for_each(|isle| {
    isle.iter().for_each(|gp| {
      let n = gp.read_number();

      gp.get_all_fuels()
        .iter()
        .for_each(|f| redirect.entry(*f).or_default().push(*n));
    })
  });

  // Remove duplicates
  for vec in redirect.values_mut() {
    vec.sort_unstable(); // sort first
    vec.dedup(); // remove consecutive duplicates
  }

  let traffic: Vec<(Fuel, Vec<Vehicle>)> = generate_traffic();

  queue_wrapper(
    grid,
    traffic.clone(),
    redirect.clone(),
    display,
    &mut m_buf,
    ref_f,
  );
}

fn queue_wrapper(
  grid: &Arc<Mutex<Grid>>,
  incomes: Vec<(Fuel, Vec<Vehicle>)>,
  redirect: HashMap<Fuel, Vec<u16>>,
  display: bool,
  m_buf: &mut Arc<Mutex<MessageBuffer>>,
  ref_f: &str,
) {
  let mut handles: Vec<Vec<std::thread::JoinHandle<()>>> = vec![];

  for i in &incomes {
    if let Some(to) = redirect.get(&i.0) {
      handles.push(queue_maker(
        grid,
        i.0,
        i.1.clone(),
        to.clone(),
        display,
        m_buf,
        ref_f,
      ));
    }
  }

  for v_handle in handles {
    for handle in v_handle {
      let _ = &handle.join().unwrap();
    }
  }
}

fn queue_maker(
  grid: &Arc<Mutex<Grid>>,
  fuel_type: Fuel,
  incomes: Vec<Vehicle>,
  redirect: Vec<u16>,
  display: bool,
  m_buf: &mut Arc<Mutex<MessageBuffer>>,
  ref_f: &str,
) -> Vec<std::thread::JoinHandle<()>> {
  let arr: Arc<Mutex<Vec<Vehicle>>> = Arc::new(Mutex::new(incomes.clone()));
  let mut handles: Vec<std::thread::JoinHandle<()>> = vec![];

  for r in redirect {
    let arr_clone = Arc::clone(&arr);
    let grid_clone = Arc::clone(grid);

    // extract indices / numbers while holding the lock
    let (pump_number, loc, isle_index) = {
      let grid = grid.lock().unwrap();
      let mut pump_number: Option<u32> = None;
      let mut loc: Option<usize> = None;
      let mut isle_index: Option<usize> = None;

      for (counter, isle) in grid.get_isles().iter().enumerate() {
        for (l, gp) in isle.iter().enumerate() {
          if *gp.read_number() == r {
            pump_number = Some((*gp.read_number()).into());
            loc = Some(l);
            isle_index = Some(counter);
            break;
          }
        }
        if pump_number.is_some() {
          break;
        }
      }

      (pump_number.unwrap(), loc.unwrap(), isle_index.unwrap())
    };

    let mut m_buf = m_buf.lock().unwrap().clone();
    let ref_f = ref_f.to_string();

    let handle = thread::spawn(move || {
      let mut status: usize = 0;

      loop {
        let mut data = arr_clone.lock().unwrap();
        if let Some(v) = data.pop() {
          let fueling_liters = Vehicle::fuel_tank(v);

          // Lock grid inside thread
          let (total, stock) = {
            let mut grid = grid_clone.lock().unwrap();

            (
              grid.fuel(isle_index, loc, fuel_type, fueling_liters, true),
              grid.read_supply(fuel_type),
            )
          };

          let mut rng = rand::thread_rng();

          let idle_time = rng.gen_range(1..=flow::IDLE_TIME) as u64;

          thread::sleep(Duration::from_millis(
            ((fueling_liters as f64) / 10.0) as u64 + idle_time,
          ));
          {
            let mut grid = grid_clone.lock().unwrap();
            let _ = grid.fuel(isle_index, loc, fuel_type, fueling_liters, false);
          };

          match total {
            Err(e) => eprintln!("Error: {e}"),
            Ok(x) => {
              let s: &str = &format!(
                "[{fuel_type}] GasPump #{pump_number} got {v:?} = {fueling_liters:.2} (liters) | total: {x}.\n"
              );

              m_buf.log_step(&ref_f, s);

              if display {
                print!("{s}");
              }
            }
          }

          if status % 20 == 0 {
            let s: &str = &format!(
              "[{}] counter: {} incomes (liters in stock: {})\n",
              fuel_type,
              status,
              formatter_liters((stock as f64) / 1000.0)
            );

            m_buf.log_step(&ref_f, s);

            if display {
              print!("{s}");
            }
          }

          status += 1;
        } else {
          break;
        }
      }
    });

    handles.push(handle);
  }

  handles
}

fn formatter_liters(stock: f64) -> String {
  let integer_part = stock.trunc() as u64;
  let fractional_part = (stock.fract() * 100.0).round() as u64; // 2 decimals

  let formatted_int = integer_part.to_formatted_string(&Locale::de); // German-style thousands separator

  // -> 340.678,90 L
  format!("{formatted_int}.{fractional_part:02} L")
}
