% Menu-driven car registry for an insurer: a dynamic auto/15 fact per car with assert/retract,
% searches by owner, plate and registration number, a report, counting, and save/load of the facts
% to cars.txt with tell/told and consult. Group 12: Gustavo Hammerschmidt, Guilherme Fenner Hey,
% Pedro Churata, Bruno Alves. Logic Programming course, PUCPR (2019).
% Load with: swipl car-registry.pl   then:  ?- start.
%
% auto('Registration(1)', 'Plate(2)', 'OwnerFirstName(3)', 'OwnerSurname(4)', 'Make(5)', 'Model(6)',
%      'Year(7)', 'Street(8)', 'Number(9)', 'Complement(10)', 'City(11)', 'State(12)',
%      'PostalCode(13)', 'Mobile(14)', 'Phone(15)').

:- dynamic auto/15.

% -----------------------------------------------------------------------

% helper of options 02, 04, 05 and 06: prints one car and fails to reach the next one
show_data(Reg, Pl, N, S, Make, M, Y, St, Num, C, City, State, Zip, Mob, Tel) :-
    nl,
    tab(10), write('First name: '), tab(10), write(N), nl,
    tab(10), write('Surname: '), tab(10), write(S), nl,
    nl,
    tab(10), write('Make: '), tab(10), write(Make), nl,
    tab(10), write('Model: '), tab(10), write(M), nl,
    tab(10), write('Plate: '), tab(10), write(Pl), nl,
    tab(10), write('Registration: '), tab(10), write(Reg), nl,
    tab(10), write('Year: '), tab(10), write(Y), nl,
    nl,
    tab(10), write('Street: '), tab(10), write(St), nl,
    tab(10), write('Number: '), tab(10), write(Num), nl,
    tab(10), write('Complement: '), tab(10), write(C), nl,
    tab(10), write('Postal code: '), tab(10), write(Zip), nl,
    tab(10), write('City: '), tab(10), write(City), nl,
    tab(10), write('State: '), tab(10), write(State), nl,
    nl,
    tab(10), write('Mobile: '), tab(10), write(Mob), nl,
    tab(10), write('Phone: '), tab(10), write(Tel), nl,
    nl,
    write('Type anything to look for more: '), read(_), nl,
    nl,
    fail.

% goes back to the menu
recall :-
    nl, nl,
    write('Type anything to go back to the menu: '), tab(10), read(_),
    nl, start.

% helper of option 12: prints the make and model of the owner's cars
owner_cars(N, S) :- auto(_,_,N,S,Make,Model,_,_,_,_,_,_,_,_,_),
              tab(10), write('Has 1 '), tab(4), write(Make),
              write(' '), tab(4), write(Model), nl, fail.

% -----------------------------------------------------------------------

% 01.
insert_car :-
    nl, write('(1) -  add a car: '), nl,
    nl,
    tab(10), write('First name: '), tab(10), read(N),
    tab(10), write('Surname: '), tab(10), read(S),
    nl,
    tab(10), write('Make: '), tab(10), read(Make),
    tab(10), write('Model: '), tab(10), read(M),
    tab(10), write('Plate: '), tab(10), read(P),
    tab(10), write('Registration: '), tab(10), read(Reg),
    tab(10), write('Year: '), tab(10), read(Y),
    nl,
    tab(10), write('Street: '), tab(10), read(St),
    tab(10), write('Number: '), tab(10), read(Num),
    tab(10), write('Complement: '), tab(10), read(C),
    tab(10), write('Postal code: '), tab(10), read(Zip),
    tab(10), write('City: '), tab(10), read(City),
    tab(10), write('State: '), tab(10), read(State),
    nl,
    tab(10), write('Mobile: '), tab(10), read(Mob),
    tab(10), write('Phone: '), tab(10), read(Tel),
    nl,
    assert(auto(Reg,P,N,S,Make,M,Y,St,Num,C,City,State,Zip,Mob,Tel)).

% 02.
find_by_name(N, S) :-
    auto(Reg,Pl,N,S,Make,M,Y,St,Num,C,City,State,Zip,Mob,Tel),
    show_data(Reg,Pl,N,S,Make,M,Y,St,Num,C,City,State,Zip,Mob,Tel).
find_by_name(N, S) :-
    not(auto(_,_,N,S,_,_,_,_,_,_,_,_,_,_,_)),
    write('Nobody with that name was found.').

% 03.
delete_car(Pl) :-
    retract(auto(_,Pl,_,_,_,_,_,_,_,_,_,_,_,_,_)), nl,
    tab(10), write('Car deleted.').
delete_car(Pl) :-
    not(auto(_,Pl,_,_,_,_,_,_,_,_,_,_,_,_,_)),
    write('No such plate was found.').

% 04.
find_by_plate(Pl) :-
    auto(Reg,Pl,N,S,Make,M,Y,St,Num,C,City,State,Zip,Mob,Tel),
    show_data(Reg,Pl,N,S,Make,M,Y,St,Num,C,City,State,Zip,Mob,Tel).
find_by_plate(Pl) :-
    not(auto(_,Pl,_,_,_,_,_,_,_,_,_,_,_,_,_)),
    write('Nobody with that plate was found.').

% 05.
find_by_registration(R) :-
    auto(R,P,N,S,Make,M,Y,St,Num,C,City,State,Zip,Mob,Tel),
    show_data(R,P,N,S,Make,M,Y,St,Num,C,City,State,Zip,Mob,Tel).
find_by_registration(R) :-
    not(auto(R,_,_,_,_,_,_,_,_,_,_,_,_,_,_)),
    write('Nobody with that registration was found.').

% 06.
car_report :-
    auto(R,P,N,S,Make,M,Y,St,Num,C,City,State,Zip,Mob,Tel),
    show_data(R,P,N,S,Make,M,Y,St,Num,C,City,State,Zip,Mob,Tel).
car_report :-
    not(auto(_,_,_,_,_,_,_,_,_,_,_,_,_,_,_)),
    write('No car in the database.').

% 07.
car_count(P, Count) :- findall(15, P, L), length(L, Count).

% 08.
save_data :- tell('cars.txt'), listing(auto/15), told.

% 09.
load_cars :- consult('cars.txt').

% 10.
clear_data :- retract(auto(_,_,_,_,_,_,_,_,_,_,_,_,_,_,_)), fail.
clear_data :- tell('cars.txt'), listing(), told.

% 12. edit the address of a car found by its plate
edit_menu(Pl) :-
    retract(auto(R,Pl,N,S,Make,M,Y,_,_,_,_,_,_,C,T)),
    nl,
    write('Re-enter the following data: '), nl,
    tab(10), write('Street: '), tab(10), read(St),
    tab(10), write('Number: '), tab(10), read(Num),
    tab(10), write('Complement: '), tab(10), read(Com),
    tab(10), write('Postal code: '), tab(10), read(Zip),
    tab(10), write('City: '), tab(10), read(City),
    tab(10), write('State: '), tab(10), read(State),
    nl,
    assert(auto(R,Pl,N,S,Make,M,Y,St,Num,Com,City,State,Zip,C,T)),
    nl, tab(4), write('The car data was updated.').
edit_menu(Pl) :-
    not(auto(_,Pl,_,_,_,_,_,_,_,_,_,_,_,_,_)),
    write('Nobody with that plate was found.').

% -----------------------------------------------------------------------

% option(X) runs menu option X

option(1) :- insert_car, recall.

option(2) :-
    nl, write('(2) -  Find a car by owner name: '),
    nl, nl, write('First name: '), tab(10), read(N),
    nl, write('Surname: '), tab(10), read(S),
    find_by_name(N, S), recall.

option(3) :-
    nl, write('(3) -  Delete a car by plate: '),
    nl, nl, write('Plate: '), tab(10), read(Pl),
    delete_car(Pl), nl, nl, recall.

option(4) :-
    nl, write('(4) -  Find a car by plate: '),
    nl, nl, write('Plate: '), tab(10), read(P),
    find_by_plate(P), recall.

option(5) :-
    nl, write('(5) -  Find a car by registration: '),
    nl,
    nl, write('Registration: '), tab(10), read(R),
    find_by_registration(R), recall.

option(6) :-
    nl, write('(6) -  Car report: '),
    nl, nl, car_report, recall.

option(7) :-
    nl, write('(7) -  Number of cars: '),
    nl, nl,
    car_count(auto(_,_,_,_,_,_,_,_,_,_,_,_,_,_,_), Count), tab(4),
    write('Number of cars: '), tab(10), write(Count), recall.

option(8) :-
    nl, write('(8) -  Save the data to a file: '),
    nl, nl, save_data, tab(10), write(' Data saved.'),
    nl, recall.

option(9) :-
    nl, write('(9) -  Load the data from a file: '), nl, nl,
    load_cars, tab(10), write(' Data loaded.'),
    nl, recall.

option(10) :-
    nl, write('(10) -  Clear the registry: '),
    nl, nl, clear_data, tab(10), write(' Data cleared.'),
    nl, recall.

option(11) :-
    nl, nl,
    tab(10), write('Leaving.'), nl,
    nl, write('---------------------------------').

option(12) :-
    nl, write('(12) Edit a car.'),
    nl,
    nl, write('Plate: '), tab(10), read(Pl),
    edit_menu(Pl), recall.

% keeps the application running on an invalid option
option(_) :- recall.

% -----------------------------------------------------------------------

%  start.    <-  the menu
start :-
    nl, write('---------------------------------'),
    nl, write('Insurer car database.'),
    nl,
    nl,
    write('Operations: '), nl,
    tab(10), write('(1)  Add a car.'), nl,
    tab(10), write('(2)  Find a car by owner name.'), nl,
    tab(10), write('(3)  Delete a car by plate.'), nl,
    tab(10), write('(4)  Find a car by plate.'), nl,
    tab(10), write('(5)  Find a car by registration.'), nl,
    tab(10), write('(6)  Car report.'), nl,
    tab(10), write('(7)  Number of cars.'), nl,
    tab(10), write('(8)  Save the data to a file.'), nl,
    tab(10), write('(9)  Load the data from a file.'), nl,
    tab(10), write('(10) Clear the registry.'), nl,
    tab(10), write('(11) Quit.'), nl,
    tab(10), write('(12) Edit a car.'), nl,
    nl,
    write('---------------------------------'), nl,
    nl,
    write('Option:'), tab(10),
    read(Aux),
    write('---------------------------------'),
    option(Aux),
    nl.
