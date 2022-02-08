package aula2;

public class RAM {

	private int[] memory;
	private int memory_size;
	
	public RAM(int byte_size) {
	
		this.memory = new int[byte_size];
		this.memory_size = byte_size;
		
	}
	
	public int Get(int address) { return this.memory[address]; }
	
	public void Set(int address, int word) { this.memory[address] = word; }
	
	public int my_size() { return this.memory_size; }
	
}
