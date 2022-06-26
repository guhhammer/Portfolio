
:-dynamic rpath/2.      % A reversed path

cc('Porto_Alegre','Florian�polis',1).
cc('Florian�polis','Porto_Alegre',1).
cc('Florian�polis','Curitiba',1).
cc('Curitiba','Florian�polis',1).
cc('Curitiba','S�o_Paulo',1).
cc('Curitiba','Campo_Grande',1).

cc('Campo_Grande','Cuiab�',1).
cc('Campo_Grande','Goi�nia',1).
cc('Campo_Grande','Belo_Horizonte',1).
cc('Campo_Grande','S�o_Paulo',1).
cc('Cuiab�','Porto_Velho',1).
cc('Cuiab�','Manaus',1).
cc('Cuiab�','Bel�m',1).
cc('Cuiab�','Palmas',1).
cc('Cuiab�','Goi�nia',1).
cc('Cuiab�','Campo_Grande',1).
cc('Goi�nia','Campo_Grande',1).
cc('Goi�nia','S�o_Paulo',1).
cc('Goi�nia','Belo_Horizonte',1).
cc('Goi�nia','Salvador',1).
cc('Goi�nia','Palmas',1).
cc('Goi�nia','Cuiab�',1).
cc('Goi�nia','Distrito_Federal',1).

cc('S�o_Paulo','Campo_Grande',1).
cc('S�o_Paulo','Belo_Horizonte',1).
cc('S�o_Paulo','Rio_de_Janeiro',1).
cc('S�o_Paulo','Curitiba',1).
cc('Rio_de_Janeiro','S�o_Paulo',1).
cc('Rio_de_Janeiro','Vit�ria',1).
cc('Rio_de_Janeiro','Belo_Horizonte',1).
cc('Vit�ria','Salvador',1).
cc('Vit�ria','Belo_Horizonte',1).
cc('Vit�ria','Rio_de_Janeiro',1).
cc('Belo_Horizonte','Vit�ria',1).
cc('Belo_Horizonte','Rio_de_Janeiro',1).
cc('Belo_Horizonte','S�o_Paulo',1).
cc('Belo_Horizonte','Goi�nia',1).
cc('Belo_Horizonte','Salvador',1).
% Minas faz divisa com DF?

%cc('Acre','Rond�nia').
cc('Rio_Branco','Manaus',1).
cc('Manaus','Rio_Branco',1).
cc('Manaus','Boa_Vista',1).
cc('Manaus','Porto_Velho',1).
cc('Manaus','Bel�m',1).
cc('Manaus','Cuiab�',1).
cc('Boa_Vista','Manaus',1).
cc('Boa_Vista','Bel�m',1).
cc('Amap�','Bel�m',1).
cc('Bel�m','Boa_Vista',1).
cc('Bel�m','Manaus',1).
cc('Bel�m','Cuiab�',1).
cc('Bel�m','Palmas',1).
cc('Bel�m','S�o_Lu�s',1).
cc('Porto_Velho','Manaus',1).
cc('Porto_Velho','Cuiab�',1).
cc('Palmas','Bel�m',1).
cc('Palmas','S�o_Lu�s',1).
cc('Palmas','Teresina',1).
cc('Palmas','Salvador',1).
cc('Palmas','Goi�nia',1).
cc('Palmas','Cuiab�',1).

cc('Salvador','Belo_Horizonte',1).
cc('Salvador','Goi�nia',1).
cc('Salvador','Palmas',1).
cc('Salvador','Terezian',1).
cc('Salvador','Aracaju',1).
cc('Salvador','Macei�',1).
cc('Salvador','Recife',1).
cc('Aracaju','Salvador',1).
cc('Aracaju','Macei�',1).
cc('Macei�','Salvador',1).
cc('Macei�','Recife',1).
cc('Macei�','Aracaju',1).
cc('Recife','Macei�',1).
cc('Recife','Salvador',1).
cc('Recife','Terezian',1).
cc('Recife','Fortaleza',1).
cc('Recife','Jo�o_Pessoa',1).
cc('Terezina','Salvador',1).
cc('Terezina','S�o_Lu�z',1).
cc('Terezina','Fortaleza',1).
cc('Terezina','Recife',1).
cc('Terezina','Palmas',1).
cc('S�o_Lu�z','Palmas',1).
cc('S�o_Lu�z','Terezina',1).
cc('S�o_Lu�z','Bel�m',1).
cc('Fortaleza','Terezina',1).
cc('Fortaleza','Recife',1).
cc('Fortaleza','Natal',1).
cc('Fortaleza','Jo�o_Pessoa',1).
cc('Natal','Fortaleza',1).
cc('Natal','Jo�o_Pessoa',1).
cc('Jo�o_Pessoa','Fortaleza',1).
cc('Jo�o_Pessoa','Natal',1).
cc('Jo�o_Pessoa','Recife',1).




path(From,To,Dist) :- cc(To,From,Dist).
path(From,To,Dist) :- cc(From,To,Dist).

shorterPath([H|Path], Dist) :-		       % path < stored path? replace it
	rpath([H|_], D), !, Dist < D,          % match target node [H|_]
	retract(rpath([H|_],_)),
	assert(rpath([H|Path], Dist)).
shorterPath(Path, Dist) :-		       % Otherwise store a new path
	assert(rpath(Path,Dist)).

traverse(From, Path, Dist) :-		    % traverse all reachable nodes
	path(From, T, D),		    % For each neighbor
	not(memberchk(T, Path)),	    %	which is unvisited
	shorterPath([T,From|Path], Dist+D), %	Update shortest path and distance
	traverse(T,[From|Path],Dist+D).	    %	Then traverse the neighbor

traverse(From) :-
	retractall(rpath(_,_)),           % Remove solutions
	traverse(From,[],0).              % Traverse from origin
traverse(_).

go(From, To) :-
	traverse(From),                   % Find all distances
	rpath([To|RPath], Dist)->         % If the target was reached
	  reverse([To|RPath], Path),      % Report the path and distance
	  Distance is round(Dist),
	  writef('Shortest path is %w with distance %w = %w\n',
	       [Path, Dist, Distance]);
	writef('There is no route from %w to %w\n', [From, To]).
