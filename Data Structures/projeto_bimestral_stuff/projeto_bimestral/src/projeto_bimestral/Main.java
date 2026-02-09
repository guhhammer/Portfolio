
package projeto_bimestral;

import java.util.Random;
import java.util.Scanner;
import static projeto_bimestral.Heapsort.*;
import static projeto_bimestral.Introsort.*;
import static projeto_bimestral.Mergesort.*;
import static projeto_bimestral.Quicksort.*;
import static projeto_bimestral.Radixsort.*;
import static projeto_bimestral.Shellsort.*;
 
public class Main{
 
    public static int[] decrescente(int n){
        int[] array = new int[n];
        
        for(int i = 0; i < array.length; i++){ array[i] = (array.length-1)-i;}
        
        return array;
    }
    
    public static int[] total_random(int n){
        int[] array = new int[n];
        
        for(int i = 0; i < array.length; i++){
            array[i] = new Random().nextInt(n*10);
        }
        
        return array;
    }   
    
    public static int[] quase_random(int n){
        int[] array = new int[n];
        
        for(int i = 0; i < array.length; i++){
            if(Math.random()*100 <= 15.0){
                array[i] = (int) new Random().nextInt(n);
            }
            else{ array[i] = i;}
        }
        
        return array;
    }   
    
    
    static String tabela(float[] a){
        return (String.format(
        ("\n------------------------------------------------------------------------- "+
         "\n| Tamanho do Conjunto: %1$d elementos.                             |"+
         "\n------------------------------------------------------------------------- "+
         "\n|             |           Tempos obtidos pelos algoritmos:              |"+
         "\n------------------------------------------------------------------------- "+
         "\n|  Algoritmo  |    Quase Ordenado  |  Desordenado   | Ordem Decrescente |"+
         "\n------------------------------------------------------------------------- "+
         "\n|  Quicksort  |       %2$.4f s     |    %3$.4f s    |      %4$.4f s     |"+
         "\n------------------------------------------------------------------------- "+
         "\n|  Shellsort  |       %5$.4f s     |    %6$.4f s    |      %7$.4f s     |"+
         "\n------------------------------------------------------------------------- "+
         "\n|  Heapsort   |       %8$.4f s     |    %9$.4f s    |      %10$.4f s     |"+  
         "\n------------------------------------------------------------------------- "+
         "\n|  Mergesort  |       %11$.4f s     |    %12$.4f s    |      %13$.4f s     |"+  
         "\n------------------------------------------------------------------------- "+
         "\n|  Radixsort  |       %14$.4f s     |    %15$.4f s    |      %16$.4f s     |"+  
         "\n------------------------------------------------------------------------- "+
         "\n|  Introsort  |       %17$.4f s     |    %18$.4f s    |      %19$.4f s     |"+  
         "\n------------------------------------------------------------------------- "),       
         ((int) a[0]),a[1],a[2],a[3],a[4],a[5],a[6],a[7],a[8],a[9],a[10],a[11],a[12],
         a[13],a[14],a[15],a[16],a[17],a[18]));
    }
   
    
    // Quicksort máximo ->  85.000.000 elementos.
    // Shellsort máximo ->  85.000.000 elementos.
    // Heapsort  máximo ->  85.000.000 elementos.
    // Mergesort máximo ->  85.000.000 elementos.
    // Radixsort máximo ->  70.000.000 elementos
    // Introsort máximo ->  73.000.000 elementos.
  
    public static void main(String[] args){
     
        
        Scanner sc = new Scanner(System.in);
        
        System.out.print("\nDigite o tamanho:  ");
        int size = sc.nextInt();
        
       
        int[] x = quase_random(size);
        int[] y = total_random(size);
        int[] z = decrescente(size);
        
        
        long quick_x = System.currentTimeMillis();
        quick_sorted(x.clone());                
        float Qx = (float) ((System.currentTimeMillis()-quick_x)/1000.0);
       
        long quick_y = System.currentTimeMillis();
        quick_sorted(y.clone()); 
        float Qy = (float) ((System.currentTimeMillis()-quick_y)/1000.0);
       
        long quick_z = System.currentTimeMillis();
        quick_sorted(z.clone());
        float Qz = (float) ((System.currentTimeMillis()-quick_z)/1000.0);
        System.out.print("\nQuick sorted: ok.\n");
      
        
        long shell_x = System.currentTimeMillis();
        shell_sorted(x.clone());
        float Sx = (float) ((System.currentTimeMillis()-shell_x)/1000.0); 
        
        long shell_y = System.currentTimeMillis();
        shell_sorted(y.clone());
        float Sy = (float) ((System.currentTimeMillis()-shell_y)/1000.0);
        
        long shell_z = System.currentTimeMillis();
        shell_sorted(z.clone());
        float Sz = (float) ((System.currentTimeMillis()-shell_z)/1000.0);
        System.out.print("Shell sorted: ok.\n");
       
       
        long heap_x = System.currentTimeMillis();
        heap_sorted(x.clone());
        float Hx = (float) ((System.currentTimeMillis()-heap_x)/1000.0);
      
        long heap_y = System.currentTimeMillis();
        heap_sorted(y.clone());
        float Hy = (float) ((System.currentTimeMillis()-heap_y)/1000.0);
    
        long heap_z = System.currentTimeMillis();
        heap_sorted(z.clone());
        float Hz = (float) ((System.currentTimeMillis()-heap_z)/1000.0);
        System.out.print("Heap sorted:  ok.\n");
        
        
        long merge_x = System.currentTimeMillis();
        merge_sorted(x.clone());
        float Mx = (float) ((System.currentTimeMillis()-merge_x)/1000.0);
       
        long merge_y = System.currentTimeMillis();
        merge_sorted(y.clone());
        float My = (float) ((System.currentTimeMillis()-merge_y)/1000.0);
       
        long merge_z = System.currentTimeMillis();
        merge_sorted(z.clone());
        float Mz = (float) ((System.currentTimeMillis()-merge_z)/1000.0);
        System.out.print("Merge sorted: ok.\n");
        

        long bucket_x = System.currentTimeMillis();
        radix_sorted(x.clone());
        float Bx = (float) ((System.currentTimeMillis()-bucket_x)/1000.0);
       
        long bucket_y = System.currentTimeMillis();
        radix_sorted(y.clone());
        float By = (float) ((System.currentTimeMillis()-bucket_y)/1000.0);
       
        long bucket_z = System.currentTimeMillis();
        radix_sorted(z.clone());
        float Bz = (float) ((System.currentTimeMillis()-bucket_z)/1000.0);
        System.out.print("Radix sorted: ok.\n");
        
 
        long intro_x = System.currentTimeMillis();
        intro_sorted(x.clone());
        float Ix = (float) ((System.currentTimeMillis()-intro_x)/1000.0);
      
        long intro_y = System.currentTimeMillis();
        intro_sorted(y.clone());
        float Iy = (float) ((System.currentTimeMillis()-intro_y)/1000.0);
       
        long intro_z = System.currentTimeMillis();
        intro_sorted(z.clone());
        float Iz = (float) ((System.currentTimeMillis()-intro_z)/1000.0);
        System.out.print("Intro sorted: ok.\n");
        
     
        float[] tb = { ((float) size), 
                       Qx, Qy, Qz, Sx, Sy, Sz,
                       Hx, Hy, Hz, Mx, My, Mz, 
                       Bx, By, Bz, Ix, Iy, Iz};
        
        System.out.println("\n"+tabela(tb)+"\n");
    } 
    
    
}
