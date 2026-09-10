import java.util.ArrayList;

/** Informed search: greedy best-first and A* (plain and iterative-deepening),
 *  using straight-line distance between coordinates as the heuristic. */
public class HeuristicSearch {

    private static ArrayList<State> visited;
    private static boolean done = false;

    // h(n): estimated straight-line distance from x to the goal.
    public static double h(State x, State goal) {
        double dx = x.getLatitude() - goal.getLatitude();
        double dy = x.getLongitude() - goal.getLongitude();
        return Math.sqrt(dx * dx + dy * dy);
    }

    // g(n): estimated distance from the start to x.
    public static double g(State x, State start) { return h(x, start); }

    // greedy: pick the neighbour with the smallest h(n).
    private static State leastDistant(ArrayList<State> states, State goal) {
        double lowest = Double.MAX_VALUE;
        State hold = null;
        for (State state : states) {
            double hi = h(state, goal);
            if (!visited.contains(state) && (hi < lowest)) {
                lowest = hi;
                hold = state;
            }
        }
        return hold;
    }

    private static double average(double a, double b) { return (a + b) / 2; }

    // A*: pick the neighbour with the smallest average of g(n) and h(n).
    private static State leastDistantGH(ArrayList<State> states, State start, State end) {
        double lowestH = Double.MAX_VALUE, lowestG = Double.MAX_VALUE;
        State hold = null;
        for (State state : states) {
            double hi = h(state, end), gi = g(state, start);
            if (!visited.contains(state) && (average(gi, hi) < average(lowestG, lowestH))) {
                lowestG = gi;
                lowestH = hi;
                hold = state;
            }
        }
        return hold;
    }

    private static void searchGreedy(Graph g, String startState, String endState) {
        ArrayList<State> states = g.getState(startState).getNeighbours();
        for (int i = 0; i < states.size(); i++) {
            State state = leastDistant(states, g.getState(endState));
            if (done || state == null) { break; }
            if (state.getName().equals(endState)) {
                visited.add(state);
                done = true;
            } else if (!visited.contains(state)) {
                visited.add(state);
                searchGreedy(g, state.getName(), endState);
            }
        }
    }

    private static void searchAStar(Graph g, String startState, String endState) {
        ArrayList<State> states = g.getState(startState).getNeighbours();
        for (int i = 0; i < states.size(); i++) {
            State state = leastDistantGH(states, g.getState(startState), g.getState(endState));
            if (done || state == null) { break; }
            if (state.getName().equals(endState)) {
                visited.add(state);
                done = true;
            } else if (!visited.contains(state)) {
                visited.add(state);
                searchAStar(g, state.getName(), endState);
            }
        }
    }

    private static void searchAStarIterative(Graph g, String startState, String endState, int depth, int counter) {
        ArrayList<State> states = g.getState(startState).getNeighbours();
        if (depth == 0) { done = true; }
        for (int i = 0; i < states.size(); i++) {
            State state = leastDistantGH(states, g.getState(startState), g.getState(endState));
            if (done || state == null) { break; }
            if (state.getName().equals(endState)) {
                visited.add(state);
                done = true;
            } else if (counter + 1 == depth) {
                if (!visited.contains(state)) { visited.add(state); }
            } else if (!visited.contains(state)) {
                visited.add(state);
                searchAStarIterative(g, state.getName(), endState, depth, counter + 1);
            } else {
                searchAStarIterative(g, state.getName(), endState, depth, counter + 1);
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

    public static String greedy(Graph g, String startState, String endState) {
        done = false;
        visited = new ArrayList<>();
        visited.add(g.getState(startState));
        searchGreedy(g, startState, endState);
        if (stringify().contains(" " + endState + " ")) { return stringify(); }
        return "State " + endState + " was not found!\n\tPaths: " + stringify();
    }

    public static String aStar(Graph g, String startState, String endState) {
        done = false;
        visited = new ArrayList<>();
        visited.add(g.getState(startState));
        searchAStar(g, startState, endState);
        if (stringify().contains(" " + endState + " ")) { return stringify(); }
        return "State " + endState + " was not found!\n\tPaths: " + stringify();
    }

    public static String aStarIterative(Graph g, String startState, String endState) {
        int depth = 1;
        do {
            done = false;
            visited = new ArrayList<>();
            visited.add(g.getState(startState));
            searchAStarIterative(g, startState, endState, depth - 1, 0);
            if (stringify().contains(" " + endState + " ")) { break; }
            depth++;
        } while (true);
        return "\n\t\tState " + endState + " found at iteration " + depth + ".\n\t\t\t" + stringify() + "\n";
    }
}
