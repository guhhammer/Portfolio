// Package station wires the grid, dashboards, price updater, and simulations
// together.
package station

import (
	"gasstation/components/station/dashboards"
	"gasstation/components/station/layouts"
	"gasstation/components/station/simulations"

	priceupdater "gasstation/components/station/priceUpdater"
)

// GridStart loads the pump grid, applies the latest prices, prints the layout,
// and starts the background price updater.
func GridStart(grid *layouts.Grid) {
	grid.Init(true)
	dashboards.SetStart(grid)
	grid.Print()

	go priceupdater.Run(true)
}

// PriceReader keeps the terminal dashboard in sync with price updates.
// Blocks; run it as the foreground loop.
func PriceReader(grid *layouts.Grid) {
	dashboards.PriceReader(grid)
}

// SimulateFlow starts the vehicle-fueling simulation in the background.
func SimulateFlow(grid *layouts.Grid) {
	go simulations.Run(grid)
}
