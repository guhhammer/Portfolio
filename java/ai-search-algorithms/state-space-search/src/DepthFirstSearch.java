import java.util.ArrayList;

/** Depth-first search: plain, depth-limited and iterative-deepening variants. */
public class DepthFirstSearch {

    private static ArrayList<State> visited;
    private static boolean done = false;

    private static void searchLimited(Graph g, String startState, String endState, int depth, int counter) {
        ArrayList<State> states = g.getState(startState).getNeighbours();

        if (depth == 0) { done = true; }

        for (State state : states) {
            if (done) { break; }

            if (state.getName().equals(endState)) {
                visited.add(state);
                done = true;
            } else if (counter + 1 == depth) {
                if (!visited.contains(state)) { visited.add(state); }
            } else if (!visited.contains(state)) {
                visited.add(state);
                searchLimited(g, state.getName(), endState, depth, counter + 1);
            } else {
                searchLimited(g, state.getName(), endState, depth, counter + 1);
            }
        }
    }

    private static void search(Graph g, String startState, String endState) {
        ArrayList<State> states = g.getState(startState).getNeighbours();

        for (State state : states) {
            if (done) { break; }

            if (state.getName().equals(endState)) {
                visited.add(state);
                done = true;
            } else if (!visited.contains(state)) {
                visited.add(state);
                search(g, state.getName(), endState);
            }
        }
    }

    private static String stringify() {
        StringBuilder ret = new StringBuilder(" ");
        for (int i = 0; i < visited.size(); i++) {
            ret.append(visited.get(i).getName());
            if (i < visited.size() - 1) { ret.append(" -> "); }
        }
        return ret + " ";
    }

    public static String depthFirst(Graph g, String startState, String endState) {
        done = false;
        visited = new ArrayList<>();
        visited.add(g.getState(startState));

        search(g, startState, endState);

        if (stringify().contains(" " + endState + " ")) {
            return stringify();
        }
        return "State " + endState + " was not found!\n\tPaths: " + stringify();
    }

    public static String depthLimited(Graph g, String startState, String endState, int depth) {
        done = false;
        visited = new ArrayList<>();
        visited.add(g.getState(startState));

        searchLimited(g, startState, endState, depth - 1, 0);

        if (stringify().contains(" " + endState + " ")) {
            return stringify();
        }
        return "State " + endState + " was not found at depth " + depth + "!\n\tPaths: " + stringify();
    }

    public static String iterativeDeepening(Graph g, String startState, String endState) {
        int depth = 1;
        do {
            done = false;
            visited = new ArrayList<>();
            visited.add(g.getState(startState));

            searchLimited(g, startState, endState, depth - 1, 0);

            if (stringify().contains(" " + endState + " ")) { break; }
            depth++;
        } while (true);

        return "\nState " + endState + " found at iteration " + depth + ".\n" + stringify() + "\n";
    }
}
