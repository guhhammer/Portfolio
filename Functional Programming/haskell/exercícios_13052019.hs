
-- Nome: Gustavo Hammerschmidt.

-------------------------------------------
-- Exercícios: Implementar as funções a seguir.

-- 1: concatenar duas strings 
-- 2: Retornar o maior de três números dados
-- 3: myMap, myFilter, myFold
-- 4: Usando myFold, retornar o maior valor de uma lista de inteiros.
-- 5: Dada uma lista de números reais, multiplicar todos esses números por 1.8 e retornar a soma dos números maiores do que 10.
-- 6: Fibonacci por meio de pattern matching
-- 7: Fibonacci com recursividade de cauda
-- 8: Retornar o caracter situado na posição pos de uma string
-- 9: Retornar True se o  caracter situado na posição pos de uma string for igual a um valor dado, False caso contrário.
-- 10: Retornar True se todos os valores de uma lista de inteiros forem iguais, False caso contrário.
-------------------------------------------


-- escrever no jupyter: 
-- soma :: Integer -> Integer -> Integer
-- soma = \x  -> x + y 
-- soma é uma função composta em duas: uma em x e outra em y  lambda x, lambda y
-- "todas as funções de haskell são unárias ;  binária -> uma unária seguida de uma unária"


--  Exercício 1 ->  Concatena duas strings.
concatenar :: String -> String -> String
concatenar x y = x ++ y
-- exemplo:  concatena "has" "kell"


--  Exercício 2 -> retorna maior de 3 números.
maior2 :: Int -> Int -> Int
maior2 x y = if x > y then x else y

maior :: Int -> Int -> Int -> Int
maior x y z = if x > (maior2 y z) then x else (maior2 y z)
-- exemplo:  maior 2 3 4 


-- Exercício 3: myMap, myFilter, myFold.
myfilter :: (a -> Bool) -> [a] -> [a]
myfilter f (x:y) = if f x then x : rest else rest where rest = myfilter f y
myfilter _ [] = []
-- exemplo:  myfilter (\x -> x > 3) [2,3,5,6,4]


mymap :: (a -> b) -> [a] -> [b]
mymap f [] = []
mymap f (x:y) = f x : mymap f y
-- exemplo:  mymap (\x ->  x * 1.8) [1,2,3,4,5]


myfold :: (a -> b -> b) -> [a] ->  b -> b
myfold _ [] res = res
myfold f (x:y) res = f x (myfold f y res)
-- exemplo:  myfold (+) [1,2,3,4] 0


-- Exercício 4: Usando myFold, retornar o maior valor de uma lista de inteiros.
maior_lista :: [Int] -> Int
maior_lista (x:y) = myfold (\m k -> maior2 m k) (x:y) 0
-- exemplo:  maior_lista [2,3,4,5]


-- Exercício 5: Dada uma lista, multiplicá-la por 1.8 e retornar a soma dos números maiores do que 10.
ex5 :: [Float] -> Float
ex5 = \y -> myfold (+) (myfilter (\y -> y > 10) (mymap (\x -> x * 1.8) y)) 0
-- exemplo:  ex5 [1,2,3,4,5,6,9]

-- Exercício 6: Fibonacci por meio de pattern matching.
fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n = ((fib (n-2)) + (fib (n-1)))
-- exemplo:  fib 5


-- Exercício 7: Fibonacci com recursividade de cauda
fibb n acc a b = if (n == 0) then acc else fibb (n-1) (b) (b) (a+b)
fibt n = fibb n 0 0 1 
-- exemplo:  fibt 5


-- Exercício 8: Retornar o caracter situado na posição pos de uma string.
search_char :: String -> Int -> Char
search_char = \str pos -> str !! pos
-- exemplo:  search_char "Haskell" 0


-- Exercício 9:
check_char :: String -> Int -> Char -> Bool
check_char = \str pos krt ->  if (str !! pos) == krt then True else False
-- exemplo:  check_char "Haskell" 0 'H'


-- Exercício 10: 
igual :: [Float] -> Bool
igual [] = True
igual [x] = True
igual (x:y:z) = if (x == y) then igual (y:z) else False 
-- exemplo:  igual [1,1,1,1]

-- versão 2 do Excercício 10 (usando fold):
igual2 :: [Int] -> Bool
igual2 [] = True
igual2 [x] = True                        -- list comprehension
igual2 (x:y:z) = myfold (\k m -> k == m) [ x == y | x <- (x:y:z)] True
-- exemplo:  igual2 [1,1,1,1]




