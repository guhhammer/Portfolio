package index;

import dictionary.Dictionary;
import dictionary.Statistics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import text.Parser;
import text.TextFile;
import util.Pair;

/** Builds the TF, DF, IDF and TF-IDF matrices of a document collection over a dictionary. */
public class Indexer {

    private final Dictionary vocabulary;
    private int[][] tf;
    private double[] df;
    private double[] idf;
    private double[][] tfidf;

    public Indexer(Dictionary vocabulary) { this.vocabulary = vocabulary; }

    public void index(String folder, String[] files, String delimiter) {
        List<Integer[]> rows = new ArrayList<>();
        for (String file : files) {
            String[] tokens = new Parser(delimiter).split(TextFile.read(folder, file).toUpperCase());
            rows.add(buildRow(Statistics.termFrequency(tokens)));
        }
        tf = termFrequency(rows);
        df = documentFrequency(tf);
        idf = inverseDocumentFrequency(df, files.length);
        tfidf = tfidf(tf, idf);
    }

    public int[][] getTf() { return tf; }

    public double[] getDf() { return df; }

    public double[] getIdf() { return idf; }

    public double[][] getTfidf() { return tfidf; }

    /** One row of the TF matrix: the count of each dictionary term in a document. */
    public Integer[] buildRow(Map<String, Pair> counts) {
        Integer[] row = new Integer[vocabulary.size()];
        for (int i = 0; i < row.length; i++) { row[i] = 0; }
        for (Pair p : counts.values()) {
            int position = vocabulary.position(p.getKey());
            if (position >= 0) { row[position] = p.getValue(); }
        }
        return row;
    }

    public int[][] termFrequency(List<Integer[]> rows) {
        int[][] matrix = new int[rows.size()][vocabulary.size()];
        for (int i = 0; i < rows.size(); i++) {
            Integer[] row = rows.get(i);
            for (int j = 0; j < row.length; j++) { matrix[i][j] = row[j]; }
        }
        return matrix;
    }

    /** In how many documents each term appears. */
    public double[] documentFrequency(int[][] tf) {
        double[] df = new double[vocabulary.size()];
        for (int[] row : tf) {
            for (int j = 0; j < row.length; j++) { if (row[j] != 0) { df[j]++; } }
        }
        return df;
    }

    /** log2(N / df) for each term. */
    public double[] inverseDocumentFrequency(double[] df, int documents) {
        double[] idf = new double[df.length];
        for (int i = 0; i < df.length; i++) { idf[i] = Math.log10(documents / df[i]) / Math.log10(2); }
        return idf;
    }

    public double[][] tfidf(int[][] tf, double[] idf) {
        double[][] x = new double[tf.length][tf[0].length];
        for (int i = 0; i < tf.length; i++) {
            for (int j = 0; j < tf[0].length; j++) { x[i][j] = tf[i][j] * idf[j]; }
        }
        return x;
    }

    private void printHeader() {
        for (String term : vocabulary.getTerms()) { System.out.print(term + "\t"); }
        System.out.println();
    }

    public void printMatrix(int[][] m) {
        printHeader();
        for (int[] row : m) {
            for (int value : row) { System.out.print(value + "\t"); }
            System.out.println();
        }
    }

    public void printMatrix(double[][] m) {
        printHeader();
        for (double[] row : m) {
            for (double value : row) { System.out.printf("%1.2f\t", value); }
            System.out.println();
        }
    }

    public void printVector(double[] v) {
        printHeader();
        for (double value : v) { System.out.printf("%1.2f\t", value); }
        System.out.println();
    }
}
