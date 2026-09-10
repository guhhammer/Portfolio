package dictionary;

import java.util.HashMap;
import java.util.Map;
import util.Pair;

public class Statistics {

    /** Term frequency of every term in the token list. */
    public static Map<String, Pair> termFrequency(String[] tokens) {
        Map<String, Pair> counts = new HashMap<>();
        for (String token : tokens) {
            String term = token.trim();
            Pair pair = counts.get(term);
            if (pair == null) { counts.put(term, new Pair(term, 1)); }
            else { pair.setValue(pair.getValue() + 1); }
        }
        return counts;
    }

    public static void print(Map<String, Pair> counts) {
        for (String term : counts.keySet()) {
            System.out.println(term + " : " + counts.get(term).getValue());
        }
    }

    /** Cosine similarity between two vectors as a rounded percentage; 0 when either vector is null. */
    public static double similarity(double[] v1, double[] v2) {
        double dot = 0.0, n1 = 0.0, n2 = 0.0;
        for (int i = 0; i < v1.length; i++) {
            dot += v1[i] * v2[i];
            n1 += v1[i] * v1[i];
            n2 += v2[i] * v2[i];
        }
        if (n1 == 0 || n2 == 0) { return 0; }
        return Math.round(dot / (Math.sqrt(n1) * Math.sqrt(n2)) * 100.0);
    }
}
