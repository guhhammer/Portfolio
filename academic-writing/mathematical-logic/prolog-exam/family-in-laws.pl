% Mathematical Logic exam, question 1 (group work).
%
% Given facts about who is married to whom and who is whose mother, define
% the relation co_sibling_in_law/2 ("concunhado" in Portuguese): the spouse
% of a sibling of one's spouse, or the spouse of one's own sibling-in-law.

mother(maria, joao).
mother(maria, julia).
mother(gabriela, eduardo).
mother(gabriela, vitoria).
mother(joana, camila).

married(joao, vitoria).
married(vitoria, joao).
married(camila, eduardo).
married(eduardo, camila).

sibling(X, Y) :- mother(A, X), mother(A, Y), X \= Y.

sibling_in_law(X, Y) :- sibling(X, A), married(A, Y).

% Y is married to a sibling of X's spouse ...
co_sibling_in_law(X, Y) :-
    married(X, Z), mother(W, Z), mother(W, A), A \= Z, married(A, Y).
% ... or Y is married to a sibling-in-law of X.
co_sibling_in_law(X, Y) :-
    sibling_in_law(X, A), married(A, Y).

% ?- co_sibling_in_law(joao, Who).
% Who = camila.
