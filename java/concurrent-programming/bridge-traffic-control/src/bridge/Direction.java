package bridge;

public enum Direction {
    NORTH, SOUTH;

    public Direction opposite() { return this == NORTH ? SOUTH : NORTH; }
}
