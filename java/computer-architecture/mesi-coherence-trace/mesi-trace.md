# MESI cache-coherence trace

Two processors, P1 and P2, share a variable `x`. For each step, the table gives the MESI state transition of the cache line holding `x` in each processor (M = Modified, E = Exclusive, S = Shared, I = Invalid).

| Step | Event         | P1 cache line | P2 cache line |
| ---- | ------------- | ------------- | ------------- |
| 1    | P1 reads `x`  | I -> E        | stays I       |
| 2    | P2 reads `x`  | E -> S        | I -> S        |
| 3    | P2 writes `x` | S -> I        | S -> M        |
| 4    | P1 writes `x` | I -> M        | M -> I        |
| 5    | P2 reads `x`  | M -> S        | I -> S        |
