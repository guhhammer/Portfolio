
package projeto_bimestral;

public class Quicksort {
    

    int partition(int arr[], int left, int right) {

        int i = left, j = right;

        int tmp;

        int pivot = arr[(left + right) / 2];

        while (i <= j) {

            while (arr[i] < pivot) {
                i++;
            }

            while (arr[j] > pivot) {
                j--;
            }

            if (i <= j) {

                tmp = arr[i];

                arr[i] = arr[j];

                arr[j] = tmp;

                i++;

                j--;

            }

        };

        return i;

    }

    void quicksort(int arr[], int left, int right) {

        int index = partition(arr, left, right);

        if (left < index - 1) {
            quicksort(arr, left, index - 1);
        }

        if (index < right) {
            quicksort(arr, index, right);
        }

    }

    
    public static void quick_sorted(int[] x) {
        if (x.length > 1) {
            new Quicksort().quicksort(x, 0, x.length - 1);
          
        }
    }

}
