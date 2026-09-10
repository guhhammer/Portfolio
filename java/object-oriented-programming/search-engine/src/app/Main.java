package app;

import dictionary.Dictionary;
import dictionary.Statistics;
import index.Indexer;
import retrieval.Retriever;

/** Vector-space search engine over six small documents: builds the dictionary, the TF / DF / IDF / TF-IDF
 *  matrices, then ranks the documents against a query by cosine similarity.
 *  Usage: java app.Main [documents-folder] [query words...]   (defaults: documents/ and "aa aa ee ff bb ff ee bb")
 *  Object-Oriented Programming course, PUCPR (2018). */
public class Main {

    private static final String DELIMITER = "\\s+";

    public static void main(String[] args) {
        String folder = args.length > 0 ? args[0] : "documents";
        String query = args.length > 1 ? String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length))
                                       : "aa aa ee ff bb ff ee bb";
        String[] files = {"doc1.txt", "doc2.txt", "doc3.txt", "doc4.txt", "doc5.txt", "doc6.txt"};

        Dictionary vocabulary = new Dictionary();
        vocabulary.build(DELIMITER, files, folder);
        vocabulary.print();

        Indexer indexer = new Indexer(vocabulary);
        indexer.index(folder, files, DELIMITER);

        System.out.println("\nTF (term frequency per document):");
        indexer.printMatrix(indexer.getTf());
        System.out.println("\nTerms in the dictionary: " + vocabulary.size());
        System.out.println("\nDF (documents containing each term):");
        indexer.printVector(indexer.getDf());
        System.out.println("\nIDF (log2 N/DF):");
        indexer.printVector(indexer.getIdf());
        System.out.println("\nTF-IDF:");
        double[][] tfidf = indexer.getTfidf();
        indexer.printMatrix(tfidf);

        Retriever retriever = new Retriever(DELIMITER, vocabulary, indexer.getIdf());
        double[] queryVector = retriever.tfidf(query);
        System.out.println("\nTF-IDF of the query \"" + query + "\":");
        retriever.printVector(queryVector);

        System.out.println("\nCosine similarity between the query and each document:");
        for (int d = 0; d < tfidf.length; d++) {
            System.out.println("  " + files[d] + ": " + Statistics.similarity(tfidf[d], queryVector) + "%");
        }
    }
}
