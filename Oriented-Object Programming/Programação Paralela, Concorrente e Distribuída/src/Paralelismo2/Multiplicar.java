
package Paralelismo2;

import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Multiplicar extends Thread{
    
    public Queue<int[]> elementos;
    public Matrizes MC, MA, MB;
    public Semaphore concluido;
    public long tempoExecucao = 0;
    
    public Multiplicar(Matrizes a, Matrizes b, Matrizes c,
                       Queue<int[]> elem, Semaphore concluido){
    
        this.MA = a;
        this.MB = b;
        this.MC = c;
        this.elementos = elem;
        this.concluido = concluido;
    
    }
    
    private long multiplicar(){
        
        int x = 0, y = 0;
        
        long tempoExec = 0, tempoAux = 0;
        
        if(MA.colunas == MB.linhas){
            while(!elementos.isEmpty()){
                x = elementos.peek()[0]; y = elementos.peek()[1];
               
                tempoAux = System.currentTimeMillis();
                for(int k = 0; k < MA.colunas; k++){
                    for(int l = 0; l < MB.colunas; l++){
                        if(k == l){
                           MC.matriz[x][y] += MA.matriz[x][k]*MB.matriz[l][y];
                        }
                    }
                }
                tempoExec += System.currentTimeMillis()-tempoAux;
                
                elementos.poll();
            }
                    
        }
        
        return tempoExec;
        
    }
    
    public void run(){
        
    
        tempoExecucao = multiplicar();
    
        concluido.release();
    
    }
    
    
}
