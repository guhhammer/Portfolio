aa = ["a", "e", "i", "o", "u"]; ab = ["0", "1", "2", "2", "3"]; a = \l (y:z) -> length (filter (\c -> c == l) (y:z)); b = \l -> a l aa;
e = \l (x:y) (z:w) -> if l == x then z else e l y w; f = \l -> e l aa ab; s [] r =  r; s (x:y) r = if (b [x]) == 1 then s y r++(f [x]) else s y r++[x];

-- karaca's encryption algorithm
encrypt = \w -> (s w [])++"aca";
