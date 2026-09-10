# Logic programming (Prolog)

Coursework from the Logic Programming course at PUCPR (2019), translated to English: facts, rules, recursion over lists, graph search over the Brazilian state capitals, and small knowledge-base applications with dynamic predicates and file I/O. Every file loads in SWI-Prolog (verified with version 9.2).

| Folder | Files | What they show |
| --- | --- | --- |
| [`basics/`](basics/) | `class-examples.pl`, `family-relations.pl`, `people-database.pl`, `exercise-lists-3-4-5.pl` | Facts and rules, arithmetic, negation as failure and the cut, a family tree, queries over a database of people (twins, family members, age difference in years/months/days), cars, presidents by year. |
| [`lists/`](lists/) | `list-exercises.pl`, `exam-practice.pl`, `list-basics.pl` | 25 list predicates written without the built-ins (`length`, `member`, `reverse`, `flatten`): filtering, average, splitting, merging with a hand-written selection sort, sub-lists, run-length encoding and decoding, replication; a fold/reduce with a `yall` lambda. |
| [`graphs/`](graphs/) | `capitals-graph-search.pl`, `capitals-dijkstra.pl`, `breadth-first-search.pl`, `weighted-paths.pl` | Depth-first and breadth-first search and all-paths enumeration over the map of Brazilian capitals; a Dijkstra-style shortest path that keeps the best path per node in a dynamic predicate. |
| [`knowledge-bases/`](knowledge-bases/) | `car-registry.pl` (+ `cars.txt`), `store-sales.pl` (+ `sales.txt`), `calculator-and-gradebook.pl` (+ `students.txt`), `restaurant-preferences.pl`, `bagof-setof-cities.pl` | Menu-driven CRUD over `assert`/`retract` with save and load through `tell`/`told` and `consult`; totals with a global accumulator; `bagof`/`setof` with the `^` quantifier. |
| [`documents/`](documents/) | PDF answers of exercise lists 3-4-5 and 6 (group 12) and the report on list 7 (graphs), in Portuguese. | |

```sh
cd graphs && swipl capitals-graph-search.pl
?- question_3('Curitiba', 'Manaus', Path, Distance).
?- go('Curitiba', 'Manaus').          % in capitals-dijkstra.pl
cd ../lists && swipl list-exercises.pl
?- question_23([a,a,a,b,c,c], Z).     % Z = [[a,3],[b,1],[c,2]]
```
