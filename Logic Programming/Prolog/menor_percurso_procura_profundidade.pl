/*
caminho(Node,Node, _,[Node],_,N,D):- D is N.
caminho(A,Z,Percurso,[A|Fim],Profundidade,Count,D) :-
    Profundidade > 0,
    cc(A, Aux,T),not(member(Aux, Percurso)),
    caminho(Aux,Z,[A|Percurso],Fim,Profundidade-1,Count+T,D),!.

try(X,Y,Z,P,D):-  not(caminho(X,Y,[1],Z,P,0,D)), try(X,Y,Z,P+1,D).
try(X,Y,Z,P,D) :- caminho(X,Y,[1],Z,P,0,D).
*/

