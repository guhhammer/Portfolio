package sorting;

import java.util.Random;

public class recursive_selectionsort {

	private static Random r_generator = new Random();

	// selectionsort(...) -> C1 + ... + C8 = (2 + 3n) * n = 2n + 3n² = O( n² )
	//
	private static void selectionsort(int[] array, int index) {

		if(index >= array.length - 1) { return; }				// C1 <- n

		int k = index;											// C2 <- 1
		for(int i = index + 1; i < array.length; i++ ) {		// C3 <- n-1
			
			if (array[i] < array[k] ){  k = i; }				// C4 <- n-1

		}

		int temp = array[index];							    // C5 <- 1
		array[index] = array[k];								// C6 <- 1
		array[k] = temp;										// C7 <- 1

		selectionsort(array, index + 1);					// C8 <- n * n

	}

	// selectionsort_wrapper(...) -> C1 = O( n² )
	//
	public static void selectionsort_wrapper(int[] arr){

		selectionsort(arr, 0);				// C1 <- n²

	}

	// partition(...) -> C1 + ... + C15 = 3 + 6n = O( n )
	//
	private static int partition(int[] arr, int pivot_index){

		int i = 0;											// C1 <- 1

		if(pivot_index != 0){								// C2 <- 1

			int aux = arr[0];								// C3 <- 1
			arr[0] = arr[pivot_index];						// C4 <- 1
			arr[pivot_index] = aux;							// C5 <- 1

		}

		for(int j = 0; j < (arr.length - 1); j++){			// C6 <- n-1

			if(arr[j+1] < arr[0]){							// C7 <- n-1

				int aux = arr[j + 1];						// C8 <- n-1
				arr[j + 1] = arr[i + 1];					// C9 <- n-1
				arr[i + 1] = aux;							// C10 <- n-1
				i++;										// C11 <- n-1

			}

		}

		int aux = arr[0];									// C12 <- 1
		arr[0] = arr[i];									// C13 <- 1
		arr[i] = aux;										// C14 <- 1

		return i;											// C15 <- 1

	}

	// selectionsort_random(...) -> C1 + ... + C13 = 9 + 4n = O( n )
	//
	private static int selectionsort_random(int[] arr, int k){

		if(arr.length == 1){  return arr[0]; }								// C1 <- 1
		else{

			int j = partition(arr, r_generator.nextInt(arr.length));		// C2 <- n

			int L[] = new int[j], R[] = new int[arr.length-j];				// C3 <- 2

			for (int i = 0; i < L.length; i++){  L[i] = arr[i];  }			// C4 ~ n/2
			for (int m = 0; m < R.length; m++){  R[m] = arr[j+m];  }		// C5 ~ n/2

																			//  ^ = n

			if(j == k){														// C6 <- 1
				return arr[j];												// C7 <- 1
			}
			else if(j > k){													// C8 <- 1

			    R = null;													// C9 <- 1
				return selectionsort_random(L, k);							// C10 <- n

			}
			else{

			    L = null;													// C11 <- 1
				k = k - j - 1;												// C12 <- 1
				return selectionsort_random(R, k);							// C13 <- n

			}

		}

	}

	// append(...) -> C1 + ... + C4 = 3 + n = O( n )
	//
	private static int[] append(int[] arr, int n){

		int[] narr = new int[arr.length+1];								// C1 <- 1

		for(int i = 0; i < arr.length; i++){ narr[i] = arr[i]; }		// C2 <- n

		narr[arr.length] = n;											// C3 <- 1

		return narr;													// C4 <- 1

	}

	// selectionsort_control(...) -> C1 + ... + C4 = 2 + n + n² = O( n² )
	//
	private static int[] selectionsort_control(int[] arr, int contador, int[] Q){

		if(contador == arr.length){ return Q; }							// C1 <- 1
		else{

			Q = append(Q, selectionsort_random(arr, contador));			// C2 <- n
			selectionsort_control(arr, contador + 1, Q);		// C3 <- n * n

		}

		return Q;														// C4 <- 1

	}

	//	selectionsort_random_wrapper(...) -> C1 = n² = O( n² )
	//
	public static void selectionsort_random_wrapper(int[] arr){

		selectionsort_control(arr, 0, new int[]{});		// C1 <- n²

	}

}
