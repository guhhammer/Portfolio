feminine(joanne).
feminine(helen).
feminine(heater).


masculine(john).
masculine(steve).
masculine(paul).

dog(runner).
dog(fighter).

bird(feuer).

rabbit(snow).

animal(dog(runner)).
animal(dog(fighter)).
animal(bird(feuer)).
animal(rabbit(snow)).

my_pet(kate, runner).
my_pet(junior, feuer).
my_pet(lis, fighter).
my_pet(jake, snow).

married(paul, joanne).
married(steve, helen).


person(X) :- feminine(X).
person(X) :- masculine(X).



mother(helen, junior).
mother(joanne, kate).
mother(heater, lis).
mother(heater, jake).


father(X, Y) :- masculine(X),married(X, Z), mother(Z, Y).



son(X, Y) :- masculine(X), mother(Y, X).
son(X, Y) :- masculine(X), father(Y, X).

daughter(X, Y) :- feminine(X), mother(Y, X).
daughter(X, Y) :- feminine(X), father(Y, X).

brother(X, Y) :- masculine(X), mother(Z, X), mother(Z, Y), X\=Y.
brother(X, Y) :- masculine(X), father(Z, X), mother(Z, Y), X\=Y.

sister(X, Y) :- feminine(X), mother(Z, X), mother(Z, Y), X\=Y.
sister(X, Y) :- feminine(X), father(Z, X), father(Z, Y), X\=Y.

uncle(X, Y) :- masculine(X), brother(X, Z), father(Z, Y).
uncle(X, Y) :- masculine(X), brother(X, Z), mother(Z, Y).

aunt(X, Y) :- feminine(X), sister(X, Z), father(Z, Y).
aunt(X, Y) :- feminine(X), sister(X, Z), mother(Z, Y).

cousin(X, Y) :- masculine(X), mother(Z, X), mother(W, Y), sister(Z, W).
cousin(X, Y) :- masculine(X), father(Z, X), father(W, Y), brother(Z, W).
cousin(X, Y) :- feminine(X), mother(Z, X), mother(W, Y), sister(Z, W).
cousin(X, Y) :- feminine(X), father(Z, X), father(W, Y), brother(Z, W).

grandpa(X, Y) :- masculine(X), father(X, Z), father(Z, Y).
grandpa(X, Y) :- masculine(X), father(X, Z), mother(Z, Y).

grandma(X, Y) :- feminine(X), mother(X, Z), father(Z, Y).
grandma(X, Y) :- feminine(X), mother(X, Z), mother(Z, Y).


sibling(X, Y) :- masculine(X), married(X, Z), mother(Z, Y).
sibling(X, Y) :- feminine(X), married(X, Z), father(Z, Y).

dog(X, Y) :- animal(X), dog(X) , my_pet(Y,X).
bird(X, Y) :- animal(X), bird(X), my_pet(Y,X).
rabbit(X, Y) :- animal(X), rabbit(X), my_pet(Y, X).

















