package sorting;

import java.util.Random;

public class recursive_quicksort{

    protected static Random r_generator = new Random();

    // swap(...) -> C1 + ... + C3 = 3 = O(1)
    //
    private static void swap(int[] arr, int i, int j){

        int aux = arr[i];                               // C1 <- 1
        arr[i] = arr[j];                                // C2 <- 1
        arr[j] = aux;                                   // C3 <- 1

    }

    // partition(...) -> C1 + ... + C10 = 6 + 2n = O(n)
    //
    private static int partition(int[] arr, int p, int r){

        int x = (r+p)/2;                            // C1 <- 1
        swap_r(arr, x, r);                          // C2 <- 1

        x = arr[r];                                 // C3 <- 1

        int i = p - 1;                              // C4 <- 1

        for(int j = p; j < r; j++){                 // C5 <- n/2

            if(arr[j] <= x){                        // C6 <- n/2

                i++;                                // C7 <- n/2
                swap(arr, i, j);                    // C8 <- n/2

            }

        }

        swap(arr, i+1, r);                       // C9 <- 1

        return i + 1;                               // C10 <- 1

    }

    // quicksort(...) -> C1 + ... + C4 = 1 + n + 2 * n * log n = O( n * log n )
    //
    private static void quicksort(int[] arr, int p, int r){

        if(p < r){                                  // C1 <- 1

            int q = partition(arr, p, r);           // C2 <- n
            quicksort(arr, p, q - 1);            // C3 <- n * log n
            quicksort(arr, q + 1, r);           // C4 <- n * log n

        }

    }

    // quicksort_wrapper(...) -> C1 + C2 = 1 + n * log n = O( n * log n )
    //
    public static void quicksort_wrapper(int[] arr){

        int N = arr.length - 1;                     // C1 <- 1

        quicksort(arr, 0, N);                   // C2 <- n * log n

    }


    // swap_r(...) -> C1 + ... + C3 = 3 = O(1)
    //
    private static void swap_r(int[] arr, int i, int j){

        int aux = arr[i];                               // C1 <- 1
        arr[i] = arr[j];                                // C2 <- 1
        arr[j] = aux;                                   // C3 <- 1

    }

    // partition_r(...) -> C1 + ... + C10 = 6 + 2n = O(n)
    //
    private static int partition_r(int[] arr, int p, int r){

        int x = r_generator.nextInt(r - p) + p;      // C1 <- 1

        swap_r(arr, x, r);                                  // C2 <- 1

        x = arr[r];                                         // C3 <- 1
        int i = p - 1;                                      // C4 <- 1

        for(int j = p; j < r; j++){                         // C5 <- n/2

            if(arr[j] <= x){                                // C6 <- n/2

                i++;                                        // C7 <- n/2
                swap_r(arr, i, j);                          // C8 <- n/2

            }

        }

        swap_r(arr, i+1, r);                             // C9 <- 1

        return i + 1;                                       // C10 <- 1

    }

    // quicksort_r(...) -> C1 + ... + C4 = 1 + n + 2 * n * log n = O( n * log n )
    //
    private static void quicksort_r(int[] arr, int p, int r){

        if(p < r){                                      // C1 <- 1

            int q = partition_r(arr, p, r);             // C2 <- n
            quicksort_r(arr, p, q - 1);              // C3 <- n * log n
            quicksort_r(arr, q + 1, r);             // C4 <- n * log n

        }

    }

    // quicksort_random_wrapper(...) -> C1 + C2 = 1 + n * log n = O( n * log n )
    //
    public static void quicksort_random_wrapper(int[] arr){

        int N = arr.length - 1;                         // C1 <- 1

        quicksort_r(arr, 0, N);                     // C2 <- n * log n

    }

}
