import java.util.ArrayList;

/** Breadth-first search over the state graph. */
public class BreadthFirstSearch {

    private static ArrayList<State> path;
    private static int indicator;
    private static boolean done = false;

    private static void append(Graph g, State s, String endName) {
        if (path.contains(g.getState(endName))) {
            done = true;
        } else if (endName.equals(s.getName()) && !done) {
            path.add(s);
            done = true;
        } else if (!path.contains(s) && !done) {
            path.add(s);
        }
    }

    private static void search(Graph g, String endState) {
        path.get(indicator).getNeighbours().forEach(state -> append(g, state, endState));

        if (indicator == path.size() || done) {
            return;
        }
        indicator++;
        search(g, endState);
    }

    private static String stringify() {
        StringBuilder ret = new StringBuilder(" ");
        for (int i = 0; i < path.size(); i++) {
            ret.append(path.get(i).getName());
            if (i < path.size() - 1) { ret.append(" -> "); }
        }
        return ret + " ";
    }

    public static String breadthFirst(Graph g, String startState, String endState) {
        path = new ArrayList<>();
        indicator = 0;
        done = false;
        path.add(g.getState(startState));

        search(g, endState);

        if (stringify().contains(" " + endState + " ")) {
            return stringify();
        }
        return "State " + endState + " was not found!\n\tPaths: " + stringify();
    }
}
