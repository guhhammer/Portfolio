package dashboards

import (
	"fmt"
	"gasProject/components/station/layouts"
	priceupdater "gasProject/components/station/priceUpdater"
	"time"
)

func PriceReader(grid *layouts.Grid) {

	var counter uint = 0

	var prices map[string]float32

	go priceupdater.ReadLast(&counter, &prices)

	var last_counter uint = counter

	for {

		if counter != last_counter {

			last_counter = counter

			grid.UpdatePrices(prices)

		}

		fmt.Print("\033[H\033[2J")
		grid.Print()

		time.Sleep(10 * time.Second)

	}

}

func SetStart(grid *layouts.Grid) {

	var prices map[string]float32

	priceupdater.StartLast(&prices)

	fmt.Print(prices)

	grid.UpdatePrices(prices)

}
