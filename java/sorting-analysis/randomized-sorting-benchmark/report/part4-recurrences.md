# Part 4 - recurrences

Recursive merge sort (RM), random recursive merge sort (RRM), recursive quicksort (RQ) and random recursive quicksort (RRQ):

```
T(0) = 1
T(n) = 2 T(n/2) + n        =>  O(n log n)
```

Recursive selection sort (RS) and random recursive selection sort (RRS):

```
T(0) = 1
T(n) = T(n-1) + n          =>  O(n²)
```
