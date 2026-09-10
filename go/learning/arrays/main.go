package main

import (
	"fmt"
	"time"
)

func main() {

	var intArr [3]int32

	intArr[1] = 132

	fmt.Println(intArr[0])
	fmt.Println(intArr[1:3])

	// int32 is 4 bytes of memory; intArr allocates 12 bytes.

	fmt.Println(&intArr[0])
	fmt.Println(&intArr[1])
	fmt.Println(&intArr[2]) // use & to print address of each slot.

	var intArr2 [3]int32 = [3]int32{1, 2, 3}
	fmt.Println(intArr2)

	//Or intArr3 := [3]int32{1, 2, 3}
	intArr3 := [...]int32{1, 2, 3}
	fmt.Println(intArr3)

	// slices:

	var intSlice []int32 = []int32{4, 5, 6}
	fmt.Printf("The length is %v with capacity %v", len(intSlice), cap(intSlice))

	intSlice = append(intSlice, 7)
	fmt.Printf("The length is %v with capacity %v", len(intSlice), cap(intSlice))

	//fmt.Println(intSlice[4]) // You can't access this value. Index out of range.

	var intSlice2 []int32 = []int32{8, 9}
	intSlice2 = append(intSlice, intSlice2...) // append multiple values using the spread operator.
	fmt.Println(intSlice2)

	var intSlice3 []int32 = make([]int32, 3, 8) // type, length, capacity or just type, length
	fmt.Println(intSlice3)

	// Maps:

	var myMap map[string]uint8 = make(map[string]uint8)
	fmt.Println(myMap)

	var myMap2 = map[string]uint8{"Adam": 23, "Sarah": 45}
	fmt.Println(myMap2["Adam"])
	fmt.Println(myMap2["Jason"])

	var age, ok = myMap2["steve"]

	if ok {

		fmt.Printf("The Age is %v", age)

	} else {

		fmt.Println("Invalid Name")

	}

	myMap2["steve"] = 22

	delete(myMap2, "steve")

	fmt.Println(myMap2)

	for name, age := range myMap2 {

		fmt.Printf("Name: %v, Age: %v \n", name, age)

	}

	for i, v := range intArr {

		fmt.Printf("Index: %v, Value: %v \n", i, v)

	}

	var i, j int = 0, 0

	for i < 10 {

		fmt.Println(i)
		i += 1

	}

	for {

		if j >= 10 {
			break
		}

		fmt.Println(j)

		j++

	}

	for k := 0; k < 10; k++ {

		fmt.Println(k)

	}

	// Testing time for pre-allocating slices and not:

	var n int = 1000000
	var testSlice = []int{}
	var testSlice2 = make([]int, 0, n)

	fmt.Printf("Total time without preallocation: %v\n", timeLoop(testSlice, n))
	fmt.Printf("Total time with preallocation: %v\n", timeLoop(testSlice2, n))

}

func timeLoop(slice []int, n int) time.Duration {

	var t0 = time.Now()

	for len(slice) < n {
		slice = append(slice, 1)
	}

	return time.Since(t0)

}
