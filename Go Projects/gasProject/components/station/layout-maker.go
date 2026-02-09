package station

import (
	"gasProject/components/station/dashboards"
	"gasProject/components/station/layouts"
	"gasProject/components/station/simulations"

	priceupdater "gasProject/components/station/priceUpdater"
)

func initializeGrid(grid *layouts.Grid) {

	grid.Init(true)

	dashboards.SetStart(grid)

	grid.Print()

	priceRunner()

}

func priceRunner() {

	go priceupdater.Run(true)

}

func PriceReader(grid *layouts.Grid) {

	dashboards.PriceReader(grid)

}

func GridStart(grid *layouts.Grid) {

	initializeGrid(grid)

}

func SimulateFlow(grid *layouts.Grid) {

	go simulations.Run(grid)

}
