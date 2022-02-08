
package projeto_bimestral;

public class Heapsort{

    void heap(int[] x, int n, int i){ 
        int largest = i;
        int esq = 2*i + 1; 
        int dir = 2*i + 2;
   
        if (esq < n && x[esq] > x[largest]){  largest = esq;}
  
        if (dir < n && x[dir] > x[largest]){  largest = dir;}
  
        if (largest != i){ 
            int swap = x[i]; 
            x[i] = x[largest]; 
            x[largest] = swap; 
  
            heap(x, n, largest); 
        } 
    } 
    
    public void heapsort(int[] x){ 
        
        for (int i = x.length / 2 - 1; i >= 0; i--){ heap(x, x.length, i);}
  
        for (int i = x.length-1; i>=0; i--){ 
            
            int temp = x[0]; 
            x[0] = x[i]; 
            x[i] = temp; 

            heap(x, i, 0); 
        } 
    } 
    
    public static void heap_sorted(int[] x){
        new Heapsort().heapsort(x);
    }
    
}
