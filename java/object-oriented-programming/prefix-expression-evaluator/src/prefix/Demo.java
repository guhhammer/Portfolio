package prefix;

/** Builds nested expressions with constants and variables and prints them in prefix notation. */
public class Demo {

    public static void main(String[] args) {
        Expression x = new Variable("x", 4);
        Expression y = new Variable("y", 2.5);

        Expression e1 = new Addition(new Constant(1), x, y);                       // +( 1, x, y )
        Expression e2 = new Division(new Multiplication(x, y), new Constant(5));   // /( *( x, y ), 5 )
        Expression e3 = new Subtraction(e1, e2, new Constant(0.5));                // -( +( 1, x, y ), /( *( x, y ), 5 ), 0.5 )

        // the examples of the class exercise: (2 + 5), (A * (2 * 5)) and ((B / A) - (A * (2 * 5)))
        Expression a = new Variable("A", 3), b = new Variable("B", 9);
        Expression twoTimesFive = new Multiplication(new Constant(2), new Constant(5));
        Expression e4 = new Addition(new Constant(2), new Constant(5));
        Expression e5 = new Multiplication(a, twoTimesFive);
        Expression e6 = new Subtraction(new Division(b, a), e5);

        for (Expression e : new Expression[]{e1, e2, e3, e4, e5, e6}) {
            System.out.println(e.toPrefixNotation() + " = " + e.evaluate());
        }
    }
}
