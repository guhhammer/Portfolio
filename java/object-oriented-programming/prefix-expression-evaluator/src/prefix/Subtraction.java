package prefix;

/** -( a, b, c ) = a - b - c. */
public class Subtraction extends PrefixOperator {

    public Subtraction(Expression... operands) { super(operands); }

    public Subtraction(double[] values) { super(values); }

    @Override
    protected String symbol() { return "-"; }

    @Override
    public double evaluate() {
        double result = operands[0].evaluate();
        for (int i = 1; i < operands.length; i++) { result -= operands[i].evaluate(); }
        return result;
    }
}
