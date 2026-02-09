package structs

type GasPump struct {
	Number        uint8 // maybe change it for uint16
	Prices        map[string]float32
	CurrentAmount float32
	CurrentFuel   string
}

func (g *GasPump) ReadNumber() uint8 {

	return g.Number

}

func (g *GasPump) SetNumber(n uint8) {

	g.Number = n

}

func (g *GasPump) ReadPrices(fuels []string) map[string]float32 {

	return g.Prices

}

func (g *GasPump) UpdatePrices(fuels []string, prices []float32) {

	for index, name := range fuels {

		g.Prices[name] = prices[index]

	}

}

func (g *GasPump) ReadCurrentAmount() float32 {

	return g.CurrentAmount

}

func (g *GasPump) ReadCurrentFuel() string {

	return g.CurrentFuel

}

// UNSAFE
func (g *GasPump) ReadCurrentSale() float32 {

	return g.Prices[g.CurrentFuel] * g.CurrentAmount

}
