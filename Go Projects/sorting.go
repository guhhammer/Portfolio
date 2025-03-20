package main

import (
	"fmt"
	random "math/rand"
	"strings"
	"time"
)

const threshold = 10_000 // Threshold for sequential sorting
var currTime time.Time
var exTime time.Duration

var sortingMethods = map[string]func([]int) []int{
	"bubblesort":    bubblesort,
	"insertionsort": insertionsort,
	"mergesort":     mergesort,
	"quicksort":     quicksort,
	"heapsort":      heapsort,
}

var sortingLimits = map[string][2]int{ // maxtime or maxelem
	"bubblesort":    {20, 100_000},    // 100_000 ~ 14s
	"insertionsort": {20, 200_000},    // 200_000 ~ 6s
	"mergesort":     {20, 50_000_000}, // 50_000_000 ~ 4s
	"quicksort":     {50, 50_000_000}, // 50_000_000 ~ 25s
	"heapsort":      {50, 50_000_000}, // 500_000_000 ~ 4m45s
}

var arrSizeTests = [...]int{
	10, 100, 1_000, 10_000, 50_000, 100_000, 200_000, 500_000,
	1_000_000, 2_000_000, 5_000_000, 10_000_000, 20_000_000,
	50_000_000, 100_000_000, 200_000_000, 500_000_000,
	1_000_000_000,
}

type info struct {
	MethodName     string
	ArraySize      int
	GenerationTime time.Duration
	executionTime  time.Duration
}

var stats, counter = make(map[int]info), 0

func main() {

	for method, _ := range sortingMethods {

		runner(method, 1)

	}

	for i := 0; i < counter; i++ {

		fmt.Println(stats[i].MethodName, stats[i].ArraySize, stats[i].GenerationTime, stats[i].executionTime)

	}

}

func generateArr(arrSize int) []int {

	arr := make([]int, arrSize)

	for i := 0; i < arrSize; i++ {

		arr[i] = random.Intn(arrSize * 10)

	}

	return arr

}

func runExecutionSortFor(method string, arrSize int) {

	fmt.Printf("\n%s Algorithm test:\n", strings.Title(method))

	fmt.Printf("\n\tArray Size: %d\n", arrSize)

	currTime = time.Now()

	arr := generateArr(arrSize)

	exTime = time.Since(currTime)

	aux := exTime

	fmt.Printf("\n\tGeneration Time: %s\n", exTime)

	currTime = time.Now()

	if methodCaller, ok := sortingMethods[method]; ok {

		arr = methodCaller(arr)

		exTime = time.Since(currTime)

	} else {
		fmt.Print("\n\tInvalid sorting algorithm specified.")
	}

	fmt.Printf("\n\tExecution Time: %s\n", exTime)

	stats[counter] = info{
		MethodName:     method,
		ArraySize:      arrSize,
		GenerationTime: aux,
		executionTime:  exTime,
	}

	counter++

}

func mergesort(arr []int) []int {

	if len(arr) < threshold {
		return sequentialMergeSort(arr)
	}

	leftCh := make(chan []int)
	rightCh := make(chan []int)

	mid := len(arr) / 2

	go func() { leftCh <- mergesort(arr[:mid]) }()
	go func() { rightCh <- mergesort(arr[mid:]) }()

	left := <-leftCh
	right := <-rightCh

	return merge(left, right)
}

func merge(left, right []int) []int {

	final := make([]int, 0, len(left)+len(right))
	l, r := 0, 0

	for l < len(left) && r < len(right) {
		if left[l] < right[r] {
			final = append(final, left[l])
			l++
		} else {
			final = append(final, right[r])
			r++
		}
	}

	final = append(final, left[l:]...)
	final = append(final, right[r:]...)

	return final

}

func sequentialMergeSort(arr []int) []int {

	if len(arr) <= 1 {
		return arr
	}

	mid := len(arr) / 2
	left := sequentialMergeSort(arr[:mid])
	right := sequentialMergeSort(arr[mid:])

	return merge(left, right)
}

func bubblesort(arr []int) []int {

	for i := 0; i < len(arr); i++ {

		for j := i; j < len(arr); j++ {

			if arr[i] > arr[j] {

				arr[i], arr[j] = arr[j], arr[i]

			}

		}
	}

	return arr

}

func insertionsort(arr []int) []int {

	for i := 1; i < len(arr); i++ {

		key := arr[i]
		j := i - 1
		for j >= 0 && key < arr[j] {
			arr[j+1] = arr[j]
			j -= 1
		}
		arr[j+1] = key

	}

	return arr
}

func quicksort(arr []int) []int {

	if len(arr) <= 1 {
		return arr
	}

	pivot := arr[len(arr)/2]
	var left, middle, right []int

	for _, value := range arr {
		switch {
		case value < pivot:
			left = append(left, value)
		case value == pivot:
			middle = append(middle, value)
		case value > pivot:
			right = append(right, value)
		}
	}

	left = quicksort(left)
	right = quicksort(right)

	return append(append(left, middle...), right...)

}

func heapify(arr []int, n, i int) {

	largest, left, right := i, 2*i+1, 2*i+2

	if left < n && arr[left] > arr[largest] {
		largest = left
	}
	if right < n && arr[right] > arr[largest] {
		largest = right
	}
	if largest != i {
		arr[i], arr[largest] = arr[largest], arr[i]
		heapify(arr, n, largest)
	}

}

func heapsort(arr []int) []int {
	n := len(arr)

	for i := n/2 - 1; i > -1; i-- {
		heapify(arr, n, i)
	}

	for i := n - 1; i > -1; i-- {
		arr[i], arr[0] = arr[0], arr[i]
		heapify(arr, i, 0)
	}

	return arr
}

func tracker(method string, arrSize, mode int, limit bool) bool {

	done := make(chan struct{})

	if limit && arrSize > mode {
		return false
	}

	go func() {
		runExecutionSortFor(method, arrSize)
		done <- struct{}{}
	}()

	select {
	case <-done:
		return true
	case <-time.After(time.Duration(mode) * time.Second):
		fmt.Printf("\n\t\033[31mSorting exceeded %d seconds. Stopping execution.\033[0m\n", mode)
		return false
	}

}

func runner(method string, mode int) {

	for i := range arrSizeTests {

		if !tracker(method, arrSizeTests[i], sortingLimits[method][mode], true) {
			break
		}

	}
}
