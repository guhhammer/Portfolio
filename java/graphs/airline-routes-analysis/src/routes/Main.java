package routes;

/** Final project of the Graph Theory course, PUCPR (2019): a graph library on sparse adjacency lists with
 *  connectivity, Eulerian and cycle checks, components, betweenness and closeness centrality, random graphs,
 *  Pajek import/export, and an "air bridge" finder over the OpenFlights route dataset (data/routes.csv). */
public class Main {

    public static void main(String[] args) {
        System.out.println("\n\n\nStart:\n\n");

        // 1. a hand-built undirected graph with three components.
        Graph graph = new Graph(15, false);
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
        graph.addEdge(9, 10, 5);
        graph.addEdge(10, 11, 1);
        graph.addEdge(9, 12, 3);
        graph.addEdge(10, 12, 2);
        graph.addEdge(13, 14, 5);

        System.out.print("Connected: " + graph.isConnected() + "\n");
        System.out.print("Eulerian: " + graph.isEulerian() + "\n");
        System.out.print("Cyclic: " + graph.hasCycle() + "\n\n");
        graph.printOnlyComponents();
        graph.betweennessCentrality();
        graph.closenessCentrality();

        System.out.print("===============================================================\n\n");

        // 2. a random connected directed graph, saved to and reloaded from a Pajek file.
        Graph random = new Graph(8, true).random(15, true);
        random.printEdges();
        random.printOnlyComponents();
        System.out.print("Connected: " + random.isConnected() + "\n");
        random.writePajek("r");
        Graph reloaded = new Graph().readPajek("r.pajek");
        reloaded.printEdges();

        System.out.print("\n\n===============================================================\n\n");

        // 3. the OpenFlights route database as an undirected airport graph.
        Graph routes = new Graph(false).airlineRoutes();
        System.out.println("\n\nRoutes: " + routes.vertexCount() + " airports");
        System.out.println("Routes: " + routes.edgeCount() + " edges\n\n");
        // routes.printAirportEdges();
        // routes.isConnected() and routes.hasCycle() overflow the stack on this graph (recursive searches).
        System.out.print("Eulerian: " + routes.isEulerian() + "\n");

        // Some IATA codes in routes.csv: GRU Sao Paulo, CWB Curitiba, LHR London Heathrow, LAX Los Angeles,
        // CDG Paris, YTZ Toronto, YVR Vancouver, AEP Buenos Aires, CGN Cologne, HAM Hamburg, BCN Barcelona,
        // LIS Lisbon, MEL Melbourne, FUK Fukuoka, HIJ Hiroshima, NRT Tokyo.
        String departure = "LAX", destination = "NRT";
        String[] connections = {"LAS", "SAN"};
        String[] connections2 = {"LAS", "PHX", "MEX"};
        int hops = 5;       // more than 3 gets slow: worst case O(n!)
        int maxPaths = 20;  // maximum number of air bridges to show

        System.out.println("\n\nNote: the next step can take 10 to 90 seconds depending on the chosen air bridge.\n\n");
        try { Thread.sleep(5000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // (P =>) marks the departure and ( => D) the destination of each air bridge.
        routes.airBridges(departure, destination, hops, maxPaths);
        routes.airBridgesWithConnections(departure, connections, destination, hops, maxPaths / connections.length);
        routes.airBridgesWithConnections(departure, connections2, destination, hops, maxPaths / connections2.length);

        System.out.println("\n\n\nEnd.\n");
    }
}
