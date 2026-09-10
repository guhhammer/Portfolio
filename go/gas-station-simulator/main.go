// gasstation simulates a fuel station: a grid of pumps with live prices,
// a price updater that writes market fluctuations to a local JSON store,
// and terminal dashboards that track prices and fuel flow.
package main

import (
	"gasstation/components/station"
	"gasstation/components/station/layouts"
)

func main() {
	var grid layouts.Grid

	station.GridStart(&grid)

	// Optional live views; enable as needed:
	// station.PriceReader(&grid)   // refreshes the price dashboard
	// station.SimulateFlow(&grid)  // simulates vehicles fueling
	// select {}
}
