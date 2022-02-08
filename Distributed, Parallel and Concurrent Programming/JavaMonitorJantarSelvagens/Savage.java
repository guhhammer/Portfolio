import java.util.Random;

public class Savage extends Thread {

	private int ID;
	private Pot pot;
	private Color color;

	public Savage(int ID, Pot pote) {
		this.ID = ID;
		this.pot = pote;
		color = Color.GREEN;
	}

	public int get_ID() { return ID; }

	public Color get_color() { return color; }
	
	public void set_color( Color color )
	{
		this.color = color;
		//System.out.println( " ( " + ID + ": became + : " + color + ") ");
	}
	
	public void run() {
		Random r = new Random();
		while (true) {
			try {
				int time_for_resting = 1000 + r.nextInt(4000);
				Thread.sleep(time_for_resting);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			set_color( Color.YELLOW );
			pot.get_served( this );
			try {
				int time_for_eating = 500 + r.nextInt(1000);
				Thread.sleep(time_for_eating);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
