
package ParalelismoRecursivo;

import static ParalelismoRecursivo.MergeSortSequencial.merger;
import static ParalelismoRecursivo.MergeSortSequencial.RootMerger;
import java.util.concurrent.Semaphore;

public class MergeSortParalelo extends Thread{
    
    int arr[], l, r, nivel;
    public Semaphore concluido;

    public MergeSortParalelo(int[] arr, int l, int r, Semaphore s, int nivel){
        this.arr = arr; this.l = l; this.r = r; this.concluido = s; 
        this.nivel = nivel;
    }
    
    private void merge_sort_paralelo(int[] arr, int l, int r)
                                             throws InterruptedException{
       
            if(l < r){
                int m = (l+r)/2;
                Semaphore s = new Semaphore(-1);
                if(Math.pow(2, nivel) >= Runtime.getRuntime().availableProcessors()){
                   merger(arr, l, r);
                   concluido.release();
                   return;
                }
                
                MergeSortParalelo m1 = new MergeSortParalelo(arr, l, m,s,nivel+1);
                MergeSortParalelo m2 = new MergeSortParalelo(arr, m+1, r,s,nivel+1);
                  
                m1.start(); m2.start();
                try{ s.acquire(); }catch(Exception e){ e.getSuppressed();}
                RootMerger(arr, l, r); 
                
            }
            concluido.release();
    }
    
    public void run(){
        try{
            merge_sort_paralelo(arr, l, r);
        }catch(Exception e){ e.getSuppressed(); }
        
    }
           
}
