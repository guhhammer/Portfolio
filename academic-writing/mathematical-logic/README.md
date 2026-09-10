# Mathematical logic

Coursework on propositional and predicate logic and the foundations of mathematics (PUCPR, 2019). Most of the course was handwritten exercise lists, which are not kept here; what remains is the group exam, rewritten in English, and a book report.

- `prolog-exam/`: the two exam questions ([`questions.md`](prolog-exam/questions.md)) and their Prolog answers, verified with SWI-Prolog 9. [`family-in-laws.pl`](prolog-exam/family-in-laws.pl) defines siblings, siblings-in-law and co-siblings-in-law from mother/married facts; [`restaurant-preferences.pl`](prolog-exam/restaurant-preferences.pl) is a small knowledge base of customers, dishes and wines that answers what a customer likes to eat and drink.
- `logicomix-book-report.docx`: a critical review (in Portuguese) of *Logicomix*, the graphic novel about Bertrand Russell and the foundational crisis of mathematics.

```prolog
?- co_sibling_in_law(joao, Who).
Who = camila.
?- likes_to_drink(antonio, Wine).
Wine = red_wine ; Wine = rose_wine.
```
