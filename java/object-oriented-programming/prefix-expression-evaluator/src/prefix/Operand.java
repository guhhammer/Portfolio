package prefix;

/** A leaf of the expression: its value is fixed. */
public abstract class Operand extends Expression {

    private final double value;

    protected Operand(double value) { this.value = value; }

    public double getValue() { return value; }

    @Override
    public double evaluate() { return value; }
}
