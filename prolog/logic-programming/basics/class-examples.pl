% Small class examples from the Logic Programming course, PUCPR (2019):
% facts and rules, arithmetic, comparison, negation as failure and the cut.
% Load with: swipl class-examples.pl

% --- line segments: a segment is vertical when both points share x, horizontal when they share y
vertical(segment(point(X,_), point(X,_))).
horizontal(segment(point(_,Y), point(_,Y))).

% --- arithmetic
sum(X, Y, Z) :- Z is X + Y.

larger(X, Y, X) :- X > Y.
larger(X, Y, Y) :- Y > X.
larger(X, X, 'equal').

smaller(X, Y, X) :- X < Y.
smaller(X, Y, Y) :- Y < X.
smaller(X, X, 'equal').

even(X) :- Z is X mod 2, Z = 0.
odd(X) :- \+ even(X).

average(X, Y, Z) :- Z is (X + Y) / 2.

between_values(X, Y, Z) :- Y =< X, X =< Z.   % X is between Y and Z

same(X, X).

% sign of a number in three bands
band(N, X) :- N < 10, X is -1 ; N >= 10, N < 100, X is 0 ; N >= 100, X is 1.

sign(N, negative) :- N < 0, !.
sign(N, positive) :- N > 0, !.
sign(0, zero).

max(X, Y, X) :- X >= Y, !.
max(_, Y, Y).

% larger/3 written with the cut (the second clause is only reached when X =< Y)
larger_cut(X, Y, X) :- X > Y, !.
larger_cut(_, Y, Y).

% --- population density
population(china, 1600).
population(india, 1300).
population(eu, 350).
population(brazil, 210).
area(china, 11).
area(india, 1.5).
area(eu, 8.6).
area(brazil, 8.5).

density(Country, D) :- population(Country, P), area(Country, A), D is P / A.

% --- uniform motion: final velocity from initial velocity, acceleration and time
velocity(V0, A, T, V) :- V is V0 + A * T.

% --- euclidean distance between two points
squared(X, Y) :- Y is X * X.
distance(X1, Y1, X2, Y2, D) :- squared(X2 - X1, S), squared(Y2 - Y1, T), D is sqrt(S + T).

% --- a family tree
married(john, jenny).
married(john_junior, mary).
married(steve, stacey).
married(ben, anne).
father(john, anne).
father(john, john_junior).
father(john_junior, drake).
father(ben, joe).

mother(X, Y) :- married(Z, X), father(Z, Y).
sibling(X, Y) :- father(Z, X), father(Z, Y), X \= Y.
sibling(X, Y) :- mother(Z, X), mother(Z, Y), X \= Y.
uncle(X, Y) :- sibling(X, Z), father(Z, Y).
uncle(X, Y) :- sibling(X, Z), mother(Z, Y).
grandfather(X, Y) :- father(X, Z), father(Z, Y).
cousin(X, Y) :- father(Z, X), sibling(Z, W), father(W, Y), X \= Y.
cousin(X, Y) :- mother(Z, X), sibling(Z, W), father(W, Y), X \= Y.
brother_in_law(X, Y) :- married(X, Z), sibling(Z, Y).

% --- negation as failure with the cut: not/1 written by hand
my_not(P) :- P, !, fail.
my_not(_).

person(ana).

animal(jaguar).
animal(parrot).
animal(monkey).
animal(dog).
animal(cat).

% ana likes every animal except the monkey
likes(X, Y) :- person(X), animal(Y), my_not(Y = monkey).

% "not" applied to a data value: nobody likes jelly
food(a).
food(b).
food(jelly).
food(d).

likes_food(_, Y) :- food(Y), my_not(Y = jelly).

% --- if-then-else inside a clause
grade(X, 'E') :- 3.4 =< X -> true.
