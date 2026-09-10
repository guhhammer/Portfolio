package counter;

public class Counter {

    private int value = 0;

    public void increment() { value++; }

    public int reset() { return value = 0; }

    public int getValue() { return value; }

    public static void main(String[] args) {
        Counter x = new Counter();
        x.increment();
        System.out.println(x.getValue());
        System.out.println(x.reset());
    }
}
