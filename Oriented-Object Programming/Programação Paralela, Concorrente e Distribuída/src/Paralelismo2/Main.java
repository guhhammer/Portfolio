
package Paralelismo2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
 
    /*
    
        IMPORTANTE: ESTE CÓDIGO FOI TESTADO E CONSTRUÍDO COM BASE NO MEU
                    COMPUTADOR QUE POSSUI APENAS 4 PROCESSADORES.
    
    
                    Tomei a Liberdade de enviar este arquivo já com os
                    arquivos .TXT da minha última execução. A cada execução,
                    os Arquivos serão atualizados, não é necessário apagá-los.
                    
                    Vale ressaltar que como as dimensões das matrizes são 
                    pequenas o ganho de processamento em paralelo não é tão
                    expressivo quanto o sequencial na maioria das vezes.
                    Aumentar o boundary das Matrizes pode ajudar a melhorar 
                    este ganho.
    
    */
    
    
    static int NumeroDeExecucao = 0;
    static int NumeroDeArqSpeedUp = 0;

    public static long[] execute(int MatrizAI, int MatrizAJ, int MatrizBI,
                                int MatrizBJ, int boundaryA, int boundaryB)
                throws InterruptedException,FileNotFoundException,
                                            UnsupportedEncodingException{
        
        Matrizes a = new Matrizes(MatrizAI, MatrizAJ),
                 b = new Matrizes(MatrizBI, MatrizBJ),
                 c = new Matrizes(a.linhas,b.colunas),
                 aS = new Matrizes(MatrizAI, MatrizAJ),
                 bS = new Matrizes(MatrizBI, MatrizBJ),
                 cS = new Matrizes(aS.linhas,bS.colunas);
        
        a.setBoundary(boundaryA); b.setBoundary(boundaryB);
        aS.setBoundary(boundaryA); bS.setBoundary(boundaryB);
        a.preencherRandom(); b.preencherRandom();
        aS.preencherRandom(); bS.preencherRandom();
     
        NumeroDeExecucao++; String utf = "UTF-8";
        String nomeA = 
        "src\\Paralelismo2\\matrizAParelela_execução_"+NumeroDeExecucao+".txt";
        String nomeB = 
        "src\\Paralelismo2\\matrizBParelela_execução_"+NumeroDeExecucao+".txt";
        String nomeC = 
        "src\\Paralelismo2\\matrizCParelela_execução_"+NumeroDeExecucao+".txt";
        String nomeAS = 
        "src\\Paralelismo2\\matrizASequencial_execução_"+NumeroDeExecucao+".txt";
        String nomeBS = 
        "src\\Paralelismo2\\matrizBSequencial_execução_"+NumeroDeExecucao+".txt";
        String nomeCS = 
        "src\\Paralelismo2\\matrizCSequencial_execução_"+NumeroDeExecucao+".txt";
          
        PrintWriter MA = new PrintWriter(new File(nomeA), utf);
        a.setNome("Matriz a(Execução Paralela)");
        MA.println(""); MA.println(""); MA.println(a.getNome()); 
        MA.println(""); a.escreverMatriz(MA); MA.close();
        a.printMatriz(); 
        
        PrintWriter MAS = new PrintWriter(new File(nomeAS), utf);
        aS.setNome("Matriz a(Execução Sequencial)");
        MAS.println(""); MAS.println(""); MAS.println(aS.getNome());
        MAS.println(""); aS.escreverMatriz(MAS); MAS.close();
        aS.printMatriz();
        
        PrintWriter MB = new PrintWriter(new File(nomeB), utf);
        b.setNome("Matriz b(Execução Paralela)");
        MB.println(""); MB.println(""); MB.println(b.getNome());
        MB.println(""); b.escreverMatriz(MB); MB.close(); 
        b.printMatriz();
        
        PrintWriter MBS = new PrintWriter(new File(nomeBS), utf);
        bS.setNome("Matriz b(Execução Sequencial)");
        MBS.println(""); MBS.println(""); MBS.println(bS.getNome()); 
        MBS.println(""); bS.escreverMatriz(MBS); MBS.close();
        bS.printMatriz();     
        
        int NP = Runtime.getRuntime().availableProcessors();
        Semaphore concluidoP1 = new Semaphore(0), concluidoP2 = new Semaphore(0),
                  concluidoP3 = new Semaphore(0), concluidoP4 = new Semaphore(0);
        
        int totalElementos = c.linhas*c.colunas;
        ArrayList<int[]> elementos = new ArrayList<>();
        for(int i = 0; i < c.linhas; i++){
            for(int j = 0; j < c.colunas; j++){elementos.add(new int[]{i, j});}
        }
        
        int contador = totalElementos, k, aux[] = new int[NP];
        boolean flag = true;
        while(flag){
            for(k = 0; k < NP; k++){
                if(contador == 0){ flag = false; break;} aux[k]++; contador--;
            }
            if(contador == 0){ flag = false;}           
        }
        
        Queue<int[]> p1_ = new LinkedList<>(), p2_ = new LinkedList<>(),
                     p3_ = new LinkedList<>(), p4_ = new LinkedList<>();
        
        int param = 0, i;
        for(i = 0; i < aux[0]; i++){ p1_.offer(elementos.get(i));}
        param += aux[0];
        for(i = param; i < param+aux[1]; i++){ p2_.offer(elementos.get(i));}
        param += aux[1];
        for(i = param; i < param+aux[2]; i++){ p3_.offer(elementos.get(i));}
        param += aux[2];
        for(i = param; i < param+aux[3]; i++){ p4_.offer(elementos.get(i));}   
        elementos.clear();
     
        Multiplicar p1 = new Multiplicar(a, b, c, p1_, concluidoP1);     
        Multiplicar p2 = new Multiplicar(a, b, c, p2_, concluidoP2);
        Multiplicar p3 = new Multiplicar(a, b, c, p3_, concluidoP3);
        Multiplicar p4 = new Multiplicar(a, b, c, p4_, concluidoP4);
        
        p1.start(); p2.start(); p3.start(); p4.start();
        concluidoP1.acquire(); concluidoP2.acquire(); 
        concluidoP3.acquire(); concluidoP4.acquire();
        
        long tempoPFIM = p1.tempoExecucao+p2.tempoExecucao
                        +p3.tempoExecucao+p4.tempoExecucao;
        
        PrintWriter MC = new PrintWriter(new File(nomeC), utf);
        c.setNome("Matriz c(Execução Paralela)");
        MC.println(""); MC.println(""); MC.println(c.getNome());
        MC.println(""); c.escreverMatriz(MC); MC.close();
        c.printMatriz();
        
        long tempoS = System.currentTimeMillis();
        cS = cS.multiplicar(aS, bS);
        long tempoSFIM = System.currentTimeMillis() - tempoS;
        
        PrintWriter MCS = new PrintWriter(new File(nomeCS), utf);
        cS.setNome("Matriz c(Execução Sequencial)");
        MCS.println(""); MCS.println(""); MCS.println(cS.getNome());
        MCS.println(""); cS.escreverMatriz(MCS); MCS.close();
        cS.printMatriz();
       
        return new long[]{tempoPFIM, tempoSFIM};
    
    }
    
    
    
    
    /*
        Esta função é para calcular os valores estabelecidos no exercício.
        Os valores da tabela logo após o exercício 5.
        
    */
    public static void valoresDefinidos() throws InterruptedException,
                                                 FileNotFoundException,
                                                 UnsupportedEncodingException{
        
        long[] value1 = execute(100, 100, 100, 100, 1000, 1000);
        long[] value2 = execute(200, 200, 200, 200, 1000, 1000);
        long[] value3 = execute(100, 200, 200, 100, 1000, 1000);
        long[] value4 = execute(200, 400, 400, 200, 1000, 1000);
        
        int N = Runtime.getRuntime().availableProcessors();
        
        String inf0 = "Boundary de A e B significa o limite Random().nextInt(Boundary).";
        String info = "Nº Proc., (m), (k), (n), Boundary de A e B, Tempo Paralelo(Matriz C), "
                    + "Tempo Sequencial(Matriz C), Speed Up";
        
        String s = ",  ";
               
        /* n. processador, Amk, Bkn, Cmn, Boundary de A e B, SpeedUp*/
        
        String value1S = N+s+100+s+100+s+100+s+1000+s+value1[0]
                             +s+value1[1]+s+(value1[1]*1.0/value1[0]);
        
        String value2S = N+s+200+s+200+s+200+s+1000+s+value2[0]
                             +s+value2[1]+s+(value2[1]*1.0/value2[0]);

        String value3S = N+s+100+s+100+s+100+s+1000+s+value3[0]
                             +s+value3[1]+s+(value3[1]*1.0/value3[0]);
                
        String value4S = N+s+200+s+200+s+200+s+1000+s+value4[0]
                             +s+value4[1]+s+(value4[1]*1.0/value4[0]);        
          
        System.out.println("\n\n"+inf0+"\n\n"+info+"\n\n");
        System.out.println(value1S+"\n"+value2S+"\n"+value3S+"\n"+value4S+"\n");
    
        NumeroDeArqSpeedUp++; String utf = "UTF-8";
        String nomeSpeedUp = 
        "src\\Paralelismo2\\RESULTADOSPEEDUP_"+NumeroDeArqSpeedUp+".txt";
        PrintWriter nsp = new PrintWriter(new File(nomeSpeedUp), utf);
        nsp.println(""); nsp.println("");
        nsp.println("Resultado do teste de Speed Up:");
        nsp.println(""); nsp.println(inf0); nsp.println(info);
        nsp.println(value1S); nsp.println(value2S); nsp.println(value3S);
        nsp.println(value4S); nsp.println(""); nsp.println(""); nsp.close();
        
    }
    
    
    public static void continuaGerandoMatrizesAleatorias(boolean se) 
                                       throws InterruptedException,
                                              FileNotFoundException, 
                                              UnsupportedEncodingException{
        if(se){
            Random ck = new Random();
            while(true){

                execute(ck.nextInt(200), 100, 100, ck.nextInt(200), 
                                    ck.nextInt(5000), ck.nextInt(5000));

                Thread.sleep(10000);

                System.out.println("\n\n\n\n");
            }
        }
        
    }
    
    
    public static void main(String[] args) throws InterruptedException, 
                                                  FileNotFoundException,
                                                  UnsupportedEncodingException {
        
        /*
    
        IMPORTANTE: ESTE CÓDIGO FOI TESTADO E CONSTRUÍDO COM BASE NO MEU
                    COMPUTADOR QUE POSSUI APENAS 4 PROCESSADORES.
        
                    Tomei a Liberdade de enviar este arquivo já com os
                    arquivos .TXT da minha última execução. A cada execução,
                    os Arquivos serão atualizados, não é necessário apagá-los.
                    
                    Vale ressaltar que como as dimensões das matrizes são 
                    pequenas o ganho de processamento em paralelo não é tão
                    expressivo quanto o sequencial na maioria das vezes.
                    Aumentar o boundary das Matrizes pode ajudar a melhorar 
                    este ganho.
                    
        */
        
        
        
        valoresDefinidos();
        
        Thread.sleep(10000);
      
        // true -> gera matrizes para sempre
        continuaGerandoMatrizesAleatorias(false); 
        
        
        
                  
    }
    
}