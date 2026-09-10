% Exam practice: the list predicates of the exercise lists rewritten from memory (greater than,
% sum, average, before/after a position, split, sub-list, in order, remove, flatten, intersection,
% run-length encoding and decoding, replicate). Logic Programming course, PUCPR (2019).
% Load with: swipl exam-practice.pl

greater(_, [], []).
greater(N, [X|Y], [X|Z]) :- X > N, greater(N, Y, Z), !.
greater(N, [_|Y], Z) :- greater(N, Y, Z), !.

sum([], 0).
sum([X], X) :- !.
sum([X|Y], S) :- sum(Y, K), S is X + K, !.

greater_than_average(L, Z) :- length(L, N), sum(L, S), greater((S / N), L, Z).

before_acc(_, _, [], []).
before_acc(N, K, [X|Y], [X|Z]) :- N > K, before_acc(N, K + 1, Y, Z).
before_acc(N, K, [_|Y], Z) :- before_acc(N, K + 1, Y, Z).
before(N, L, Z) :- before_acc(N, 0, L, Z), !.

after_acc(_, _, [], []).
after_acc(N, K, [X|Y], [X|Z]) :- N =< K, after_acc(N, K + 1, Y, Z).
after_acc(N, K, [_|Y], Z) :- after_acc(N, K + 1, Y, Z).
after(N, L, Z) :- after_acc(N, 0, L, Z), !.

one_to_n(X, L) :- findall(S, between(1, X, S), L).

neighbours_acc(_, _, [], []).
neighbours_acc(N, K, [X|Y], [X|Z]) :- N =:= K - 1, neighbours_acc(N, K + 1, Y, Z).
neighbours_acc(N, K, [X|Y], [X|Z]) :- N =:= K + 1, neighbours_acc(N, K + 1, Y, Z).
neighbours_acc(N, K, [_|Y], Z) :- neighbours_acc(N, K + 1, Y, Z).
left_and_right(N, L, Z) :- neighbours_acc(N, 0, L, Z), !.

left(_, _, [], []).
left(N, K, [X|Y], [X|Z]) :- N > K, left(N, K + 1, Y, Z), !.
left(N, K, [_|Y], Z) :- left(N, K, Y, Z), !.
right(_, _, [], []).
right(N, K, [X|Y], [X|Z]) :- N =< K, right(N, K + 1, Y, Z), !.
right(N, K, [_|Y], Z) :- right(N, K + 1, Y, Z), !.
split(N, List, A, B) :- left(N, 0, List, A), right(N, 0, List, B), !.

from_a_to_b(A, B, Z) :- findall(X, between(A, B, X), Z).

sizes([], []).
sizes([X|Y], [S|Z]) :- length(X, S), sizes(Y, Z).

% merge two lists into a sorted list, with a hand-written merge or the built-in merge/3
choose([], [], []).
choose(S, [], S).
choose([], S, S).
choose([A|B], [C|D], [A|Z]) :- A =< C, choose(B, [C|D], Z).
choose([A|B], [C|D], [C|Z]) :- C =< A, choose([A|B], D, Z).
merge_sorted(X, Y, Z) :- sort(0, @=<, X, XS), sort(0, @=<, Y, YS), choose(XS, YS, Z), !.
merge_sorted_v2(X, Y, Z) :- sort(0, @=<, X, XS), sort(0, @=<, Y, YS), merge(XS, YS, Z), !.

reverse_list(L, Z) :- reverse(L, Z).

sublist([], _).
sublist([X], Z) :- member(X, Z), !.
sublist([X|Y], Z) :- member(X, Z), sublist(Y, Z), !.

in_order([], _).
in_order([X|Y], [X|Z]) :- in_order(Y, Z), !.
in_order(L, [_|S]) :- in_order(L, S).

remove_first_acc([], _, [], _).
remove_first_acc([X|Y], N, Z, Key) :- N =:= X, Key =:= 0, remove_first_acc(Y, N, Z, 1).
remove_first_acc([X|Y], N, [X|Z], Key) :- remove_first_acc(Y, N, Z, Key).
remove_first(L, N, Z) :- remove_first_acc(L, N, Z, 0), !.

remove_every([], _, []).
remove_every([X|Y], N, [X|Z]) :- N =\= X, remove_every(Y, N, Z).
remove_every([_|Y], N, Z) :- remove_every(Y, N, Z).
remove_all(L, N, Z) :- remove_every(L, N, Z), !.

remove_at_acc([], _, [], _).
remove_at_acc([_|Y], N, Z, K) :- K =:= N, remove_at_acc(Y, N, Z, K + 1).
remove_at_acc([X|Y], N, [X|Z], K) :- remove_at_acc(Y, N, Z, K + 1).
remove_at(L, N, Z) :- remove_at_acc(L, N, Z, 0), !.

check_list(X) :- is_list(X), !.

flatten_list(L, Z) :- flatten(L, Z).

inter([], _, []).
inter([X|Y], L, [X|Z]) :- member(X, L), inter(Y, L, Z).
inter([_|Y], L, Z) :- inter(Y, L, Z).
intersection_of(L, L2, Z) :- inter(L, L2, Z), !.

find([], _, _, []).
find([X|Y], N, K, [X|Z]) :- N =:= K, find(Y, N, K + 1, Z).
find([_|Y], N, K, Z) :- find(Y, N, K + 1, Z).
run([], _, []).
run([X|Y], K, [N|Z]) :- find(X, K, 0, N), run(Y, K, Z).
elements_at(List, K, Z) :- run(List, K, Z), !.

block([], []).
block([X], [[X]]).
block([X, Y|Z], [[X]|R]) :- X \= Y, block([Y|Z], R).
block([X, X|Y], [[X|Z]|T]) :- block([X|Y], [Z|T]).
blocks(List, Z) :- block(List, Z), !.

code([], []).
code([[X|Y]|Z], [[X,N]|T]) :- length([X|Y], N), code(Z, T).
encode(List, Z) :- code(List, Z).

repli([], [], _, _).
repli([X|Y], [X|Z], N, K) :- K > 0, K1 is K - 1, repli([X|Y], Z, N, K1).
repli([_|Y], Z, N, 0) :- repli(Y, Z, N, N).
replicate(List, N, Z) :- repli(List, Z, N, N), !.

decode_blocks([], []).
decode_blocks([[X|Y]|Z], [[N]|T]) :- replicate([X], Y, N), decode_blocks(Z, T).
decode(List, Z) :- decode_blocks(List, X), flatten(X, Z).

remove_first_member(X, List, Z) :- member(X, List), select(X, List, Z), !.
remove_first_member(_, List, List).
