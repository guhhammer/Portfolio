use crate::datastructures::fuel::Fuel;

/*
    ==================  Street
    //////////////////  Sideways
    ------------------  Run 6
      #7         #8               Each Sell Diesel50 And Diesel500 on two triggers.
    ------------------  Run 5
    ------------------  Run 4
      #5         #6               Both Sell Ethanol70 And Gasoline70
    ------------------  Run 3
      #3         #4               Both Sell Ethanol70 And Gasoline70
    ------------------  Run 2
      #1         #2               Both Sell Gasoline70, Gasoline85 And Gasoline95
    ------------------  Run 1
*/

pub fn example_grid() -> Vec<Vec<(Vec<u16>, Vec<Fuel>)>> {
  vec![
    vec![(
      vec![1, 2],
      vec![Fuel::Gasoline70, Fuel::Gasoline85, Fuel::Gasoline95],
    )],
    vec![(vec![3, 4], vec![Fuel::Ethanol70, Fuel::Gasoline70])],
    vec![(vec![5, 6], vec![Fuel::Ethanol70, Fuel::Gasoline70])],
    vec![
      (vec![7], vec![Fuel::Diesel50, Fuel::Diesel50]),
      (vec![8], vec![Fuel::Diesel500, Fuel::Diesel500]),
    ],
  ]
}
