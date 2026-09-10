# Randomized sorting benchmark (Java)

Bimonthly project of the Algorithm Complexity course at PUCPR (2021): comparing the purely recursive and the randomised-recursive versions of quicksort, merge sort and selection sort, on ascending, descending and random arrays from 1,000 up to 80 million elements.

Each sort is annotated with its per-line cost and its recurrence (see the source comments), timed, and the results are plotted (`report/charts/`) and analysed (`report/`). The written conclusions are in `report/part3-answers.md` and the recurrences in `report/part4-recurrences.md`.

| Package | Classes |
| --- | --- |
| `sorting/` | `RecursiveQuicksort`, `RecursiveMergesort`, `RecursiveSelectionsort` (each with a plain and a randomised variant), `AbstractSort` |
| `arrays/` | `ArrayFactory` builds ascending, descending and random `int[]` arrays |
| `main/` | `Main` runs the benchmark grid and prints/plots the average times |

Build and run:

```sh
javac -d out src/main/Main.java src/arrays/ArrayFactory.java src/sorting/*.java && (cd out && java main.Main)
```

`make_excel.py` turns the raw timing dumps in `data/` into the spreadsheet. This is the Java project of the same course whose Python work is in [`../../python/algorithms-complexity/`](../../python/algorithms-complexity/).
