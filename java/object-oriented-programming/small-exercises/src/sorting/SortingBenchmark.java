package sorting;

import java.util.Arrays;
import java.util.Random;

/** Times merge sort, quicksort, heapsort, pigeonhole sort and comb sort on 100,000 random ints. */
public class SortingBenchmark {

    void merge(int[] x, int l, int m, int r) {
        int n1 = m - l + 1, n2 = r - m;
        int[] left = new int[n1], right = new int[n2];
        for (int i = 0; i < n1; ++i) { left[i] = x[l + i]; }
        for (int j = 0; j < n2; ++j) { right[j] = x[m + 1 + j]; }
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) { x[k] = left[i]; i++; } else { x[k] = right[j]; j++; }
            k++;
        }
        while (i < n1) { x[k] = left[i]; i++; k++; }
        while (j < n2) { x[k] = right[j]; j++; k++; }
    }

    void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
            }
        }
        int temp = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = temp;
        return i + 1;
    }

    void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    void heapify(int[] arr, int n, int i) {
        int largest = i, l = 2 * i + 1, r = 2 * i + 2;
        if (l < n && arr[l] > arr[largest]) { largest = l; }
        if (r < n && arr[r] > arr[largest]) { largest = r; }
        if (largest != i) {
            int swap = arr[i]; arr[i] = arr[largest]; arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }

    void heapSort(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) { heapify(arr, arr.length, i); }
        for (int i = arr.length - 1; i >= 0; i--) {
            int temp = arr[0]; arr[0] = arr[i]; arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

    static void pigeonholeSort(int[] arr, int n) {
        int min = arr[0], max = arr[0];
        for (int a = 0; a < n; a++) {
            if (arr[a] > max) { max = arr[a]; }
            if (arr[a] < min) { min = arr[a]; }
        }
        int range = max - min + 1;
        int[] holes = new int[range];
        Arrays.fill(holes, 0);
        for (int i = 0; i < n; i++) { holes[arr[i] - min]++; }
        int index = 0;
        for (int j = 0; j < range; j++) { while (holes[j]-- > 0) { arr[index++] = j + min; } }
    }

    int nextGap(int gap) {
        gap = (gap * 10) / 13;
        return gap < 1 ? 1 : gap;
    }

    void combSort(int[] arr) {
        int gap = arr.length;
        boolean swapped = true;
        while (gap != 1 || swapped) {
            gap = nextGap(gap);
            swapped = false;
            for (int i = 0; i < arr.length - gap; i++) {
                if (arr[i] > arr[i + gap]) {
                    int temp = arr[i]; arr[i] = arr[i + gap]; arr[i + gap] = temp;
                    swapped = true;
                }
            }
        }
    }

    static int[] randomArray(int size) {
        int[] x = new int[size];
        Random random = new Random();
        for (int i = 0; i < x.length; i++) { x[i] = random.nextInt(size * 1000); }
        return x;
    }

    public static void main(String[] args) {
        int size = 100000;
        int[] m = randomArray(size), q = randomArray(size), h = randomArray(size), p = randomArray(size), c = randomArray(size);
        SortingBenchmark x = new SortingBenchmark();

        long t = System.currentTimeMillis(); x.mergeSort(m, 0, m.length - 1); System.out.println("merge sort:      " + (System.currentTimeMillis() - t) / 1000.0 + " s");
        t = System.currentTimeMillis(); x.quickSort(q, 0, q.length - 1);       System.out.println("quicksort:       " + (System.currentTimeMillis() - t) / 1000.0 + " s");
        t = System.currentTimeMillis(); x.heapSort(h);                          System.out.println("heapsort:        " + (System.currentTimeMillis() - t) / 1000.0 + " s");
        t = System.currentTimeMillis(); pigeonholeSort(p, p.length);            System.out.println("pigeonhole sort: " + (System.currentTimeMillis() - t) / 1000.0 + " s");
        t = System.currentTimeMillis(); x.combSort(c);                          System.out.println("comb sort:       " + (System.currentTimeMillis() - t) / 1000.0 + " s");
    }
}
