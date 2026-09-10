package io;

public class IO {
    private final java.io.PrintStream out;
    public IO(java.io.PrintStream out) { this.out = out; }
    public void write(String s) { out.println(s); }
}
