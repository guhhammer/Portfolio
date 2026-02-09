package first_app;

public class X_millions {


	public static void main(String[] args) {
	
		long startTime = System.currentTimeMillis();
		
		
		for(int x = 0; x < 1000000; x++) {
			System.out.print("x");
		}
		
		long endTime = System.currentTimeMillis() - startTime;
		
		System.out.println("\n\n\t\t Time elapsed: "+endTime+" miliseconds. \n");
		
	}
	
	
}
