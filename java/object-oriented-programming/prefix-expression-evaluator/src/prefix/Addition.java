package prefix;

public class Addition extends PrefixOperator {

    public Addition(Expression... operands) { super(operands); }

    public Addition(double[] values) { super(values); }

    @Override
    protected String symbol() { return "+"; }

    @Override
    public double evaluate() {
        double sum = 0;
        for (Expression e : operands) { sum += e.evaluate(); }
        return sum;
    }
}
