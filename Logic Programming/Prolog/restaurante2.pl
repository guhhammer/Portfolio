

% QUEST�O 2:

% NOME: Gustavo Hammerschmidt.


% Lista de clientes: In�cio.
cliente([maria,
         helena,
         jos�,
         ant�nio]).
% Lista de clientes: Fim.



% Card�pio:  In�cio.
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
                 arroz_e_feij�o,
                 batata_e_bife,
                 salada_ceasar,
                 macarr�o]).
%
%
% tipos:  sobremesas.
sobremesa([sorvete_chocolate,
           sorvete_baunilha,
           sorvete_morango,
           sorvete_flocos,
           sorvete_lim�o]).
%
%
% tipos:  vinhos.
tipo_vinho([champagne,
            vinho_tinto,
            vinho_branco,
            vinho_ros�,
            vinho_seco]).
%
% Card�pio: Fim.





/*
 *   - Lista de coisas que o cliente gosta(comida ou bebida).
 *   - eu gosto de chocolate ->     gosta(gustavo, [chocolate]).
 *   - ordem da lista [entrada,
 *                     principal,
 *                     sobremesa,
 *                     bebida]
*/
%
% Prefer�ncias:  In�cio.
%
gosta(ant�nio, [torrada,
                file_mignon, macarr�o,
                sorvete_chocolate,
                vinho_tinto, vinho_ros�]).

gosta(maria, [sopa,
              batata_e_bife, salada_ceasar,
              sorvete_baunilha, sorvete_morango, sorvete_flocos,
              champagne]).

gosta(helena, [batata_chips,
               file_mignon,
               sorvete_lim�o,
               vinho_branco, vinho_ros�, vinho_seco]).

gosta(jos�, [sopa, batata_chips, torrada, bolachinha,
             arroz_e_feij�o, batata_e_bife,
             sorvete_chocolate, sorvete_baunilha,
             vinho_branco]).
%
% Prefer�ncias: Fim.






/*    O que a pessoa X gosta de comer(Y).
 *    gosta_de_comer(ant�nio, Y) ou quem gosta de comer sopa.
 *
 *    gosto eu de comer X(macarr�o) -> gosta_de_comer(gustavo, X).
 *                                              Retorna: macarr�o.
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
% Fun��es gosta_de_comer(...): Fim.







/*    O que a pessoa X gosta de tomar(Y).
 *    gosta_de_tomar(ant�nio, Y) ou quem gosta de tomar vinho_tinto.
 *
 *    gosto eu de tomar X(champagne) -> gosta_de_tomar(gustavo, x).
 *                                              Retorna: champagne.
*/
%
gosta_de_tomar(X, Y) :- cliente(L), member(X, L),
                        gosta(X, M), member(Y, M),
                        tipo_vinho(N), member(Y, N).
%
% Fun��es gosta_de_tomar(...): Fim.






/*    A pessoa X, que gosta de comer(Y), gosta de comer(Z)    ou
 *    A pessoa X, que gosta de comer(Z), gosta de comer(Y)    ou
 *    A pessoa X, que gosta de comer(macarr�o), e que gosta de
      comer(Z, sendo Z uma vari�vel ou n�o).
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
% Fun��es gosta_de_X_e_de(...): Fim.



































