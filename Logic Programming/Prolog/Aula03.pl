% Família Pereira Santos
pessoa(joao, pereira_santos, nascimento(27,05,1938), origem(curitiba), profissao(engenheiro)).
pessoa(maria, pereira_santos, nascimento(12,06,1945), origem(sao_paulo),
       profissao(professora)).
pessoa(julio, pereira_santos, nascimento(14,09,1972), origem(curitiba),
       profissao(medico)).
pessoa(ana, pereira_santos, nascimento(22,11,1975), origem(curitiba),
       profissao(dentista)).
pessoa(claudia, pereira_santos, nascimento(05,05,1978), origem(curitiba),
       profissao(musico)).

% Família Silva Pinheiro
pessoa(carlos, silva_pinheiro, nascimento(01,04,1962), origem(guarulhos),
       profissao(mecanico)).
pessoa(ana_claudia, silva_pinheiro, nascimento(18,07,1966), origem(castro),
       profissao(do_lar)).
pessoa(silvia, silva_pinheiro, nascimento(27,12,1998), origem(sao_paulo),
       profissao(nenhuma)).
pessoa(carolina, silva_pinheiro, nascimento(27,12,1998), origem(sao_paulo),
       profissao(nenhuma)).
pessoa(claudia, silva_pinheiro, nascimento(15,04,2003), origem(curitiba),
       profissao(nenhuma)).

% Família Nogueira Carvalho
pessoa(marcos, nogueira_carvalho, nascimento(12,07,1952), origem(curitiba),
       profissao(advogado)).
pessoa(patricia, nogueira_carvalho, nascimento(07,09,1952), origem(jau),
       profissao(enfermeira)).
pessoa(andrea,  nogueira_carvalho, nascimento(14,02,1978), origem(curitiba),
       profissao(nenhuma)).
pessoa(augusto, nogueira_carvalho, nascimento(22,12,1983), origem(curitiba),
       profissao(nenhuma)).



media(X, Y, Z) :-   Z is  (X + Y)/2.

entre(X, Y, Z)  :-   Y  =<  X,  X =< Z.


igual(X,X).


f(N, X) :-  N<10, X is -1 ; N>=10, N<100, X is 0; N>=100, X is 1.



gemeos(X,Y,Z) :- pessoa(X, Z, nascimento(D,M,A),_,_), pessoa(Y, Z, nascimento(D,M,A), _,_), X \= Y.
