package fibonacci;

import java.util.ArrayList;
import java.util.List;

/** Builds the sequence bottom-up in a list; linear time. */
public class IterativeFibonacci {

    public static final List<Integer> sequence = new ArrayList<>();

    public static int fib(int n) {
        sequence.clear();
        if (n == 0) { sequence.add(0); }
        else if (n == 1) { sequence.add(1); }
        else {
            sequence.add(0);
            sequence.add(1);
            for (int i = 1; i < n; i++) {
                sequence.add(sequence.get(sequence.size() - 1) + sequence.get(sequence.size() - 2));
            }
        }
        return sequence.get(sequence.size() - 1);
    }
}
