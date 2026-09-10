package search;

/** Generates sequential identifiers UID000000, UID000001, ... */
public class UniqueId {

    private int series = -1;
    private static final String PREFIX = "UID";

    public String next() {
        series++;
        return PREFIX + String.format("%06d", series);
    }
}
