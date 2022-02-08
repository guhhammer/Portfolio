

fakt = \n -> if (n == 0) then 1 else n * fakt(n-1)


arranjo = \n r -> fakt(n) / fakt(n-r)

combination = \n r -> fakt(n)/(fakt(r) * fakt(n-r))
