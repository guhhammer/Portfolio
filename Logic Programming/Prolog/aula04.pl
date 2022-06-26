/*
p(a).
p(b).
p(c).
r(d).
r(e).

p(X,Y) :- p(X), r(Y).

%maior(X,Y,X) :- X>Y.
maior(X,Y,X) :- X>Y,!.
%maior(_,Y,Y).
maior(X,Y,Y) :- Y>=X.


% to cancel trace: notrace.
% to cancel debug: nodebug.

not(P) :- P, !, fail.
not(_).
% swi prolog: not(false).
*/
/*
p(a).
p(b).
p(c).
p(X,Y) :-p(X),r(Y),q(X).
r(a).
r(e) :- !.
r(f).
Q(a) .
q(b).
*/

pessoa(ana).

animal(onca).
animal(papagaio).
animal(macaco).
animal(cachorro).
animal(gato).

eh_macaco(macaco).
not(X) :- X, !, fail.
not(_).
%gosta(X,Y) :- pessoa(X), animal(Y), not(eh_macaco(Y)).
gosta(X,Y) :- pessoa(X), animal(Y), not(Y = macaco).









