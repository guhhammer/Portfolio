package prefix;

/** A named operand: prints its name, evaluates to its bound value. */
public class Variable extends Operand {

    private final String name;

    public Variable(String name, double value) {
        super(value);
        this.name = name;
    }

    @Override
    public String toPrefixNotation() { return name; }
}
