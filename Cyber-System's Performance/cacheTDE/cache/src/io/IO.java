package io;

public class IO {

    private java.io.PrintStream out = null;

    public IO(java.io.PrintStream o){ this.out = o; }

    public void Write(String s){ out.println(s); }

}
