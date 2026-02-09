package main

import (
	"errors"
	"fmt"
)

func main() {

	var printValue string = "Hey"

	printMe(printValue)

	var numerator, denominator int = 11, 2

	var result, remainder, err = intDivision(numerator, denominator)

	if err != nil {
		fmt.Println(err.Error())
	} else {
		fmt.Printf("numerator(%v)/denominator(%v) = %v (remainder = %v)", numerator, denominator, result, remainder)
	}

	// && and || for AND and OR operations.

	switch {
	case err != nil:
		fmt.Println(err.Error())
	case remainder == 0:
		fmt.Printf("The result of the integer division is %v", result)
	default:
		fmt.Printf("numerator(%v)/denominator(%v) = %v (remainder = %v)", numerator, denominator, result, remainder)
	}

	switch remainder {
	case 0:
		fmt.Println("The division was exact")
	default:
		fmt.Println("The division was not exact")
	}

	// switch case breaks are implicit.

}

func printMe(printValue string) {
	fmt.Println(printValue)
}

func intDivision(numerator int, denominator int) (int, int, error) {

	var err error

	if denominator == 0 {
		err = errors.New("Cannot Divide by Zero")
		return 0, 0, err
	}

	return numerator / denominator, numerator % denominator, err

}
