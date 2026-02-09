package MonitorSemCondição;


public class BufferLimitado {
	
	private int N;
	private int buffer[];
	private int front = 0;
	private int rear = 0;
	private int count = 0;
		
	public BufferLimitado(int N)
	{
		this.N = N;
		buffer = new int[N];
	}
	
	public synchronized void depositar(int dado) throws InterruptedException
	{
		while ( count == N ) wait();
		buffer[rear] = dado;
		rear = (rear + 1) % N;
		count++;
		System.out.println( "Produzido: " + dado + "[" + count + "]" );
		notify(); // signal
	}
	
	public synchronized int retirar() throws InterruptedException
	{
		while ( count == 0 ) wait();
		int dado = buffer[front];
		front = (front + 1) % N;
		count--;
		System.out.println( "Consumido: " + dado + "[" + count + "]" );
		notify(); // signal
		return dado;
	}

}
