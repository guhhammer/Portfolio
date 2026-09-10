package prefix;

public class Constant extends Operand {

    public Constant(double value) { super(value); }

    @Override
    public String toPrefixNotation() { return String.valueOf(getValue()); }
}
