% Mathematical Logic exam, question 2 (group work).
%
% A restaurant keeps a list of customers, starters, main courses, desserts
% and wines, and what each customer likes.  Given one preference (a wine, a
% dessert, a main course or a starter) the system answers the others.

customer(maria).
customer(helena).
customer(jose).
customer(antonio).

starter(soup).
starter(potato_chips).
starter(toast).
starter(crackers).

main_course(filet_mignon).
main_course(rice_and_beans).
main_course(steak_and_potatoes).
main_course(caesar_salad).
main_course(pasta).

dessert(chocolate_ice_cream).
dessert(vanilla_ice_cream).
dessert(strawberry_ice_cream).
dessert(cookies_and_cream_ice_cream).
dessert(lemon_ice_cream).

wine(champagne).
wine(red_wine).
wine(white_wine).
wine(rose_wine).
wine(dry_wine).

% likes(Customer, Item): Item may be food or drink.
likes(antonio, toast).
likes(antonio, red_wine).
likes(antonio, chocolate_ice_cream).
likes(antonio, filet_mignon).
likes(antonio, pasta).
likes(antonio, rose_wine).

% What does a customer like to eat?  Answers only dishes.
%   ?- likes_to_eat(antonio, X).   X = toast ; X = filet_mignon ; ...
likes_to_eat(X, Y) :- customer(X), likes(X, Y), starter(Y).
likes_to_eat(X, Y) :- customer(X), likes(X, Y), main_course(Y).
likes_to_eat(X, Y) :- customer(X), likes(X, Y), dessert(Y).

% What does a customer like to drink?  Answers only wines.
%   ?- likes_to_drink(antonio, X).   X = red_wine ; X = rose_wine.
likes_to_drink(X, Y) :- customer(X), likes(X, Y), wine(Y).

% Customer X likes Y; does X also like Z?  With Z unbound it lists the other
% things X likes; with Z bound it answers true or false.
%   ?- likes_both(antonio, red_wine, pasta).   true.
likes_both(X, Y, Z) :- likes(X, Y), likes_to_eat(X, Z), Y \= Z.
likes_both(X, Y, Z) :- likes(X, Y), likes_to_drink(X, Z), Y \= Z.
