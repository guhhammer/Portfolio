package main

import "fmt"

type gasEngine struct {
	mpg     uint8
	gallons uint8
	owner
	// ownerInfo owner ~> same as accessing ownerInfo.name: ""
	// or owner ~> then the type has direct access to fields of owner type.
	// you could do that to any type like int.
}

type electricEngine struct {
	mpkwh uint8
	kwh   uint8
	owner
	// ownerInfo owner ~> same as accessing ownerInfo.name: ""
	// or owner ~> then the type has direct access to fields of owner type.
	// you could do that to any type like int.
}

type owner struct {
	name string
}

// assigning a method to a struct:

func (e gasEngine) milesLeft() uint16 {
	return uint16(e.gallons) * uint16(e.mpg)
}

func (e electricEngine) milesLeft() uint16 {
	return uint16(e.kwh) * uint16(e.mpkwh)
}

type engine interface {
	milesLeft() uint16
}

func canMakeIt(e engine, miles uint16) {
	if miles <= e.milesLeft() {
		fmt.Println("You can make it there!")
	} else {
		fmt.Println("Need to fuel up first")
	}
}

func main() {

	var myEngine gasEngine
	fmt.Println(myEngine.mpg, myEngine.gallons, myEngine.name)

	var myEngine2 gasEngine = gasEngine{mpg: 25, gallons: 40, owner: owner{"Alex"}} // or gasEngine{25, 40}
	fmt.Println(myEngine2.mpg, myEngine2.gallons, myEngine2.name)

	fmt.Printf("Total miles left in tank: %v\n", myEngine2.milesLeft())

	var myEngine3 electricEngine = electricEngine{10, 10, owner{"Alex"}} // or gasEngine{25, 40}

	canMakeIt(myEngine2, 400)

	canMakeIt(myEngine3, 500)

}
