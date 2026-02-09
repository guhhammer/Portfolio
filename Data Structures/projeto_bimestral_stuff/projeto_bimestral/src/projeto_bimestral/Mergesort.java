
package projeto_bimestral;

public class Mergesort{
    
 
    static void merge(int arr[], int esq, int meio, int dir){ 
     
        int metade1 = meio - esq + 1; 
        int metade2 = dir - meio; 
  
        int Esquerda[] = new int[metade1]; 
        int Direita[] = new int [metade2]; 
  
        for(int i=0; i < metade1; ++i){  Esquerda[i] = arr[esq + i]; }
       
        for(int j=0; j < metade2; ++j){  Direita[j] = arr[meio + 1+ j];} 
  
   
        int i = 0, j = 0, k = esq; 
        while (i < metade1 && j < metade2){ 
            if (Esquerda[i] <= Direita[j]){ arr[k] = Esquerda[i];   i++; } 
            else{  arr[k] = Direita[j];   j++;  } 
            k++; 
        } 
  
        while(i < metade1){ arr[k] = Esquerda[i];  i++;  k++; } 
  
        while(j < metade2){ arr[k] = Direita[j];  j++;  k++; }
        
    } 
 
   
    void mergesort(int arr[], int l, int r){ 
        if (l < r){ 
            int m = (l+r)/2; 
  
            mergesort(arr, l, m); 
            mergesort(arr , m+1, r); 
   
            merge(arr, l, m, r); 
        } 
    } 
    
    public static void merge_sorted(int[] x){
        new Mergesort().mergesort(x, 0, x.length-1); 
    }
    
    
} 
    

