import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pot {
	private int N;
	private int available_food = 0;
	private int orange_savages = 0;
	private boolean there_is_a_red_savage = false;

	private Lock gate_M = new ReentrantLock();

	Condition gate_A = gate_M.newCondition();
	Condition gate_B = gate_M.newCondition();
	Condition gate_C = gate_M.newCondition();

	public Pot(int N) {
		this.N = N;
	}

	public void fill_in() {
		try {
			gate_M.lock();
			System.out.println("*** Cook waits at his lounge");
			gate_A.await();
			System.out.println("*** Cook enters the monitor" );
			try {
				int time_for_cooking = 1000 + new Random().nextInt(1000);
				Thread.sleep(time_for_cooking);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			available_food = N;
			gate_C.signal();
			System.out.println("*** Cook fills the pot in and signals gate C");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			gate_M.unlock();
		}
	}

	public void get_served(Savage savage) {
		try {
			gate_M.lock();
			boolean served = false;
			while (!served) {
				System.out.print(
						"Savage enters the pot monitor: " + savage.get_ID() + ", " + savage.get_color() + " ==> ");
				switch (savage.get_color()) {
				case GREEN:
					System.out.println("shouldn't be there!");
					break;
				case YELLOW:
					if (there_is_a_red_savage || orange_savages > 0) {
						savage.set_color(Color.ORANGE);

                                                orange_savages++;
						System.out.println("becomes orange and waits at gate B");
						gate_B.await();
					} else if (available_food == 0) {
						savage.set_color(Color.RED);
						there_is_a_red_savage = true;
						gate_A.signal();
						System.out.println("becomes red, signals gate A and waits at gate C");
						gate_C.await();
					} else {
						available_food--;
						served = true;
						savage.set_color(Color.GREEN);
						//gate_B.signal(); // não é necessário, mas também não atrapalha
						System.out.println("gets served, becomes green and signals gate B");
					}
					break;
				case ORANGE:
					if (available_food == 0) {
						orange_savages--;
						savage.set_color(Color.RED);
						there_is_a_red_savage = true;
						gate_A.signal();
						System.out.println("becomes red, signals gate A and waits at gate C");
						gate_C.await();
					} else {
						available_food--;
						served = true;
						orange_savages--;
						savage.set_color(Color.GREEN);
						gate_B.signal();
						System.out.println("gets served, becomes green and signals gate B");
					}
					break;
				case RED:
					available_food--;
					served = true;
					there_is_a_red_savage = false;
					savage.set_color(Color.GREEN);
					gate_B.signal();
					System.out.println("gets served, becomes green and signals gate B");
					break;
				}
			} // while
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			gate_M.unlock();
		}
	}
}
