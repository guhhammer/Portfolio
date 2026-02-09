



quicksort [] = []
quicksort (x:y) = quicksort (filter (< x) y) ++ [x] ++ quicksort (filter (>= x) y)


menor lista = head (quicksort lista)




somar :: [Integer] -> Integer -> Integer
somar [] acc = acc
somar (x:y) acc = somar y (acc+x)


somar_lista :: [Integer] -> Integer
somar_lista lista = somar lista 0