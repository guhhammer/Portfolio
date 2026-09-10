package car;

public class Exterior {

    private final String colour, wheelType, glassType;

    public Exterior(String colour, String wheelType, String glassType) {
        this.colour = colour;
        this.wheelType = wheelType;
        this.glassType = glassType;
    }

    public String describe() {
        return "\nCar colour: " + colour + "\nWheel type: " + wheelType + "\nGlass type: " + glassType;
    }
}
