
package Barreira_Reusavel;

import static Barreira_Reusavel.Main.filaDeArquivos;
import static Barreira_Reusavel.Main.quantidadeDeNumeros;
import static Barreira_Reusavel.Main.zeitWarten;
import static Barreira_Reusavel.Trabalhadora.contadorDeArquivo;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class Combinadora extends Thread{
    
    Semaphore thread1, thread2, thread3, thread4;
    
    private int contadorDeMergeFile = 1;
    
    public Combinadora(Semaphore T1, Semaphore T2, Semaphore T3, Semaphore T4){
        
        this.thread1 = T1;
        this.thread2 = T2;
        this.thread3 = T3;
        this.thread4 = T4;
        
    }
    
    
    public void run(){
        
        while(true){
        
            try{
                
                thread1.acquire();
                thread2.acquire(); // Acquire das quatro threads.
                thread3.acquire();
                thread4.acquire();
                
                contadorDeArquivo += 1;
                
                Thread.sleep(zeitWarten);
                
                // leitura(peek) e remoção(poll) da cabeça da lista queue.
                String text1 = filaDeArquivos.peek();
                filaDeArquivos.poll();
                System.out.println("Combinadora removeu o arquivo Thread1_"+
                                    (contadorDeArquivo-1)+".txt da lista.\n");
                
                String text2 = filaDeArquivos.peek();
                filaDeArquivos.poll();
                System.out.println("Combinadora removeu o arquivo Thread2_"+
                                    (contadorDeArquivo-1)+".txt da lista.\n");
                
                String text3 = filaDeArquivos.peek();
                filaDeArquivos.poll();
                System.out.println("Combinadora removeu o arquivo Thread3_"+
                                    (contadorDeArquivo-1)+".txt da lista.\n");
                
                String text4 = filaDeArquivos.peek();
                filaDeArquivos.poll();
                System.out.println("Combinadora removeu o arquivo Thread4_"+
                                    (contadorDeArquivo-1)+".txt da lista.\n");
                
                
                 
                // leitura de arquivo e construção de array.
                Scanner scText1 = new Scanner(new File(text1));
                int [] arrText1 = new int[quantidadeDeNumeros];
                int countT1 = 0;
                while(scText1.hasNextInt()){
                    arrText1[countT1++] = scText1.nextInt();
                }
                
                Scanner scText2 = new Scanner(new File(text2));
                int [] arrText2 = new int[quantidadeDeNumeros];
                int countT2 = 0;
                while(scText2.hasNextInt()){
                    arrText2[countT2++] = scText2.nextInt();
                }
                
                Scanner scText3 = new Scanner(new File(text3));
                int [] arrText3 = new int[quantidadeDeNumeros];
                int countT3 = 0;
                while(scText3.hasNextInt()){
                    arrText3[countT3++] = scText3.nextInt();
                }
                
                Scanner scText4 = new Scanner(new File(text4));
                int [] arrText4 = new int[quantidadeDeNumeros];
                int sountT4 = 0;
                while(scText4.hasNextInt()){
                    arrText4[sountT4++] = scText4.nextInt();
                }
                
              
                
                // Merge de todos os quatro arquivos.
                int[] aux = merge(merge(arrText1, arrText2),
                                        merge(arrText3, arrText4));
                
                // Retorna vetor com valores distintos.
                int[] textMerge = IntStream.of(aux).distinct().toArray();
                
                System.out.println("Combinadora fez merge e eliminou"
                                                 + " as repetições.\n");
                
                String path = "src\\Barreira_Reusavel\\MergeThreads_"+
                                (contadorDeMergeFile)+".txt";
                
                
                File criar = new File(path);

                FileWriter file = new FileWriter(path);
                
                for(int i = 0; i < textMerge.length; i++){
                    file.write(textMerge[i]+"\n");
                }

                file.close();
                
                System.out.println("Arquivo MergeThreads_"+contadorDeMergeFile+
                                                        ".txt foi salvo.\n");
                
                contadorDeMergeFile += 1;
                Thread.sleep(zeitWarten);
                
            }catch(Exception e){ e.printStackTrace(); }
        
        }
        
    }
    
    
    public int[] merge(int[] A, int[] B){
        int[] aux = new int[A.length+B.length];
        int i = 0, j = 0;
        
        while(i < A.length && j < B.length){
            if(A[i] < B[j]){ aux[i+j] = A[i++];}
            else{ aux[i+j] = B[j++];}
        }
        
        while(i < A.length){ aux[i+j] = A[i++];}
        
        while(j < B.length){ aux[i+j] = B[j++];}
        
        return aux;
        
    }
    
    
}
