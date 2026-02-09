package first_app;

public class Main {
	
	private static float powerOf(int x, int y) {
		return (float) Math.pow(x, y)*1.0f;
	}
	
	public static float chunk(int k){
		
		return ( (1/powerOf(16,k))* ( (4.0f/(8.0f*k + 1)) - (2.0f/(8.0f*k+4)) - (1.0f / (8.0f*k + 5)) - (1.0f/(8.0f*k+6))) );
		
	}
	
	
	public static float pi(int precision) {
		
		float sum = 0.0f;
		for(int x = 0; x < precision; x++) {
			sum += chunk(x);
		}
		
		return sum;
	
	} // Bellard
	
	
	public static float chunk2(int k) {
		
		return (((k % 2 == 0) ? 1 : -1)*1.0f)/ ((2.0f*k+2)*(2.0f*k+3)*(2.0f*k+4));
		
	}
	
	public static float pi2(int precision) {
		
		float sum = 0.0f;
		for(int x = 0; x < precision; x++) {
			sum += chunk2(x);
		}

		return 3 + 4*sum;
		
	} // Nilakhanta
	
	
	
	
	public static float pi_sehr_gut(int precision) {
		
		float sum = 0.0f;
		for(int k = 0; k < precision; k++) {
			sum += (((k % 2 == 0) ? 1 : -1)*1.0f)/ (8.0f*(k+1)*(k+1.5f)*(k+2.0f));
		}
		
		return 3 + 4 * sum;
		
	}
	
	
	public static void professor_sol() {
		
		long t0 = System.currentTimeMillis();
		
		double pi = 0.0, num = 1.0, i2 = 0.0;
		for(long i = 0; i < 1000000000; i++) {
			i2  = 2*i;
			pi += num / ((i2+2)*(i2+3)*(i2+4));
			num = -num;
		}
		
		pi *= 4.0;
		pi += 3.0;
		
		long d = System.currentTimeMillis() - t0;
		
		System.out.println("\n\nTime: "+d+" miliseconds.\n");
		
	}
	
	

	public static void main(String Args[]) {
		
		long begin = System.currentTimeMillis();
				
		//float pi = pi(1000000000);
		
		long end = System.currentTimeMillis() - begin;
		
		//System.out.printf(" PI = %.20f \n", pi);
		
		
		System.out.println("Time: "+end);
		
		
		long begin2 = System.currentTimeMillis();
		
		float pi2 = pi2(1000000000);
		
		long end2 = System.currentTimeMillis() - begin2;
		
		System.out.printf(" PI = %.20f \n", pi2);
		
		
		System.out.println("Time: "+end2);
		
		
		
		long begin3 = System.currentTimeMillis();
		
		float pi3 = pi_sehr_gut(1000000000);
		
		long end3 = System.currentTimeMillis() - begin3;
		
		System.out.printf(" PI = %.20f \n", pi3);
		
		
		System.out.println("Time: "+end3);
		
		
		professor_sol();
		
		
	}
	
	
}
