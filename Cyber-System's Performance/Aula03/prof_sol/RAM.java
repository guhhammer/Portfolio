package pscf;

class InvalidAddress extends Exception {
	private final int address;

	InvalidAddress() {
		super();
		this.address = -1;
	}

	InvalidAddress(int address) {
		super("invalid address");
		this.address = address;
	}

	public int getAddress() {
		return address;
	}
}

public class RAM {
	private int [] mem = null;
	private int size = 0;

	public RAM(int size) {
		this.size = size;
		this.mem = new int[size];
	}

	public int Size() { return size; }

	public int Get(int address) throws InvalidAddress {
		CheckAddress(address);
		return mem[address];
	}

	public void Set(int address, int word) throws InvalidAddress {
		CheckAddress(address);
		mem[address] = word;
	}

	private void CheckAddress(int address) throws InvalidAddress {
		if (address < 0 || address >= size)
			throw new InvalidAddress(address);
	}
}
