# Parallel merge sort (Go)

`sorts/mergesort.go` implements the classic recursive merge sort and a parallel version that splits the array into N chunks, sorts each chunk in its own goroutine (`sync.WaitGroup`), and merges the results. `main.go` builds random arrays of increasing size and times the parallel sort for different thread counts, printing the durations so the speed-up can be compared.

```bash
go run .
```
