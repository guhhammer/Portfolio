package search;

import java.util.ArrayList;
import java.util.List;

/** One store's answer to one search: when it was made, its id, the query, the store and the products found. */
public class SearchRecord {

    public final String date, uid, query, store, products;

    public SearchRecord(String date, String uid, String query, String store, String products) {
        this.date = date;
        this.uid = uid;
        this.query = query;
        this.store = store;
        this.products = products;
    }

    @Override
    public String toString() { return date + " " + uid + " " + query + " " + store + " " + products; }

    /** Parses " { {name __SEPARATOR__ price} {name __SEPARATOR__ price} } " into readable lines. */
    private String formatProducts() {
        char[] s = products.toCharArray();
        StringBuilder current = new StringBuilder();
        List<String[]> items = new ArrayList<>();
        for (int i = 2; i < s.length - 2; i++) {
            if (s[i] == '{') {
                continue;
            } else if (s[i] == '}') {
                String[] parts = current.toString().trim().split("__SEPARATOR__");
                items.add(new String[]{parts[0].trim(), parts[1].trim()});
                current.setLength(0);
            } else {
                current.append(s[i]);
            }
        }
        StringBuilder out = new StringBuilder();
        for (String[] item : items) {
            out.append("\tTitle: ").append(item[0]).append("\n\tPrice: ").append(item[1]).append("\n----------\n");
        }
        return out.toString();
    }

    public String formatSearch() { return "\nStore: " + store + "\n" + formatProducts() + "\n"; }

    public String formatHistory() {
        return "\nDate:  " + date + "\nUID:  " + uid + "\nQuery:  " + query
                + "\nStore:  " + store + "\nProducts returned:  \n" + formatProducts();
    }
}
