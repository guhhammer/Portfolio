package Main;

import java.io.PrintStream;

public class IO {
	
	private PrintStream console = null;
	
	public IO(PrintStream c) {
		this.console = c;
	}
	
	public void write(String s) { this.console.println(s);}
	
}
