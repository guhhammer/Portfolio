-- First Haskell class: functions as lambdas, recursion, a hand-written map and a Riemann-style sum.
-- Functional Programming course, PUCPR (2019).

-- \ stands for lambda

-- sum of two integers
add = \x y -> x + y

-- power
power = \x y -> if y == 0 then 1 else x * (power x (y-1))

-- factorial with an accumulator
fact = \x acc -> if x == 0 then 1*acc else (fact (x-1) (x*acc))

-- map used by the integral:
myMap :: (a -> b) -> [a] -> [b]
myMap f [] = []
myMap f (x:y) = f x : myMap f y

-- integral: sums the function over the points [step, lowerBound .. upperBound]
integral :: (Float -> Float) -> Float -> Float -> Float -> Float
integral = \equation lowerBound upperBound step -> (sum (myMap (equation) [step, lowerBound .. upperBound]))
