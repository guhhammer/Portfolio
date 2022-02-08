
package projeto_bimestral;

public class Shellsort {

    void shellsort(int[] x){
        
        for(int gap = x.length/2; gap > 0; gap /= 2){
            
            for(int i = gap; i < x.length; i += 1){
                int temp = x[i];
                
                int j;
                for(j = i; j >= gap && x[j-gap] > temp; j -= gap){
                    x[j] = x[j-gap];
                }
                
                x[j] = temp;
            }
        }
    }
    
    public static void shell_sorted(int[] x){ new Shellsort().shellsort(x);}
    
}
