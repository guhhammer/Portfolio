carro(joao_da_silva,corsa_sedan,data_compra(27,05,1997),14250,placa(sj_pinhais,'AJV',2453)).
carro(carlos_pereira,cherokee,data_compra(02,08,2000),57400,placa(curitiba,'KCV',
1490)).
carro(ana_cruz,monza,data_compra(14,06,2000),11600,placa(curitiba,'EAF',3544)).
carro(carlos_pereira,silverado,data_compra(15,08,2001),46800,placa(curitiba,'LHR',
1178)).
carro(jose_emanuel,corsa_sedan,data_compra(06,11,2004),23400,placa(sj_pinhais,'AJV',2273)).
carro(jose_emanuel,clio,data_compra(19,12,2004),25730,placa(sj_pinhais,'CKP',5194)).


% Família Pereira Santos
pessoa(joao, pereira_santos, nascimento(27,05,1938), origem(curitiba), profissao(engenheiro)).
pessoa(maria, pereira_santos, nascimento(12,06,1945), origem(sao_paulo), profissao(professora)).
pessoa(julio, pereira_santos, nascimento(14,09,1972), origem(curitiba), profissao(medico)).
pessoa(ana, pereira_santos, nascimento(22,11,1975), origem(curitiba), profissao(dentista)).
pessoa(claudia, pereira_santos, nascimento(05,05,1978), origem(curitiba), profissao(musico)).

% Família Silva Pinheiro
pessoa(carlos, silva_pinheiro, nascimento(01,04,1962), origem(guarulhos), profissao(mecanico)).
pessoa(ana_claudia, silva_pinheiro, nascimento(18,07,1966), origem(castro), profissao(do_lar)).
pessoa(silvia, silva_pinheiro, nascimento(27,12,1998), origem(sao_paulo), profissao(nenhuma)).
pessoa(carolina, silva_pinheiro, nascimento(27,12,1998), origem(sao_paulo), profissao(nenhuma)).
pessoa(claudia, silva_pinheiro, nascimento(15,04,2003), origem(curitiba), profissao(nenhuma)).

% Família Nogueira Carvalho
pessoa(marcos, nogueira_carvalho, nascimento(12,07,1952), origem(curitiba), profissao(advogado)).
pessoa(patricia, nogueira_carvalho, nascimento(07,09,1952), origem(jau), profissao(enfermeira)).
pessoa(andrea,  nogueira_carvalho, nascimento(14,02,1978), origem(curitiba), profissao(nenhuma)).
pessoa(augusto, nogueira_carvalho, nascimento(22,12,1983), origem(curitiba), profissao(nenhuma)).









programa( X ) :-
	carro(X,_,_,_,P),
	carro(X,_,_,_,P1),
	P \= P1.

% compra_cidade_ano(X, Y, Z) :- carro(Ko,_,data_compra(_,_,Y), _,
% placa(Z,_,_)), X is Ko.



compra_cidade_ano(X,Y,Z) :- carro(X, _,data_compra(_,_,Z),_,placa(Y, _,_)).

governou(deodoro_da_fonseca,1891,1891).
governou(floriano_peixoto,1891,1894).
governou(prudente_de_moraes,1894,1898).
governou(compos_sales,1898,1902).
governou(rodrigues_alves,1902,1906).
governou(afonso_pena,1906,1909).
governou(nilo_pecanha,1909,1910).


presidente(Y, X) :- governou(X,A,B), A =< Y, Y =< B.

cubo(X,Y) :-  Y is X**3.

maior(A,B, X) :- X is B, B> A;  X is A, A>B.


power(X,Y) :-  0 =< X -> pow(X,2,Y); pow(-1*X,2,Y).
distancia(X1,X2,Y1,Y2,D) :-  power(X1-Y1,S), power(X2-Y2,T), D is sqrt((S+T)/2).



informa_classe(X) :-  (X < 700.00 -> writeln('X = classe_baixa.')),!.
informa_classe(X) :-  (701.00 =< X, X =< 2500.00 -> writeln('X = classe_média.')),!.
informa_classe(X) :-  (2501.00 =< X, X =< 4500.00 -> writeln('X = classe_média-alta')),!.
informa_classe(X) :-  (4500.00 < X -> writeln('X = classe_alta')),!.



p(a).
p(b).
q(a,1).
q(a,2).
q(b,3).
q(b,4).
r(1,1).
r(3,5).
r(1,2).
r(3,6).
r(2,3).
r(4,7).
r(2,4).
r(4,8).



% Esta ta funcionando colocar no word porque ainda não está lá.
verifica_profissao(N, S, P) :- pessoa(N, S, _,_, profissao(P)) -> true,
	write(N),write(' é '),write(P).

verifica_profissao(N, S, P) :- not(pessoa(N, S, _,_, profissao(P))),
	write(N),write(' não é '),write(P).





% Encontra_membros está feito.
finder(Sobrenome) :-
	pessoa(N, Sobrenome, nascimento(A,B,C), origem(X),profissao(Y)),nl,
	write('Nome: '),write(N),nl,
	write('Sobrenome: '),write(Sobrenome),nl,
	write('Nascimento: '),write(A),write('/'),write(B),write('/'),write(C),nl,
	write('Origem: '),write(X),nl,
	write('Profissão: '),write(Y),nl,nl,fail.

finder(Sobrenome) :-
	not(pessoa(_, Sobrenome,_,_,_)),
	write('Nenhuma pessoa dessa família foi encontrada.').



encontra_membros :-
	write('Por favor, entre com o nome da família: '),nl,
	read(Sobrenome),nl,
	write('Os membros dessa família são: '),nl,nl,
	finder(Sobrenome).



% pessoa 1 for mais velha.
diferença(Nom1, Sob1, Nom2, Sob2, D, M,A) :-
	pessoa(Nom1, Sob1, nascimento(F,G,H),_,_),
	pessoa(Nom2, Sob2, nascimento(I,J,K),_,_),
	K >= H,
	A is K - H,
	M is (12-G)+J,
	D is (31-F)+I.


% pessoa 2  for mais velha.
diferença(Nom1, Sob1, Nom2, Sob2, D, M,A) :-
	pessoa(Nom1, Sob1, nascimento(F,G,H),_,_),
	pessoa(Nom2, Sob2, nascimento(I,J,K),_,_),
	K < H,
	A is H - K,
	M is (12-J)+G,
	D is (31-I)+F.


diferença_idade(Nom1, Sob1, Nom2, Sob2) :-

	diferença(Nom1, Sob1, Nom2, Sob2, D, M,A),nl,
	write('A diferença de idade é de '),
	write(A), write(' anos, '), write(M),
	write(' meses e '), write(D), write(' dias.')
	.








classe(N, negativo) :-	N < 0,!.
classe(N, positivo) :-  N > 0,!.
classe(0, zero).




max(X,Y,X) :- X >= Y, !.
	max(X,Y,Y).




