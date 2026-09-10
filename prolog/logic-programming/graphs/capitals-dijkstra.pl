% Shortest path between Brazilian state capitals: a Dijkstra-style traversal that stores the best
% known reversed path to every node in a dynamic predicate. Logic Programming course, PUCPR (2019).
% Load with: swipl capitals-dijkstra.pl   then:  ?- go('Curitiba', 'Manaus').

:- dynamic rpath/2.      % a reversed path and its distance

cc('Porto_Alegre','Florianópolis',1).
cc('Florianópolis','Porto_Alegre',1).
cc('Florianópolis','Curitiba',1).
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
cc('Cuiabá','Palmas',1).
cc('Cuiabá','Goiânia',1).
cc('Cuiabá','Campo_Grande',1).
cc('Goiânia','Campo_Grande',1).
cc('Goiânia','São_Paulo',1).
cc('Goiânia','Belo_Horizonte',1).
cc('Goiânia','Salvador',1).
cc('Goiânia','Palmas',1).
cc('Goiânia','Cuiabá',1).
cc('Goiânia','Distrito_Federal',1).

cc('São_Paulo','Campo_Grande',1).
cc('São_Paulo','Belo_Horizonte',1).
cc('São_Paulo','Rio_de_Janeiro',1).
cc('São_Paulo','Curitiba',1).
cc('Rio_de_Janeiro','São_Paulo',1).
cc('Rio_de_Janeiro','Vitória',1).
cc('Rio_de_Janeiro','Belo_Horizonte',1).
cc('Vitória','Salvador',1).
cc('Vitória','Belo_Horizonte',1).
cc('Vitória','Rio_de_Janeiro',1).
cc('Belo_Horizonte','Vitória',1).
cc('Belo_Horizonte','Rio_de_Janeiro',1).
cc('Belo_Horizonte','São_Paulo',1).
cc('Belo_Horizonte','Goiânia',1).
cc('Belo_Horizonte','Salvador',1).

cc('Rio_Branco','Manaus',1).
cc('Manaus','Rio_Branco',1).
cc('Manaus','Boa_Vista',1).
cc('Manaus','Porto_Velho',1).
cc('Manaus','Belém',1).
cc('Manaus','Cuiabá',1).
cc('Boa_Vista','Manaus',1).
cc('Boa_Vista','Belém',1).
cc('Amapá','Belém',1).
cc('Belém','Boa_Vista',1).
cc('Belém','Manaus',1).
cc('Belém','Cuiabá',1).
cc('Belém','Palmas',1).
cc('Belém','São_Luís',1).
cc('Porto_Velho','Manaus',1).
cc('Porto_Velho','Cuiabá',1).
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
cc('Maceió','Aracaju',1).
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

path(From, To, Dist) :- cc(To, From, Dist).
path(From, To, Dist) :- cc(From, To, Dist).

shorterPath([H|Path], Dist) :-             % path < stored path? replace it
    rpath([H|_], D), !, Dist < D,          % match target node [H|_]
    retract(rpath([H|_], _)),
    assert(rpath([H|Path], Dist)).
shorterPath(Path, Dist) :-                 % otherwise store a new path
    assert(rpath(Path, Dist)).

traverse(From, Path, Dist) :-              % traverse all reachable nodes
    path(From, T, D),                      % for each neighbour
    not(memberchk(T, Path)),               %   which is unvisited
    shorterPath([T,From|Path], Dist+D),    %   update the shortest path and distance
    traverse(T, [From|Path], Dist+D).      %   then traverse the neighbour

traverse(From) :-
    retractall(rpath(_, _)),               % remove old solutions
    traverse(From, [], 0).                 % traverse from the origin
traverse(_).

go(From, To) :-
    traverse(From),                        % find all distances
    rpath([To|RPath], Dist) ->             % if the target was reached
      reverse([To|RPath], Path),           % report the path and distance
      Distance is round(Dist),
      writef('Shortest path is %w with distance %w = %w\n', [Path, Dist, Distance]);
    writef('There is no route from %w to %w\n', [From, To]).
