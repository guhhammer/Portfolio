% A menu-driven calculator (read/1 input, arithmetic, division-by-zero guard) and a gradebook that
% consults students.txt and prints each student's average. Logic Programming course, PUCPR (2019).
% Load with: swipl calculator-and-gradebook.pl   then:  ?- calc.   or   ?- gradebook.

option(X) :-
    X =:= 1,
    nl,
    writeln('--- (Sum = A + B) ---'),
    write('A: '), read(A), nl,
    write('B: '), read(B), nl,
    Y is A + B,
    write(A), write(' + '), write(B), write(' = '), write(Y).

option(X) :-
    X =:= 2,
    nl,
    writeln('--- (Subtraction = A - B) ---'),
    write('A: '), read(A), nl,
    write('B: '), read(B), nl,
    Y is A - B,
    write(A), write(' - '), write(B), write(' = '), write(Y).

option(X) :-
    X =:= 3,
    nl,
    writeln('--- (Multiplication = A * B) ---'),
    write('A: '), read(A), nl,
    write('B: '), read(B), nl,
    Y is A * B,
    write(A), write(' * '), write(B), write(' = '), write(Y).

option(X) :-
    X =:= 4,
    nl,
    writeln('--- (Division = A / B) ---'),
    write('A: '), read(A), nl,
    write('B: '), read(B), nl,
    B =\= 0,
    Y is A / B,
    write(A), write(' / '), write(B), write(' = '), write(Y).

option(X) :-
    X =:= 4,
    nl,
    write('Cannot divide by zero.'), read(_).

calc :-
    nl,
    writeln('***********************************'),
    writeln('*        Simple calculator        *'),
    writeln('***********************************'), nl, nl,
    writeln('Choose an option:'), nl,
    writeln('1- Sum.'),
    writeln('2- Subtraction.'),
    writeln('3- Multiplication.'),
    writeln('4- Division.'),
    writeln('5- Quit.'), nl, nl,
    read(Option),
    Option =\= 5,
    option(Option),
    calc.

calc :-
    nl,
    write('Leaving the calculator.').

% students.txt holds facts a(Name, Grade1, Grade2, Grade3).
gradebook :-
    nl,
    consult('students.txt'),
    writeln('============================'),
    writeln('**        Class grades        **'),
    writeln('============================'),
    writeln("Name  G1   G2   G3   Average"),
    a(Name, N1, N2, N3),
    Average is ((N1 + N2 + N3) / 3),
    write(Name), tab(5),
    write(N1), tab(5),
    write(N2), tab(5),
    write(N3), tab(5),
    format('~2f', Average), nl,
    writeln('-----------------------------------------'),
    fail,
    nl.
