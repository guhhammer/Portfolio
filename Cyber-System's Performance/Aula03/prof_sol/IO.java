package pscf;

public class IO {
	private java.io.PrintStream out = null;

	public IO(java.io.PrintStream o) { out = o;	}
	public void Write(String s) {
		out.println(s);
	}
}
