
package algorithm_test;

import java.util.Arrays;
import java.util.Random;

public class main {
    
    
    void merge(int[] x, int l, int m, int r) { 
         
        int n1 = m - l + 1; 
        int n2 = r - m; 
  
        
        int L[] = new int [n1]; 
        int R[] = new int [n2]; 
  
        
        for (int i=0; i<n1; ++i){L[i] = x[l + i];} 
        for (int j=0; j<n2; ++j){R[j] = x[m + 1+ j];} 
 
        int i = 0, j = 0; 
  
        
        int k = l; 
        while (i < n1 && j < n2){ 
            if (L[i] <= R[j]) { x[k] = L[i];  i++; } 
            else{  x[k] = R[j];   j++; } 
            k++; 
        } 
  
        
        while (i < n1) { x[k] = L[i]; i++; k++; } 
  
        while (j < n2) { x[k] = R[j]; j++; k++; } 
    } 
    
    void sortM(int arr[], int l, int r){ 
        if (l < r){ 
            int m = (l+r)/2; 
  
            sortM(arr, l, m); 
            sortM(arr , m+1, r); 
  
            merge(arr, l, m, r); 
        } 
    } 
  
    
    int partition(int arr[], int low, int high){ 
        int pivot = arr[high];  
        int i = (low-1);
     
        for (int j=low; j<high; j++) { 
            if (arr[j] <= pivot){ 
                i++; 
  
                int temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
            } 
        } 
   
        int temp = arr[i+1]; 
        arr[i+1] = arr[high]; 
        arr[high] = temp; 
  
        return i+1; 
    } 
  
    void sortq(int arr[], int low, int high){ 
        if (low < high){ 
        
            int pi = partition(arr, low, high); 
   
            sortq(arr, low, pi-1); 
            sortq(arr, pi+1, high); 
        } 
    } 
  
    
    void heapify(int arr[], int n, int i) { 
        int largest = i; 
        int l = 2*i + 1; 
        int r = 2*i + 2; 
   
        if (l < n && arr[l] > arr[largest]){  largest = l; } 
  
        if (r < n && arr[r] > arr[largest]){  largest = r; }
        
        if (largest != i){ 
            int swap = arr[i]; 
            arr[i] = arr[largest]; 
            arr[largest] = swap; 
            
            heapify(arr, n, largest); 
        } 
    } 
    
    void sorth(int arr[]) { 
  
        for(int i = arr.length/2-1;i >= 0;i--){ heapify(arr, arr.length, i); } 
 
        for (int i=arr.length-1; i>=0; i--){ 
            
            int temp = arr[0]; 
            arr[0] = arr[i]; 
            arr[i] = temp; 
   
            heapify(arr, i, 0); 
        } 
    } 
    
    
    static void pigeonhole_sort(int arr[], int n){ 
        int min = arr[0]; 
        int max = arr[0]; 
        int range, i, j, index;  
  
        for(int a=0; a<n; a++){ 
            if(arr[a] > max){ max = arr[a];} 
            if(arr[a] < min){ min = arr[a];} 
        } 
  
        range = max - min + 1; 
        int[] phole = new int[range]; 
        Arrays.fill(phole, 0); 
  
        for(i = 0; i<n; i++){ phole[arr[i] - min]++;} 
  
        index = 0; 
 
        for(j = 0; j<range; j++){while(phole[j]-->0){arr[index++]=j+min;}}
    
    }
    
   
    int getNextGap(int gap){
        gap = (gap*10)/13; 
        if (gap < 1){ return 1;} 
        return gap; 
    } 
   
    void sortc(int arr[]){  
        
        int gap = arr.length; 
  
        boolean swapped = true; 
  
        while (gap != 1 || swapped == true){
            gap = getNextGap(gap); 
               
            swapped = false; 
            
            for (int i=0; i<arr.length-gap; i++){ 
                if (arr[i] > arr[i+gap]){ 
                    int temp = arr[i]; 
                    arr[i] = arr[i+gap]; 
                    arr[i+gap] = temp; 
  
                    swapped = true; 
                } 
            } 
        } 
    } 
 
    
    static void printArray(int arr[]) { 
        for (int i=0; i<arr.length; ++i){System.out.print(arr[i] + " ");} 
        System.out.println(); 
    } 
    
   
    static int[] mach_vektor(int tamanho){
        int[] x = new int[tamanho];
        for(int i = 0; i < x.length; i++){
            x[i] = new Random().nextInt(tamanho*1000); 
        }
        return x;
    }  
    
   
    public static void main(String args[]){ 
        
        int Kon = 100000;
        
        int m[] = mach_vektor(Kon);
        int q[] = mach_vektor(Kon);
        int l[] = mach_vektor(Kon);
        int p[] = mach_vektor(Kon);
        int c[] = mach_vektor(Kon);
        
        main x = new main(); 
        
        long mt = System.currentTimeMillis();
        x.sortM(m, 0, m.length-1); 
        long mtf = System.currentTimeMillis();
        long qt = System.currentTimeMillis();
        x.sortq(q, 0, q.length-1);
        long qtf = System.currentTimeMillis();
        long ht = System.currentTimeMillis();
        x.sorth(l);
        long htf = System.currentTimeMillis();
        long pt = System.currentTimeMillis();
        x.pigeonhole_sort(p, p.length);
        long ptf = System.currentTimeMillis();
        long ct = System.currentTimeMillis();
        x.sortc(c);
        long ctf = System.currentTimeMillis();
        
        System.out.println("\n->\n\n"); 
        
        //printArray(m);
        System.out.println("M tempo ->  "+((mtf-mt)/1000.0));
        //printArray(q);
        System.out.println("Q tempo ->  "+((qtf-qt)/1000.0));
        //printArray(l);
        System.out.println("H tempo ->  "+((htf-ht)/1000.0));
        //printArray(p);
        System.out.println("P tempo ->  "+((ptf-pt)/1000.0));
        //printArray(c);
        System.out.println("C tempo ->  "+((ctf-ct)/1000.0));
    } 

    
}
