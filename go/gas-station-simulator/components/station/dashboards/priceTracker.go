// Package dashboards renders the station's state in the terminal.
package dashboards

import (
	"fmt"
	"gasstation/components/station/layouts"
	"log"
	"time"

	priceupdater "gasstation/components/station/priceUpdater"
)

// PriceReader keeps the on-screen price grid in sync with the price store,
// redrawing every 10 seconds. Blocks; run it as the foreground loop.
func PriceReader(grid *layouts.Grid) {
	var latest priceupdater.LatestPrices
	go priceupdater.Watch(&latest)

	var lastVersion uint

	for {
		if prices, version := latest.Get(); version != lastVersion {
			lastVersion = version
			grid.UpdatePrices(prices)
		}

		fmt.Print("\033[H\033[2J") // clear the terminal
		grid.Print()

		time.Sleep(10 * time.Second)
	}
}

// SetStart applies the latest stored prices to the grid once, at startup.
func SetStart(grid *layouts.Grid) {
	prices, err := priceupdater.Latest()
	if err != nil {
		log.Printf("dashboards: no stored prices yet: %v", err)
		return
	}

	grid.UpdatePrices(prices)
}
