package fibonacci;

public class Fibonacci {

    public static int[] fib(int n) {
        int[] a = new int[n];
        a[0] = 1;
        a[1] = 1;
        for (int i = 2; i < n; i++) { a[i] = a[i - 1] + a[i - 2]; }
        return a;
    }

    public static void print(int[] a) {
        System.out.print("Fibonacci[" + a.length + "] = {");
        int perLine = 0;
        for (int i = 0; i < a.length; i++) {
            if (i == a.length - 1) { System.out.println(a[i] + "} ."); }
            else {
                System.out.print(a[i] + ", ");
                if (++perLine == 10) { System.out.print("\n            "); perLine = 0; }
            }
        }
    }

    public static void main(String[] args) { print(fib(20)); }
}
