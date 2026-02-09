package layouts

import (
	"encoding/json"
	"fmt"
	"gasProject/components/station/structs"
	"os"
	"path/filepath"
	"runtime"
	"strings"
	"time"
)

// This whole data is suppossed to be collected from database and store in a local unchanged/permanent file
// that can only be altered by system admins, user calls team supervisor for changes.

// PLACE THE READDOCS.GO IN UTILS AND THE SCHEMA AS WELL OR UNDER SCHEMA/

/*
	==================  Street
    //////////////////  Sideways
	------------------  Run 6
	  #7         #8               Both Sell Diesel50 And Diesel500
	------------------  Run 5
    ------------------  Run 4
      #5         #6               Both Sell Ethanol70 And Gasoline70
	------------------  Run 3
      #3         #4               Both Sell Ethanol70 And Gasoline70
	------------------  Run 2
	  #1         #2               Both Sell Gasoline70, Gasoline85 And Gasoline95
	------------------  Run 1
*/

type Grid struct {
	gasPumps     []structs.GasPump
	CountPumps   int
	RequestChain chan structs.FuelRequest
}

func (g *Grid) Init(loadLocal bool) {

	if loadLocal {

		grid, err := loadFromLocal("localGrid.json")

		if err != nil {
			fmt.Print(err)
		}

		g.gasPumps = append(g.gasPumps, grid...)

		g.CountPumps = len(g.gasPumps)

	} else {

		grid, err := loadFromDB()

		if err != nil {
			fmt.Print(err)
		}

		g.gasPumps = append(g.gasPumps, grid...)

		g.CountPumps = len(g.gasPumps)

	}

	g.RequestChain = make(chan structs.FuelRequest)

	for i := 0; i < g.CountPumps; i++ {

		go g.runPump(g.gasPumps[i].Number)

	}

}

func (g *Grid) Print() {

	fmt.Println()
	// 1. Collect all fuels to create rows
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

	// 2. Print header
	fmt.Printf("%-12s", "Fuel\\Pump")
	for _, p := range g.gasPumps {
		fmt.Printf("| Pump %-3d ", p.Number)
	}
	fmt.Println()
	fmt.Println(strings.Repeat("-", 12+len(g.gasPumps)*11))

	// 3. Print rows
	for _, fuel := range fuels {
		// Fuel color
		color := "\033[0m"
		switch {
		case strings.HasPrefix(fuel, "Gasoline"):
			color = "\033[31m" // red
		case strings.HasPrefix(fuel, "Ethanol"):
			color = "\033[32m" // green
		case strings.HasPrefix(fuel, "Diesel"):
			color = "\033[33m" // brown/yellow
		}
		fmt.Printf("%s%-12s\033[0m", color, fuel)

		// Print each pump's price
		for _, p := range g.gasPumps {
			price, ok := p.Prices[fuel]

			if !ok {
				fmt.Printf("| %-9s", "-")
			} else {
				fmt.Printf("| $%-6.2f  ", price)
			}
		}
		fmt.Println()
	}

}

// UNSAFE
func (g *Grid) UpdateGrid() {

	// see how to make changes locally and adjust them.

	// make connection to db below:
	saveToDB("localGrid.json", g.gasPumps)

}

func (g *Grid) runPump(pumpName uint8) {

	for req := range g.RequestChain {

		fmt.Printf("Pump #%d: fueling %.2f liters of %s\n", pumpName, req.Liters, req.FuelName)

		time.Sleep(time.Duration(req.Liters*100) * time.Millisecond)
		fmt.Printf("Pump #%d: finished fueling %s\n", pumpName, req.FuelName)

	}

}

func (g *Grid) Fuel(name string, liters float32) {

	g.RequestChain <- structs.FuelRequest{FuelName: name, Liters: liters}

}

func (g *Grid) UpdatePrices(prices map[string]float32) {

	for _, n := range g.gasPumps {

		for i := range n.Prices {

			n.Prices[i] = prices[i]

		}

	}

}

func loadFromDB() ([]structs.GasPump, error) {

	var grid []structs.GasPump

	// make connection to DB.

	// make it write to local file.

	return grid, nil

}

func loadFromLocal(filename string) ([]structs.GasPump, error) {

	_, currentFile, _, ok := runtime.Caller(0)
	if !ok {
		return nil, fmt.Errorf("cannot get caller info")
	}

	baseDir := filepath.Dir(currentFile)

	// Join the JSON filename with that folder
	fullPath := filepath.Join(baseDir, filename)

	data, err := os.ReadFile(fullPath)
	if err != nil {
		return nil, err
	}

	var grid []structs.GasPump

	err = json.Unmarshal(data, &grid)
	if err != nil {
		return nil, err
	}

	return grid, err

}

func saveToDB(filename string, pumps []structs.GasPump) error {
	// Get the folder of this source file
	_, currentFile, _, ok := runtime.Caller(0)
	if !ok {
		return fmt.Errorf("cannot get caller info")
	}
	baseDir := filepath.Dir(currentFile)

	fullPath := filepath.Join(baseDir, filename)

	// Marshal with indentation for readability
	data, err := json.MarshalIndent(pumps, "", "  ")
	if err != nil {
		return err
	}

	// Write file
	err = os.WriteFile(fullPath, data, 0644)
	if err != nil {
		return err
	}

	return nil
}
