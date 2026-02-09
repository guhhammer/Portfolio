package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var m = sync.Mutex{}
var wg = sync.WaitGroup{}
var dbData = []string{"id1", "id2", "id3", "id4", "id5"}
var results = []string{}

func main() {

	execSerialized()

	execConcurrent()

}

func execSerialized() {

	t0 := time.Now()

	for i := 0; i < len(dbData); i++ {
		dbCall(i)
	}

	fmt.Printf("\nTotal execution time: %v\n", time.Since(t0))

}

func dbCall(i int) {

	var delay float32 = rand.Float32() * 2000
	time.Sleep(time.Duration(delay) * time.Millisecond)
	fmt.Println("The result from the database is: ", dbData[i])

}

func execConcurrent() {

	t0 := time.Now()

	for i := 0; i < len(dbData); i++ {
		wg.Add(1)
		go dbCall2(i)
	}
	wg.Wait()
	fmt.Printf("\nTotal execution time: %v\n", time.Since(t0))
	fmt.Printf("\nThe results are %v", results)

}

func dbCall2(i int) {

	var delay float32 = rand.Float32() * 2000
	time.Sleep(time.Duration(delay) * time.Millisecond)
	fmt.Println("The result from the database is: ", dbData[i])
	m.Lock()
	results = append(results, dbData[i])
	m.Unlock()
	wg.Done()

}
