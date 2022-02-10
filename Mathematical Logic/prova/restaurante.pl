
cliente(maria).      % QUEST�O 2:
cliente(helena).
% NOMES: Gustavo Hammerschmidt, Allan Tayamichi, Jo�o Felipe Bittencourt, Vinicius Abade, Jo�o Pedro Capoani.
cliente(jos�).       % clientes
cliente(ant�nio).

prato_entrada(sopa).
prato_entrada(batata_chips).     %tipos: pratos de entrada.
prato_entrada(torrada).
prato_entrada(bolachinha).


prato_principal(file_mignon).
prato_principal(arroz_e_feij�o).    % tipos: pratos principais.
prato_principal(batata_e_bife).
prato_principal(salada_ceasar).
prato_principal(macarr�o).


sobremesa(sorvete_chocolate).
sobremesa(sorvete_baunilha).
sobremesa(sorvete_morango).   % tipos:  sobremesas.
sobremesa(sorvete_flocos).
sobremesa(sorvete_lim�o).

tipo_vinho(champagne).
tipo_vinho(vinho_tinto).
tipo_vinho(vinho_branco).   % tipos:  vinhos.
tipo_vinho(vinho_ros�).
tipo_vinho(vinho_seco).

gosta(ant�nio, torrada).
gosta(ant�nio, vinho_tinto).
gosta(ant�nio, sorvete_chocolate).   % coisas que ant�nio gosta.
gosta(ant�nio, file_mignon).
gosta(ant�nio, macarr�o).
gosta(ant�nio, vinho_ros�).
% cliente gosta de algo(pode ser comida ou bebida).
%   eu gosto de chocolate ->     gosta(gustavo, chocolate).

gosta_de_comer(X, Y) :- cliente(X), gosta(X,Y), prato_entrada(Y).
gosta_de_comer(X, Y) :- cliente(X), gosta(X,Y), prato_principal(Y).
gosta_de_comer(X, Y) :- cliente(X), gosta(X,Y), sobremesa(Y).
% O que o cliente gosta de comer?
% Responde apenas comidas que o cliente gosta de comer.
% gosto eu de comer X(macarr�o) ->  gosta_de_comer(gustavo, X).
%                             Retorna:   macarr�o.

gosta_de_tomar(X, Y) :- cliente(X), gosta(X,Y), tipo_vinho(Y).
% O que o cliente gosta de tomar?
% Responde apenas bebidas que o cliente gosta de tomar.
%  gosto eu de X(vinho_tinto) ->  gosta_de_tomar(gustavo, X).
%                              Retorna:    vinho_tinto.

gosta_de_X_e_de(X,Y,Z) :- gosta(X, Y), gosta_de_comer(X, Z),  Y\=Z.
gosta_de_X_e_de(X,Y,Z) :- gosta(X, Y), gosta_de_tomar(X, Z), Y\=Z.
% "O cliente gosta de vinho e de macarr�o?" -> gosta_de_X_e_de(cliente,
% vinho, macarr�o).
% retorna falso ou verdadeiro.
%
% X representa cliente, Y representa algo que o cliente gosta e voc�
% sabe, e Z representa algo que gosta e voc� sabe e quer ter certeza ou
% n�o sabe e quer descobrir o que �.
%
















