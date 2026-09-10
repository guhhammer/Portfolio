-- Factorial, arrangements (k-permutations) and combinations. Functional Programming course, PUCPR (2019).

fact = \n -> if (n == 0) then 1 else n * fact(n-1)

arrangement = \n r -> fact(n) / fact(n-r)

combination = \n r -> fact(n)/(fact(r) * fact(n-r))
