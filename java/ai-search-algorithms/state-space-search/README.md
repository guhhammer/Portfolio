# State-space search (Java)

Graph search over the 27 Brazilian states, from the Artificial Intelligence course at PUCPR (2021). The map is built as an adjacency list (`Graph`, `State`), each state carrying its capital's coordinates, and six search strategies are run between two states and then timed over 500 random start/end pairs.

| Strategy | Class |
| --- | --- |
| Breadth-first search | `BreadthFirstSearch` |
| Depth-first, depth-limited and iterative-deepening search | `DepthFirstSearch` |
| Greedy best-first and A* (plain and iterative-deepening), with straight-line distance as the heuristic | `HeuristicSearch` |

The Python implementations of the same course are in [`../../../python/artificial-intelligence/`](../../python/artificial-intelligence/).

Build and run:

```sh
javac -d out src/*.java && (cd out && java Main)
```
