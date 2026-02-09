/*

Grupo 12: Gustavo Hammerschmidt,
          Guilherme Henrique Fenner Hey,
          Pedro Henrique da Silva Churata,
          Bruno Henrique Barbosa Alves.

auto('Renavam(1)','Placa(2)','Nome do Proprietário(3)',
    'Sobrenome do Proprietário(4)','Marca(5)','Modelo(6)',
    'Ano de Fabricação(7)','Logradouro(8)','Número(9)',
    'Complemento(10)','Cidade(11)', 'estado(12)',
    'CEP(13)','Celular(14)','Telefone(15)').
*/

:- dynamic auto/15.

% -----------------------------------------------------------------------
% -----------------------------------------------------------------------

% função complementar de 02, 04, 05 e 06.
show_data(Ren,Pl,N,S,Mar,M,A,L,Num,C,Cid,Est,Cep,Cel,Tel) :-
    nl,

    tab(10),write('Nome: '),tab(10),write(N),nl,
    tab(10),write('Sobrenome: '),tab(10),write(S),nl,
    nl,
    tab(10),write('Marca: '),tab(10),write(Mar),nl,
    tab(10),write('Modelo: '),tab(10),write(M),nl,
    tab(10),write('Placa: '),tab(10),write(Pl),nl,
    tab(10),write('Renavam: '),tab(10),write(Ren),nl,
    tab(10),write('Ano de Fabricação: '),tab(10),write(A),nl,
    nl,
    tab(10),write('Logradouro: '),tab(10),write(L),nl,
    tab(10),write('Número: '),tab(10),write(Num),nl,
    tab(10),write('Complemento: '),tab(10),write(C),nl,
    tab(10),write('CEP: '),tab(10),write(Cep),nl,
    tab(10),write('Cidade: '),tab(10),write(Cid),nl,
    tab(10),write('Estado: '),tab(10),write(Est),nl,
    nl,
    tab(10),write('Celular: '),tab(10),write(Cel),nl,
    tab(10),write('Telefone: '),tab(10),write(Tel),nl,
    nl,
    write('Digite algo para ver se há mais: '),read(_),nl,
    nl,
    fail.


% função que chama start novamente.
recall :-
    nl,nl,
    write('Digite algo para voltar ao menu: '),tab(10),read(_),
    nl, start.


% -----------------------------------------------------------------------
% -----------------------------------------------------------------------


% 01.
inserir_auto :-
    nl,write('(1) -  incluir automóvel: '),nl,
    nl,
    tab(10),write('Nome: '),tab(10),read(N),
    tab(10),write('Sobrenome: '),tab(10),read(S),
    nl,
    tab(10),write('Marca: '),tab(10),read(Mar),
    tab(10),write('Modelo: '),tab(10),read(M),
    tab(10),write('Placa: '),tab(10),read(P),
    tab(10),write('Renavam: '),tab(10),read(Ren),
    tab(10),write('Ano de Fabricação: '),tab(10),read(A),
    nl,
    tab(10),write('Logradouro: '),tab(10),read(L),
    tab(10),write('Número: '),tab(10),read(Num),
    tab(10),write('Complemento: '),tab(10),read(C),
    tab(10),write('CEP: '),tab(10),read(Cep),
    tab(10),write('Cidade: '),tab(10),read(Cid),
    tab(10),write('Estado: '),tab(10),read(Est),
    nl,
    tab(10),write('Celular: '),tab(10),read(Cel),
    tab(10),write('Telefone: '),tab(10),read(Tel),
    nl,

    assert(auto(Ren,P,N,S,Mar,M,A,L,Num,C,Cid,Est,Cep,Cel,Tel)).

% 02.
localizar_nome(N,S) :-
    auto(Ren,Pl,N,S,Mar,M,A,L,Num,C,Cid,Est,Cep,Cel,Tel),
    show_data(Ren,Pl,N,S,Mar,M,A,L,Num,C,Cid,Est,Cep,Cel,Tel).

% 02.
localizar_nome(N,S) :-
    not(auto(_,_,N,S,_,_,_,_,_,_,_,_,_,_,_)),
    write('Ninguém com esse nome foi encontrado').

% 03.
excluir_auto(Pl) :-
    retract(auto(_,Pl,_,_,_,_,_,_,_,_,_,_,_,_,_)),nl,
    tab(10),write('Automóvel excluído.').
% 03.
excluir_auto(Pl) :-
    not(auto(_,Pl,_,_,_,_,_,_,_,_,_,_,_,_,_)),
    write('Nenhuma placa foi encontrada').


% 04.
localizar_placa(Pl) :-
    auto(Ren,Pl,N,S,Mar,M,A,L,Num,C,Cid,Est,Cep,Cel,Tel),
    show_data(Ren,Pl,N,S,Mar,M,A,L,Num,C,Cid,Est,Cep,Cel,Tel).

% 04.
localizar_placa(Pl) :-
    not(auto(_,Pl,_,_,_,_,_,_,_,_,_,_,_,_,_)),
    write('Ninguém com essa placa foi encontrado.').

% 05.
localizar_renavam(R) :-
    auto(R,P,N,S,Mar,M,A,L,Num,C,Cid,Est,Cep,Cel,Tel),
    show_data(R,P,N,S,Mar,M,A,L,Num,C,Cid,Est,Cep,Cel,Tel).

% 05.
localizar_renavam(R) :-
    not(auto(R,_,_,_,_,_,_,_,_,_,_,_,_,_,_)),
    write('Ninguém com esse renavam foi encontrado.').

% 06.
relatorio_auto :-
    auto(R,P,N,S,Mar,M,A,L,Num,C,Cid,Est,Cep,Cel,Tel),
    show_data(R,P,N,S,Mar,M,A,L,Num,C,Cid,Est,Cep,Cel,Tel).

% 06.
relatorio_auto :-
    not(auto(_,_,_,_,_,_,_,_,_,_,_,_,_,_,_)),
    write('Nenhum automóvel na base de dados.').

% 07.
quantidade_auto(P,Count) :- findall(15,P,L), length(L,Count).

% 08.
salvar_dados :-    tell('autos.txt'), listing(auto/15), told.

% 09.
carrega_autos :-  consult('autos.txt').

% 10.
limpar_dados :- retract(auto(_,_,_,_,_,_,_,_,_,_,_,_,_,_,_)),fail.

% 10.
limpar_dados :- tell('autos.txt'), listing(), told.

% -----------------------------------------------------------------------
% -----------------------------------------------------------------------

% chama(X) <- chama a opção X referente.

chama(1) :- inserir_auto,recall.

chama(2) :-
    nl,write('(2) -  Localizar automóvel pelo Nome: '),
    nl,nl,write('Digite o Nome: '),tab(10),read(N),
    nl,write('Digite o Sobrenome: '),tab(10),read(S),
    localizar_nome(N,S),recall.

chama(3) :-
    nl,write('(3) -  Excluir automóvel pela Placa: '),
    nl,nl,write('Digite a Placa: '),tab(10),read(Pl),
    excluir_auto(Pl),nl,nl, recall.

chama(4) :-
    nl,write('(4) -  Localizar automóvel pela Placa: '),
    nl,nl,write('Digite a Placa: '),tab(10),read(P),
    localizar_placa(P), recall.

chama(5) :-
    nl,write('(5) -  Localizar automóvel pelo Renavam: '),
    nl,
    nl,write('Digite o Renavam: '),tab(10),read(R),
    localizar_renavam(R), recall.

chama(6) :-
    nl,write('(6) -  Relatório de Automóveis: '),
    nl,nl, relatorio_auto, recall.

chama(7) :-
    nl,write('(7) -  Informar a Quantidade de Automóveis: '),
    nl,nl,
    quantidade_auto(auto(_,_,_,_,_,_,_,_,_,_,_,_,_,_,_),Count),
    write('Quantidade de automóveis: '),tab(10),write(Count),recall.

chama(8) :-
    nl,write('(8) -  Salvar dados em Arquivo: '),
    nl,nl,salvar_dados,tab(10),write(' Dados foram salvos.'),
    nl,recall.

chama(9) :-
    nl,write('(9) -  Carregar dados em Arquivo: '),nl,nl,
    carrega_autos,tab(10),write(' Dados foram carregados.'),
    nl,recall.

chama(10) :-
    nl,write('(10) -  Limpar Dados de Cadastro: '),
    nl,nl,limpar_dados,tab(10),write(' Dados foram Limpados.'),
    nl,recall.

chama(11) :-
    nl,nl,
    tab(10),write('Encerrando pesquisa.'),nl,
    nl,write('---------------------------------').

% mantém a aplicação rodando se houver input error.
chama(_) :- recall.


% -----------------------------------------------------------------------
% -----------------------------------------------------------------------

%  start.    <-  interface.
start :-

    nl,write('---------------------------------'),
    nl,write('Seguradora Database.'),
    nl,
    nl,
    write('Operations: '),nl,
    tab(10),write('(1)  Incluir Automóvel.'),nl,
    tab(10),write('(2)  Localizar Automóvel pelo Nome.'),nl,
    tab(10),write('(3)  Excluir Automóvel pela Placa.'),nl,
    tab(10),write('(4)  Localizar Automóvel pela Placa.'),nl,
    tab(10),write('(5)  Localizar Automóvel pelo Renavam.'),nl,
    tab(10),write('(6)  Relatório de Automoveis.'),nl,
    tab(10),write('(7)  Informar quantidade de automóveis.'),nl,
    tab(10),write('(8)  Salvar Dados em Arquivo.'),nl,
    tab(10),write('(9)  Carregar Dados de Arquivo.'),nl,
    tab(10),write('(10) Limpar Dados de Cadastro.'),nl,
    tab(10),write('(11) Encerrar.'),nl,
    nl,
    write('---------------------------------'),nl,
    nl,
    write('Digite a opção:'),tab(10),
    read(Aux),
    write('---------------------------------'),
    chama(Aux),
    nl.


%  ----------------------------------------------------------------------
%  ----------------------------------------------------------------------


