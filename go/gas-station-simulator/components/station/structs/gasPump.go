package structs

// GasPump is one pump in the station grid: its number, the price table for
// the fuels it sells, and what it is currently dispensing.
type GasPump struct {
	Number        uint8
	Prices        map[string]float32
	CurrentAmount float32
	CurrentFuel   string
}

// CurrentSale is the value of the fuel currently dispensed. The caller must
// hold whatever lock guards the pump while a sale is in progress.
func (g *GasPump) CurrentSale() float32 {
	return g.Prices[g.CurrentFuel] * g.CurrentAmount
}
