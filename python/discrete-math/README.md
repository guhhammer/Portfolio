# Discrete mathematics (Python)

Coursework from the "Discrete Problem Solving" course at PUCPR (2019): counting (sum and product rules, inclusion–exclusion, pigeonhole principle, permutations and combinations), proof techniques (direct, by contradiction, by induction), recursive definitions, Boolean algebra with Karnaugh maps and logic circuits, and a final assignment on hashing and cryptography (Caesar cipher and RSA).

For a non-technical reader: this is the mathematics behind programming: counting how many possibilities exist, proving that a claim always holds, and designing the logic circuits inside a machine.

| Folder | What it is | Run |
| --- | --- | --- |
| `counting/` | Counting exercises with ASCII decision trees (coin tosses without consecutive heads, an election, a best-of-seven play-off, IP address classes) and the pigeonhole / permutation / combination list; every answer is a small function | `python3 counting_exercises.py`, `python3 pigeonhole_permutations_combinations.py` |
| `proofs/` | Direct proofs and proofs by contradiction about even and odd numbers and divisibility, and assignment 03 on mathematical induction (closed-form sums proved step by step) | read the documents |
| `recursion/` | Assignment 04: ten recursive definitions (multiplication by repeated addition, Fibonacci, geometric sums, string reverse and length, factorial, population growth) implemented in Python, plus the written answers | `python3 recursive_definitions.py` |
| `boolean-algebra/group-assignment-3-elevator-circuit/` | Group assignment: input and output variables, truth tables, Karnaugh map and the resulting circuits for an elevator "going up / going down" controller | read `report.docx` and the figures |
| `boolean-algebra/assignment-05-vending-machine-circuit/` | Assignment 05 (with André Wlodkovski): Boolean circuit for a coffee / milk / tea machine that allows at most one choice at a time, then extended with hot chocolate; truth tables, expressions and circuits for each item in `figures/` | read `report.pdf` |
| `hashing-and-cryptography/` | Assignment 07 (team of five): hash table with chaining, linear congruential random generator, Caesar cipher and textbook RSA over a 27-symbol alphabet | `python3 hashing_and_cryptography.py` |

The documents are in Portuguese (the course language); the code, comments and this index are in English. All scripts run with Python 3.13 (`python -m py_compile` clean). The textbooks and the professor's slides that used to sit here were removed.
