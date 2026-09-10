package car;

public class Interior {

    private final String seatType, seatColour, carpetColour;

    public Interior(String seatType, String seatColour, String carpetColour) {
        this.seatType = seatType;
        this.seatColour = seatColour;
        this.carpetColour = carpetColour;
    }

    public String describe() {
        return "\nSeat type: " + seatType + "\nSeat colour: " + seatColour + "\nCarpet colour: " + carpetColour;
    }
}
