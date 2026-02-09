package Main;

import java.util.Arrays;

public class Cache extends Memory{

	private RAM myram = null;
	private int[] addresses = null;
	
	public Cache(int size, RAM ram) {
		super(size);
		this.myram = ram;
		this.addresses = new int[size];
	}
	
	private int missed(int address) {
		
		for(int i = 0; i < this.size(); i++) {
			this.addresses[i] = (address+i) % this.myram.size();
			super.Set(i, this.myram.Get(address+i));
		}

		return this.myram.Get(address);
	
	}
	
	public int Get(int address) {
		
		int base_address = address - this.addresses[0];
	
		if (base_address < 0 || base_address > this.size()-1) { return missed(address); }
		
		boolean hit = (this.addresses[base_address] == address);
		
		return (hit) ? super.Get(base_address) : missed(address);
		
	}
	
	public void Set(int address, int word) { 
		this.myram.Set(address, word);
		
		int base_address = address - this.addresses[0];
		
		if (!(base_address < 0 || base_address > this.size()-1)) { 
			super.Set(base_address, this.myram.Get(address));
		}
		
		
		//System.out.println("Cache Memory: "+Arrays.toString(this.memory));
		//System.out.println("Address Label Array: "+Arrays.toString(this.addresses));
		//System.out.println("RAM: "+Arrays.toString(this.myram.memory));		

	}

}
