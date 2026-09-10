# Algorithms Library

More than eighty classic algorithms and data structures implemented from scratch in Rust, each in its own file with a small `main` that demonstrates it. `src/main.rs` is a registry: flip a `false` to `true` next to any entry and `cargo run` executes that demo.

For a non-technical reader: this is the "textbook" foundation behind everything else here, written by hand in Rust to learn the language's ownership and generics model properly.

## Contents

| Module | Implementations |
| --- | --- |
| `sorting/` | bubble, selection, insertion, shell, merge, quick, randomized quick, heap, counting, radix, bucket; a timing harness (`kronometrmain.rs`) |
| `search_related/` | linear, binary, breadth-first, depth-first |
| `datastructure/` | linked list, stack, queue, deque, binary tree, heap, hash table, set, trie, graph, node |
| `graph_related/` | Dijkstra shortest path, A* search, Kruskal and Prim minimum spanning trees |
| `dynamic_programming/` | Fibonacci, 0/1 knapsack, longest common subsequence |
| `backtracking/` | Hamiltonian path |
| `string_related/` | Knuth-Morris-Pratt, Boyer-Moore, Rabin-Karp, trie-based search |
| `cryptographic/` | Caesar cipher, Diffie-Hellman key exchange |
| `mathematical/` | GCD (Euclid), sieve of Eratosthenes, fast and modular exponentiation, Chinese remainder theorem, Euler's totient, Wilson's theorem, factorials (iterative, recursive, memoized), Fibonacci variants, permutations and combinations, Bell and Catalan numbers, random number generation, reservoir sampling, Monte Carlo integration, Las Vegas optimization, Voronoi diagrams |
| `activities/` | three warm-up exercises on enums, structs and pattern matching |

## Run

```bash
cargo run            # runs every entry marked `true` in src/main.rs
cargo check          # compiles the whole library (no warnings)
```
