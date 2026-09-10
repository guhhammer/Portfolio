# Gas station simulator (Go)

A fuel station simulated in the terminal. A grid of pumps is loaded from `localGrid.json`, each pump runs as its own goroutine serving fuel requests from a channel, a background price updater writes randomized market prices to a JSON store every 20 to 40 seconds, and dashboards redraw the grid with the latest prices. A traffic simulation feeds trucks, cars and ethanol fills into the pumps with random arrival gaps.

This is the Go counterpart of the Rust [gas-station API](../../rust/gas-station-api/); the same domain was implemented in both languages to compare them.

## Layout

```
main.go                                   starts the grid and the price updater
components/station/layout-maker.go        wiring: GridStart, PriceReader, SimulateFlow
components/station/layouts/gridMaker.go   Grid: load pumps (local file or DB stub), one goroutine per pump, Fuel() requests, Print()
components/station/priceUpdater/          price snapshots, JSON store (prices.json), Watch/Latest readers
components/station/dashboards/            terminal price dashboard
components/station/simulations/           randomized vehicle traffic
components/station/structs/               GasPump, Fuel, FuelRequest
```

## Run

```bash
go run .
```

Uncomment `station.PriceReader(&grid)` or `station.SimulateFlow(&grid)` in `main.go` to keep the dashboard refreshing or to start the vehicle simulation.
