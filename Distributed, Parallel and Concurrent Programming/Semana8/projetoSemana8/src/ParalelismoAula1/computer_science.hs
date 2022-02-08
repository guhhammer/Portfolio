
-- :load computer_science.hs

---------------------------------------------------------

module Language where

import IO

---------------------------------------------------------

-- computer science functions
--

pair :: t1 -> t2 -> (t1 -> t2 -> t) -> t
pair = \x y f -> (f x) y

ffalse = \x y -> y
ftrue   = \x y -> x
t_      = \x -> True
f_      = \x -> False

r_process = \proc neuter stop sel next x -> case () of
  _ | stop x -> neuter
    | sel x  -> proc x (r_process proc neuter stop sel next (next x))
    | otherwise -> r_process proc neuter stop sel next (next x)

fat_ = r_process (*) 1 (\n -> n == 0) (\x -> True) pred --(\n -> n - 1)
--r_fat 5

-- itera f n vezes sobre v
iter_ = \f n v ->
    r_process (\n z -> ffalse n (f z)) v (\n -> n == 0) t_ pred n
--iter_ succ 3 2

fold_ = \f v ->
    r_process (\a b -> f (head a) b) v null t_ tail

filter_ = \p ->
    r_process (\a b -> (:) (head a) b) [] null (\l -> p (head l)) tail
--filter_ (menor 4) [1, 2, 3, 4, 5, 6]

map_ = \f ->
    r_process (\a b -> (:) (f (head a)) b) [] null t_ tail
--map_ succ [1, 2, 3]

-- a função stream_ abaixo mostra uma diferença de semântica
-- entre a avaliação lazy do Racket e a do Haskell
-- em Haskell produz-se loop infinito
-- em Racket produz-se uma estrutura lazy
-- em Racket, "cons" é uma operação lazy (é executada sob
-- demanda); em Haskell ":" provoca uma execução imediata, não
-- lazy
-- assim, a semântica lazy das duas linguagens não é a mesma
-- e r-process do Racket não é equivalente a r_process
-- do Haskell
-- o predicado stop = f_ provoca uma execução lazy
-- em Racket e não-lazy em Haskell
stream_ = r_process (:) [] f_ t_

--------------------------------------------------------------------
-- Funções de serviço
--------------------------------------------------------------------

mmsign = \n -> case () of
               _ | n < 0 -> -1
                 | n == 0 -> 0
                 | otherwise -> 1

msign n | n < 0 = -1
        | n == 0 = 0
        | otherwise = 1

soma :: Float -> Float -> Float
soma = \x y -> x + y

somaUm = soma 1
-- \y -> 1 + y

dobro = \x  -> x + x

menor :: Int -> Int -> Bool
menor = \ x y -> x < y

fibn 0 = 0
fibn 1 = 1
fibn n = fibn (n - 1) + fibn (n - 2)

-- "normal factorial", probably tail recursive
fat :: Integer -> Integer
fat 0 = 1
fat n = n * fat (n - 1)

-- tail recursive factorial
fat_tr :: Integer -> Integer -> Integer
fat_tr = \n accm ->
            if n == 0
                then accm
                else fat_tr (n - 1) (n * accm)

----------------------------------------------------------------

-- itera f n vezes sobre x
iter :: Num a => (t -> t) -> a -> t -> t
iter f 0 x = x
iter f n x = f (iter f (n -1) x)

itera :: Num a => (t -> t) -> a -> t -> t
itera = \f n x ->
            if n == 0
                then x
                else f (itera f (n - 1) x)

-- combinador análogo a Y para Haskell
fix :: (a -> a) -> a
fix f = f (fix f)

-- variante de itera para uso com o combinador fix
itr = \itera f n x -> if n == 0 then x else f (itera f (n - 1) x)

concat :: String -> String -> String
concat = \x y -> x ++ y

not_eq :: Float -> Float -> Bool
not_eq = \a b -> a /= b

sign x =
	if x < 0
		then -1
		else
			if x > 0
				then 1
				else 0
--
m_filter pred list =
	if list == []
		then []
		else
			if pred (head list)
				then head list : m_filter pred (tail list)
				else m_filter pred (tail list)
--
-- m_filter (\x -> x > 3) [1, 2, 3, 4, 5, 6, 7]
-- m_filter (\x -> x > 3) [1]
-- m_filter (\x -> x > 3) [1 .. 10]

-- retorna o caracter situado na posição pos de str
m_show :: String -> Int -> Char
m_show = \ str pos ->
            if pos == 0
                then head str
                else  m_show (tail str) (pos - 1)

conc_list :: [Int] -> [Int] -> [Int]
conc_list = \x y -> x ++ y
