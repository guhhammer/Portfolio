// Package simulations feeds the station with randomized vehicle traffic.
package simulations

import (
	"gasstation/components/station/layouts"
	"math/rand"
	"time"
)

// fillQueues sends numberVehicles randomized fuel requests into the grid,
// with random arrival gaps between them.
func fillQueues(grid *layouts.Grid, numberVehicles int, minLiters, maxLiters float32, fuelOptions []string) {
	for i := 0; i < numberVehicles; i++ {
		fuel := fuelOptions[rand.Intn(len(fuelOptions))]
		liters := minLiters + rand.Float32()*(maxLiters-minLiters)

		grid.Fuel(fuel, liters)

		time.Sleep(time.Duration(rand.Intn(3)+1) * time.Second)
	}
}

// Run simulates a day of traffic: trucks on diesel, cars on gasoline, and a
// lighter flow of ethanol fills. Blocks forever.
func Run(grid *layouts.Grid) {
	go fillQueues(grid, 20, 20.0, 200.0, []string{"Diesel50", "Diesel500"})
	go fillQueues(grid, 100, 5.0, 60.0, []string{"Gasoline70", "Gasoline85", "Gasoline95"})
	go fillQueues(grid, 60, 1.0, 40.0, []string{"Ethanol70"})

	select {}
}
