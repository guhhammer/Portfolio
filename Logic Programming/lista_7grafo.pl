
% NOME: Gustavo Hammerschmidt.


% Quest�o 1: Caminhos entre as capitais.
%
% O Predicado cc (Capital Capital) relaciona
% os caminhos entre as capitais e confere um
% peso aos caminhos entre essas capitais.
%
%  Predicado | Capital |  Capital  | Peso
%      cc (  'exemploX','Exemploy',  Z).
%

cc('Porto_Alegre','Florian�polis',1).
cc('Florian�polis','Porto_Alegre',1).
cc('Florian�polis','Curitiba',1).       % Regi�o Sul.
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
cc('Cuiab�','Palmas',1).             % Regi�o Centro-Oeste.
cc('Cuiab�','Goi�nia',1).
cc('Cuiab�','Campo_Grande',1).
cc('Goi�nia','Campo_Grande',1).
cc('Goi�nia','S�o_Paulo',1).
cc('Goi�nia','Belo_Horizonte',1).
cc('Goi�nia','Salvador',1).
cc('Goi�nia','Palmas',1).
cc('Goi�nia','Cuiab�',1).
cc('Goi�nia','Distrito_Federal',1).
cc('Distrito_Federal','Goi�nia',1).
cc('Distrito_Federal','Belo_Horizonte',1).

cc('S�o_Paulo','Campo_Grande',1).
cc('S�o_Paulo','Belo_Horizonte',1).
cc('S�o_Paulo','Rio_de_Janeiro',1).
cc('S�o_Paulo','Curitiba',1).
cc('Rio_de_Janeiro','S�o_Paulo',1).
cc('Rio_de_Janeiro','Vit�ria',1).
cc('Rio_de_Janeiro','Belo_Horizonte',1).
cc('Vit�ria','Salvador',1).           % Regi�o Sudeste.
cc('Vit�ria','Belo_Horizonte',1).
cc('Vit�ria','Rio_de_Janeiro',1).
cc('Belo_Horizonte','Vit�ria',1).
cc('Belo_Horizonte','Rio_de_Janeiro',1).
cc('Belo_Horizonte','S�o_Paulo',1).
cc('Belo_Horizonte','Goi�nia',1).
cc('Belo_Horizonte','Salvador',1).
cc('Belo_Horizonte','Distrito_Federal',1).

cc('Rio_Branco','Porto_Velho',1).
cc('Rio_Branco','Manaus',1).
cc('Manaus','Rio_Branco',1).
cc('Manaus','Boa_Vista',1).
cc('Manaus','Porto_Velho',1).
cc('Manaus','Bel�m',1).
cc('Manaus','Cuiab�',1).
cc('Boa_Vista','Manaus',1).
cc('Boa_Vista','Bel�m',1).
cc('Macap�','Bel�m',1).
cc('Bel�m','Macap�',1).
cc('Bel�m','Boa_Vista',1).
cc('Bel�m','Manaus',1).               % Regi�o Norte.
cc('Bel�m','Cuiab�',1).
cc('Bel�m','Palmas',1).
cc('Bel�m','S�o_Lu�s',1).
cc('Porto_Velho','Manaus',1).
cc('Porto_Velho','Cuiab�',1).
cc('Porto_Velho','Rio_Branco',1).
cc('Palmas','Bel�m',1).
cc('Palmas','S�o_Lu�s',1).
cc('Palmas','Teresina',1).
cc('Palmas','Salvador',1).
cc('Palmas','Goi�nia',1).
cc('Palmas','Cuiab�',1).

cc('Salvador','Belo_Horizonte',1).
cc('Salvador','Goi�nia',1).
cc('Salvador','Palmas',1).
cc('Salvador','Terezina',1).
cc('Salvador','Aracaju',1).
cc('Salvador','Macei�',1).
cc('Salvador','Recife',1).
cc('Aracaju','Salvador',1).
cc('Aracaju','Macei�',1).
cc('Macei�','Salvador',1).
cc('Macei�','Recife',1).
cc('Macei�','Aracaju',1).         % Regi�o Nordeste.
cc('Recife','Macei�',1).
cc('Recife','Salvador',1).
cc('Recife','Terezina',1).
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

% //////////////////////////////////////////////////////////


% Fun��o de busca em profundidade.
% caminho(In�cio,Fim,Percurso,Lista a retornar,Contador,Dist�ncia).
caminho(No,No, _,[No],D,D).

caminho(A,Z,Percurso,[A|Fim],Count,D):-
    cc(A,Prox,Peso), % Pega o n� filho de A.
    not(member(Prox,Percurso)), % Verifica se j� n�o est� no Percurso.
    caminho(Prox,Z,[A|Percurso],Fim,Count+Peso,D).%Faz o mesmo Processo para o n� filho.


% Retorna o primeiro caminho encontrado.
% A,Z -> Capitais; Lista -> Caminho de A a Z.
quest_2(A,Z,Lista):-
    caminho(A,Z,[1],Lista,0,_), % Input -> A, Z,[1], 0.
    !.  % Output -> Lista.

% Retorna o primeiro caminho encontrado e a dist�ncia.
% A,Z -> Capitais; Lista -> Percurso; D -> Dist�ncia.
quest_3(A,Z,Lista,D):-
    caminho(A,Z,[1],Lista,0,X),!, % Input -> A,Z,[1],0.
    D is X. % Output -> Lista,D.



% Fun��o de busca em largura.
% caminho4(Destino,[n(Destinho, Percurso)]|_],N�s_visitados,
%                                                Caminho_revertido).
caminho4(Meta,[n(Meta,Caminho)|_],_,Rcaminho):-  reverse(Rcaminho,Caminho).

caminho4(Meta,[n(Inicio,CI)|RCI],Visitados,Caminho):-
    write('--------------------------------------'),nl,
    write('Meta: '),write(Meta),tab(4),write('In�cio: '),write(Inicio),nl,
    write('Caminho desde o In�cio: '),write(CI),nl,nl,

    write('N�s filhos anteriores: '),write(RCI),nl,nl,

                           % Pega o pr�ximo de in�cio se n�o estiver em visitados
    findall(n(I1,[CI,[Inicio]]),(cc(Inicio,I1,_),\+member(I1,Visitados)),Cs),
                           % Depois adiciona a uma lista e a retorna.

    write('N�s Filhos: '),tab(4),write(Cs),nl,nl,
    append(RCI,Cs,Nc), % Novo Come�o � Lista do findall + Calda(RCI).
    write('Novo Caminho: '),tab(4),write(Nc),nl,nl,
    write('N�s Visitados: '),tab(4),write([Inicio,Visitados]),nl,nl,
    caminho4(Meta,Nc,[Inicio|Visitados],Caminho). % chama para n�s filhos.


% fun��o auxiliar ao predicado quest_4/3.
edit([X|Y],Meta,SS):-
    Z = [Y|X],      % organiza lista
    append(Z,Meta,Z1), % coloca o destino.
    flatten(Z1,SS).    % achata a lista.


% Retorna o Caminho entre In�cio e Meta.
% In�cio, Meta -> Capitais; Caminho -> Caminho de In�cio � Meta.
quest_4(Inicio,Meta,Caminho):-
    caminho4(Meta,[n(Inicio,[])],[],Aux), % Input -> Meta, n(In�cio,[]),[].
    edit(Aux,Meta,Caminho),!. %  Output -> Caminho



% Obs.: Quest�o 5 utiliza o mesmo predicado das quest�es 2 e 3:
% caminho/6.

% vetor das dist�ncias poss�veis. Retorna todos os caminhos.
vek(A,Z,Y):-
    findall([Lista,D],(caminho(A,Z,[1],Lista,0,X),D is X),Y).

% fun��o para organizar o output do predicado quest_5/2.
organizar([[X|Y]|Z]):-
    write('Caminho: '),write(X),nl,  % printa o percurso
    write('Dist�ncia: '),write(Y),nl, % printa a dist�ncia.
    nl,organizar(Z).

% Retorna todos os caminhos poss�veis de A a Z e suas
% respectivas dist�ncias.
% A,Z -> Capitais.
quest_5(A,Z) :-
    vek(A,Z,Lista), % Input -> A,Z.
    organizar(Lista). % Output -> Lista.



