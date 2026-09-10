package prefix;

public class Multiplication extends PrefixOperator {

    public Multiplication(Expression... operands) { super(operands); }

    public Multiplication(double[] values) { super(values); }

    @Override
    protected String symbol() { return "*"; }

    @Override
    public double evaluate() {
        double product = 1;
        for (Expression e : operands) { product *= e.evaluate(); }
        return product;
    }
}
