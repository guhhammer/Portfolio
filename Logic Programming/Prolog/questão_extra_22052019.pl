aresta(1,7,10).
aresta(1,8,1).
aresta(1,3,1).
aresta(7,4,1).
aresta(7,20,1).
aresta(7,17,1).
aresta(8,6,1).
aresta(3,9,1).
aresta(3,12,1).
aresta(9,19,1).
aresta(4,42,1).
aresta(20,28,1).
aresta(17,10,1).
aresta(6,10,1).
aresta(10,28,1).


%  D -> distância.
caminh(Node,Node, _,[Node],N,D):- D is N.
caminh(Com,Fim,Perc,[Com|Rest],Count,D) :-
    aresta(Com, Aux,T),
    not(member(Aux, Perc)),
    caminh(Aux,Fim,[Aux | Perc],Rest,Count + T,D).


cam(X,Y,Z,D) :-  caminh(X,Y,[1],Z,0,D).


