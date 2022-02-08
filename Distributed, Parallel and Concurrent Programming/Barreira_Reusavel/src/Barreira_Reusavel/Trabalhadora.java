
package Barreira_Reusavel;

import static Barreira_Reusavel.Main.contador;
import static Barreira_Reusavel.Main.limite;
import static Barreira_Reusavel.Main.quantidadeDeNumeros;
import static Barreira_Reusavel.Main.filaDeArquivos;
import static Barreira_Reusavel.Main.numeroTrabalhadoras;
import static Barreira_Reusavel.Main.zeitWarten;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Trabalhadora extends Thread{
    
    public static int id;
    private static Random generator = new Random();
    public String nomeDoArquivo = "";
    public Semaphore mutex, mutexCritico, sinalizador, 
                                barreiraEntrada, barreiraSaida;
    
    public static int contadorDeArquivo = 1;
    
    public Trabalhadora(int id, String nome, Semaphore mutex,
            Semaphore mutexConflito, Semaphore sinalizador,
            Semaphore bEntrada, Semaphore bSaida){
        
        this.id = id;
        this.nomeDoArquivo = nome;
        this.mutex = mutex;
        this.mutexCritico = mutexConflito;
        this.sinalizador = sinalizador;
        this.barreiraEntrada = bEntrada;
        this.barreiraSaida = bSaida;
    
    }
    
    
    public void run(){
        
        while(true){
            
            try{
                
                int[] array = new int[quantidadeDeNumeros]; 
 
                for(int i = 0; i < array.length; i++){
                    array[i] = generator.nextInt(limite);
                }

                Arrays.sort(array);
                
                String path = "src\\Barreira_Reusavel\\"+nomeDoArquivo+"_"+
                                contadorDeArquivo+".txt";
                File criar = new File(path);

                FileWriter file = new FileWriter(path);
                
                for(int i = 0; i < array.length; i++){ 
                    file.write(array[i]+"\n");
                }

                file.close();
                
                System.out.println("Arquivo "+nomeDoArquivo+"_"+
                                     contadorDeArquivo+" foi salvo.\n");
                
                mutex.acquire();
                
                    contador += 1;
                    
                    if(contador == numeroTrabalhadoras){
                        barreiraSaida.acquire();
                        barreiraEntrada.release();
                    }
                
                mutex.release();
                
                barreiraEntrada.acquire();
                barreiraEntrada.release();
                
                mutexCritico.acquire();
                
                    filaDeArquivos.offer(path);
                
                mutexCritico.release();
                
                Thread.sleep(zeitWarten);
                
                System.out.println("Arquivo "+nomeDoArquivo+"_"+contadorDeArquivo+
                        " foi adicionado à lista queue.\n");
                
                sinalizador.release();
                
                mutex.acquire();
                    
                    contador -= 1;
                    
                    if(contador == 0){
                        barreiraEntrada.acquire();
                        barreiraSaida.release();
                    }
                
                mutex.release();
                
                barreiraSaida.acquire();
                barreiraSaida.release();
                
                Thread.sleep(zeitWarten);
                
            }
            catch(Exception e){ e.printStackTrace();}
        
        }
        
    }    
    
}
