package geo;

import java.util.ArrayList;
import java.util.List;

public class Country {

    public String iso, name;
    public double population, area;
    private final List<Country> neighbours = new ArrayList<>();

    public Country(String iso, String name, double area) {
        this.iso = iso;
        this.name = name;
        this.area = area;
    }

    public void setPopulation(double population) { this.population = population; }

    public void addNeighbour(Country c) { neighbours.add(c); }

    public String getIso() { return iso; }

    public String getName() { return name; }

    public List<Country> getNeighbours() { return neighbours; }

    public boolean isSameCountry(Country other) { return iso.equals(other.iso); }

    public boolean isNeighbourOf(Country other) {
        for (Country c : neighbours) { if (c.name.equals(other.name)) { return true; } }
        return false;
    }

    public double populationDensity() { return population / area; }

    /** Countries that border both this one and the other. */
    public List<Country> commonNeighbours(Country other) {
        List<Country> common = new ArrayList<>();
        for (Country a : neighbours) {
            for (Country b : other.neighbours) { if (a == b) { common.add(a); } }
        }
        return common;
    }
}
