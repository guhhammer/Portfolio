package mergesort;

public class SequentialMergeSort {

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1, n2 = right - mid;
        int[] leftHalf = new int[n1], rightHalf = new int[n2];
        for (int i = 0; i < n1; ++i) { leftHalf[i] = arr[left + i]; }
        for (int j = 0; j < n2; ++j) { rightHalf[j] = arr[mid + 1 + j]; }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftHalf[i] <= rightHalf[j]) { arr[k] = leftHalf[i]; i++; }
            else { arr[k] = rightHalf[j]; j++; }
            k++;
        }
        while (i < n1) { arr[k] = leftHalf[i]; i++; k++; }
        while (j < n2) { arr[k] = rightHalf[j]; j++; k++; }
    }

    public void sort(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    public static void mergeSort(int[] x) { new SequentialMergeSort().sort(x, 0, x.length - 1); }

    /** Called by ParallelMergeSort once the thread limit is reached. */
    public static void sortRange(int[] x, int l, int r) { new SequentialMergeSort().sort(x, l, r); }

    /** Called by ParallelMergeSort after both halves have been sorted by child threads. */
    public static void mergeHalves(int[] arr, int l, int r) { merge(arr, l, (l + r) / 2, r); }
}
