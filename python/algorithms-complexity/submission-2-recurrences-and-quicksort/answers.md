# Submission 2: recurrences, quicksort cost and Fibonacci (translated)

## 1) T(n) = T(n/2) + 1

```
function T(n)
	if n == 0          # C1 <- 1
		return 1       # C2 <- 1
	else
		return T(int(n/2)) + 1   # C3 <- 1 (linear)
	endif
end
```

T(3) = T(2) + 1 = T(1) + 2 = T(0) + 3 = 4. T(n) = 1 if n = 0, T(n/2) + 1 if n > 0. Each level costs one function call plus one return, so T(n) is bounded by O(n) in this counting (the tight bound is O(log n), since n halves at every call).

## 2) Quicksort

- `partition`: C1 + ... + C9 = 4*C4 + 6 = 4n + 6 = O(n).
- `swap`: 3 instructions, O(1).
- `quick`: C1 + ... + C4 = 2*log(n) + n + 1, giving O(n log n).

Recurrence of quicksort: 2*log(n) + n + 1. Best case: 2*log(n) + n + 1 = O(n log n). Worst case: 2*log(n-1) + O(n) per level = O(n^2). In Big-O notation: O(n log n).

Show that f(n) <= O(g(n)) with g(n) = n^2: logarithmic growth is smaller than polynomial growth (log 10 -> 1 while 10^2 = 100), so f(n) = n log n <= O(n^2).

## 3) Fibonacci

F(0) = 0, F(1) = 1, F(n) = F(n-1) + F(n-2). Expanding the recurrence gives a binary call tree whose leaves are F(0) and F(1); each level roughly doubles the number of calls, which is why the naive recursive version is exponential. The expansion trees for F(4), F(5) and F(6) are sketched in the original notes.
