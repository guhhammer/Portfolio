package structs

type Fuel struct {
	Name string
}

func (f *Fuel) ReadFuel() string {

	return f.Name

}
