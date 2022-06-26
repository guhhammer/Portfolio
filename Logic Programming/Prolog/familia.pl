
casado(joao, maria).
casado(jose, doida).

mae(maria, lucas).
mae(maria, vitinho).
mae(maria, deividison).
mae(doida, antoniltão).
mae(doida, andreison).
mae(doida, arnoldinho).


pai(X,Z) :- casado(X,Y), mae(Y,Z).
pai(silvão, maria).

irmao(Y, Z) :- mae(X, Y), mae(X, Z), (Z\=Y).
irmao(maria, jose).
irmao(jose, maria).

tio(Y, Z) :- irmao(Y, X), pai(X, Z).

primo(Y, Z) :- pai(A, Z), pai(B, Y), irmao(A, B).

sogro(Y, Z) :- pai(Y, K), casado(K, Z), not(pai(Y, Z)).





