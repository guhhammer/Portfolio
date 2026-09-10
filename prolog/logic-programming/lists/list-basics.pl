% List basics: evens, power, last, n-th, merge with sort/4, and fold / reduce with a yall lambda.
% Logic Programming course, PUCPR (2019). Load with: swipl list-basics.pl

even(X) :- (X mod 2) =:= 0.

evens([], []).
evens([X|Y], [X|Z]) :- even(X), evens(Y, Z), !.
evens([_|Y], Z) :- evens(Y, Z).

pow(_, 0, 1).
pow(X, Y, Z) :- Y1 is Y - 1, pow(X, Y1, Z1), Z is Z1 * X, !.

size(A, N) :- length(A, N).

last_element([X], X) :- !.
last_element([_|Z], X) :- last_element(Z, X).

nth_element(N, [X|_], X) :- N =:= 0, !.
nth_element(N, [_|T], X) :- nth_element(N - 1, T, X).

my_append([], List, List).
my_append([Head|Tail], List, [Head|Rest]) :- my_append(Tail, List, Rest).
merge_sorted(L, L2, List) :- my_append(L, L2, Z), sort(0, @=<, Z, List).

% fold(List, Result, Accumulator, Function)
fold([], Acc, Acc, _F).
fold([A|As], B, Acc1, F) :- call(F, Acc1, A, Acc2), fold(As, B, Acc2, F).
reduce([A|As], Bs, F) :- fold(As, Bs, A, F).

% product of a list through reduce and a lambda
product(L, S) :- reduce(L, S, [X,Y,Z]>>(Z is X*Y)).
