package Main;

public class Main {

	public static void main(String[] args) {
		
		IO io = new IO(System.out);
		
		RAM ram = new RAM(128);
        ram.Set(10,120);
        ram.Set(11,127);

		Cache cache = new Cache(16, ram);
        
		CPU cpu = new CPU(cache, io);
        
		cpu.Run(10);
        
	}
			
}
