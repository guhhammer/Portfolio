

p(a).
p(b).
q(a,1).
q(a,2).
q(b,3).
q(b,4).
r(1,1).
r(1,2).
r(2,3).
r(2,4).
r(3,5).
r(3,6).
r(4,7).
r(4,8).



max(X,Y,X) :- X >= Y,!.
max(X,Y,Y).




conceito(X,Y) :- (3.4 =< X) -> Y = 'E'.



comida(a).
comida(b).
comida(gelatina).
comida(d).

nop(P) :- P, !,fail.

gosta(X,Y) :- nop(Y=gelatina).






xd(X,Y) :- Y = 'asa'.
