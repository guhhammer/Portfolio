
public class Start {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Pot pot = new Pot( 5 );
		Cook cook = new Cook( pot );
		cook.start();
		for ( int i = 0; i < 10; i++ )
		{
			Savage s = new Savage( i, pot );
			s.start();
		}

	}

}
