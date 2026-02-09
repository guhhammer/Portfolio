

% QUESTÃO 2:

% NOME: Gustavo Hammerschmidt.


% Lista de clientes: Início.
cliente([maria,
         helena,
         josé,
         antônio]).
% Lista de clientes: Fim.



% Cardápio:  Início.
%
% tipos: pratos de entrada.
prato_entrada([sopa,
               batata_chips,
               torrada,
               bolachinha]).
%
%
% tipos:   pratos principais.
prato_principal([file_mignon,
                 arroz_e_feijão,
                 batata_e_bife,
                 salada_ceasar,
                 macarrão]).
%
%
% tipos:  sobremesas.
sobremesa([sorvete_chocolate,
           sorvete_baunilha,
           sorvete_morango,
           sorvete_flocos,
           sorvete_limão]).
%
%
% tipos:  vinhos.
tipo_vinho([champagne,
            vinho_tinto,
            vinho_branco,
            vinho_rosé,
            vinho_seco]).
%
% Cardápio: Fim.





/*
 *   - Lista de coisas que o cliente gosta(comida ou bebida).
 *   - eu gosto de chocolate ->     gosta(gustavo, [chocolate]).
 *   - ordem da lista [entrada,
 *                     principal,
 *                     sobremesa,
 *                     bebida]
*/
%
% Preferências:  Início.
%
gosta(antônio, [torrada,
                file_mignon, macarrão,
                sorvete_chocolate,
                vinho_tinto, vinho_rosé]).

gosta(maria, [sopa,
              batata_e_bife, salada_ceasar,
              sorvete_baunilha, sorvete_morango, sorvete_flocos,
              champagne]).

gosta(helena, [batata_chips,
               file_mignon,
               sorvete_limão,
               vinho_branco, vinho_rosé, vinho_seco]).

gosta(josé, [sopa, batata_chips, torrada, bolachinha,
             arroz_e_feijão, batata_e_bife,
             sorvete_chocolate, sorvete_baunilha,
             vinho_branco]).
%
% Preferências: Fim.






/*    O que a pessoa X gosta de comer(Y).
 *    gosta_de_comer(antônio, Y) ou quem gosta de comer sopa.
 *
 *    gosto eu de comer X(macarrão) -> gosta_de_comer(gustavo, X).
 *                                              Retorna: macarrão.
*/
%
gosta_de_comer(X, Y) :- cliente(L), member(X, L),
                        gosta(X, M), member(Y, M),
                        prato_entrada(N), member(Y, N).
%
%
gosta_de_comer(X, Y) :- cliente(L), member(X, L),
                        gosta(X, M), member(Y, M),
                        prato_principal(N), member(Y, N).
%
%
gosta_de_comer(X, Y) :- cliente(L), member(X, L),
                        gosta(X, M), member(Y, M),
                        sobremesa(N), member(Y, N).
%
% Funções gosta_de_comer(...): Fim.







/*    O que a pessoa X gosta de tomar(Y).
 *    gosta_de_tomar(antônio, Y) ou quem gosta de tomar vinho_tinto.
 *
 *    gosto eu de tomar X(champagne) -> gosta_de_tomar(gustavo, x).
 *                                              Retorna: champagne.
*/
%
gosta_de_tomar(X, Y) :- cliente(L), member(X, L),
                        gosta(X, M), member(Y, M),
                        tipo_vinho(N), member(Y, N).
%
% Funções gosta_de_tomar(...): Fim.






/*    A pessoa X, que gosta de comer(Y), gosta de comer(Z)    ou
 *    A pessoa X, que gosta de comer(Z), gosta de comer(Y)    ou
 *    A pessoa X, que gosta de comer(macarrão), e que gosta de
      comer(Z, sendo Z uma variável ou não).
      Ou, em vez de comer, aquilo que a pessoa gosta de tomar.
 *
*/
%
gosta_de_X_e_de(X,Y,Z) :- gosta_de_comer(X, Y),
                          gosta_de_comer(X, Z),
                          Y\=Z.
%
%
gosta_de_X_e_de(X,Y,Z) :- gosta_de_tomar(X, Y),
                          gosta_de_tomar(X, Z),
                          Y\=Z.
%
% Funções gosta_de_X_e_de(...): Fim.



































