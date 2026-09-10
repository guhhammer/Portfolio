# Algorithm complexity (Python)

Coursework from the "Algorithm Complexity" course at PUCPR (2021): counting instruction costs, asymptotic notation (Big-O, Omega, Theta) checked numerically with charts, recurrence equations and the master theorem, complexity classes, and two larger projects on lottery combinations (a set-cover problem) with full cost analysis of every line.

For a non-technical reader: this course is about predicting how the running time of a program grows with the size of its input, and proving it.

## Classes and exercises

| Folder | Topic | Files |
| --- | --- | --- |
| `class-01-introduction/` | Why algorithms matter (insertion vs merge sort on ten million numbers), diagnostic assessment on factorial and binary search | `discussion-answers.md`, `diagnostic-answers.md`, `validation.py`, handwritten answers |
| `class-02-sorting-cost/` | Insertion sort and two bubble sorts annotated with per-line costs, timed and plotted | `sorting_cost.py`, chart |
| `class-03-asymptotic-notation/` | Ten claims of the form f(n) = O/Omega/Theta(g(n)) proved or refuted algebraically and drawn with matplotlib | `asymptotic_notation.py`, `charts/` |
| `class-04-induction/` | Proof by induction of a closed-form sum | `induction-exercise.md`, exercises document |
| `class-10-recurrence-analysis/`, `class-11-recurrence-check/` | Deriving T(n) recurrences from annotated recursive code and checking given recurrences against the code | `recurrences.py`, `recurrence_check.py` |
| `class-12-master-theorem/` | Five recurrences solved with the master theorem, including the regularity constant | `master-theorem-exercises.md` |
| `class-14-complexity-classes/` | P, NP and NP-complete classification of five problems | `answers.md` |
| `submission-2-recurrences-and-quicksort/` | Cost of quicksort's partition/swap/quick functions, recurrence and Big-O; the Fibonacci call tree | `answers.md` |
| `exam-1/`, `exam-1-review/` | Exam charts for Theta/Omega/Big-O claims, the exam PDF, a C++ file annotating bubble sort and binary search costs | `exam_charts.py`, `review_charts.py`, `cost_annotations.cpp` |
| `assignment-1-film-analysis/` | Report on the film about Ramanujan and its relation to proofs | `report.pdf`, `notes.md` |
| `assignment-2-parallel-insertion-sort/` | Insertion sort fixed from the handout, run in parallel on partitions with threads and merged; best/worst-case analysis | `parallel_insertion_sort.py` |

## Projects

- [`project-1-lottery-combinations/`](project-1-lottery-combinations/): smallest sets of 5-number cards covering every 2-, 3- and 4-number combination of 1..50 (greedy set cover in phases), payouts under lottery prize rules, a backtest against real draw history, and the time-complexity analysis of nine programs.
- [`project-2-lottery-subsets/`](project-2-lottery-subsets/): nested selections of 15-number cards covering every 14-, 13-, 12- and 11-number subset of 1..25, built with generators, timed and charted.

The sorting analysis project of this course was written in Java and lives in [`../../java/sorting-analysis/`](../../java/sorting-analysis/).

All scripts compile with Python 3.13 (`python -m py_compile`); the chart scripts need `numpy` and `matplotlib`.
