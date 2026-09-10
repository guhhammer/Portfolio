% Exercise list 7: graph search over the Brazilian state capitals. cc/3 links two neighbouring
% capitals with a weight; question_2 to question_5 answer the list with depth-first search,
% breadth-first search and an enumeration of all paths. Logic Programming course, PUCPR (2019).
% Load with: swipl capitals-graph-search.pl
%
%  Predicate | Capital  |  Capital  | Weight
%      cc (  'ExampleX', 'ExampleY',   Z).

cc('Porto_Alegre','Florianópolis',1).
cc('Florianópolis','Porto_Alegre',1).
cc('Florianópolis','Curitiba',1).       % South
cc('Curitiba','Florianópolis',1).
cc('Curitiba','São_Paulo',1).
cc('Curitiba','Campo_Grande',1).

cc('Campo_Grande','Cuiabá',1).
cc('Campo_Grande','Goiânia',1).
cc('Campo_Grande','Belo_Horizonte',1).
cc('Campo_Grande','São_Paulo',1).
cc('Cuiabá','Porto_Velho',1).
cc('Cuiabá','Manaus',1).
cc('Cuiabá','Belém',1).
cc('Cuiabá','Palmas',1).             % Centre-West
cc('Cuiabá','Goiânia',1).
cc('Cuiabá','Campo_Grande',1).
cc('Goiânia','Campo_Grande',1).
cc('Goiânia','São_Paulo',1).
cc('Goiânia','Belo_Horizonte',1).
cc('Goiânia','Salvador',1).
cc('Goiânia','Palmas',1).
cc('Goiânia','Cuiabá',1).
cc('Goiânia','Distrito_Federal',1).
cc('Distrito_Federal','Goiânia',1).
cc('Distrito_Federal','Belo_Horizonte',1).

cc('São_Paulo','Campo_Grande',1).
cc('São_Paulo','Belo_Horizonte',1).
cc('São_Paulo','Rio_de_Janeiro',1).
cc('São_Paulo','Curitiba',1).
cc('Rio_de_Janeiro','São_Paulo',1).
cc('Rio_de_Janeiro','Vitória',1).
cc('Rio_de_Janeiro','Belo_Horizonte',1).
cc('Vitória','Salvador',1).           % Southeast
cc('Vitória','Belo_Horizonte',1).
cc('Vitória','Rio_de_Janeiro',1).
cc('Belo_Horizonte','Vitória',1).
cc('Belo_Horizonte','Rio_de_Janeiro',1).
cc('Belo_Horizonte','São_Paulo',1).
cc('Belo_Horizonte','Goiânia',1).
cc('Belo_Horizonte','Salvador',1).
cc('Belo_Horizonte','Distrito_Federal',1).

cc('Rio_Branco','Porto_Velho',1).
cc('Rio_Branco','Manaus',1).
cc('Manaus','Rio_Branco',1).
cc('Manaus','Boa_Vista',1).
cc('Manaus','Porto_Velho',1).
cc('Manaus','Belém',1).
cc('Manaus','Cuiabá',1).
cc('Boa_Vista','Manaus',1).
cc('Boa_Vista','Belém',1).
cc('Macapá','Belém',1).
cc('Belém','Macapá',1).
cc('Belém','Boa_Vista',1).
cc('Belém','Manaus',1).               % North
cc('Belém','Cuiabá',1).
cc('Belém','Palmas',1).
cc('Belém','São_Luís',1).
cc('Porto_Velho','Manaus',1).
cc('Porto_Velho','Cuiabá',1).
cc('Porto_Velho','Rio_Branco',1).
cc('Palmas','Belém',1).
cc('Palmas','São_Luís',1).
cc('Palmas','Teresina',1).
cc('Palmas','Salvador',1).
cc('Palmas','Goiânia',1).
cc('Palmas','Cuiabá',1).

cc('Salvador','Belo_Horizonte',1).
cc('Salvador','Goiânia',1).
cc('Salvador','Palmas',1).
cc('Salvador','Teresina',1).
cc('Salvador','Aracaju',1).
cc('Salvador','Maceió',1).
cc('Salvador','Recife',1).
cc('Aracaju','Salvador',1).
cc('Aracaju','Maceió',1).
cc('Maceió','Salvador',1).
cc('Maceió','Recife',1).
cc('Maceió','Aracaju',1).         % Northeast
cc('Recife','Maceió',1).
cc('Recife','Salvador',1).
cc('Recife','Teresina',1).
cc('Recife','Fortaleza',1).
cc('Recife','João_Pessoa',1).
cc('Teresina','Salvador',1).
cc('Teresina','São_Luís',1).
cc('Teresina','Fortaleza',1).
cc('Teresina','Recife',1).
cc('Teresina','Palmas',1).
cc('São_Luís','Palmas',1).
cc('São_Luís','Teresina',1).
cc('São_Luís','Belém',1).
cc('Fortaleza','Teresina',1).
cc('Fortaleza','Recife',1).
cc('Fortaleza','Natal',1).
cc('Fortaleza','João_Pessoa',1).
cc('Natal','Fortaleza',1).
cc('Natal','João_Pessoa',1).
cc('João_Pessoa','Fortaleza',1).
cc('João_Pessoa','Natal',1).
cc('João_Pessoa','Recife',1).

% //////////////////////////////////////////////////////////

% Depth-first search.
% path(Start, Goal, Visited, PathFound, Counter, Distance).
path(Node, Node, _, [Node], D, D).

path(A, Z, Visited, [A|Rest], Count, D) :-
    cc(A, Next, Weight),            % take a child of A
    not(member(Next, Visited)),     % that is not on the path yet
    path(Next, Z, [A|Visited], Rest, Count + Weight, D).   % and repeat from the child

% Returns the first path found.
% A, Z -> capitals; List -> path from A to Z.
question_2(A, Z, List) :-
    path(A, Z, [1], List, 0, _),
    !.

% Returns the first path found and its distance.
question_3(A, Z, List, D) :-
    path(A, Z, [1], List, 0, X), !,
    D is X.

% Breadth-first search.
% bfs(Goal, [n(Node, PathSoFar)|_], VisitedNodes, ReversedPath).
bfs(Goal, [n(Goal, Path)|_], _, ReversedPath) :- reverse(ReversedPath, Path).

bfs(Goal, [n(Start, CI)|RCI], Visited, Path) :-
    write('--------------------------------------'), nl,
    write('Goal: '), write(Goal), tab(4), write('Start: '), write(Start), nl,
    write('Path from the start: '), write(CI), nl, nl,
    write('Previous child nodes: '), write(RCI), nl, nl,
                           % expand the children of Start that were not visited yet
    findall(n(I1, [CI, [Start]]), (cc(Start, I1, _), \+ member(I1, Visited)), Cs),
    write('Child nodes: '), tab(4), write(Cs), nl, nl,
    append(RCI, Cs, Nc),   % the new frontier is the previous frontier plus the children
    write('New frontier: '), tab(4), write(Nc), nl, nl,
    write('Visited nodes: '), tab(4), write([Start, Visited]), nl, nl,
    bfs(Goal, Nc, [Start|Visited], Path).

% helper of question_4/3: tidies the nested path returned by bfs/4
tidy([X|Y], Goal, SS) :-
    Z = [Y|X],           % reorder
    append(Z, Goal, Z1), % add the goal
    flatten(Z1, SS).     % flatten

% Returns the path between Start and Goal found breadth-first.
question_4(Start, Goal, Path) :-
    bfs(Goal, [n(Start, [])], [], Aux),
    tidy(Aux, Goal, Path), !.

% Question 5 uses the same predicate as questions 2 and 3: path/6.

% every path from A to Z with its distance
all_paths(A, Z, Y) :-
    findall([List, D], (path(A, Z, [1], List, 0, X), D is X), Y).

% prints the output of question_5/2
print_paths([[X|Y]|Z]) :-
    write('Path: '), write(X), nl,
    write('Distance: '), write(Y), nl,
    nl, print_paths(Z).

% Returns every possible path from A to Z with its distance.
question_5(A, Z) :-
    all_paths(A, Z, List),
    print_paths(List).

% number of paths between A and Z
path_count(A, Z, K) :-
    findall(0, (path(A, Z, [1], _, 0, _)), Y), length(Y, K).
