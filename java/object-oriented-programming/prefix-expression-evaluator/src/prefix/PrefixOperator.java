package prefix;

/** An n-ary operator written before its operands: +( a, b, c ). */
public abstract class PrefixOperator extends Expression {

    protected final Expression[] operands;

    protected PrefixOperator(Expression... operands) {
        if (operands.length == 0) { throw new IllegalArgumentException("an operator needs at least one operand"); }
        this.operands = operands;
    }

    protected PrefixOperator(double[] values) { this(constants(values)); }

    private static Expression[] constants(double[] values) {
        Expression[] out = new Expression[values.length];
        for (int i = 0; i < values.length; i++) { out[i] = new Constant(values[i]); }
        return out;
    }

    protected abstract String symbol();

    @Override
    public String toPrefixNotation() {
        StringBuilder sb = new StringBuilder(symbol()).append("( ");
        for (int i = 0; i < operands.length; i++) {
            if (i > 0) { sb.append(", "); }
            sb.append(operands[i].toPrefixNotation());
        }
        return sb.append(" )").toString();
    }
}
