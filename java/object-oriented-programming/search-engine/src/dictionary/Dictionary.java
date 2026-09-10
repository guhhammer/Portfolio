package dictionary;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import text.Parser;
import text.TextFile;

/** The vocabulary: every distinct term of the collection mapped to its column in the term matrices. */
public class Dictionary {

    private final Map<String, Integer> terms = new LinkedHashMap<>();
    private int counter = 0;

    public void build(String delimiter, String[] files, String folder) {
        for (String file : files) {
            String[] tokens = new Parser(delimiter).split(TextFile.read(folder, file));
            for (String token : tokens) {
                String term = token.toUpperCase();
                if (!terms.containsKey(term)) { terms.put(term, counter++); }
            }
        }
    }

    public void print() {
        System.out.println("Dictionary <term, position>:");
        for (String term : terms.keySet()) {
            System.out.println("  <" + term + ", " + terms.get(term) + ">");
        }
    }

    public boolean contains(String term) { return terms.containsKey(term); }

    /** Column of the term, or -1 when it is not in the collection. */
    public int position(String term) {
        Integer position = terms.get(term);
        return position == null ? -1 : position;
    }

    public int size() { return counter; }

    public Set<String> getTerms() { return terms.keySet(); }
}
