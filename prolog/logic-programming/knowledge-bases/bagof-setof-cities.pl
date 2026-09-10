% bagof/3 and setof/3 over a table of cities: grouping by state and country, with and without the
% ^ existential quantifier. Logic Programming course, PUCPR (2019). Load with: swipl bagof-setof-cities.pl

city(curitiba, parana, brazil).
city(blumenau, santa_catarina, brazil).
city(joinville, santa_catarina, brazil).
city(foz_do_iguacu, parana, brazil).
city(londrina, parana, brazil).
city(maringa, parana, brazil).
city(ushuaia, tierra_del_fuego, argentina).
city(santa_rosa, la_pampa, argentina).
city(cordoba, cordoba, argentina).

% 1. every city of Argentina, whatever the state
ex1(Cities) :- bagof(C, Y^city(C, Y, argentina), Cities).
% ?- ex1(Cities).
% Cities = [ushuaia, santa_rosa, cordoba].

% 2. the cities of Brazil, one answer per state
ex2(Cities, State) :- bagof(C, city(C, State, brazil), Cities).
% ?- ex2(Cities, State).
% Cities = [curitiba, foz_do_iguacu, londrina, maringa], State = parana ;
% Cities = [blumenau, joinville], State = santa_catarina.

% 3. the cities of every state of every country
ex3(Cities, State) :- bagof(C, Country^city(C, State, Country), Cities).

% 4. the distinct states of each country
ex4(States, Country) :- setof(State, X^city(X, State, Country), States).
% ?- ex4(States, Country).
% States = [cordoba, la_pampa, tierra_del_fuego], Country = argentina ;
% States = [parana, santa_catarina], Country = brazil.

% 5. the same with bagof: states repeat once per city
ex5(States, Country) :- bagof(State, X^city(X, State, Country), States).
% ?- ex5(States, Country).
% States = [tierra_del_fuego, la_pampa, cordoba], Country = argentina ;
% States = [parana, santa_catarina, santa_catarina, parana, parana, parana], Country = brazil.
