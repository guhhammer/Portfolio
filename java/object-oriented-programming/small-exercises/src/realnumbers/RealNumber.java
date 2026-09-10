package realnumbers;

/** Decomposes a real number into sign, integer part and fractional part, and recombines two such
 *  decompositions with the four operations. */
public class RealNumber {

    private final int sign;
    private final int integerPart;
    private final double fractionalPart;

    public RealNumber(double n) {
        sign = n < 0 ? -1 : 1;
        double magnitude = Math.abs(n);
        integerPart = (int) magnitude;
        fractionalPart = magnitude - integerPart;
    }

    public int getSign() { return sign; }

    public int getIntegerPart() { return integerPart; }

    public double getFractionalPart() { return fractionalPart; }

    public boolean isPositive() { return sign > 0; }

    public double value() { return sign * (integerPart + fractionalPart); }

    /** {sign, integer part, fractional part}. */
    public double[] decompose() { return new double[]{sign, integerPart, fractionalPart}; }

    @Override
    public String toString() { return "(" + sign + ")*( " + integerPart + " + " + fractionalPart + " )"; }

    public String add(RealNumber b) {
        return "(" + sign + ")*(" + b.sign + ") * { (" + integerPart + "+" + b.integerPart + ") + (" + fractionalPart + "+" + b.fractionalPart + ") } = "
                + (sign * b.sign) * ((integerPart + b.integerPart) + (fractionalPart + b.fractionalPart));
    }

    public String subtract(RealNumber b) {
        return "(" + sign + ")*(" + b.sign + ") * { (" + integerPart + "-" + b.integerPart + ") + (" + fractionalPart + "-" + b.fractionalPart + ") } = "
                + (sign * b.sign) * ((integerPart - b.integerPart) + (fractionalPart - b.fractionalPart));
    }

    public String multiply(RealNumber b) {
        return "(" + sign + ")*(" + b.sign + ") * { (" + integerPart + "+" + fractionalPart + ") * (" + b.integerPart + "+" + b.fractionalPart + ") } = "
                + (sign * b.sign) * ((integerPart + fractionalPart) * (b.integerPart + b.fractionalPart));
    }

    public String divide(RealNumber b) {
        return "(" + sign + ")*(" + b.sign + ") * { (" + integerPart + "+" + fractionalPart + ") / (" + b.integerPart + "+" + b.fractionalPart + ") } = "
                + (sign * b.sign) * ((integerPart + fractionalPart) / (b.integerPart + b.fractionalPart));
    }

    public static void main(String[] args) {
        RealNumber a = new RealNumber(-8.5);
        System.out.println("Item A:\nNumber: " + a.value()
                + "\nInteger part: " + a.getIntegerPart()
                + "\nFractional part: " + a.getFractionalPart()
                + "\nThe number is " + (a.isPositive() ? "positive" : "negative") + "."
                + "\n" + a.value() + " = " + a + "\n");

        double[] d = a.decompose();
        System.out.println("Item B:\ndecompose() returned {sign, integer, fraction} = { " + (int) d[0] + ", " + (int) d[1] + ", " + d[2]
                + " }, so the number was " + (d[0] * (d[1] + d[2])) + "\n");

        RealNumber b = new RealNumber(2.54412);
        System.out.println("Add:      " + a.add(b));
        System.out.println("Subtract: " + a.subtract(b));
        System.out.println("Multiply: " + a.multiply(b));
        System.out.println("Divide:   " + a.divide(b));
    }
}
