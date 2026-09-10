-- Exercise list of 2019-05-13. Functional Programming course, PUCPR.
--
-- 1: concatenate two strings
-- 2: the largest of three numbers
-- 3: myMap, myFilter, myFold
-- 4: using myFold, the largest value of a list of integers
-- 5: given a list of reals, multiply them all by 1.8 and return the sum of those greater than 10
-- 6: Fibonacci by pattern matching
-- 7: Fibonacci with tail recursion
-- 8: the character at position pos of a string
-- 9: True if the character at position pos of a string equals a given one, False otherwise
-- 10: True if all the values of a list of integers are equal, False otherwise

-- Every Haskell function is unary: a binary function is a unary function returning a unary function.

-- Exercise 1: concatenate two strings.
concatenate :: String -> String -> String
concatenate x y = x ++ y
-- example:  concatenate "has" "kell"

-- Exercise 2: the largest of three numbers.
largest2 :: Int -> Int -> Int
largest2 x y = if x > y then x else y

largest :: Int -> Int -> Int -> Int
largest x y z = if x > (largest2 y z) then x else (largest2 y z)
-- example:  largest 2 3 4

-- Exercise 3: myMap, myFilter, myFold.
myFilter :: (a -> Bool) -> [a] -> [a]
myFilter f (x:y) = if f x then x : rest else rest where rest = myFilter f y
myFilter _ [] = []
-- example:  myFilter (\x -> x > 3) [2,3,5,6,4]

myMap :: (a -> b) -> [a] -> [b]
myMap f [] = []
myMap f (x:y) = f x : myMap f y
-- example:  myMap (\x ->  x * 1.8) [1,2,3,4,5]

myFold :: (a -> b -> b) -> [a] ->  b -> b
myFold _ [] res = res
myFold f (x:y) res = f x (myFold f y res)
-- example:  myFold (+) [1,2,3,4] 0

-- Exercise 4: using myFold, the largest value of a list of integers.
largestOfList :: [Int] -> Int
largestOfList (x:y) = myFold (\m k -> largest2 m k) (x:y) 0
-- example:  largestOfList [2,3,4,5]

-- Exercise 5: multiply a list by 1.8 and sum the numbers greater than 10.
ex5 :: [Float] -> Float
ex5 = \y -> myFold (+) (myFilter (\y -> y > 10) (myMap (\x -> x * 1.8) y)) 0
-- example:  ex5 [1,2,3,4,5,6,9]

-- Exercise 6: Fibonacci by pattern matching.
fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n = ((fib (n-2)) + (fib (n-1)))
-- example:  fib 5

-- Exercise 7: Fibonacci with tail recursion.
fibAcc n acc a b = if (n == 0) then acc else fibAcc (n-1) (b) (b) (a+b)
fibTail n = fibAcc n 0 0 1
-- example:  fibTail 5

-- Exercise 8: the character at position pos of a string.
charAt :: String -> Int -> Char
charAt = \str pos -> str !! pos
-- example:  charAt "Haskell" 0

-- Exercise 9:
checkChar :: String -> Int -> Char -> Bool
checkChar = \str pos c ->  if (str !! pos) == c then True else False
-- example:  checkChar "Haskell" 0 'H'

-- Exercise 10:
allEqual :: [Float] -> Bool
allEqual [] = True
allEqual [x] = True
allEqual (x:y:z) = if (x == y) then allEqual (y:z) else False
-- example:  allEqual [1,1,1,1]

-- Exercise 10, version 2 (with a fold and a list comprehension):
allEqual2 :: [Int] -> Bool
allEqual2 [] = True
allEqual2 [x] = True
allEqual2 (x:y:z) = myFold (\k m -> k == m) [ x == y | x <- (x:y:z)] True
-- example:  allEqual2 [1,1,1,1]
