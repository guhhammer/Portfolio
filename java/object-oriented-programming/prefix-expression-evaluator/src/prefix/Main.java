package prefix;

import java.util.Scanner;

/** Reads operands for each of the four n-ary prefix operators and prints the expression in Polish (prefix)
 *  notation together with its value. Object-Oriented Programming course, PUCPR (2018). */
public class Main {

    private static double[] readOperands(Scanner in, String operator) {
        System.out.println("\n " + operator + ":\nHow many operands? ");
        int n = in.nextInt();
        double[] values = new double[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter operand [" + i + "]: ");
            values[i] = in.nextDouble();
        }
        return values;
    }

    private static void show(Expression e) {
        System.out.print("Expression: " + e.toPrefixNotation() + "\nValue: " + e.evaluate() + "\n");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("\nThe decimal separator follows your locale (0.5 or 0,5).\n");

        show(new Addition(readOperands(in, "ADDITION")));
        show(new Subtraction(readOperands(in, "SUBTRACTION")));
        show(new Multiplication(readOperands(in, "MULTIPLICATION")));
        show(new Division(readOperands(in, "DIVISION")));
    }
}
