a = \x y z -> if y == 0 then z else a x (y-1) (z*x); b = \x y z -> if x == 1 || x == z then y else b x (y++[(a (z+1) 2 1)-(sum y)]) (z+1); 
c = \x -> if x > 0 then putStrLn ("\n\n\t\t"++(show x)++" squared is equal to the sum of the following array:\n\n\t\t\t "++(show (b x [1] 1))++"\n\n") else putStrLn ""; 
sum_of_power = \x -> c x; sop = \x -> c x;