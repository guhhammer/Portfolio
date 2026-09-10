-- Lazy evaluation example: `smallest` only forces the head of the sorted list, so quicksort never
-- sorts the rest; `sumList` is strict because the accumulator is evaluated at every step.
-- Functional Programming course, PUCPR (2019).

quicksort [] = []
quicksort (x:y) = quicksort (filter (< x) y) ++ [x] ++ quicksort (filter (>= x) y)

smallest list = head (quicksort list)

sumAcc :: [Integer] -> Integer -> Integer
sumAcc [] acc = acc
sumAcc (x:y) acc = sumAcc y (acc+x)

sumList :: [Integer] -> Integer
sumList list = sumAcc list 0
