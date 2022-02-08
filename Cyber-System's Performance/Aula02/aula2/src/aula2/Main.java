package aula2;

public class Main {
	
	public static void main(String[] args) {
		
		RAM ram = new RAM(128); 
		IO es = new IO(System.out, ram);
		CPU cpu = new CPU(ram, es);
		
		ram.Set(0, 0x78);
		ram.Set(1, 0x7E);
		
		ram.Set(0x78, 0xA);   // Boot Process Memory.
		ram.Set(0x79, 0x14);
		ram.Set(0x7A, 0x15);
		ram.Set(0x7B, 0x16);
		ram.Set(0x7C, 0x17);
		ram.Set(0x7D, 0xC);
		ram.Set(0x7E, 0x1F);
		
		
		cpu.Run();
	
	}
		
}
