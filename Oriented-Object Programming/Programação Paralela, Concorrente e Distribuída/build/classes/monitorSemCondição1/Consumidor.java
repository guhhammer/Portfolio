
import java.util.Random;


public class Consumidor extends Thread {

	private BufferLimitado buffer;
	private Random aleatorio;
	
	public Consumidor(BufferLimitado buffer)
	{
		this.buffer = buffer;
		aleatorio = new Random();
	}
	
	public void run()
	{
		while (true)
		{
			try {
				int dado = buffer.retirar( );
				int tempo = aleatorio.nextInt(2000);
				Thread.sleep(tempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
		
	}
}