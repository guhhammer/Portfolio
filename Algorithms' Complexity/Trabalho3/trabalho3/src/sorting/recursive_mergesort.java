package sorting;

import java.util.Random;

public class recursive_mergesort {

    private static Random r_generator = new Random();

    // merge(...) -> C1 + ... + C13 = O( 13r + 3q - 19*p + 28 )
    //
    private static void merge(int array[], int p, int q, int r) {

        int left = q - p + 1, right = r - q;                                // C1 <- 2

        int L[] = new int[left], R[] = new int[right];                      // C2 <- 2

        for (int i = 0; i < left; i++){  L[i] = array[p + i];  }            // C3 <- q - p + 1
        for (int j = 0; j < right; j++){  R[j] = array[q + 1 + j];  }       // C4 <- r - q

        int i = 0, j = 0, k = p;                                            // C5 <- 3

        while (i < left && j < right) {                                     // C6 <- r - p + 1
            if (L[i] <= R[j]) { array[k] = L[i]; i++; }                     // C7 <- 2 * (r - p + 1)
            else { array[k] = R[j]; j++; }                                  // C8 <- 2 * (r - p + 1)
            k++;                                                            // C9 <- r - p + 1
        }

        while (i < left) { array[k] = L[i]; i++; k++; }                     // C10 <- ( r +  q - 2*p + 2) * 3

        while (j < right) { array[k] = R[j]; j++; k++; }                    // C11 <- (r - 2*p + 2) * 3

        L = null;                                                           // C12 <- 1
        R = null;                                                           // C13 <- 1

    }

    // merge(...) -> C1 + ... + C5 = O( (13r + 3q - 19*p + 32) * log N )
    //
    private static void mergesort(int array[], int left, int right) {

        if (left < right) {                                     // C1 <- 1

            int mid = (left + right) / 2;                       // C2 <- 1

            mergesort(array, left, mid);                        // C3 <- log n
            mergesort(array, mid + 1, right);              // C4 <- log n

            merge(array, left, mid, right);                     // C5 <- 13r + 3q - 19*p + 28

        }

    }

    // mergesort_wrapper( n ) -> C1 ->
    //                        -> (13r + 3q - 19*p + 32) * log N  :: 13r - 13p + 13 == 13n
    //                        -> (3q - 6p + 19 + 13n) * log n
    //                        -> ( x + 13n) * log n :: x == (3q - 6p + 19) => q-p ~ log n & p ~ 0 + 19
    //                        -> ( x + 13n) * log n :: x == (log n + 19)
    //                        -> ( x + 13n) * log n :: n > log n > c ~ (13n + log n + 19)
    //                        -> ( x + 13n) * log n :: (x + 13n) = (13 n) :: x ~ 0
    //                        -> 13n * log n
    //                        -> O( 13n * log n)
    //
    // mergesort_wrapper( n ) -> O( n * log n )
    public static void mergesort_wrapper(int[] arr){

        mergesort(arr, 0, arr.length-1);               // C1 <- (13r + 3q - 19*p + 32) * log N

    }

    // merge(...) -> C1 + ... + C5 = O( (13r + 3q - 19*p + 32) * log N )
    //
    private static void mergesort_random(int array[], int left, int right){

        if(left < right){                                                           // C1 <- 1

            int mid = (r_generator.nextInt(right - left) + left);            // C2 <- 1

            mergesort_random(array, left, mid);                                     // C3 <- log n
            mergesort_random(array, mid + 1, right);                           // C4 <- log n

            merge(array, left, mid, right);                                         // C5 <- 13r + 3q - 19*p + 28

        }

    }

    // ... mergersort_random_wrapper(n) -> O( n * log n )
    //
    public static void mergesort_random_wrapper(int[] arr){

        mergesort_random(arr, 0, arr.length-1);         // C1 <- (13r + 3q - 19*p + 32) * log N

    }

}
