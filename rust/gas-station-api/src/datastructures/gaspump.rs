use crate::datastructures::fuel::Fuel;
use crate::datastructures::pump::Pump;
use crate::datastructures::pump_status::PumpStatus;

#[derive(Debug, Clone, serde::Serialize, serde::Deserialize)]
pub struct GasPump {
  number: u16,
  triggers: Vec<Pump>,
}

impl GasPump {
  pub fn new() -> Self {
    GasPump {
      number: 0,
      triggers: Vec::new(),
    }
  }

  pub fn add_trigger(&mut self, pump: &Pump) {
    self.triggers.push(*pump);
  }

  #[allow(dead_code)]
  pub fn add_triggers(&mut self, pump: &mut Vec<Pump>) {
    self.triggers.append(pump);
  }

  pub fn add_number(&mut self, new_number: u16) {
    self.number = new_number;
  }

  pub fn read_number(&self) -> &u16 {
    &self.number
  }

  #[allow(dead_code)]
  pub fn remove_trigger(&mut self, index: usize) {
    if index < self.triggers.len() {
      self.triggers.remove(index);
    }
  }

  pub fn get_triggers(&self) -> &Vec<Pump> {
    &self.triggers
  }

  pub fn push_price_to_pump(&mut self, fuel_type: Fuel, price: u32) {
    self.triggers.iter_mut().for_each(|p| {
      if p.read_fuel_type() == fuel_type {
        p.update_price(price);
      }
    });
  }

  pub fn get_all_fuels(&self) -> Vec<Fuel> {
    self.triggers.iter().map(|p| p.read_fuel_type()).collect()
  }

  pub fn set_pump_status(&mut self, fuel: Fuel, status: PumpStatus) {
    for p in &mut self.triggers {
      if p.read_fuel_type() == fuel {
        p.update_pump_status(status);
      }
    }
  }

  #[allow(dead_code)]
  pub fn get_pump_status(&self, fuel: Fuel) -> Option<PumpStatus> {
    for p in &self.triggers {
      if p.read_fuel_type() == fuel {
        return Some(p.read_pump_status());
      }
    }

    None
  }
}
