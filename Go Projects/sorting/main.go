package main

import (
	"fmt"
	"math/rand"
	"sorting/sorts"
	"time"
)

func makeArr(size int) []int {

	rand.Seed(time.Now().UnixNano())

	arr := rand.Perm(size * 10)

	return arr[:size]

}

func tracker(size int, nThreads int) time.Duration {

	arr := makeArr(size)

	start := time.Now()

	sorts.ParallelMergesort(arr, nThreads)

	end := time.Since(start)

	return end

}

func averageTime(size int, nThreads int, rounds int) {

	fmt.Printf("ParallelMergesort (rounds:%v) [nThreads:%v] Array(size:%v) : [ ", rounds, nThreads, size)

	var totalTime time.Duration = 0

	for i := 0; i < rounds; i++ {

		totalTime += tracker(size, nThreads)

		fmt.Printf("%v ", i)

	}

	fmt.Printf("] = %v ms (elapsed time)\n", totalTime.Milliseconds()/int64(rounds))

}

func loopTest(nThread int, rounds int) {

	var sizes []int = []int{
		10,
		100,
		1000,
		10000,
		100000,
		1000000,   // 1M
		10000000,  // 10M
		100000000, // 100M
		//1000000000, // 1BI
	}

	for _, v := range sizes {

		averageTime(v, nThread, rounds)

	}

}

func main() {

	loopTest(16, 4)

}
