%equipe 7(n_esimo)


potencia(_,0,1).
potencia(Base,Expo,V):-
    W is Expo-1,
    potencia(Base,W,K),
    V is Base*K,!.

n_elementos([],0).
n_elementos([_|Y], X):-
    n_elementos(Y,W),
    X is W+1.

n_esimo(1,[X|_],X):-!.
n_esimo(N,[_|Y],X):-
    W is N-1,
    n_esimo(W,Y,X).

merge([],X,X):-!.
merge(X,[],X):-!.
merge([X|Y],[W|Z],[X|L]):-  X=<W, merge(Y,[W|Z],L),!.
merge([X|Y],[W|Z],[W|L]):-  W=<X, merge([X|Y],Z,L).

insere(E,1,[],[E]):-!.
insere(E,1,[X|Y],[E,X|Y]):-!.
insere(E,P,[X|Y],[X|LR]):-
    W is P-1,
    insere(E,W,Y,LR).

concatena([],X,X):-!.
concatena([X|Y],Z,[X|W]):-
    concatena(Y,Z,W).

inverte(X,L):-
    inv(X,[],L).
inv([],X,X).
inv([H|T],Z,Y):-
    inv(T,[H|Z],Y).

ultimo([X],X):-!.
ultimo([_|Y],X):-
    ultimo(Y,X).
