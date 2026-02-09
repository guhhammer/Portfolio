use crate::datastructures::grid::Grid;
use std::fs::File;
use std::io::BufReader;
use std::io::BufWriter;
use std::sync::{Arc, Mutex};
use std::thread;
use std::time::Duration;

pub fn save_grid_to_json(grid: &Arc<Mutex<Grid>>, filename: &str) -> std::io::Result<()> {
  let file = File::create(filename)?;
  let writer = BufWriter::new(file);

  let g = grid.lock().unwrap();

  serde_json::to_writer_pretty(writer, &*g)?;
  Ok(())
}

pub fn periodic_save_grid(
  grid: &Arc<Mutex<Grid>>,
  filename: String,
  period: u64,
  display: bool,
  run: bool,
) {
  if !run {
    return;
  }

  let grid_clone = Arc::clone(grid); // clone the Arc

  let _handle = thread::spawn(move || {
    loop {
      thread::sleep(Duration::from_secs(period));

      let _ = save_grid_to_json(&grid_clone, &filename);
      if display {
        println!("[GRID SAVED TO FILE: {filename:?}]");
      }
    }
  });
}

pub fn load_grid_from_json(filename: &str) -> std::io::Result<Arc<Mutex<Grid>>> {
  let file = File::open(filename)?;
  let reader = BufReader::new(file);
  let g = serde_json::from_reader(reader)?;

  Ok(Arc::new(Mutex::new(g)))
}
