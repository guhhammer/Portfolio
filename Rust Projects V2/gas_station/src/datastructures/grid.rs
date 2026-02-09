use crate::datastructures::fuel::Fuel;
use crate::datastructures::gaspump::GasPump;
use crate::datastructures::message_buf::MessageBuffer;
use crate::datastructures::pump_status::PumpStatus;
use std::collections::{HashMap, HashSet};

#[derive(Debug, Clone, serde::Serialize, serde::Deserialize)]
pub struct Grid {
  #[serde(default)]
  isles: Vec<Vec<GasPump>>,
  #[serde(default)]
  price_controller: HashMap<Fuel, u32>,
  #[serde(default)]
  supplier: HashMap<Fuel, u32>,

  name: String,
}

impl Grid {
  #[allow(dead_code)]
  pub fn new() -> Self {
    Grid {
      isles: Vec::new(),

      price_controller: HashMap::new(),

      supplier: HashMap::new(),

      name: "".to_string(),
    }
  }

  pub fn new_with_name(name: &str) -> Self {
    Grid {
      isles: Vec::new(),

      price_controller: HashMap::new(),

      supplier: HashMap::new(),

      name: name.to_string(),
    }
  }

  pub fn new_with(isles: Vec<Vec<GasPump>>) -> Self {
    Grid {
      isles,

      price_controller: HashMap::new(),

      supplier: HashMap::new(),

      name: "".to_string(),
    }
  }

  #[allow(dead_code)]
  pub fn add_isle(&mut self, new_isle: &[GasPump]) {
    self.isles.push(new_isle.to_vec());
  }

  #[allow(dead_code)]
  pub fn add_isles(&mut self, new_isles: &mut Vec<Vec<GasPump>>) {
    self.isles.append(new_isles);
  }

  #[allow(dead_code)]
  pub fn remove_isle(&mut self, index: usize) {
    self.isles.remove(index);
  }

  #[allow(dead_code)]
  pub fn remove_gaspump(&mut self, isle_index: usize, gaspump_index: usize) {
    if let Some(inner) = self.isles.get_mut(isle_index)
      && gaspump_index < inner.len()
    {
      inner.remove(gaspump_index);
    }
  }

  pub fn get_isles(&self) -> &Vec<Vec<GasPump>> {
    &self.isles
  }

  #[allow(dead_code)]
  pub fn get_isles_mut(&mut self) -> &mut Vec<Vec<GasPump>> {
    &mut self.isles
  }

  #[allow(dead_code)]
  pub fn get_isle(&self, index: usize) -> Option<&Vec<GasPump>> {
    self.isles.get(index)
  }

  #[allow(dead_code)]
  pub fn get_isle_mut(&mut self, index: usize) -> Option<&Vec<GasPump>> {
    self.isles.get(index)
  }

  #[allow(dead_code)]
  pub fn get_gaspump(&self, isle_index: usize, gaspump_index: usize) -> Option<&GasPump> {
    self.isles.get(isle_index)?.get(gaspump_index)
  }

  fn push_price_update(&mut self, fuel_type: Fuel, price: u32) {
    self.isles.iter_mut().for_each(|isle| {
      isle
        .iter_mut()
        .for_each(|g| g.push_price_to_pump(fuel_type, price));
    });
  }

  pub fn update_price(&mut self, fuel_type: Fuel, price: u32) {
    self.price_controller.insert(fuel_type, price);

    self.push_price_update(fuel_type, price);
  }

  pub fn update_prices(&mut self, list: Vec<(Fuel, u32)>) {
    list
      .iter()
      .for_each(|pair| self.update_price(pair.0, pair.1));
  }

  #[allow(dead_code)]
  pub fn read_prices(&self) -> &HashMap<Fuel, u32> {
    &self.price_controller
  }

  pub fn display_prices(&self, m_buf: &mut MessageBuffer, api_return: bool) {
    m_buf.push("\nFuel Prices:\n");

    let convert = |f: Fuel| {
      if api_return {
        f.api_return_format()
      } else {
        format!("{f}")
      }
    };

    for (k, v) in &self.price_controller {
      m_buf.push(&format!(
        "\t{} = ${:.2}\n",
        convert(*k),
        self.thousandths_to_decimals(*v)
      ));
    }

    m_buf.push("\n");
  }

  pub fn update_name(&mut self, new_name: String) {
    self.name = new_name;
  }

  pub fn read_name(&self) -> &String {
    &self.name
  }

  pub fn display(&self, m_buf: &mut MessageBuffer, api_format: bool) {
    m_buf.push(&"-".repeat(60).to_string());

    m_buf.push(&format!("GRID : {}\n\n{{\n", self.name));

    m_buf.push("\n Isles:");
    for isle in 0..self.isles.len() {
      self.display_isle(isle, m_buf, api_format);
    }

    m_buf.push("\n\n}}");

    m_buf.push(&"-".repeat(60).to_string());
  }

  fn display_isle(&self, isle: usize, m_buf: &mut MessageBuffer, api_format: bool) {
    m_buf.push("\n - Isle {isle}: ");

    if let Some(i) = &self.isles.get(isle) {
      // println!("{:?}", i);

      for gp in 0..i.len() {
        m_buf.push(&format!("\n - - GasPump #{}: ", i[gp].read_number()));

        let loc = i[gp].get_triggers();

        for p in loc {
          if api_format {
            m_buf.push(&format!(
              "\n - - - Pump #{:<2} | Price: {:>6.2} | {:<14} | (status:{:<12})\n",
              p.read_number(),
              p.read_price_decimals(),
              p.read_fuel_type().api_return_format(),
              p.read_pump_status().api_return_format(),
            ));
          } else {
            m_buf.push(&format!(
              "\n - - - Pump #{:<2} | Price: {:>6.2} | {:<14} | (status:{:<12})",
              p.read_number(),
              p.read_price_decimals(),
              p.read_fuel_type(),
              p.read_pump_status()
            ));
          }
        }
      }
    }
  }

  pub fn fuel_catalog(&self) -> Vec<Fuel> {
    let mut z: Vec<Fuel> = Vec::new();

    self.isles.iter().for_each(|x| {
      x.iter().for_each(|y| {
        y.get_all_fuels().iter().for_each(|a| z.push(*a));
      });
    });

    let mut set = HashSet::new();

    let unique: Vec<Fuel> = z.into_iter().filter(|x| set.insert(*x)).collect();

    unique
  }

  pub fn add_supply(&mut self, supply_hashmap: HashMap<Fuel, u32>) {
    for (k, v) in supply_hashmap {
      *self.supplier.entry(k).or_insert(0) += v;
    }
  }

  #[allow(dead_code)]
  pub fn update_supply(&mut self, supply_hashmap: HashMap<Fuel, u32>) {
    for (k, v) in supply_hashmap {
      *self.supplier.entry(k).or_insert(0) = v;
    }
  }

  pub fn read_supply(&mut self, supply_key: Fuel) -> u32 {
    *self.supplier.entry(supply_key).or_insert(0)
  }

  pub fn get_supplier(&self) -> &HashMap<Fuel, u32> {
    &self.supplier
  }

  #[allow(dead_code)]
  pub fn remove_supply(&mut self, supply_list: Vec<Fuel>) {
    for i in 0..supply_list.len() {
      let n_fuel: Fuel = *supply_list.get(i).unwrap();

      *self.supplier.entry(n_fuel).or_insert(0) = 0;
    }
  }

  pub fn display_supply(&self, m_buf: &mut MessageBuffer, api_return: bool) {
    m_buf.push("\nFuel Supply:\n");

    let convert = |f: Fuel| {
      if api_return {
        f.api_return_format()
      } else {
        format!("{f}")
      }
    };

    for (k, v) in &self.supplier {
      m_buf.push(&format!(
        "\t - {} = {} liters\n\n",
        convert(*k),
        self.thousandths_to_decimals(*v)
      ));
    }

    m_buf.push("\n");
  }

  pub fn thousandths_to_decimals(&self, thousandths: u32) -> f64 {
    (thousandths as f64) / 1000.0
  }

  pub fn fuel(
    &mut self,
    isle_id: usize,
    gaspump_id: usize,
    fuel_type: Fuel,
    liters: u32,
    start: bool,
  ) -> Result<f64, String> {
    if isle_id > self.isles.len() || gaspump_id > self.isles.get(isle_id).unwrap().len() {
      return Err("Wrong indexes".to_string());
    }

    let stock: u32 = *self.supplier.entry(fuel_type).or_insert(0);

    if liters > stock {
      return Err("Not enough stock".to_string());
    }

    let gp_ref: &mut GasPump = self
      .isles
      .get_mut(isle_id)
      .unwrap()
      .get_mut(gaspump_id)
      .unwrap();

    if !start {
      gp_ref.set_pump_status(fuel_type, PumpStatus::Active);
    }

    if !gp_ref.get_all_fuels().contains(&fuel_type) {
      return Err("Wrong fueling point".to_string());
    }

    *self.supplier.entry(fuel_type).or_insert(0) -= liters;

    let curr_price: u32 = *self.price_controller.entry(fuel_type).or_insert(0);

    let total: f64 = ((liters as f64) / 1000.0) * ((curr_price as f64) / 1000.0);

    gp_ref.set_pump_status(fuel_type, PumpStatus::Pumping);

    Ok(total)
  }

  #[allow(dead_code)]
  fn price_decimals(liters: u32, price: u32) -> f64 {
    ((liters as f64) / 1000.0) * ((price as f64) / 1000.0)
  }
}
