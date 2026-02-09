mae(maria,joao).
mae(maria,julia).
mae(gabriela,eduardo).
mae(gabriela,vitoria).
mae(joana,camila).

casado(joao,vitoria).
casado(vitoria,joao).
casado(camila,eduardo).
casado(eduardo,camila).

irmao(X,Y) :- mae(A,X), mae(A,Y), X\=Y .

cunhado(X,Y) :- irmao(X,A), casado(A,Y).


conc(X,Y) :- casado(X,Z), mae(W,Z), mae(W,A), casado(A,Y).

conc(X,Y) :- cunhado(X,A), casado(A,Y).
