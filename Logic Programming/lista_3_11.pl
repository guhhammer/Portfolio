

/*

Grupo 12: Gustavo Hammerschmidt,
          Guilherme Henrique Fenner Hey,
          Bruno Henrique Barbosa Alves,
          Pedro Henrique da Silva Churata.

*/


% Quest�o 1. Maiores([1,2,3,4,5,6,7],5,N) -> N = [6,7]
maior([],[],_).
maior([X|Y],[X|Z],N) :- X > N, maior(Y,Z,N),!.
maior([_|Y],Z,N) :- maior(Y,Z,N).
quest_1(Num,Lista,Z) :-
    nl,write('Elementos maiores do que '),
    write(Num),write(' na lista '),write(Lista),
    write(' : '),maior(Lista, Z, Num),nl.

% Quest�o 1: Fim.

% Quest�o 2. M�dia lista.
somar([],0).
somar([X],X) :- !.
somar([X|Y],S) :- somar(Y, K), S is X + K,!.

soma(Lista,Soma) :-  somar(Lista, Soma).
size(X,Y) :- length(X,Y).

media(Lista, Soma) :-
    soma(Lista,SomaL),size(Lista,Tamanho),
    Tamanho =\= 0,Soma is SomaL/Tamanho,!.
media(_,0).
quest_2(L,S) :-
    nl,write('M�dia dos elementos na lista '),
    write(L),write(' : '),
    media(L,S),nl.

% Quest�o 2: Fim.

% Quest�o 3. Maior que m�dia aritm�tica.
maiores_media(Lista,Z) :- media(Lista,Media), maior(Lista,Z,Media).
quest_3(L,Z) :-
    nl,write('Elementos maiores do que a m�dia da lista ('),
    media(L,Aux),write(Aux),write(') na lista '),write(L),
    write(' : '),maiores_media(L,Z),nl.

% Quest�o 3: Fim.

% Quest�o 4. Elementos anteriores � pos. n.
ante([],[],_,_).
ante([X|Y],[X|Z],N,Count) :- Count < N, ante(Y,Z,N,Count+1),!.
ante([_|Y],Z,N,Count) :- ante(Y,Z,N,Count+1).
anterior(Num, Lista, Z) :- ante(Lista,Z, Num,0).
quest_4(N,L,X) :-
    nl,write('Elementos anteriores � posi��o '),write(N),
    write(' na lista '),write(L),
    write(' : '),anterior(N,L,X),nl.

% Quest�o 4: Fim.

% Quest�o 5. Elementos posteriores � pos. n. incluindo n
post([],[],_,_).
post([X|Y],[X|Z],N,Count) :- Count >= N, post(Y,Z,N,Count+1),!.
post([_|Y],Z,N,Count) :- post(Y,Z,N,Count+1).
posterior(Num, Lista, Z) :-  post(Lista,Z,Num,0).
quest_5(N,L,X) :-
    nl,write('Elementos posteriores � posi��o '),write(N),
    write(' na lista '),write(L),
    write(' : '),posterior(N,L,X),nl.

% Quest�o 5: Fim.

% Quest�o 6. Lista 1 a n.
list1_n(N,Z) :- findall(X,between(1,N,X),Z).
quest_6(N,L) :-
    nl,write('Lista de 1 a '),
    write(N),write(': '),list1_n(N,L),nl.

% Quest�o 6: Fim.

% Quest�o 7. Esquerda e Direita de X.
esqdir([],[],_,_).
esqdir([X|Y],[X|Z],N,Count) :- Count =:= N-1,esqdir(Y,Z,N,Count+1),!.
esqdir([X|Y],[X|Z],N,Count) :- Count =:= N+1,esqdir(Y,Z,N,Count+1),!.
esqdir([_|Y],Z,N,Count) :- esqdir(Y,Z,N,Count+1).
esq_dir(X,Lista,Z) :- esqdir(Lista,Z,X,0).
quest_7(N,L,X) :-
    nl,write('Elementos � esquerda e � direita da posi��o '),
    write(N),write(' na lista '),
    write(L),write(' : '),
    esq_dir(N,L,X),nl.

% Quest�o 7: Fim.

% Quest�o 8. Duas listas.
divide(X,Lista,A,B) :- anterior(X,Lista,A), posterior(X,Lista,B).
quest_8(X,L,A,B) :-
    nl,write('Divide a lista '), write(L),write(' em duas na posi��o: '),
    write(X),write('. '),divide(X,L,A,B),nl.

% Quest�o 8: Fim.

% Quest�o 9. Intervalo entre A e B.
intervalo(A,B,Lista):- findall(X, between(A,B,X), Lista).
quest_9(A,B,L) :-
    nl,write('Lista de '),write(A),write(' a '),
    write(B),write(': '),intervalo(A,B,L),nl.

% Quest�o 9: Fim.

% Quest�o 10. Tamanho elementos.
tam([],_).
tam([Y],[A]) :- length(Y,A),!.
tam([X|Y], [A|B]) :- length(X,A), tam(Y,B),!.
tamanho(List,N) :- tam(List,N).
quest_10(L,N) :-
    nl,write('Tamanho dos elementos da lista '),
    write(L),write(' : '),
    tamanho(L,N),nl.

% Quest�o 10: Fim.

% Quest�o 11. Merge listas.
merging(L,L2, M) :-  append(L,L2, Aux), sort(0, @=< , Aux, M).
quest_11(L,L2,List) :-
    nl,write('Merge das listas '),write(L),
    write(' e '),write(L2),write(' : '),
    merging(L,L2,List),nl.

% Quest�o 11: Fim.
