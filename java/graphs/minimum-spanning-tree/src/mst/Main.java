package mst;

import static mst.KruskalMst.kruskal;
import static mst.PrimMst.prim;

/** Minimum spanning tree of a weighted graph by Kruskal's and by Prim's algorithm. Graph Theory course, PUCPR (2019). */
public class Main {

    public static void main(String[] args) {
        System.out.println("\n\n\nStart:\n\n");

        Graph graph = new Graph(9);

        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 3);
        graph.addEdge(1, 4, 2);
        graph.addEdge(2, 3, 1);
        graph.addEdge(2, 5, 2);
        graph.addEdge(3, 4, 4);
        graph.addEdge(3, 5, 3);
        graph.addEdge(4, 6, 1);
        graph.addEdge(5, 6, 3);
        graph.addEdge(6, 7, 2);
        graph.addEdge(6, 8, 2);
        graph.addEdge(7, 8, 1);

        // undirected view: edge (0,1,2) is also (1,0,2).
        boolean undirected = true;

        System.out.print("Edges (" + (undirected ? "undirected" : "directed") + ") of the input graph:\n");
        if (undirected) { graph.printUndirectedEdges(); } else { graph.printEdges(); }

        Graph byKruskal = kruskal(graph);
        System.out.print("\n\n\nEdges of the minimum spanning tree by Kruskal:\n");
        if (undirected) { byKruskal.printUndirectedEdges(); } else { byKruskal.printEdges(); }

        Graph byPrim = prim(graph);
        System.out.print("\n\n\n\nEdges of the minimum spanning tree by Prim:\n");
        if (undirected) { byPrim.printUndirectedEdges(); } else { byPrim.printEdges(); }

        System.out.println("\n\n\nEnd.\n");
    }
}
