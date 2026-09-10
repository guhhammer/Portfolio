package enron;

import static enron.BreadthFirstSearch.breadthFirst;
import static enron.BreadthFirstSearch.printBreadthFirstPath;
import static enron.DataExtractor.extract;
import static enron.DepthFirstSearch.depthFirst;
import static enron.DepthFirstSearch.printDepthFirstPath;
import static enron.GraphStatistics.printStatistics;
import static enron.LongestPathDijkstra.printLongestPath;
import static enron.NodesAtDistance.nodesAtDistance;
import static enron.NodesAtDistance.printNodesAtDistance;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/** Enron e-mail network analyser: builds a directed, weighted graph from the "maildir" corpus (one vertex per
 *  address, one edge per message sent), then reports the top senders and receivers, breadth- and depth-first
 *  paths between two people, the people at a given distance, and the path of largest accumulated dependency.
 *  Usage: java enron.Main <path-to-maildir>   (bimonthly project, Graph Theory course, PUCPR, 2019) */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String path = args.length > 0 ? args[0] : "maildir";
        System.out.println("\n\n\n\nSTART\n\n");

        Graph g = extract(path);

        printStatistics(g, 20);

        // breadth-first and depth-first search between two people.
        String start = g.vertices[0].getName();
        String end = g.vertices[3].getName();
        ArrayList<String> depth = depthFirst(g, start, end);
        ArrayList<String> breadth = breadthFirst(g, start, end);
        // options: "formatted" prints the developer's layout, "array" prints Arrays.toString(...).
        printDepthFirstPath(depth, "formatted");
        printBreadthFirstPath(breadth, "formatted");

        // people at distance D from someone.
        ArrayList<String> atDistance = nodesAtDistance(g, g.vertices[2].getName(), 1);
        printNodesAtDistance(atDistance, "formatted");

        // the path of largest accumulated dependency between two people.
        printLongestPath(g, g.vertices[0].getName(), g.vertices[3].getName(), "formatted");

        System.out.println("END\n");
    }
}
