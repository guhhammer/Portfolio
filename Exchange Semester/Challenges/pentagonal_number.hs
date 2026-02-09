r = \x y c -> if y == 0 then c else r x (y-1) (c*x); d = \x y -> r x y 1;

-- Pentagonal Number
pn = \x ->  if x > 0 then ((3 * (d x 2)) - x) / 2 else -1;

-- Pentagonal sum
pns = \x -> if x == 0 then 0 else (pn x) + (pns (x-1));