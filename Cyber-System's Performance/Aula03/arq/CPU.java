package Main;

public class CPU {
	
	private Memory memory = null;
	private IO io = null;
	private int PC = 0;

	public CPU(Memory m, IO es) {
		this.memory = m;
		this.io = es;
	}

	public void Run(int pc_address){

		PC = pc_address;

		int iaddr = this.memory.Get(PC++);
		int faddr = this.memory.Get(PC);
		
		for (int i=iaddr; i<=faddr; ++i) {
			this.memory.Set(i, i - iaddr + 1);
			this.io.write(i + " -> " + this.memory.Get(i));
		}
	
	}
	
}
