package components;

/** Connected components of an undirected graph, found by breadth-first search from every vertex.
 *  Graph Theory course, PUCPR (2019). */
public class Main {

    public static void main(String[] args) {
        System.out.println("\n\n\nStart:\n\n");

        Graph graph = new Graph(15);

        graph.addUndirectedEdge(0, 1, 2);
        graph.addUndirectedEdge(0, 2, 1);
        graph.addUndirectedEdge(1, 3, 3);
        graph.addUndirectedEdge(1, 4, 2);
        graph.addUndirectedEdge(2, 3, 1);
        graph.addUndirectedEdge(2, 5, 2);
        graph.addUndirectedEdge(3, 4, 4);
        graph.addUndirectedEdge(3, 5, 3);
        graph.addUndirectedEdge(4, 6, 1);
        graph.addUndirectedEdge(5, 6, 3);
        graph.addUndirectedEdge(6, 7, 2);
        graph.addUndirectedEdge(6, 8, 2);
        graph.addUndirectedEdge(7, 8, 1);

        graph.addUndirectedEdge(9, 10, 5);
        graph.addUndirectedEdge(10, 11, 1);
        graph.addUndirectedEdge(9, 12, 3);
        graph.addUndirectedEdge(10, 12, 2);

        graph.addUndirectedEdge(13, 14, 5);

        graph.printComponents();   // the components and how many there are

        System.out.println("\n\n\nEnd.\n");
    }
}
