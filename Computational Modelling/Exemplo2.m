Y  = [1.5, 
      6.5, 
      10, 
      11, 
      11.5, 
      16.5];

X = [1  0 0,
     1  1	2,
     1  1	4,
     1  2	2,
     1  2	4,
     1  3	6];
 

Beta = inv(transpose(X)*X)*transpose(X)*Y