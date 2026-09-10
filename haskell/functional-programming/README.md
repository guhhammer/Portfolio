# Functional programming (Haskell)

Coursework from the Functional Programming course at PUCPR (2019), translated to English: lecture-by-lecture Haskell scripts (lambdas, recursion, accessors over tuples, hand-written map / filter / fold, a generic recursion combinator) and two short essays on pure functions and lazy vs eager evaluation. Every script loads in GHCi (verified with GHC 9.6).

## Scripts (`src/`)

| File | What it is |
| --- | --- |
| `lambdas-and-integral.hs` | First class: add, power and factorial as lambdas, a hand-written map and a Riemann-style sum. |
| `exercise-list.hs` | Ten exercises: concatenation, largest of three, `myMap` / `myFilter` / `myFold`, largest of a list with a fold, Fibonacci (pattern matching and tail recursion), string indexing, all-equal check. |
| `bookstore-queries.hs` | A `Book` tuple type with accessors and map / filter / foldr queries (titles by year and genre, sales totals by year, title and author). |
| `recursion-combinator.hs` | `map_`, `filter_` and `fold_` derived from a single generic recursion combinator (`r_process`, presented in class). |
| `map-reduce-filter.hs` | Hand-written map, reduce and filter, sums and a tail-recursive factorial. |
| `lazy-quicksort.hs` | Quicksort whose `smallest` only forces the head (lazy), next to a strict accumulator sum. |
| `pure-and-impure-functions.hs` | The Haskell examples of the pure-functions essay. |
| `combinatorics.hs`, `geometric-series.hs` | Factorial, arrangements and combinations; 1 + 1/2 + ... + 1/2^n. |

## Notes (`notes/`)

- [`pure-functions.md`](notes/pure-functions.md): what pure functions are, with pure and impure examples in Julia, Python and Haskell.
- [`lazy-vs-eager-evaluation.md`](notes/lazy-vs-eager-evaluation.md): the two evaluation strategies, their trade-offs and examples in the same three languages.
- [`imperative-vs-functional.md`](notes/imperative-vs-functional.md): a one-page comparison.

```sh
ghci src/exercise-list.hs
ghci> fibTail 10
55
```
