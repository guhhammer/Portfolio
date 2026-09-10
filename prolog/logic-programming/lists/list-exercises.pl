% Exercise list 6: 25 list-processing predicates written without length/2, member/2, reverse/2 or
% flatten/2 (own helpers instead). Group 12: Gustavo Hammerschmidt, Guilherme Fenner Hey, Bruno Alves,
% Pedro Churata. Logic Programming course, PUCPR (2019). Load with: swipl list-exercises.pl
% Each question has a question_N/... predicate that prints the result.

% helper: length of a list
list_size([], 0).
list_size([_|Y], X) :- list_size(Y, W), X is W + 1.

% helper: does the number N belong to the list? (third argument is true/false)
belongs([], _, false) :- !.
belongs([X], X, true) :- !.
belongs([X], N, false) :- X =\= N, !.
belongs([X|Y], N, Z) :- X =\= N, belongs(Y, N, Z), !.
belongs([X|_], N, Z) :- X =:= N, Z = true.
is_member(S, L, M) :- belongs(L, S, M).

% Question 1. greater([1,2,3,4,5,6,7], 5, N) -> N = [6,7]
greater([], [], _).
greater([X|Y], [X|Z], N) :- X > N, greater(Y, Z, N), !.
greater([_|Y], Z, N) :- greater(Y, Z, N).
question_1(Num, List, Z) :-
    nl, write('Elements greater than '), write(Num), write(' in the list '), write(List),
    write(' : '), greater(List, Z, Num), nl.

% Question 2. average of a list
add_all([], 0).
add_all([X], X) :- !.
add_all([X|Y], S) :- add_all(Y, K), S is X + K, !.

sum(List, Sum) :- add_all(List, Sum).
size(X, Y) :- list_size(X, Y).

average(List, Avg) :-
    sum(List, S), size(List, N),
    N =\= 0, Avg is S / N, !.
average(_, 0).
question_2(L, S) :-
    nl, write('Average of the elements of the list '), write(L), write(' : '),
    average(L, S), nl.

% Question 3. elements greater than the arithmetic mean
greater_than_average(List, Z) :- average(List, Avg), greater(List, Z, Avg).
question_3(L, Z) :-
    nl, write('Elements greater than the average of the list ('),
    average(L, Aux), write(Aux), write(') in the list '), write(L),
    write(' : '), greater_than_average(L, Z), nl.

% Question 4. elements before position N
before_pos([], [], _, _).
before_pos([X|Y], [X|Z], N, Count) :- Count < N, before_pos(Y, Z, N, Count + 1), !.
before_pos([_|Y], Z, N, Count) :- before_pos(Y, Z, N, Count + 1).
before(Num, List, Z) :- before_pos(List, Z, Num, 0).
question_4(N, L, X) :-
    nl, write('Elements before position '), write(N), write(' in the list '), write(L),
    write(' : '), before(N, L, X), nl.

% Question 5. elements from position N on (N included)
after_pos([], [], _, _).
after_pos([X|Y], [X|Z], N, Count) :- Count >= N, after_pos(Y, Z, N, Count + 1), !.
after_pos([_|Y], Z, N, Count) :- after_pos(Y, Z, N, Count + 1).
after(Num, List, Z) :- after_pos(List, Z, Num, 0).
question_5(N, L, X) :-
    nl, write('Elements after position '), write(N), write(' in the list '), write(L),
    write(' : '), after(N, L, X), nl.

% Question 6. the list 1..N
count_down(0, []) :- !.
count_down(X, [X|L]) :- X1 is X - 1, count_down(X1, L), !.
one_to_n(N, []) :- 1 > N, !.
one_to_n(N, Z) :- count_down(N, Aux), reverse_list(Aux, Z).   % reverse_list is question 12
question_6(N, L) :-
    nl, write('List from 1 to '), write(N), write(': '), one_to_n(N, L), nl.

% Question 7. the elements left and right of position N
neighbours([], [], _, _).
neighbours([X|Y], [X|Z], N, Count) :- Count =:= N - 1, neighbours(Y, Z, N, Count + 1), !.
neighbours([X|Y], [X|Z], N, Count) :- Count =:= N + 1, neighbours(Y, Z, N, Count + 1), !.
neighbours([_|Y], Z, N, Count) :- neighbours(Y, Z, N, Count + 1).
left_and_right(X, List, Z) :- neighbours(List, Z, X, 0).
question_7(N, L, X) :-
    nl, write('Elements to the left and right of position '), write(N),
    write(' in the list '), write(L), write(' : '), left_and_right(N, L, X), nl.

% Question 8. split a list in two at position N
left_part([], [], _, _).
left_part([X|Y], [X|Z], N, Count) :- Count < N, left_part(Y, Z, N, Count + 1), !.
left_part([_|Y], Z, N, Count) :- left_part(Y, Z, N, Count + 1).
right_part([], [], _, _).
right_part([X|Y], [X|Z], N, Count) :- Count >= N, right_part(Y, Z, N, Count + 1), !.
right_part([_|Y], Z, N, Count) :- right_part(Y, Z, N, Count + 1).
split(X, List, A, B) :- left_part(List, A, X, 0), right_part(List, B, X, 0).
question_8(X, L, A, B) :-
    nl, write('Split the list '), write(L), write(' in two at position '), write(X),
    write('. '), split(X, L, A, B), nl.

% Question 9. the interval A..B
count_down_to(X, N, []) :- X - 1 =:= N, !.
count_down_to(X, N, [N|L]) :- N1 is N - 1, count_down_to(X, N1, L).
interval(A, B, []) :- A > B, !.
interval(A, B, List) :- count_down_to(A, B, Aux), reverse_list(Aux, List).
question_9(A, B, L) :-
    nl, write('List from '), write(A), write(' to '), write(B), write(': '), interval(A, B, L), nl.

% Question 10. the length of each element (a list of lists)
sizes([], _).
sizes([Y], [A]) :- list_size(Y, A), !.
sizes([X|Y], [A|B]) :- list_size(X, A), sizes(Y, B), !.
element_sizes(List, N) :- sizes(List, N).
question_10(L, N) :-
    nl, write('Sizes of the elements of the list '), write(L), write(' : '), element_sizes(L, N), nl.

% Question 11. merge two lists into one sorted list (selection sort + merge, no sort/2)
min([], X, X).
min([H|T], M, X) :- H =< M, min(T, H, X).
min([H|T], M, X) :- M < H, min(T, M, X).
minimum([H|T], X) :- min(T, H, X).

ascending([], []).
ascending([X], [X]) :- !.
ascending([X|Y], [Aux|Z]) :-
    minimum([X|Y], Aux), remove_first(Aux, [X|Y], W), ascending(W, Z), !.
sort_list(L, S) :- ascending(L, S).

merge_lists([], X, X) :- !.
merge_lists(X, [], X) :- !.
merge_lists([X|Y], [W|Z], [X|L]) :- X =< W, merge_lists(Y, [W|Z], L), !.
merge_lists([X|Y], [W|Z], [W|L]) :- W =< X, merge_lists([X|Y], Z, L).

merge_sorted(L, L2, M) :- sort_list(L, O), sort_list(L2, O2), merge_lists(O, O2, M).
question_11(L, L2, List) :-
    nl, write('Merge of the lists '), write(L), write(' and '), write(L2), write(' : '),
    merge_sorted(L, L2, List), nl.

% Question 12. reverse a list
reverse_acc([], Z, Z).
reverse_acc([X|Y], Z, Acc) :- reverse_acc(Y, Z, [X|Acc]).
reverse_list(List, Y) :- reverse_acc(List, Y, []).
question_12(X, Y) :-
    nl, write('Reverse of the list '), write(X), write(' : '), reverse_list(X, Y), nl.

% Question 13. is A a sub-list of B? (every element of A belongs to B)
sub([], _).
sub([Y], A) :- is_member(Y, A, true), !.
sub([X|Y], A) :- is_member(X, A, true), sub(Y, A), !.
sublist(List, List2) :- sub(List, List2).
question_13(X, Y) :-
    nl, write(X), write(' is a sub-list of '), write(Y), write(' ? '), sublist(X, Y), nl.

% Question 14. is List contained, in order, in List2?
in_order([], []) :- !.
in_order([X|L], [X|S]) :- in_order(L, S), !.
in_order(L, [_|S]) :- in_order(L, S), !.
contained_in_order(List, List2) :- in_order(List, List2).
question_14(X, Y) :-
    nl, write(X), write(' is contained in order in the list '), write(Y), write(' ? '),
    contained_in_order(X, Y), nl.

% Question 15. remove the first occurrence of X
remove_first_acc(_, [], [], _) :- !.
remove_first_acc(N, [N], [N], 1) :- !.
remove_first_acc(N, [B], [B], _) :- N =\= B, !.
remove_first_acc(N, [A|B], Z, Key) :- A =:= N, Key =:= 0, !, remove_first_acc(N, B, Z, 1).
remove_first_acc(N, [A|B], [A|Z], Key) :- remove_first_acc(N, B, Z, Key).
remove_first(X, List, Z) :- remove_first_acc(X, List, Z, 0).
question_15(X, L, Z) :-
    nl, write('Remove the first occurrence of '), write(X), write(' from the list '), write(L),
    write(' : '), remove_first(X, L, Z), nl.

% Question 16. remove every occurrence of X
remove_every(_, [], []) :- !.
remove_every(N, [N], []) :- !.
remove_every(N, [B], [B]) :- N =\= B, !.
remove_every(N, [A|B], Z) :- A =:= N, !, remove_every(N, B, Z).
remove_every(N, [A|B], [A|Z]) :- remove_every(N, B, Z).
remove_all(X, List, Z) :- remove_every(X, List, Z).
question_16(X, L, Z) :-
    nl, write('Remove every '), write(X), write(' from the list '), write(L), write(' : '),
    remove_all(X, L, Z), nl.

% Question 17. remove the element at position N
remove_pos([], [], _, _).
remove_pos([X|Y], [X|Z], N, Count) :- Count =\= N, remove_pos(Y, Z, N, Count + 1), !.
remove_pos([_|Y], Z, N, Count) :- remove_pos(Y, Z, N, Count + 1).
remove_at(Position, List, Z) :- remove_pos(List, Z, Position, 0).
question_17(P, L, X) :-
    nl, write('Remove the element at position '), write(P), write(' from the list '), write(L),
    write(' : '), remove_at(P, L, X), nl.

% Question 18. is it a list?
check_list([]).
check_list([_]) :- !.
check_list([_|T]) :- check_list(T).
is_list_(X) :- check_list(X).
question_18(X) :-
    nl, write(X), write(' is a list ?'), is_list_(X), nl.

% Question 19. flatten a nested list
concatenate([], X, X) :- !.
concatenate([X|Y], Z, [X|W]) :- concatenate(Y, Z, W).

flat([], []).
flat([X|Y], Z) :- flat(X, A), flat(Y, B), concatenate(A, B, Z).
flat([X|Y], [X|Z]) :- X \= [], X \= [_|_], flat(Y, Z).

flatten_list(List, Z) :- flat(List, Z), !.
question_19(L, Z) :-
    nl, write('Flatten the list '), write(L), write(' : '), flatten_list(L, Z), nl.

% Question 20. intersection of two lists
inter([], [], _).
inter([X|Y], [X|Z], L) :- is_member(X, L, true), inter(Y, Z, L), !.
inter([_|Y], Z, L) :- inter(Y, Z, L).
intersection_of(List, List2, Inter) :- inter(List, Inter, List2).
question_20(L, L2, In) :-
    nl, write('Intersection of the lists '), write(L), write(' and '), write(L2), write(' : '),
    intersection_of(L, L2, In), nl.

% Question 21. the element at position N of every sub-list
search([], [], _, _).
search([X], [X], N, Count) :- Count =:= N, !.
search([_], [], N, Count) :- Count =\= N.
search([_|Y], Z, N, Count) :- Count =\= N, search(Y, Z, N, Count + 1), !.
search([X|Y], [X|Z], N, Count) :- Count =:= N, search(Y, Z, N, Count + 1).
search_all([], [], _).
search_all([X], [Z], N) :- search(X, Z, N, 0), !.
search_all([X|Y], [R|Z], N) :- search(X, R, N, 0), search_all(Y, Z, N), !.
elements_at(List, Num, Z) :- search_all(List, Y, Num), flatten_list(Y, Z).
question_21(L, N, X) :-
    nl, write('Elements at position '), write(N), write(' of the sub-lists of '), write(L),
    write(' : '), elements_at(L, N, X), nl.

% Question 22. group consecutive equal elements into blocks
block([], []).
block([X], [[X]]) :- !.
block([X, Y|Z], [[X]|R]) :- X \= Y, block([Y|Z], R), !.
block([X, X|Y], [[X|Z]|T]) :- block([X|Y], [Z|T]), !.
blocks(List, Z) :- block(List, Z).
question_22(L, Z) :-
    nl, write('Split the list '), write(L), write(' into blocks: '), blocks(L, Z), nl.

% Question 23. run-length encode the blocks: [[a,a,a],[b]] -> [[a,3],[b,1]]
code([], []).
code([[X|Y]|Z], [[X,N]|W]) :- list_size([X|Y], N), code(Z, W).
encode(List1, List2) :- blocks(List1, L), code(L, List2).
question_23(L, Z) :-
    nl, write('Encode the list '), write(L), write(' : '), encode(L, Z), nl.

% Question 24. decode a run-length encoded list
decode_all([], []).
decode_all([X], [R]) :- search_all(X, Z, 0), search_all(X, W, 1), repeat_each([Z], W, R, W), !.
decode_all([[X|Y]|Z], [[R]|W]) :- repeat_each([X], Y, R, Y), decode_all(Z, W), !.
decode(List, Z) :- decode_all(List, B), !, flatten_list(B, Z).
question_24(L, Z) :-
    nl, write('Decode the list '), write(L), write(' : '), decode(L, Z), nl.

% Question 25. replicate each element N times
repeat_each([], _, [], _).                                       % end of the list
repeat_each([_|Y], N, Z, 0) :- repeat_each(Y, N, Z, N).           % done repeating X; move on to Y
repeat_each([X|Y], N, [X|Z], K) :- K > 0, K1 is K - 1, repeat_each([X|Y], N, Z, K1).
replicate(List, Num, Z) :- repeat_each(List, Num, Z, Num), !.
question_25(L, N, X) :-
    nl, write('Replicate '), write(N), write(' times the elements of the list '), write(L),
    write(' : '), replicate(L, N, X), nl.
