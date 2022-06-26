% ls. shows all archives in the paste.
% deixar explícito que venda será dinâmico.
% listing shows all predicates;  listing(predicate/arity).

:- dynamic venda/5.
:- dynamic cont/1.
:- dynamic venda_total/1.

carrega_vendas :-
    consult('vendasLoja.txt'),
    retractall(vendas_totais(_)),
    assert(vendas_totais(0)).

show_predicates :-  listing(venda/5).

% soma_tudo precisa de uma variável global.
soma_vendas :- venda(_,_,Q,_,V),soma_tudo(Q,V),fail.


soma_vendas :-
    write('Total de vendas: '),
    vendas_totais(X),!,
    format('~2f',X),
    retractall(vendas_totais(_)),
    assert(vendas_totais(0)),nl.

soma_tudo(Q,V) :-
       retract(vendas_totais(S)), Z is S + Q * V,
       assert(vendas_totais(Z)).


soma_vendas_mes(M,A) :- venda(_,data(_,M,A),Q,_,V),soma_tudo(Q,V),fail.


soma_vendas_mes(_,_) :-
    write('Total de vendas: '),
    vendas_totais(X),!,
    format('~2f',X),
    retractall(vendas_totais(_)),
    assert(vendas_totais(0)),nl.


salva(X) :-
    venda(A,B,C,D,E),
    write(X, venda(A,B,C,D,E)),
    writeln(X,'.'),
    nl,fail.

salva(_).


save_write() :-
    open('vendasLoja.txt', write, A),
    salva(A),
    close(A).

save_tell() :-
    tell('vendasLoja.txt'), listing(venda/5), told.

alterar :-
    retract(venda(claudia, data(18,2,2015),3,sapatos,X)),
    assert(venda(claudia,data(18,2,2015),3,sapatos,181.90)).


/*
Salvando predicados em arquivos:
?- open('dados.pl',write,A),
    write (A, gosta(maria, livros)), write(A,'.'),
    close(A)
? - tell('dados.pl',), listing(gosta/2), told.
*/
