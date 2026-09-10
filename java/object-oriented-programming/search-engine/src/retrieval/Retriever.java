package retrieval;

import dictionary.Dictionary;
import dictionary.Statistics;
import java.util.Map;
import text.Parser;
import util.Pair;

/** Turns a query into a TF-IDF vector over the same dictionary as the documents. */
public class Retriever {

    private final String delimiter;
    private final Dictionary vocabulary;
    private final double[] idf;

    public Retriever(String delimiter, Dictionary vocabulary, double[] idf) {
        this.delimiter = delimiter;
        this.vocabulary = vocabulary;
        this.idf = idf;
    }

    public double[] tfidf(String query) {
        String[] tokens = new Parser(delimiter).split(query.toUpperCase());
        Map<String, Pair> counts = Statistics.termFrequency(tokens);
        return tfidf(buildRow(counts));
    }

    public Integer[] buildRow(Map<String, Pair> counts) {
        Integer[] row = new Integer[vocabulary.size()];
        for (int i = 0; i < row.length; i++) { row[i] = 0; }
        for (Pair p : counts.values()) {
            int position = vocabulary.position(p.getKey());
            if (position >= 0) { row[position] = p.getValue(); }   // terms outside the collection are ignored
        }
        return row;
    }

    public double[] tfidf(Integer[] tf) {
        double[] x = new double[tf.length];
        for (int i = 0; i < tf.length; i++) { x[i] = tf[i] * idf[i]; }
        return x;
    }

    public void printVector(double[] v) {
        for (String term : vocabulary.getTerms()) { System.out.print(term + "\t"); }
        System.out.println();
        for (double value : v) { System.out.printf("%1.2f\t", value); }
        System.out.println();
    }
}
