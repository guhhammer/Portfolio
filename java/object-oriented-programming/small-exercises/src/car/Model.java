package car;

import java.util.ArrayList;
import java.util.List;

/** A car model that collects the exterior and interior options a customer configured (composition). */
public class Model {

    private final String engine, brand, year, horsepower, name;
    private final List<Exterior> exteriorOptions = new ArrayList<>();
    private final List<Interior> interiorOptions = new ArrayList<>();

    public Model(String engine, String brand, String year, String horsepower, String name) {
        this.engine = engine;
        this.brand = brand;
        this.year = year;
        this.horsepower = horsepower;
        this.name = name;
    }

    public boolean addExterior(Exterior e) { return exteriorOptions.add(e); }

    public boolean addInterior(Interior i) { return interiorOptions.add(i); }

    public List<String> describe() {
        List<String> lines = new ArrayList<>();
        lines.add("Model: " + name + " (" + brand + ", " + year + ")\nEngine: " + engine + ", " + horsepower + " hp");
        for (Exterior e : exteriorOptions) { lines.add(e.describe()); }
        for (Interior i : interiorOptions) { lines.add(i.describe()); }
        return lines;
    }

    public static void main(String[] args) {
        Model onix = new Model("1.0 turbo", "Chevrolet", "2019", "116", "Onix");
        onix.addExterior(new Exterior("red", "alloy 16\"", "tinted"));
        onix.addInterior(new Interior("leather", "black", "grey"));
        for (String line : onix.describe()) { System.out.println(line); }
    }
}
