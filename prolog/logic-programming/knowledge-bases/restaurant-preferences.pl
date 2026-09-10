% Restaurant: customers, a menu split into courses and each customer's preferences; queries find
% what a customer likes to eat or drink and pairs of things the same customer likes.
% Logic Programming course, PUCPR (2019). Load with: swipl restaurant-preferences.pl
%   ?- likes_to_eat(antonio, X).   ?- likes_to_drink(X, red_wine).   ?- likes_both(maria, X, Y).

customers([maria, helena, jose, antonio]).

% menu
starters([soup, potato_chips, toast, crackers]).
mains([filet_mignon, rice_and_beans, steak_and_potatoes, caesar_salad, pasta]).
desserts([chocolate_ice_cream, vanilla_ice_cream, strawberry_ice_cream, stracciatella_ice_cream, lemon_ice_cream]).
wines([champagne, red_wine, white_wine, rose_wine, dry_wine]).

% preferences: likes(Customer, [starter..., main..., dessert..., drink...])
likes(antonio, [toast, filet_mignon, pasta, chocolate_ice_cream, red_wine, rose_wine]).
likes(maria, [soup, steak_and_potatoes, caesar_salad, vanilla_ice_cream, strawberry_ice_cream, stracciatella_ice_cream, champagne]).
likes(helena, [potato_chips, filet_mignon, lemon_ice_cream, white_wine, rose_wine, dry_wine]).
likes(jose, [soup, potato_chips, toast, crackers, rice_and_beans, steak_and_potatoes, chocolate_ice_cream, vanilla_ice_cream, white_wine]).

% what customer X likes to eat (Y), or who likes to eat Y
likes_to_eat(X, Y) :- customers(L), member(X, L), likes(X, M), member(Y, M), starters(N), member(Y, N).
likes_to_eat(X, Y) :- customers(L), member(X, L), likes(X, M), member(Y, M), mains(N), member(Y, N).
likes_to_eat(X, Y) :- customers(L), member(X, L), likes(X, M), member(Y, M), desserts(N), member(Y, N).

% what customer X likes to drink (Y), or who likes to drink Y
likes_to_drink(X, Y) :- customers(L), member(X, L), likes(X, M), member(Y, M), wines(N), member(Y, N).

% two different things (both food or both drink) that customer X likes
likes_both(X, Y, Z) :- likes_to_eat(X, Y), likes_to_eat(X, Z), Y \= Z.
likes_both(X, Y, Z) :- likes_to_drink(X, Y), likes_to_drink(X, Z), Y \= Z.
