package pscf;

public class CPU {
	private RAM ram = null;
	private IO io = null;
	private int PC = 0;

	public CPU(RAM r, IO es) {
		ram = r;
		io = es;
	}

	public void Run(int pc_address) throws InvalidAddress {

		PC = pc_address;

		int iaddr = ram.Get(PC++);
		int faddr = ram.Get(PC);

		for (int i=iaddr; i<=faddr; ++i) {
			ram.Set(i, i - iaddr + 1);
			io.Write(i + " -> " + ram.Get(i));
		}
	}
}
