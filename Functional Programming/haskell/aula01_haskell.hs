
-- Nome: Gustavo Hammerschmidt.
-- Aula 01 - Haskell

-- \ <- slidebar stands for lambda

-- soma de dois inteiros
soma = \x y -> x + y

-- power
power = \x y -> if y == 0 then 1 else  x * (power x (y-1) ) 

-- factorial
fact = \x acc -> if x == 0 then 1*acc else (fact (x-1) (x*acc))


-- map para integral: 
mymapintegral :: (a -> b) -> [a] -> [b]
mymapintegral f [] = []
mymapintegral f (x:y) = f x : mymapintegral f y


-- Integral:
integral :: (Float -> Float) -> Float -> Float -> Float -> Float
integral = \equation lim_A lim_B pcs -> (sum (mymapintegral (equation) [pcs, lim_A .. lim_B]))
