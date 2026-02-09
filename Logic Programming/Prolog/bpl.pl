a(a,b,lig(a,b)).
a(a,c,lig(a,c)).
a(b,d,lig(b,d)).
a(b,e,lig(b,e)).
a(c,f,lig(c,f)).
a(c,g,lig(c,g)).
a(e,h,lig(e,h)).


a(1,7,lig(1,7)).
a(1,8,lig(1,8)).
a(1,3,lig(1,3)).
a(7,4,lig(7,4)).
a(7,20,lig(7,20)).
a(7,17,lig(7,17)).
a(8,6,lig(8,6)).
a(3,9,lig(3,9)).
a(3,12,lig(3,12)).
a(4,42,lig(4,42)).
a(20,28,lig(20,28)).
a(9,19,lig(9,19)).


conectado(X,Y,A):- a(X,Y,A).
conectado(X,Y,A):- a(Y,X,A).


bus_lar(Ini, Meta, Camin):-
    bus_larg_a(Meta,[n(Ini, [])],[],Camin).


bus_larg_a(Meta,[n(Meta, Camin)|_],_,RCamin):-
    reverse(RCamin,Camin).

bus_larg_a(Meta,[n(Ini,CI)|RCI],Visitados,Camin):-
    write("-----------"),nl,write(Meta),nl,write("I:"),
    write(Ini),nl,write("CI:"),write(CI),nl,
    write("RCI:"),write(RCI),nl,
    findall(n(I1,[A|CI]),(conectado(Ini,I1,A),\+member(I1,Visitados)),Cs),
    write(Cs),nl,
    append(RCI,Cs,NC),
    write(NC),nl,
    write([Ini,Visitados]),
    bus_larg_a(Meta,NC,[Ini|Visitados],Camin),nl.

