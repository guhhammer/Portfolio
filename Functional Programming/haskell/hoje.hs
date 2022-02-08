

somar :: Integer -> Integer -> Integer

somar x y = x + y


somarVetor :: [Integer] -> Integer -> Integer

somarVetor [] add = add
somarVetor (x:y) add = somarVetor y (somar x add)

somarV:: [Integer] -> Integer 
somarV x = somarVetor x 0


myMap :: (Integer -> Integer) -> [Integer] -> [Integer]

myMap function []   = [] 
myMap function (x:y) = (function x) : (myMap function y)


myReduce :: (a -> b -> b) -> b -> [a] -> b

myReduce exp value [] = value
myReduce exp value (x:y) = exp x (myReduce exp value y)


myFilter :: (a -> Bool) -> [a] -> [a]

myFilter _ [] = []
myFilter p (x:y) = if p x then x : filter p y else filter p y


fat :: Integer -> Integer -> Integer

fat n ret = if n == 0 then ret else fat (n-1) (ret*n)

fatorial = \n -> fat n 1


f:: Float -> Float
f = \n -> n*1.0


--e end start sum = if end == start then sum else e (end) (start+1) (sum+ 1/(fatorial start))

--euler pcs = e pcs 1.0
