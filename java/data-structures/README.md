# Data structures (Java)

Coursework from the Data Structures course at PUCPR (2019): the classic data structures and algorithms, each implemented from scratch. Rewritten in English; every project compiles with `javac` (verified). Some were team projects with João Capoani and Davi Leal.

| Project | What it is |
| --- | --- |
| [`linked-list/`](linked-list/) | Singly linked list with ordered insertion, removal, cosine similarity and intersection of two lists |
| [`stack/`](stack/) | Array-backed stack, used to validate that brackets `()`, `[]`, `{}` in an expression are balanced |
| [`circular-queue/`](circular-queue/) | Circular queue and a merge of two queues in ascending order |
| [`binary-tree/`](binary-tree/) | Self-balancing AVL tree (insert, remove, rotations, traversals) and a word-frequency AVL tree that counts word occurrences across text files (`data/`) |
| [`search-benchmark/`](search-benchmark/) | Times three lookup structures on the same data: an AVL tree, a hash table with chaining, and binary search over a sorted array |
| [`sorting-comparison/`](sorting-comparison/) | Quicksort, shellsort, heapsort, mergesort, radixsort and introsort timed on nearly-sorted, unsorted and descending arrays |
| [`person-registry/`](person-registry/) | A small record type with a console registration driver |
| [`statistics-calculator/`](statistics-calculator/) | Mean and mode of an array |
| [`bubble-sort/`](bubble-sort/) | Bubble sort with console input |

Each project builds with, for example:

```sh
javac -d out $(find <project>/src -name '*.java') && (cd out && java <MainClass>)
```

The bimonthly sorting project of the Algorithm Complexity course (with charts and a report) is separate, in [`../sorting-analysis/`](../sorting-analysis/).
