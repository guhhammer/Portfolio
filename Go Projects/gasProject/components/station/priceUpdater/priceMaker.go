package priceupdater

import (
	"encoding/json"
	"fmt"
	"math"
	"math/rand"
	"os"
	"path/filepath"
	"runtime"
	"time"
)

type PriceMaker struct {
	Datetime string // "YYYY/MM/DD--HH:MM:SS"
	Prices   map[string]float32
}

//[
//   {
//     "datetime": 8,
//     "Prices": {
//       "Gasoline70": 0.0,
//       "Gasoline85": 0.0,
//       "Gasoline95": 0.0
//       "Ethanol70": 0.0,
//       "Diesel50": 0.0,
//       "Diesel500": 0.0
//   }
//]

type bind struct {
	fuel  string
	price float32
}

func Run(firstRun bool) {

	if firstRun {

		pm := kickstart([6]float32{6.29, 7.10, 8.06, 4.35, 5.10, 5.38})

		err := saveToDB("prices.json", pm)
		fmt.Print(err)

	}

	var history [][6]float32

	history = append(history, [6]float32{6.29, 7.10, 8.06, 4.35, 5.10, 5.38})

	var counter uint = 0

	for {

		var tmp [6]float32

		for i := 0; i < 6; i++ {

			tmp[i] = float32(round(float64(randomize(history[counter][i], 0.01, 0.1)), 2))

		}

		history = append(history, tmp)

		pm := kickstart(tmp)

		err := saveToDB("prices.json", pm)
		fmt.Print(err)

		counter += 1

		min := 20 * time.Second
		max := 40 * time.Second

		// Random duration: min + random 0..(max-min)
		randomDuration := min + time.Duration(rand.Int63n(int64(max-min)))
		time.Sleep(randomDuration)

	}

}

func randomize(price float32, step float32, maxChange float32) float32 {

	rand.Seed(time.Now().UnixNano())

	// Number of steps (e.g., 0.1 / 0.01 = 10)
	nSteps := int(maxChange / step)

	// Random integer from -nSteps to +nSteps
	changeSteps := rand.Intn(2*nSteps+1) - nSteps

	// Apply change
	return price + float32(changeSteps)*step

}

func round(val float64, places int) float64 {
	factor := math.Pow(10, float64(places))
	return math.Round(val*factor) / factor
}

func kickstart(v [6]float32) PriceMaker {

	var pm PriceMaker

	now := time.Now()
	datetimeStr := now.Format("2006/01/02--15:04:05")

	pm.Datetime = datetimeStr

	pm.Prices = make(map[string]float32)

	for _, b := range []bind{
		{"Gasoline70", v[0]},
		{"Gasoline85", v[1]},
		{"Gasoline95", v[2]},
		{"Ethanol70", v[3]},
		{"Diesel50", v[4]},
		{"Diesel500", v[5]},
	} {

		pm.Prices[b.fuel] = float32(round(float64(b.price), 2))

	}

	return pm

}

func saveToDB(filename string, pump PriceMaker) error {
	_, currentFile, _, ok := runtime.Caller(0)
	if !ok {
		return fmt.Errorf("cannot get caller info")
	}
	baseDir := filepath.Dir(currentFile)
	fullPath := filepath.Join(baseDir, filename)

	var all []PriceMaker

	// Check if file exists and read existing data
	if _, err := os.Stat(fullPath); err == nil {
		data, err := os.ReadFile(fullPath)
		if err != nil {
			return err
		}
		if len(data) > 0 {
			if err := json.Unmarshal(data, &all); err != nil {
				return err
			}
		}
	}

	// Append the new entry
	all = append(all, pump)

	// Marshal and write back
	data, err := json.MarshalIndent(all, "", "  ")
	if err != nil {
		return err
	}

	return os.WriteFile(fullPath, data, 0644)
}

func ReadLast(counter *uint, prices *map[string]float32) {

	var last PriceMaker

	for {

		_, currentFile, _, ok := runtime.Caller(0)
		if !ok {
			fmt.Print("cannot get caller info")
		}

		baseDir := filepath.Dir(currentFile)

		// Join the JSON filename with that folder
		fullPath := filepath.Join(baseDir, "prices.json")

		data, err := os.ReadFile(fullPath)
		if err != nil {
			fmt.Print(err)
		}

		var history []PriceMaker

		err = json.Unmarshal(data, &history)
		if err != nil {
			fmt.Print(err)
		}

		if history[len(history)-1].Datetime != last.Datetime {

			last = history[len(history)-1]

			*counter += 1

			*prices = last.Prices

		}

		time.Sleep(5 * time.Second)

	}

}

func StartLast(prices *map[string]float32) {

	var last PriceMaker

	_, currentFile, _, ok := runtime.Caller(0)
	if !ok {
		fmt.Print("cannot get caller info")
	}

	baseDir := filepath.Dir(currentFile)

	// Join the JSON filename with that folder
	fullPath := filepath.Join(baseDir, "prices.json")

	data, err := os.ReadFile(fullPath)
	if err != nil {
		fmt.Print(err)
	}

	var history []PriceMaker

	err = json.Unmarshal(data, &history)
	if err != nil {
		fmt.Print(err)
	}

	last = history[len(history)-1]

	*prices = last.Prices

}
