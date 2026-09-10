-- 1 + 1/2 + 1/4 + ... + 1/2^n, computed with an accumulator. Functional Programming course, PUCPR (2019).

geometricSum :: Int -> Float -> Float
geometricSum n acc = if n == 0 then acc+1 else (geometricSum (n-1) (acc+(1/(2^n))))
