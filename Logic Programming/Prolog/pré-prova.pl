

maior(_,[],[]).
maior(N,[X|Y],[X|Z]):- X > N, maior(N,Y,Z),!.
maior(N,[_|Y],Z):- maior(N,Y,Z),!.

soma([],0).
soma([X],X):- !.
soma([X|Y],S):- soma(Y,K),S is X + K,!.



maiormedia(L,Z):- length(L,N),soma(L,S),maior((S/N),L,Z).



anterior(_,_,[],[]).
anterior(N,K,[X|Y],[X|Z]):- N > K, anterior(N,K+1,Y,Z).
anterior(N,K,[_|Y],Z):- anterior(N,K+1,Y,Z).

ant(N,L,Z):- anterior(N,0,L,Z),!.



posterior(_,_,[],[]).
posterior(N,K,[X|Y],[X|Z]):- N =< K, posterior(N,K+1,Y,Z).
posterior(N,K,[_|Y],Z):- posterior(N,K+1,Y,Z).

post(N,L,Z):- posterior(N,0,L,Z),!.


one_to_n(X,L) :- findall(S,between(1,X,S),L).



getter(_,_,[],[]).
getter(N,K,[X|Y],[X|Z]):- N =:= K-1,getter(N,K+1,Y,Z).
getter(N,K,[X|Y],[X|Z]):- N =:= K+1,getter(N,K+1,Y,Z).
getter(N,K,[_|Y],Z):- getter(N,K+1,Y,Z).

esqdir(N,L,Z):- getter(N,0,L,Z),!.


esq(_,_,[],[]).
esq(N,K,[X|Y],[X|Z]):- N > K, esq(N,K+1,Y,Z),!.
esq(N,K,[_|Y],Z):- esq(N,K,Y,Z),!.
dir(_,_,[],[]).
dir(N,K,[X|Y],[X|Z]):- N =< K, dir(N,K+1,Y,Z),!.
dir(N,K,[_|Y],Z):- dir(N,K+1,Y,Z),!.

divide(N,Lista,A,B):- esq(N,0,Lista,A),dir(N,0,Lista,B),!.


from_a_to_b(A,B,Z):- findall(X,between(A,B,X),Z).



tamanho([],[]).
tamanho([X|Y],[S|Z]):- length(X,S),tamanho(Y,Z).


choose([],[],[]).
choose(S,[],S).
choose([],S,S).
choose([A|B],[C|D],[A|Z]):- A =< C,choose(B,[C|D],Z).
choose([A|B],[C|D],[C|Z]):- C =< A,choose([A|B],D,Z).

merg(X,Y,Z):- sort(0,@=<,X,XS),sort(0,@=<,Y,YS),choose(XS,YS,Z),!.


mergv2(X,Y,Z):- sort(0,@=<,X,XS),sort(0,@=<,Y,YS),merge(XS,YS,Z),!.


inverte(L,Z):- reverse(L,Z).


sublista([],_).
sublista([X],Z):- member(X,Z),!.
sublista([X|Y],Z):- member(X,Z),sublista(Y,Z),!.


emordem([],_).
emordem([X|Y],[X|Z]):-emordem(Y,Z),!.
emordem(L,[_|S]):-emordem(L,S).






rem1([],_,[],_).
rem1([X|Y],N,Z,Key):-N=:=X,Key=:=0,rem1(Y,N,Z,1).
rem1([X|Y],N,[X|Z],Key):-rem1(Y,N,Z,Key).
remove1st(L,N,Z):- rem1(L,N,Z,0),!.



rem([],_,[]).
rem([X|Y],N,[X|Z]):- N=\=X,rem(Y,N,Z).
rem([_|Y],N,Z):-rem(Y,N,Z).

removeX(L,N,Z):- rem(L,N,Z),!.



remN([],_,[],_).
remN([_|Y],N,Z,K):- K =:= N,remN(Y,N,Z,K+1).
remN([X|Y],N,[X|Z],K):- remN(Y,N,Z,K+1).

removeN(L,N,Z):- remN(L,N,Z,0),!.

lista(X):- is_list(X),!.


nivela(L,Z):- flatten(L,Z).



ik([],_,[]).
ik([X|Y],L,[X|Z]):- member(X,L),ik(Y,L,Z).
ik([_|Y],L,Z):- ik(Y,L,Z).


intersektion(L,L2,Z):- ik(L,L2,Z),!.




find([],_,_,[]).
find([X|Y],N,K,[X|Z]):- N=:=K,find(Y,N,K+1,Z).
find([_|Y],N,K,Z):- find(Y,N,K+1,Z).


run([],_,[]).
run([X|Y],K,[N|Z]):- find(X,K,0,N),run(Y,K,Z).

encontre(Lista,K,Z):-run(Lista,K,Z),!.






block([],[]).
block([X],[[X]]).
block([X,Y|Z],[[X]|R]):- X\=Y,block([Y|Z],R).
block([X,X|Y],[[X|Z]|T]):- block([X|Y],[Z|T]).

bloco(Lista,Z):- block(Lista,Z),!.





code([],[]).
code([[X|Y]|Z],[[X,N]|T]):- length([X|Y],N),code(Z,T).
codificar(Lista,Z):- code(Lista,Z).




repli([],[],_,_).
repli([X|Y],[X|Z],N,K):- K > 0,K1 is K-1, repli([X|Y],Z,N,K1).
repli([_|Y],Z,N,0):- repli(Y,Z,N,N).
replicar(Lista,N,Z):- repli(Lista,Z,N,N),!.


decode([],[]).
decode([[X|Y]|Z],[[N]|T]):- replicar([X],Y,N),decode(Z,T).

decodificar(Lista,Z):- decode(Lista, X),flatten(X,Z).



rem1st(X,Lista, Z):- member(X,Lista),select(X,Lista,Z),!.
rem1st(_,Lista,Lista).





rems([],[],_,_).
rems([X|Y],Z,N,Key):- X =:= N,Key =:= 0,rems(Y,Z,N,1).
rems([X|Y],[X|Z],N,Key):- rems(Y,Z,N,Key).
chama(L,N,Z):- rems(L,Z,N,0),!.
