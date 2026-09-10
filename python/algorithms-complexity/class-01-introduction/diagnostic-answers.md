# Diagnostic assessment: answers (translated)

## Pseudocode

```
function factorial(n)
	if n == 0
		return 1;
	else
		return n * factorial(n-1);
	end if
end

function bin_search(int[] keys, int key, int begin, int end)
	int begin = begin, end = end; // begin <- 0 | end <- keys.length()
	int mid = begin + (end-begin) / 2;
	if keys[mid] == key
		return mid;
	else if keys[mid] > key
		return bin_search(keys, key, 0, mid);
	else
		return bin_search(keys, key, mid, end);
	end if
end
```

## Item 4 (recurrence equations)

I read "recurrence equation" as an instruction that has a chain of events, or a function with a call stack.

**a) factorial:** for n = 3 the call stack is f(3) -> 3 * f(2) -> 2 * f(1) -> 1 * f(0) -> 1. Four stack and four unstack steps, four multiplications; from f(3) four instructions run. Running time: T_stack(n+1) + T_unstack(n+1) + T_mult(n+1) + T_return(n+1). Stated bound in the assessment: O(n!) (the correct bound is O(n); the recursion is linear).

**b) binary search:** on arr = [0..8] searching 3: begin=0, end=9, mid=4 (store: A; decide: B); mid > 3 so recurse on [0, 4] (call: C); mid=2, 2 < 3, recurse on [2, 4]; mid=3, found. A = storage instruction, B = decision instruction, C = return call. O(lg n): 9 values need at most 3 calls to find any value.

## Item 5

Binary search for value 7 in arr = [0..8], drawn as a decision tree: the left comparison value is the array index, the right one the value; the middle comparison runs first; begin and end are tested in the `if`.

## Item 6

Sum of the differences along the path: (0 - 1) + (2 - 1) + (6 - 1) + (8 - 1).
