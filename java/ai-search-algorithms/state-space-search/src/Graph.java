import java.util.ArrayList;

/** An adjacency-list graph of States, looked up by name. */
public class Graph {

    private final ArrayList<State> states;

    public Graph() { states = new ArrayList<>(); }

    public void add(State s) { states.add(s); }

    public State getState(String name) {
        for (State s : states) {
            if (s.getName().equals(name)) return s;
        }
        return null;
    }
}
