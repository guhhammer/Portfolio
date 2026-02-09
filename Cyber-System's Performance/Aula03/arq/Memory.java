package Main;

public class Memory {

	protected int[] memory = null;
	private int size = 0;
	
	public Memory(int size) {
		this.size = size;
		this.memory = new int[this.size];
	}
	
	public int size() { return this.size; }
	
	public int Get(int address){
		return this.memory[address % this.size];
	}

	public void Set(int address, int word){
		this.memory[address] = word;
	}
	
}
