-- Hand-written map, reduce and filter, plus sums and a tail-recursive factorial.
-- Functional Programming course, PUCPR (2019).

add :: Integer -> Integer -> Integer
add x y = x + y

sumList :: [Integer] -> Integer -> Integer
sumList [] acc = acc
sumList (x:y) acc = sumList y (add x acc)

sumL :: [Integer] -> Integer
sumL x = sumList x 0

myMap :: (Integer -> Integer) -> [Integer] -> [Integer]
myMap function []   = []
myMap function (x:y) = (function x) : (myMap function y)

myReduce :: (a -> b -> b) -> b -> [a] -> b
myReduce exp value [] = value
myReduce exp value (x:y) = exp x (myReduce exp value y)

myFilter :: (a -> Bool) -> [a] -> [a]
myFilter _ [] = []
myFilter p (x:y) = if p x then x : myFilter p y else myFilter p y

fact :: Integer -> Integer -> Integer
fact n ret = if n == 0 then ret else fact (n-1) (ret*n)

factorial = \n -> fact n 1

f :: Float -> Float
f = \n -> n*1.0
