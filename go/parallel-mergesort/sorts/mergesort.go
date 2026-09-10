package sorts

import "sync"

func Mergesort(array []int) []int {

	if len(array) <= 1 {

		return array

	}

	mid := len(array) / 2

	left := Mergesort(array[:mid])
	right := Mergesort(array[mid:])

	return merge(left, right)

}

func ParallelMergesort(array []int, nThreads int) []int {
	if nThreads <= 1 || len(array) <= 1 {
		return Mergesort(array)
	}

	at := len(array) / nThreads
	results := make([][]int, nThreads)
	var wg sync.WaitGroup

	for i := 0; i < nThreads; i++ {
		start := i * at
		end := start + at
		if i == nThreads-1 { // last chunk gets the remainder
			end = len(array)
		}
		wg.Add(1)
		go func(idx, s, e int) {
			defer wg.Done()
			results[idx] = Mergesort(array[s:e])
		}(i, start, end)
	}

	wg.Wait()

	// merge all sorted chunks
	sorted := results[0]
	for i := 1; i < nThreads; i++ {
		sorted = merge(sorted, results[i])
	}

	return sorted
}
func merge(left, right []int) []int {

	result := make([]int, 0, len(left)+len(right))

	i, j := 0, 0

	for i < len(left) && j < len(right) {

		if left[i] <= right[j] {

			result = append(result, left[i])

			i++

		} else {

			result = append(result, right[j])

			j++

		}

	}

	result = append(result, left[i:]...)
	result = append(result, right[j:]...)
	return result

}
