public class Cook extends Thread {

	private Pot pote;

	public Cook(Pot pote) {
		this.pote = pote;
	}

	public void run() {
		while (true) {
			System.out.println( "*** Cook is ready to fill the pot in ..." );
			pote.fill_in();
			System.out.println( "*** Cook has just filled the pot in" );
		}
	}
}