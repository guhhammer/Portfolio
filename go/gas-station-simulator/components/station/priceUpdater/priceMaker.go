// Package priceupdater simulates market price fluctuations and persists them
// to a local JSON store (stand-in for a real database).
package priceupdater

import (
	"encoding/json"
	"fmt"
	"log"
	"math"
	"math/rand"
	"os"
	"path/filepath"
	"runtime"
	"sync"
	"time"
)

// PriceMaker is one snapshot of the station's price table.
type PriceMaker struct {
	Datetime string // "YYYY/MM/DD--HH:MM:SS"
	Prices   map[string]float32
}

// The fuels sold by the station, in the order used by the seed price vectors.
var fuelNames = [6]string{
	"Gasoline70", "Gasoline85", "Gasoline95", "Ethanol70", "Diesel50", "Diesel500",
}

var seedPrices = [6]float32{6.29, 7.10, 8.06, 4.35, 5.10, 5.38}

// Run generates a new randomized price snapshot every 20–40 seconds and
// appends it to the store. Blocks; run it on its own goroutine.
func Run(firstRun bool) {
	if firstRun {
		if err := saveToStore("prices.json", snapshot(seedPrices)); err != nil {
			log.Printf("priceupdater: %v", err)
		}
	}

	current := seedPrices

	for {
		for i := range current {
			current[i] = float32(round(float64(randomize(current[i], 0.01, 0.1)), 2))
		}

		if err := saveToStore("prices.json", snapshot(current)); err != nil {
			log.Printf("priceupdater: %v", err)
		}

		minWait, maxWait := 20*time.Second, 40*time.Second
		time.Sleep(minWait + time.Duration(rand.Int63n(int64(maxWait-minWait))))
	}
}

// randomize moves a price by a random number of steps within ±maxChange.
func randomize(price, step, maxChange float32) float32 {
	nSteps := int(maxChange / step)
	changeSteps := rand.Intn(2*nSteps+1) - nSteps
	return price + float32(changeSteps)*step
}

func round(val float64, places int) float64 {
	factor := math.Pow(10, float64(places))
	return math.Round(val*factor) / factor
}

// snapshot builds a timestamped price table from a price vector.
func snapshot(v [6]float32) PriceMaker {
	pm := PriceMaker{
		Datetime: time.Now().Format("2006/01/02--15:04:05"),
		Prices:   make(map[string]float32, len(fuelNames)),
	}

	for i, name := range fuelNames {
		pm.Prices[name] = float32(round(float64(v[i]), 2))
	}

	return pm
}

// storePath resolves a store file relative to this source file, so the JSON
// lives next to the package regardless of the working directory.
func storePath(filename string) (string, error) {
	_, currentFile, _, ok := runtime.Caller(0)
	if !ok {
		return "", fmt.Errorf("cannot get caller info")
	}
	return filepath.Join(filepath.Dir(currentFile), filename), nil
}

// saveToStore appends one snapshot to the JSON store.
func saveToStore(filename string, pm PriceMaker) error {
	fullPath, err := storePath(filename)
	if err != nil {
		return err
	}

	var all []PriceMaker

	if data, err := os.ReadFile(fullPath); err == nil && len(data) > 0 {
		if err := json.Unmarshal(data, &all); err != nil {
			return fmt.Errorf("parse %s: %w", filename, err)
		}
	}

	all = append(all, pm)

	data, err := json.MarshalIndent(all, "", "  ")
	if err != nil {
		return err
	}

	return os.WriteFile(fullPath, data, 0o644)
}

// loadLatest reads the newest snapshot from the store.
func loadLatest() (PriceMaker, error) {
	fullPath, err := storePath("prices.json")
	if err != nil {
		return PriceMaker{}, err
	}

	data, err := os.ReadFile(fullPath)
	if err != nil {
		return PriceMaker{}, err
	}

	var history []PriceMaker
	if err := json.Unmarshal(data, &history); err != nil {
		return PriceMaker{}, fmt.Errorf("parse prices.json: %w", err)
	}
	if len(history) == 0 {
		return PriceMaker{}, fmt.Errorf("prices.json is empty")
	}

	return history[len(history)-1], nil
}

// LatestPrices shares the most recent price table between goroutines safely.
type LatestPrices struct {
	mu      sync.Mutex
	version uint
	prices  map[string]float32
}

// Get returns the current price table and its version. The version changes
// whenever a new snapshot lands, so callers can cheaply detect updates.
func (l *LatestPrices) Get() (map[string]float32, uint) {
	l.mu.Lock()
	defer l.mu.Unlock()
	return l.prices, l.version
}

func (l *LatestPrices) set(prices map[string]float32) {
	l.mu.Lock()
	defer l.mu.Unlock()
	l.prices = prices
	l.version++
}

// Watch polls the store and publishes new snapshots into l.
// Blocks; run it on its own goroutine.
func Watch(l *LatestPrices) {
	var lastSeen string

	for {
		if latest, err := loadLatest(); err != nil {
			log.Printf("priceupdater: %v", err)
		} else if latest.Datetime != lastSeen {
			lastSeen = latest.Datetime
			l.set(latest.Prices)
		}

		time.Sleep(5 * time.Second)
	}
}

// Latest returns the newest price table straight from the store.
func Latest() (map[string]float32, error) {
	latest, err := loadLatest()
	if err != nil {
		return nil, err
	}
	return latest.Prices, nil
}
