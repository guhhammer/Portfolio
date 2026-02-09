
public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BufferLimitado b = new BufferLimitado( 10 );
		Produtor p1 = new Produtor( b );
		Consumidor c1 = new Consumidor( b );
		p1.start();
		c1.start();
	}

}
