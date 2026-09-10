% Paths and their total weight in a small directed weighted graph (extra question, 2019-05-22).
% Logic Programming course, PUCPR. Load with: swipl weighted-paths.pl   then:  ?- route(1, 28, Path, D).

% edge(From, To, Weight)
edge(1,7,10).
edge(1,8,1).
edge(1,3,1).
edge(7,4,1).
edge(7,20,1).
edge(7,17,1).
edge(8,6,1).
edge(3,9,1).
edge(3,12,1).
edge(9,19,1).
edge(4,42,1).
edge(20,28,1).
edge(17,10,1).
edge(6,10,1).
edge(10,28,1).

% walk(Node, Goal, Visited, Path, Counter, Distance)
walk(Node, Node, _, [Node], N, D) :- D is N.
walk(Start, Goal, Visited, [Start|Rest], Count, D) :-
    edge(Start, Next, W),
    not(member(Next, Visited)),
    walk(Next, Goal, [Next|Visited], Rest, Count + W, D).

route(X, Y, Path, D) :- walk(X, Y, [1], Path, 0, D).
