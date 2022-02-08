a = \x -> show x; b = \(x:y) z w -> if z == w then x else b y z (w+1); c = \(x:y) i -> b (x:y) i 0; d = \ch (x:y) -> if ch == show x then x else d ch y;
e = \c -> d c [0,1,2,3,4,5,6,7,8,9]; f = \x y c -> if length (a x) == y then c else f x (y+1) (c++[[c (a x) y]]); k = \x -> f x 0 []; g [] r = r;
g (x:y) r = g y (r++[(e x)]); h = \(x:y) -> g (x:y) []; p _ 0 z = z; p x y z = p x (y-1) z*x; q x y = p x y 1; l [] z = z; l (x:y) z = l y z+x;
m [] w z = z; m (x:y) w z = m y w z++[q x w]; armstrong_number = \n b -> n == (l (m (h (k n)) b []) 0); ann = \n b -> armstrong_number n b;