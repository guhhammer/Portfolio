% Breadth-first search on two small graphs (letters and numbers), printing every expansion step.
% Logic Programming course, PUCPR (2019). Load with: swipl breadth-first-search.pl
%   ?- bfs(a, h, Path).      ?- bfs(1, 42, Path).

% edge(From, To, Link)
edge(a,b,link(a,b)).
edge(a,c,link(a,c)).
edge(b,d,link(b,d)).
edge(b,e,link(b,e)).
edge(c,f,link(c,f)).
edge(c,g,link(c,g)).
edge(e,h,link(e,h)).

edge(1,7,link(1,7)).
edge(1,8,link(1,8)).
edge(1,3,link(1,3)).
edge(7,4,link(7,4)).
edge(7,20,link(7,20)).
edge(7,17,link(7,17)).
edge(8,6,link(8,6)).
edge(3,9,link(3,9)).
edge(3,12,link(3,12)).
edge(4,42,link(4,42)).
edge(20,28,link(20,28)).
edge(9,19,link(9,19)).

connected(X, Y, A) :- edge(X, Y, A).
connected(X, Y, A) :- edge(Y, X, A).

bfs(Start, Goal, Path) :-
    bfs_step(Goal, [n(Start, [])], [], Path).

bfs_step(Goal, [n(Goal, Path)|_], _, ReversedPath) :-
    reverse(ReversedPath, Path).

bfs_step(Goal, [n(Start, CI)|RCI], Visited, Path) :-
    write("-----------"), nl, write(Goal), nl, write("I:"),
    write(Start), nl, write("CI:"), write(CI), nl,
    write("RCI:"), write(RCI), nl,
    findall(n(I1, [A|CI]), (connected(Start, I1, A), \+ member(I1, Visited)), Cs),
    write(Cs), nl,
    append(RCI, Cs, NC),
    write(NC), nl,
    write([Start, Visited]),
    bfs_step(Goal, NC, [Start|Visited], Path), nl.
