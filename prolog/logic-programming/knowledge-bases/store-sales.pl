% Store sales kept as dynamic facts loaded from sales.txt: totals with a global accumulator
% (retract/assert), totals per month, editing a sale and writing the facts back to the file.
% Logic Programming course, PUCPR (2019). Load with: swipl store-sales.pl
%   ?- load_sales.   ?- sum_sales.   ?- sum_sales_month(2, 2015).   ?- save_write.

% ls. lists the files in the folder; listing(Pred/Arity) shows the clauses of a predicate.
% sale/5 must be declared dynamic because it is loaded and changed at run time.

:- dynamic sale/5.
:- dynamic cont/1.
:- dynamic total_sales/1.

load_sales :-
    consult('sales.txt'),
    retractall(total_sales(_)),
    assert(total_sales(0)).

show_predicates :- listing(sale/5).

% add_all needs a global variable: total_sales/1
sum_sales :- sale(_,_,Q,_,V), add_all(Q, V), fail.

sum_sales :-
    write('Total sales: '),
    total_sales(X), !,
    format('~2f', X),
    retractall(total_sales(_)),
    assert(total_sales(0)), nl.

add_all(Q, V) :-
       retract(total_sales(S)), Z is S + Q * V,
       assert(total_sales(Z)).

sum_sales_month(M, Y) :- sale(_, date(_,M,Y), Q, _, V), add_all(Q, V), fail.

sum_sales_month(_, _) :-
    write('Total sales: '),
    total_sales(X), !,
    format('~2f', X),
    retractall(total_sales(_)),
    assert(total_sales(0)), nl.

save(X) :-
    sale(A,B,C,D,E),
    write(X, sale(A,B,C,D,E)),
    writeln(X, '.'),
    nl, fail.

save(_).

save_write() :-
    open('sales.txt', write, A),
    save(A),
    close(A).

save_tell() :-
    tell('sales.txt'), listing(sale/5), told.

% corrects the price of one sale
update :-
    retract(sale(claudia, date(18,2,2015), 3, shoes, _)),
    assert(sale(claudia, date(18,2,2015), 3, shoes, 181.90)).

/*
Saving predicates to files:
?- open('data.pl', write, A),
    write(A, likes(maria, books)), write(A, '.'),
    close(A).
?- tell('data.pl'), listing(likes/2), told.
*/
