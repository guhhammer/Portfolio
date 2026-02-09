use crate::datastructures::fuel::Fuel;
use crate::datastructures::pump_status::PumpStatus;

#[derive(Debug, Copy, Clone, serde::Serialize, serde::Deserialize)]
pub struct Pump {
  number: u16,

  liters_thousandth: u32,

  price_thousandth: u32,

  fuel_type: Fuel,

  pump_status: PumpStatus,
}

// u32 MAX LIMIT: 4,294,967,295.

// 6,54 liters is equal to 6540 milli-liters

// price of $ 4.10 per liter is equal to 4100 thousandths or milli units.

impl Pump {
  pub fn new() -> Self {
    Pump {
      number: 0,

      liters_thousandth: 0,

      price_thousandth: 0,

      fuel_type: Fuel::Undefined,

      pump_status: PumpStatus::Unactive,
    }
  }

  #[allow(dead_code)]
  pub fn new_with(
    number: u16,
    liters_thousandth: u32,
    price_thousandth: u32,
    fuel_type: Fuel,
    pump_status: PumpStatus,
  ) -> Self {
    Pump {
      number,
      liters_thousandth,
      price_thousandth,
      fuel_type,
      pump_status,
    }
  }

  pub fn update_number(&mut self, new_number: u16) {
    self.number = new_number;
  }

  pub fn update_price(&mut self, new_price_thousandth: u32) {
    self.price_thousandth = new_price_thousandth;

    if self.price_thousandth > 0 {
      self.pump_status = PumpStatus::Active;
    } else {
      self.pump_status = PumpStatus::Unactive;
    }
  }

  #[allow(dead_code)]
  pub fn update_liters_display(&mut self, new_liters_thousandth: u32) {
    self.liters_thousandth = new_liters_thousandth;
  }

  pub fn update_fuel_type(&mut self, new_fuel_type: Fuel) {
    self.fuel_type = new_fuel_type;
  }

  pub fn update_pump_status(&mut self, new_pump_status: PumpStatus) {
    self.pump_status = new_pump_status;
  }

  pub fn read_number(&self) -> u16 {
    self.number
  }

  #[allow(dead_code)]
  pub fn read_liters(&self) -> u32 {
    self.liters_thousandth
  }

  #[allow(dead_code)]
  pub fn read_price(&self) -> u32 {
    self.price_thousandth
  }

  pub fn read_fuel_type(&self) -> Fuel {
    self.fuel_type
  }

  pub fn read_pump_status(&self) -> PumpStatus {
    self.pump_status
  }

  pub fn read_price_decimals(&self) -> f64 {
    (self.price_thousandth as f64) / 1000.0
  }

  #[allow(dead_code)]
  pub fn total_amount_decimals(&self) -> f64 {
    ((self.liters_thousandth as f64) / 1000.0) * ((self.price_thousandth as f64) / 1000.0)
  }
}
