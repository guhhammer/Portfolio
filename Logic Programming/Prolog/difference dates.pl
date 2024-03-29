% Fam�lia Pereira Santos
pessoa(joao, pereira_santos, nascimento(27,05,1938), origem(curitiba), profissao(engenheiro)).
pessoa(maria, pereira_santos, nascimento(12,06,1945), origem(sao_paulo), profissao(professora)).
pessoa(julio, pereira_santos, nascimento(14,09,1972), origem(curitiba), profissao(medico)).
pessoa(ana, pereira_santos, nascimento(22,11,1975), origem(curitiba), profissao(dentista)).
pessoa(claudia, pereira_santos, nascimento(05,05,1978), origem(curitiba), profissao(musico)).

% Fam�lia Silva Pinheiro
pessoa(carlos, silva_pinheiro, nascimento(01,04,1962), origem(guarulhos), profissao(mecanico)).
pessoa(ana_claudia, silva_pinheiro, nascimento(18,07,1966), origem(castro), profissao(do_lar)).
pessoa(silvia, silva_pinheiro, nascimento(27,12,1998), origem(sao_paulo), profissao(nenhuma)).
pessoa(carolina, silva_pinheiro, nascimento(27,12,1998), origem(sao_paulo), profissao(nenhuma)).
pessoa(claudia, silva_pinheiro, nascimento(15,04,2003), origem(curitiba), profissao(nenhuma)).

% Fam�lia Nogueira Carvalho
pessoa(marcos, nogueira_carvalho, nascimento(12,07,1952), origem(curitiba), profissao(advogado)).
pessoa(patricia, nogueira_carvalho, nascimento(07,09,1952), origem(jau), profissao(enfermeira)).
pessoa(andrea,  nogueira_carvalho, nascimento(14,02,1978), origem(curitiba), profissao(nenhuma)).
pessoa(augusto, nogueira_carvalho, nascimento(22,12,1983), origem(curitiba), profissao(nenhuma)).



opp(D1,M1,A1,D2,M2,A2,Delta) :-
    A1 =:= A2, M1 =:= M2, D2 =:= D1,
    Delta is 0, !.

opp(D1,M1,A1,D2,M2,A2,Delta) :-
    A1 =:= A2, M1 =:= M2, D1 > D2,
    Delta is (D1-D2), !.

opp(D1,M1,A1,D2,M2,A2,Delta) :-
    A1 =:= A2, M1 =:= M2, D2 > D1,
    Delta is (D2-D1), !.

opp(D1,M1,A1,D2,M2,A2,Delta) :-
    A1 =:= A2, M2 > M1,
    Delta is round(((M2-M1)*153/5)+D2-D1), !.

opp(D1,M1,A1,D2,M2,A2,Delta) :-
    A1 =:= A2, M1 > M2,
    Delta is round(((M1-M2)*153/5)+D1-D2), !.

opp(D1,M1,A1,D2,M2,A2,Delta) :-
    A2 > A1,
    Delta is round((365-((((M1-1)*153/5))+D1))+((A2-A1-1)*1461/4)+((M2-1)*153/5)+D2), !.

opp(D1,M1,A1,D2,M2,A2,Delta) :-
    A1 > A2,
    Delta is round((365-((((M2-1)*153/5))+D2))+((A1-A2-1)*1461/4)+((M1-1)*153/5)+D1).

dias_pra_data(Delta, Ano, Mes, Dia) :-
    Ano is (Delta//365),
    Mes is ((Delta mod 365)//30),
    Dia is ((Delta mod 365) mod 30).


print_ano(Ano) :- Ano =:= 1, write(Ano),write(' ano, '),!.
print_ano(Ano) :- write(Ano), write(' anos, ').

print_mes(Mes) :- Mes =:= 1, write(Mes),write(' m�s e '),!.
print_mes(Mes) :- write(Mes), write(' meses e ').

print_dia(Dia) :- Dia =:= 1, write(Dia),write(' dia. '),!.
print_dia(Dia) :- write(Dia), write(' dias. ').




diferen�a_idade(N1,S1,N2,S2) :-

    pessoa(N1,S1,nascimento(D1,M1,A1),_,_),
    pessoa(N2,S2,nascimento(D2,M2,A2),_,_),
    opp(D1,M1,A1,D2,M2,A2,Delta),
    dias_pra_data(Delta, Ano, Mes, Dia),
    write('A diferen�a de idade � de '),
    print_ano(Ano),
    print_mes(Mes),
    print_dia(Dia).


