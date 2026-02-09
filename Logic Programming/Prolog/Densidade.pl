pop(china, 1600).
pop(india, 1300).
pop(eva, 350).
pop(brasil, 210).
area(china, 11).
area(india, 1.5).
area(eva, 8.6).
area(brasil, 8.5).

dens(A, K) :-  pop(A,B), area(A, C), K is B / C.

