package main

import (
	"gasProject/components/station"
	"gasProject/components/station/layouts"
)

var Grid layouts.Grid

func main() {

	station.GridStart(&Grid)

	//station.PriceReader(&Grid)

	// station.SimulateFlow(&Grid)
	// select {}

}
