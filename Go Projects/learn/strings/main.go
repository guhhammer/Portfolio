package main

import "fmt"

func main() {

	var myString = "résumé"
	var indexed = myString[0]

	fmt.Printf("%v, %T\n", indexed, indexed)

	for i, v := range myString {
		fmt.Println(i, v)
	}

	// len() counts the number of bytes in string, not characters.
	fmt.Printf("\nThe length of 'myString' is %v\n", len(myString))

	var myString2 = []rune("résumé")
	var indexed2 = myString2[0]

	fmt.Printf("%v, %T\n", indexed2, indexed2)

	for i, v := range myString2 {
		fmt.Println(i, v)
	}

	// len() counts the number of bytes in string, not characters.
	fmt.Printf("\nThe length of 'myString2' is %v", len(myString2))

	// you can concatenate with + operator, but is more cost effective
	// to use Strings builder library.

}
