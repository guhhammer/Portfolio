# Graph theory (Java)

Coursework from the Graph Theory course at PUCPR (2019): a graph library built from scratch on sparse adjacency lists, the classic algorithms on top of it, and two projects on real networks. Everything is in English and compiles with `javac` (verified).

| Project | What it is |
| --- | --- |
| [`airline-routes-analysis/`](airline-routes-analysis/) | **Final project.** Connectivity, Eulerian and cycle checks, connected components, Dijkstra, betweenness and closeness centrality, local clustering coefficient, random-graph generation, Pajek import/export, and an "air bridge" finder that lists routes with connections between two airports over the OpenFlights dataset (`data/routes.csv`, 59,036 routes). |
| [`enron-email-network/`](enron-email-network/) | **Bimonthly project.** Builds a weighted graph of who e-mails whom from the Enron `maildir` corpus, then reports top senders and receivers, breadth- and depth-first paths, people at a given distance, and the path of largest accumulated dependency (a longest-path variant of Dijkstra). Pass the corpus path as the first argument. |
| [`minimum-spanning-tree/`](minimum-spanning-tree/) | Kruskal's and Prim's algorithms on a weighted graph. |
| [`weekly-assignments/`](weekly-assignments/) | Four weekly exercises as packages: `adjacencymatrix` (a graph on an adjacency matrix), `sparsematrix` (the same on adjacency lists), `shortestpaths` (Warshall's transitive closure and Dijkstra) and `components` (connected components by breadth-first search). |

[`written-assignments/`](written-assignments/) holds the problem-based-learning worksheets, the assignment reports and exam exercise solutions (in Portuguese).

Build and run, for example:

```sh
javac -d out $(find airline-routes-analysis/src -name '*.java') && (cd airline-routes-analysis && java -cp ../out routes.Main)
javac -d out $(find weekly-assignments/src -name '*.java') && (cd out && java components.Main)
```

The airline project reads `data/routes.csv` relative to its folder and writes Pajek files to `outputs/` (git-ignored).
