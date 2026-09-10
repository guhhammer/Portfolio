// Package layouts models the physical pump grid of the station.
//
// The grid data is meant to be provisioned from a database into a local file
// that only system admins change; operators call their supervisor for edits.
//
//	==================  Street
//	//////////////////  Sideways
//	------------------  Run 6
//	  #7         #8               Both sell Diesel50 and Diesel500
//	------------------  Run 5
//	------------------  Run 4
//	  #5         #6               Both sell Ethanol70 and Gasoline70
//	------------------  Run 3
//	  #3         #4               Both sell Ethanol70 and Gasoline70
//	------------------  Run 2
//	  #1         #2               Both sell Gasoline70, Gasoline85 and Gasoline95
//	------------------  Run 1
package layouts

import (
	"encoding/json"
	"fmt"
	"gasstation/components/station/structs"
	"log"
	"os"
	"path/filepath"
	"runtime"
	"strings"
	"time"
)

type Grid struct {
	gasPumps     []structs.GasPump
	CountPumps   int
	RequestChain chan structs.FuelRequest
}

// Init loads the pump grid (from the local file, or the database when
// loadLocal is false) and starts one goroutine per pump to serve requests.
func (g *Grid) Init(loadLocal bool) {
	load := loadFromDB
	if loadLocal {
		load = func() ([]structs.GasPump, error) { return loadFromLocal("localGrid.json") }
	}

	pumps, err := load()
	if err != nil {
		log.Printf("layouts: could not load grid: %v", err)
	}

	g.gasPumps = append(g.gasPumps, pumps...)
	g.CountPumps = len(g.gasPumps)
	g.RequestChain = make(chan structs.FuelRequest)

	for i := range g.gasPumps {
		go g.runPump(g.gasPumps[i].Number)
	}
}

// Print renders the price grid as a colored fuel × pump table.
func (g *Grid) Print() {
	fmt.Println()

	// Collect all fuels sold anywhere in the grid — they become the rows.
	fuelSet := make(map[string]struct{})
	for _, p := range g.gasPumps {
		for fuel := range p.Prices {
			fuelSet[fuel] = struct{}{}
		}
	}

	fuels := make([]string, 0, len(fuelSet))
	for f := range fuelSet {
		fuels = append(fuels, f)
	}

	fmt.Printf("%-12s", "Fuel\\Pump")
	for _, p := range g.gasPumps {
		fmt.Printf("| Pump %-3d ", p.Number)
	}
	fmt.Println()
	fmt.Println(strings.Repeat("-", 12+len(g.gasPumps)*11))

	for _, fuel := range fuels {
		color := "\033[0m"
		switch {
		case strings.HasPrefix(fuel, "Gasoline"):
			color = "\033[31m" // red
		case strings.HasPrefix(fuel, "Ethanol"):
			color = "\033[32m" // green
		case strings.HasPrefix(fuel, "Diesel"):
			color = "\033[33m" // yellow
		}
		fmt.Printf("%s%-12s\033[0m", color, fuel)

		for _, p := range g.gasPumps {
			if price, ok := p.Prices[fuel]; ok {
				fmt.Printf("| $%-6.2f  ", price)
			} else {
				fmt.Printf("| %-9s", "-")
			}
		}
		fmt.Println()
	}
}

// runPump serves fuel requests for one pump.
func (g *Grid) runPump(pumpNumber uint8) {
	for req := range g.RequestChain {
		fmt.Printf("Pump #%d: fueling %.2f liters of %s\n", pumpNumber, req.Liters, req.FuelName)

		time.Sleep(time.Duration(req.Liters*100) * time.Millisecond)
		fmt.Printf("Pump #%d: finished fueling %s\n", pumpNumber, req.FuelName)
	}
}

// Fuel queues one fueling request; any free pump picks it up.
func (g *Grid) Fuel(name string, liters float32) {
	g.RequestChain <- structs.FuelRequest{FuelName: name, Liters: liters}
}

// UpdatePrices applies a new price table to every pump, for the fuels it sells.
func (g *Grid) UpdatePrices(prices map[string]float32) {
	for _, pump := range g.gasPumps {
		for fuel := range pump.Prices {
			pump.Prices[fuel] = prices[fuel]
		}
	}
}

// Save persists the grid to the local store. Admin-only.
func (g *Grid) Save() error {
	return saveToLocal("localGrid.json", g.gasPumps)
}

// gridPath resolves a grid file relative to this source file.
func gridPath(filename string) (string, error) {
	_, currentFile, _, ok := runtime.Caller(0)
	if !ok {
		return "", fmt.Errorf("cannot get caller info")
	}
	return filepath.Join(filepath.Dir(currentFile), filename), nil
}

// loadFromDB will fetch the grid from the database and mirror it locally.
// Not wired up yet.
func loadFromDB() ([]structs.GasPump, error) {
	return nil, nil
}

func loadFromLocal(filename string) ([]structs.GasPump, error) {
	fullPath, err := gridPath(filename)
	if err != nil {
		return nil, err
	}

	data, err := os.ReadFile(fullPath)
	if err != nil {
		return nil, err
	}

	var grid []structs.GasPump
	if err := json.Unmarshal(data, &grid); err != nil {
		return nil, fmt.Errorf("parse %s: %w", filename, err)
	}

	return grid, nil
}

func saveToLocal(filename string, pumps []structs.GasPump) error {
	fullPath, err := gridPath(filename)
	if err != nil {
		return err
	}

	data, err := json.MarshalIndent(pumps, "", "  ")
	if err != nil {
		return err
	}

	return os.WriteFile(fullPath, data, 0o644)
}
