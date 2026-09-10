% A small database of people (three families) and queries over it: twins, family members,
% professions and age differences. Logic Programming course, PUCPR (2019), exercise lists 3 to 5.
% Load with: swipl people-database.pl

% person(FirstName, Surname, born(Day, Month, Year), from(City), profession(Profession)).

% Pereira Santos family
person(joao, pereira_santos, born(27,05,1938), from(curitiba), profession(engineer)).
person(maria, pereira_santos, born(12,06,1945), from(sao_paulo), profession(teacher)).
person(julio, pereira_santos, born(14,09,1972), from(curitiba), profession(physician)).
person(ana, pereira_santos, born(22,11,1975), from(curitiba), profession(dentist)).
person(claudia, pereira_santos, born(05,05,1978), from(curitiba), profession(musician)).

% Silva Pinheiro family
person(carlos, silva_pinheiro, born(01,04,1962), from(guarulhos), profession(mechanic)).
person(ana_claudia, silva_pinheiro, born(18,07,1966), from(castro), profession(homemaker)).
person(silvia, silva_pinheiro, born(27,12,1998), from(sao_paulo), profession(none)).
person(carolina, silva_pinheiro, born(27,12,1998), from(sao_paulo), profession(none)).
person(claudia, silva_pinheiro, born(15,04,2003), from(curitiba), profession(none)).

% Nogueira Carvalho family
person(marcos, nogueira_carvalho, born(12,07,1952), from(curitiba), profession(lawyer)).
person(patricia, nogueira_carvalho, born(07,09,1952), from(jau), profession(nurse)).
person(andrea, nogueira_carvalho, born(14,02,1978), from(curitiba), profession(none)).
person(augusto, nogueira_carvalho, born(22,12,1983), from(curitiba), profession(none)).

% twins: same surname and same birth date
twins(X, Y, Surname) :-
    person(X, Surname, born(D,M,Y1), _, _),
    person(Y, Surname, born(D,M,Y1), _, _),
    X \= Y.

% does person N S have profession P?
check_profession(N, S, P) :-
    person(N, S, _, _, profession(P)) -> true,
    write(N), write(' is a '), write(P).
check_profession(N, S, P) :-
    \+ person(N, S, _, _, profession(P)),
    write(N), write(' is not a '), write(P).

% print every member of a family
list_members(Surname) :-
    person(N, Surname, born(D,M,Y), from(City), profession(P)), nl,
    write('First name: '), write(N), nl,
    write('Surname: '), write(Surname), nl,
    write('Born: '), write(D), write('/'), write(M), write('/'), write(Y), nl,
    write('From: '), write(City), nl,
    write('Profession: '), write(P), nl, nl, fail.
list_members(Surname) :-
    \+ person(_, Surname, _, _, _),
    write('No person of that family was found.').

find_members :-
    write('Please type the family name: '), nl,
    read(Surname), nl,
    write('The members of that family are: '), nl, nl,
    list_members(Surname).

% --- age difference between two people, in days, then converted to years, months and days
% days_between(D1,M1,Y1, D2,M2,Y2, Delta): months are counted as 153/5 days and years as 1461/4 days
days_between(D1, M1, Y1, D2, M2, Y2, 0) :-
    Y1 =:= Y2, M1 =:= M2, D2 =:= D1, !.
days_between(D1, M1, Y1, D2, M2, Y2, Delta) :-
    Y1 =:= Y2, M1 =:= M2, D1 > D2, Delta is D1 - D2, !.
days_between(D1, M1, Y1, D2, M2, Y2, Delta) :-
    Y1 =:= Y2, M1 =:= M2, D2 > D1, Delta is D2 - D1, !.
days_between(D1, M1, Y1, D2, M2, Y2, Delta) :-
    Y1 =:= Y2, M2 > M1, Delta is round(((M2-M1)*153/5) + D2 - D1), !.
days_between(D1, M1, Y1, D2, M2, Y2, Delta) :-
    Y1 =:= Y2, M1 > M2, Delta is round(((M1-M2)*153/5) + D1 - D2), !.
days_between(D1, M1, Y1, D2, M2, Y2, Delta) :-
    Y2 > Y1,
    Delta is round((365 - ((((M1-1)*153/5)) + D1)) + ((Y2-Y1-1)*1461/4) + ((M2-1)*153/5) + D2), !.
days_between(D1, M1, Y1, D2, M2, Y2, Delta) :-
    Y1 > Y2,
    Delta is round((365 - ((((M2-1)*153/5)) + D2)) + ((Y1-Y2-1)*1461/4) + ((M1-1)*153/5) + D1).

days_to_date(Delta, Years, Months, Days) :-
    Years is Delta // 365,
    Months is (Delta mod 365) // 30,
    Days is (Delta mod 365) mod 30.

print_years(1) :- write('1 year, '), !.
print_years(Y) :- write(Y), write(' years, ').
print_months(1) :- write('1 month and '), !.
print_months(M) :- write(M), write(' months and ').
print_days(1) :- write('1 day.'), !.
print_days(D) :- write(D), write(' days.').

age_difference(N1, S1, N2, S2) :-
    person(N1, S1, born(D1,M1,Y1), _, _),
    person(N2, S2, born(D2,M2,Y2), _, _),
    days_between(D1, M1, Y1, D2, M2, Y2, Delta),
    days_to_date(Delta, Years, Months, Days),
    write('The age difference is '),
    print_years(Years), print_months(Months), print_days(Days).
