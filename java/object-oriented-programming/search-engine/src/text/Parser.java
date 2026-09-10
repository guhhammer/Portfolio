package text;

import java.util.ArrayList;
import java.util.List;

/** Splits a text into terms on a delimiter (a regular expression); blank terms are dropped. */
public class Parser {

    private final String delimiter;

    public Parser(String delimiter) { this.delimiter = delimiter; }

    public String[] split(String text) {
        List<String> terms = new ArrayList<>();
        for (String t : text.split(delimiter)) {
            String term = t.trim();
            if (!term.isEmpty()) { terms.add(term); }
        }
        return terms.toArray(new String[0]);
    }
}
