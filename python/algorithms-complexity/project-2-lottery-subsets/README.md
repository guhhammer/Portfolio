# Project 2: nested lottery subsets

For the numbers 1 to 25, generate the combinations S15 (15 numbers) down to S11, then select the smallest set of 15-number cards that covers every 14-number subset (SB15_14), and repeat for 13, 12 and 11 (each built on the previous selection). Programs 6 and 7 time the selections ten times each, plot the averages in `charts/`, and price the selected cards at R$ 2.50 each.

`match_selector.py` implements the selection with generators: it turns the lower-size combinations into a dictionary, forks each larger combination into its sub-combinations and keeps the ones that cover something new. Its comments explain the set-theory reasoning and give a small worked example. Run `python3 main.py`; `sample-output.txt` is the console of a full run and `report.pdf` the written report.
