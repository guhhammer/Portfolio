# Project 1: lottery combinations and set cover

Given the numbers 1 to 50, the assignment asks for: (1) the counts of 5-, 4-, 3- and 2-number combinations; (2 to 4) the smallest set of 5-number cards that covers every 2-, 3- and 4-number combination (scenarios C1, C2, C3), a set-cover problem solved greedily in phases; (5 to 8) the payout of each scenario under the prize rules of a European lottery (2, 3, 4 and 5 matching numbers); (6/9) a backtest against the draw history in `database/euro_concursos.csv`; and (7) the time-complexity analysis of every program, written as cost annotations next to each line.

`lottery_combinations.py` is the answer: a `Programs` class with one method per program, a decorator that can stop the pipeline, threads for the independent scenarios, pickled caches of the generated scenarios (`arrs/`) and timing charts (`time_analysis/`). `development_tests.py` holds earlier ideas (a De Bruijn sequence generator and a matrix-based first version). `console/sample-output.txt` is the console of a complete run; `report/` has the assignment document with my answers and the figures used in it.

Run with `python3 lottery_combinations.py` (a full run takes a long time; set `load=True` in the scenario calls to reuse the pickled caches).
