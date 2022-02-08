package aula2;

import java.io.PrintStream;

public class IO {

	private PrintStream console;
	private RAM ram;
	
	public IO(PrintStream console, RAM ram) {
		
		this.console = console;
		this.ram = ram;
		
	}
	
	
	public void initial_boot_set_up(boolean location) {
		this.console.print((location) ? "\n\nBooting Process: \n\n" : "\nBooting Process finalized.\n\n");
	}
	
	
	public void write_boot(int address) {
		this.console.print("Address 0x"+Integer.toHexString(address).toUpperCase()+
							":  0x"+Integer.toHexString(this.ram.Get(address)).toUpperCase()+"\n");
	}
	
	public void write(int address) { 
		this.console.print(this.ram.Get(address));
	}
	
}
