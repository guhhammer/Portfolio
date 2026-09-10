package prefix;

/** Anything that can be evaluated to a number and printed in prefix (Polish) notation. */
public abstract class Expression {

    public abstract double evaluate();

    public abstract String toPrefixNotation();

    @Override
    public String toString() { return toPrefixNotation(); }
}
