package simulations

import (
	"fmt"
	"gasProject/components/station/layouts"
	"math/rand"
	"time"
)

// "Prices": {
//       "Gasoline70": 0.0,
//       "Gasoline85": 0.0,
//       "Gasoline95": 0.0
//       "Ethanol70": 0.0,
//       "Diesel50": 0.0,
//       "Diesel500": 0.0

// Fuel\Pump   | Pump 1   | Pump 2   | Pump 3   | Pump 4   | Pump 5   | Pump 6   | Pump 7   | Pump 8
// ----------------------------------------------------------------------------------------------------
// Diesel500   | -        | -        | -        | -        | -        | -        | $5.32    | $5.32
// Gasoline70  | $6.25    | $6.25    | $6.25    | $6.25    | $6.25    | $6.25    | -        | -
// Gasoline85  | $7.05    | $7.05    | -        | -        | -        | -        | -        | -
// Gasoline95  | $7.96    | $7.96    | -        | -        | -        | -        | -        | -
// Ethanol70   | -        | -        | $4.27    | $4.27    | $4.27    | $4.27    | -        | -
// Diesel50    | -        | -        | -        | -        | -        | -        | $5.01    | $5.01

// Within 15 seconds a new flow.

// pass numbers of pump diesel [7, 8]

// fuelHeads( &grid, diesel [7, 8], range amount [20L - 200L] timeTofuel )

// FuelRequest holds the fuel type and the amount requested.

func fillQueues(grid *layouts.Grid, numberVehicles int, minLiters, maxLiters float32, fuelOptions []string) {
	rand.Seed(time.Now().UnixNano())

	for i := 0; i < numberVehicles; i++ {
		// Pick random fuel from options
		fuel := fuelOptions[rand.Intn(len(fuelOptions))]
		// Random liters
		liters := minLiters + rand.Float32()*(maxLiters-minLiters)

		// Send to the grid
		grid.Fuel(fuel, liters)

		// Optional: simulate random arrival times
		time.Sleep(time.Duration(rand.Intn(3)+1) * time.Second)
	}
}

func Run(grid *layouts.Grid) {
	// Create queues

	// Start filling queues with random fuel assignments
	go fillQueues(grid, 20, 20.0, 200.0, []string{"Diesel50", "Diesel500"})
	go fillQueues(grid, 100, 5.0, 60.0, []string{"Gasoline70", "Gasoline85", "Gasoline95"})
	go fillQueues(grid, 60, 1.0, 40.0, []string{"Ethanol70"})
	fmt.Print("h")
	select {}

}
