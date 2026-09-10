% Exercise lists 3, 4 and 5 (group 12: Gustavo Hammerschmidt, Guilherme Fenner Hey, Bruno Alves,
% Pedro Churata). Logic Programming course, PUCPR (2019). Load with: swipl exercise-lists-3-4-5.pl

% car(Owner, Model, bought(Day,Month,Year), Price, plate(City, Letters, Number)).
car(joao_da_silva, corsa_sedan, bought(27,05,1997), 14250, plate(sj_pinhais, 'AJV', 2453)).
car(carlos_pereira, cherokee, bought(02,08,2000), 57400, plate(curitiba, 'KCV', 1490)).
car(ana_cruz, monza, bought(14,06,2000), 11600, plate(curitiba, 'EAF', 3544)).
car(carlos_pereira, silverado, bought(15,08,2001), 46800, plate(curitiba, 'LHR', 1178)).
car(jose_emanuel, corsa_sedan, bought(06,11,2004), 23400, plate(sj_pinhais, 'AJV', 2273)).
car(jose_emanuel, clio, bought(19,12,2004), 25730, plate(sj_pinhais, 'CKP', 5194)).

% owners of more than one car
multiple_owner(X) :-
    car(X, _, _, _, P),
    car(X, _, _, _, P1),
    P \= P1.

% who bought a car in city Y in year Z
bought_in(X, Y, Z) :- car(X, _, bought(_,_,Z), _, plate(Y, _, _)).

% Brazilian presidents of the First Republic
governed(deodoro_da_fonseca, 1891, 1891).
governed(floriano_peixoto, 1891, 1894).
governed(prudente_de_moraes, 1894, 1898).
governed(campos_sales, 1898, 1902).
governed(rodrigues_alves, 1902, 1906).
governed(afonso_pena, 1906, 1909).
governed(nilo_pecanha, 1909, 1910).

president(Year, X) :- governed(X, A, B), A =< Year, Year =< B.

cube(X, Y) :- Y is X ** 3.

larger(A, B, X) :- X is B, B > A ; X is A, A > B.

% square of the absolute value, and the distance formula built from it
square(X, Y) :- 0 =< X -> pow(X, 2, Y) ; pow(-1 * X, 2, Y).
distance(X1, X2, Y1, Y2, D) :- square(X1 - Y1, S), square(X2 - Y2, T), D is sqrt((S + T) / 2).

pow(_, 0, 1).
pow(X, Y, Z) :- Y1 is Y - 1, pow(X, Y1, Z1), Z is Z1 * X, !.

% income class
income_class(X) :- (X < 700.00 -> writeln('X = lower class')), !.
income_class(X) :- (701.00 =< X, X =< 2500.00 -> writeln('X = middle class')), !.
income_class(X) :- (2501.00 =< X, X =< 4500.00 -> writeln('X = upper-middle class')), !.
income_class(X) :- (4500.00 < X -> writeln('X = upper class')), !.

% chained relations for the unification exercise
p(a).
p(b).
q(a, 1).
q(a, 2).
q(b, 3).
q(b, 4).
r(1, 1).
r(3, 5).
r(1, 2).
r(3, 6).
r(2, 3).
r(4, 7).
r(2, 4).
r(4, 8).

sign(N, negative) :- N < 0, !.
sign(N, positive) :- N > 0, !.
sign(0, zero).

max(X, Y, X) :- X >= Y, !.
max(_, Y, Y).
