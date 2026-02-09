package main

import (
	"fmt"
	"math/rand"
	"strings"
	"time"

	"github.com/gdamore/tcell/v2"
	"github.com/rivo/tview"
)

type GasPump struct {
	Number uint8
	Prices map[string]float32
}

func main() {
	app := tview.NewApplication()

	pumps := []GasPump{
		{1, map[string]float32{"Gasoline70": 0, "Gasoline85": 0, "Gasoline95": 0}},
		{2, map[string]float32{"Gasoline70": 0, "Gasoline85": 0, "Gasoline95": 0}},
		{3, map[string]float32{"Ethanol70": 0, "Gasoline70": 0}},
		{4, map[string]float32{"Ethanol70": 0, "Gasoline70": 0}},
		{5, map[string]float32{"Ethanol70": 0, "Gasoline70": 0}},
		{6, map[string]float32{"Ethanol70": 0, "Gasoline70": 0}},
		{7, map[string]float32{"Diesel50": 0, "Diesel500": 0}},
		{8, map[string]float32{"Diesel50": 0, "Diesel500": 0}},
		{9, map[string]float32{"Diesel50": 0, "Diesel500": 0}},
	}

	fuels := []string{"Gasoline70", "Gasoline85", "Gasoline95", "Ethanol70", "Diesel50", "Diesel500"}

	table := tview.NewTable().
		SetBorders(true).
		SetFixed(1, 1) // fix header row and column

	// Function to update table
	updateTable := func() {
		// Header row
		table.SetCell(0, 0, tview.NewTableCell("Fuel\\Pump").
			SetTextColor(tcell.ColorWhite).SetAlign(tview.AlignCenter))
		for j, p := range pumps {
			table.SetCell(0, j+1, tview.NewTableCell(fmt.Sprintf("Pump %d", p.Number)).
				SetTextColor(tcell.ColorWhite).SetAlign(tview.AlignCenter))
		}

		// Fuel rows
		for i, fuel := range fuels {
			// fuel name column
			color := tcell.ColorWhite
			switch {
			case strings.HasPrefix(fuel, "Gasoline"):
				color = tcell.ColorBlue
			case strings.HasPrefix(fuel, "Ethanol"):
				color = tcell.ColorGreen
			case strings.HasPrefix(fuel, "Diesel"):
				color = tcell.ColorYellow
			}

			table.SetCell(i+1, 0, tview.NewTableCell(fuel).
				SetTextColor(color).SetAlign(tview.AlignCenter))

			// price columns
			for j, p := range pumps {
				price, ok := p.Prices[fuel]
				cellText := "-"
				if ok {
					cellText = fmt.Sprintf("$%.2f", price)
				}
				table.SetCell(i+1, j+1, tview.NewTableCell(cellText).
					SetTextColor(color).SetAlign(tview.AlignCenter))
			}
		}
	}

	updateTable()

	// Auto-update prices every second
	go func() {
		ticker := time.NewTicker(time.Second).C
		for range ticker {
			for i := range pumps {
				for fuel := range pumps[i].Prices {
					pumps[i].Prices[fuel] += float32(rand.Intn(3)) * 0.01
				}
			}
			app.QueueUpdateDraw(updateTable)
		}
	}()

	// Quit on q
	table.SetInputCapture(func(event *tcell.EventKey) *tcell.EventKey {
		if event.Key() == tcell.KeyRune && event.Rune() == 'q' {
			app.Stop()
			return nil
		}
		return event
	})

	// Set scrollable and expandable
	table.SetSelectable(true, false)

	if err := app.SetRoot(table, true).EnableMouse(true).Run(); err != nil {
		panic(err)
	}
}

//go get github.com/rivo/tview
//go get github.com/gdamore/tcell/v2
