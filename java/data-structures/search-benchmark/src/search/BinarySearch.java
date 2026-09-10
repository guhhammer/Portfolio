package search;




import java.util.Arrays;

public class BinarySearch {

     // returns the sorted array.
    public static int[] sort(int[] x){ Arrays.sort(x); return x;}
    
    // binary-searches an element in the array.
    public static int search(int[] n, int pos) {
        int inf = 0, sup = n.length - 1;
        while (inf <= sup) {
            int meio = (inf + sup) / 2;
            if(pos == n[meio]){ return meio; }
            if(pos < n[meio]){ sup = meio - 1; }
            else{  inf = meio + 1; }
        }
        return (-1);
    }

}
