package geo;

import java.util.ArrayList;
import java.util.List;

/** Classes with collections: countries grouped in a continent, with totals, extremes and neighbours.
 *  OOP course, PUCPR (2018). */
public class Continent {

    public final String name;
    private final List<Country> countries = new ArrayList<>();

    public Continent(String name) { this.name = name; }

    public void add(Country c) { countries.add(c); }

    public double area() {
        double total = 0;
        for (Country c : countries) { total += c.area; }
        return total;
    }

    public double population() {
        double total = 0;
        for (Country c : countries) { total += c.population; }
        return total;
    }

    public double density() { return population() / area(); }

    private Country extreme(boolean byPopulation, boolean largest) {
        Country best = countries.get(0);
        for (Country c : countries) {
            double value = byPopulation ? c.population : c.area, bestValue = byPopulation ? best.population : best.area;
            if (largest ? value > bestValue : value < bestValue) { best = c; }
        }
        return best;
    }

    public String largestPopulation() { Country c = extreme(true, true); return "Largest population (" + c.name + "): " + c.population; }

    public String smallestPopulation() { Country c = extreme(true, false); return "Smallest population (" + c.name + "): " + c.population; }

    public String largestArea() { Country c = extreme(false, true); return "Largest area (" + c.name + "): " + c.area; }

    public String smallestArea() { Country c = extreme(false, false); return "Smallest area (" + c.name + "): " + c.area; }

    public String areaRatio() {
        Country big = extreme(false, true), small = extreme(false, false);
        return "Area ratio (" + big.name + " / " + small.name + "): " + (big.area / small.area);
    }

    public static void main(String[] args) {
        Continent southAmerica = new Continent("South America");

        Country brazil = new Country("BRA", "Brazil", 8516000);
        Country argentina = new Country("ARG", "Argentina", 2780000);
        Country uruguay = new Country("URY", "Uruguay", 176215);
        Country paraguay = new Country("PRY", "Paraguay", 406752);
        brazil.setPopulation(200000000);
        argentina.setPopulation(40000000);
        uruguay.setPopulation(3457000);
        paraguay.setPopulation(6811000);

        brazil.addNeighbour(argentina); brazil.addNeighbour(uruguay); brazil.addNeighbour(paraguay);
        argentina.addNeighbour(brazil); argentina.addNeighbour(uruguay); argentina.addNeighbour(paraguay);

        southAmerica.add(brazil); southAmerica.add(argentina); southAmerica.add(paraguay); southAmerica.add(uruguay);

        System.out.println("Area (" + southAmerica.name + "): " + southAmerica.area());
        System.out.println("Population (" + southAmerica.name + "): " + southAmerica.population());
        System.out.println("Density (" + southAmerica.name + "): " + southAmerica.density());
        System.out.println(southAmerica.largestPopulation());
        System.out.println(southAmerica.smallestPopulation());
        System.out.println(southAmerica.largestArea());
        System.out.println(southAmerica.smallestArea());
        System.out.println(southAmerica.areaRatio());

        System.out.println("\nBrazil and Argentina are the same country: " + brazil.isSameCountry(argentina));
        System.out.println("Brazil borders Argentina: " + brazil.isNeighbourOf(argentina));
        System.out.println("Population density of Brazil: " + brazil.populationDensity());
        System.out.print("Common neighbours of Brazil and Argentina: ");
        for (Country c : brazil.commonNeighbours(argentina)) { System.out.print(c.getName() + " "); }
        System.out.println();
    }
}
