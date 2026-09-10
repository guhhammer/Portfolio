use crate::datastructures::fuel::Fuel;
use crate::datastructures::gaspump::GasPump;
use crate::datastructures::grid::Grid;
use crate::datastructures::message_buf::MessageBuffer;
use crate::datastructures::pump::Pump;
use crate::simulations::grid_model;
use std::sync::{Arc, Mutex};

pub fn grid_maker() -> Grid {
  let mut aux: Vec<Vec<GasPump>> = Vec::new();

  let example: Vec<Vec<(Vec<u16>, Vec<Fuel>)>> = grid_model::example_grid();

  for row in example {
    let mut gaspump: Vec<GasPump> = Vec::new();

    for isle in row {
      let gp_aux: Vec<GasPump> = isle
        .0
        .iter()
        .map(|n| {
          let mut gp = GasPump::new();

          let mut nn = 0;

          isle.1.iter().for_each(|p| {
            let mut aux_p = Pump::new();

            aux_p.update_number(nn);

            nn += 1;

            aux_p.update_fuel_type(*p);

            gp.add_trigger(&aux_p);
          });

          gp.add_number(*n);

          gp
        })
        .collect();

      gp_aux.iter().for_each(|g| gaspump.push(g.clone()));
    }

    aux.push(gaspump);
  }

  let grid: Grid = Grid::new_with(aux);

  grid
}

pub fn fuel_catalog(
  grid: &Arc<Mutex<Grid>>,
  display: bool,
  run: bool,
  api_return: bool,
) -> Option<String> {
  if !run {
    return None;
  }

  let mut m: MessageBuffer = MessageBuffer::new();

  m.push("\nCatalog: \n");

  let convert = |f: Fuel| {
    if api_return {
      format!("{}\n", f.api_return_format())
    } else {
      format!("\n\t- {f}")
    }
  };

  grid
    .lock()
    .unwrap()
    .fuel_catalog()
    .iter()
    .for_each(|x| m.push(convert(*x).as_str()));

  m.push("\n");

  m.log("log/fuel-catalog/", "fuel-catalog");

  if display {
    m.print();
  }

  if api_return {
    return Some(m.output_as_string());
  }

  None
}

pub fn display(
  grid: &Arc<Mutex<Grid>>,
  display: bool,
  run: bool,
  api_return: bool,
) -> Option<String> {
  if !run {
    return None;
  }

  let mut m: MessageBuffer = MessageBuffer::new();

  grid.lock().unwrap().display(&mut m, api_return);

  m.log("log/display/", "display");

  if display {
    m.print();
  }

  if api_return {
    return Some(m.output_as_string());
  }

  None
}

pub fn grid_schema(
  grid: &Arc<Mutex<Grid>>,
  display: bool,
  reverse: bool,
  run: bool,
  api_return: bool,
) -> Option<String> {
  if !run {
    return None;
  }

  let g = grid.lock().unwrap();

  let isles = g.get_isles().clone();

  let mut ids: Vec<(usize, Vec<u16>)> = Vec::new();

  for isle in isles {
    let mut aux: Vec<u16> = Vec::new();

    isle.iter().for_each(|gp| aux.push(*gp.read_number()));

    ids.push((aux.len(), aux));
  }

  let layout = format_vec_of_tuples(&ids);

  let mut m: MessageBuffer = MessageBuffer::new();

  m.push(&format!("{}:\n{}", g.read_name(), {
    if reverse {
      reverse_lines(&layout)
    } else {
      layout
    }
  }));

  m.log("log/grid-schema/", "grid-schema");

  if display {
    m.print();
  }

  if api_return {
    return Some(m.output_as_string());
  }

  None
}

fn format_vec_of_tuples(data: &Vec<(usize, Vec<u16>)>) -> String {
  let mut lines = Vec::new();

  for (_, ids) in data {
    let n = ids.len();
    if n == 0 {
      continue;
    }

    // determine dash length based on number of elements
    let dash = match n {
      1 => "",
      2 => " ------- ",
      3 => " -- ",
      _ => " - ", // fallback for larger
    };

    let line = ids
      .iter()
      .map(|id| id.to_string())
      .collect::<Vec<_>>()
      .join(dash);

    lines.push(line);
  }

  lines.join("\n")
}

fn reverse_lines(s: &str) -> String {
  s.lines() // split by \n
    .rev() // reverse the iterator
    .collect::<Vec<&str>>() // collect into a vector (optional)
    .join("\n") // join back into a string
}

pub fn gaspump_schema(
  grid: &Arc<Mutex<Grid>>,
  display: bool,
  run: bool,
  api_return: bool,
) -> Option<String> {
  if !run {
    return None;
  }

  let g = grid.lock().unwrap();

  let mut aux = "\n".to_string();

  let convert_fuel = |p: Pump| {
    if api_return {
      p.read_fuel_type().api_return_format()
    } else {
      format!("{}", p.read_fuel_type())
    }
  };
  let convert_status = |p: Pump| {
    if api_return {
      p.read_pump_status().api_return_format()
    } else {
      format!("{}", p.read_pump_status())
    }
  };

  g.get_isles().iter().for_each(|vec_gp| {
    vec_gp.iter().for_each(|gp| {
      let info: String = gp
        .get_triggers()
        .iter()
        .map(|p| (p.read_number(), convert_fuel(*p), convert_status(*p)))
        .map(|(a, b, c)| format!("Pump #{a} ({b}) ({c})"))
        .collect::<Vec<String>>()
        .join(" | ");

      aux += "GasPump #";
      aux += &gp.read_number().to_string();
      aux += ":\n|\n|--- [";
      aux += &info;
      aux += "]\n|\n";
    })
  });

  let mut m: MessageBuffer = MessageBuffer::new();

  m.push(&aux);

  m.log("log/gaspump-schema/", "gaspump-schema");

  if display {
    m.print();
  }

  if api_return {
    return Some(m.output_as_string());
  }

  None
}
