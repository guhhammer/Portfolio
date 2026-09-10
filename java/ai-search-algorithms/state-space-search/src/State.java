import java.util.ArrayList;

/** A node of the search graph: a Brazilian state with its neighbours and (optionally) its coordinates. */
public class State {

    private final ArrayList<State> neighbours;
    private final String name;
    private double latitude, longitude;

    public State(String name) {
        this.name = name;
        this.neighbours = new ArrayList<>();
    }

    public State(String name, double latitude, double longitude) {
        this(name);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() { return this.name; }

    public ArrayList<State> getNeighbours() { return this.neighbours; }

    public void addNeighbour(State s) { neighbours.add(s); }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }
}
