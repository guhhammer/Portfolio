package aula2;

public class CPU {
	
	private RAM my_ram;
	private IO my_io;
	private int PC;
	
	public CPU(RAM ram, IO es) {
		
		this.my_ram = ram;
		this.my_io = es;
		this.PC = 0;
		
	}
	
	
	public void boot() {
		
		int inf_lim = this.my_ram.Get(this.PC),
			sup_lim = this.my_ram.Get(this.PC+1);
		
		this.my_io.initial_boot_set_up(true);
		
		while(inf_lim <= sup_lim) {
			
			int instruction = this.my_ram.Get(inf_lim);
			
			// Execute instruction...
			
			this.my_io.write_boot(inf_lim++); // Display value.
			
		}
		
		this.my_io.initial_boot_set_up(false);
		
	}
	
	
	
	public void Run() {
		
		this.boot();
		
		// Do something...
		
	}
	
	
	
}
