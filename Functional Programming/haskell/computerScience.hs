-- Computer Science

--------------------------------------------
-- Funções básicas
-- :type
-- head
-- tail
-- construtor de listas: :
-- concatenação de listas: ++


--------------------------------------------
-- Exemplos de funções

-- Função definida por lambda
soma = \x y -> x + y

maior :: Int -> Int -> Int
maior = \a b -> if a > b then a else b

-- Função definida por declaração
menor :: Int -> Int -> Bool
menor x y = x < y

-- Função (recursiva) definida por pattern matching
fat :: Integer -> Integer
fat 0 = 1
fat n = n * fat (n - 1)

-- Função definida por guards
msign n | n < 0 = -1
        | n == 0 = 0
        | otherwise = 1


-------------------------------------------
-- Exercícios: Implementar as funções a seguir.

-- concat :: String -> String -> String

-- Retornar o maior de três números dados

-- myMap, myFilter, myFold

-- Usando myFold, retornar o maior valor de uma lista de inteiros.

-- Dada uma lista de números reais, multiplicar todos esses números por 1.8 e retornar a soma dos números maiores do que 10.

-- Fibonacci por meio de pattern matching
-- Fibonacci com recursividade de cauda

-- Retornar o caracter situado na posição pos de uma string

-- Retornar True o caracter situado na posição pos de uma string for igual a um valor dado, False caso contrário.

-- Retornar True se todos os valores de uma lista de inteiros forem iguais, False caso contrário.
