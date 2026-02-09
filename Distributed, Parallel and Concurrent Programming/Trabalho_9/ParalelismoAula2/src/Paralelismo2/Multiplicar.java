
package Paralelismo2;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Multiplicar extends Thread{
    
    public ArrayList<int[]> elementos;
    public Matrizes MC, MA, MB;
    public Semaphore concluido;
    
    public Multiplicar(Matrizes a, Matrizes b, Matrizes c,
                       ArrayList<int[]> elem, Semaphore concluido){
        this.MA = a;
        this.MB = b;
        this.MC = c;
        this.elementos = elem;
        this.concluido = concluido;
    }
    
    
    private Matrizes multiplicar(){
        
        if(MA.colunas == MB.linhas){
            for(int[] elem : elementos){
                int i = elem[0], j = elem[1];
                MC.matriz[i][j] = 0;
                for(int k = 0; k < MA.colunas; k++){
                    for(int l = 0; l < MB.linhas; l++){
                        if(k == l){
                            MC.matriz[i][j] += MA.matriz[i][k]*MB.matriz[l][j];
                        }
                    }
                }
                
                
            }
                    
            return MC;
            
        }
        else{
        
            return null;
            
        }
        
    }
    
    public void run(){
        
    
        multiplicar();
    
        concluido.release();
    
    }
    
    
}
