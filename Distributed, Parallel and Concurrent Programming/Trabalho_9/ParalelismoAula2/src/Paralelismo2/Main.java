
package Paralelismo2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
 
    static int NumeroDeExecucao = 0;
    static int NumeroDeArqSpeedUp = 0;

    public static long[] execute(int MatrizA_Indice_I, int MatrizA_Indice_J,
                                 int MatrizB_Indice_I, int MatrizB_Indice_J,
                                 int boundary_MatrizA, int boundary_MatrizB)
                                       throws InterruptedException,
                                              FileNotFoundException,
                                              UnsupportedEncodingException{
        
        Matrizes a = new Matrizes(MatrizA_Indice_I, MatrizA_Indice_J),
                 b = new Matrizes(MatrizB_Indice_I, MatrizB_Indice_J),
                 c = new Matrizes(a.linhas,b.colunas),
                 aS = new Matrizes(MatrizA_Indice_I, MatrizA_Indice_J),
                 bS = new Matrizes(MatrizB_Indice_I, MatrizB_Indice_J),
                 cS = new Matrizes(a.linhas,b.colunas);
        
        NumeroDeExecucao++;
        String utf = "UTF-8";
        
        String nomeA = "src\\Paralelismo2\\matrizAParelela_execução_"+
                                                       NumeroDeExecucao+".txt";
        String nomeB = "src\\Paralelismo2\\matrizBParelela_execução_"+
                                                       NumeroDeExecucao+".txt";
        String nomeC = "src\\Paralelismo2\\matrizCParelela_execução_"+
                                                       NumeroDeExecucao+".txt";
        
        String nomeAS = "src\\Paralelismo2\\matrizASequencial_execução_"+
                                                        NumeroDeExecucao+".txt";
        String nomeBS = "src\\Paralelismo2\\matrizBSequencial_execução_"+
                                                        NumeroDeExecucao+".txt";
        String nomeCS = "src\\Paralelismo2\\matrizCSequencial_execução_"+
                                                        NumeroDeExecucao+".txt";
        
        File ma = new File(nomeA);
        File mb = new File(nomeB);
        File mc = new File(nomeC);
        
        File maS = new File(nomeAS);
        File mbS = new File(nomeBS);
        File mcS = new File(nomeCS);

        PrintWriter MA = new PrintWriter(nomeA, utf),
                    MB = new PrintWriter(nomeB, utf),
                    MC = new PrintWriter(nomeC, utf),
                    MAS = new PrintWriter(nomeAS, utf),
                    MBS = new PrintWriter(nomeBS, utf),
                    MCS = new PrintWriter(nomeCS, utf);
                
        a.setNome("Matriz a(Execução Paralela)");
        b.setNome("Matriz b(Execução Paralela)");
        c.setNome("Matriz c(Execução Paralela)");
        aS.setNome("Matriz a(Execução Sequencial)");
        bS.setNome("Matriz b(Execução Sequencial)");
        cS.setNome("Matriz c(Execução Sequencial)");
        
        MA.println("");
        MA.println("");
        MA.println(a.getNome());
        MAS.println("");
        MAS.println("");
        MAS.println(aS.getNome());
        
        MB.println("");
        MB.println("");
        MB.println(b.getNome());
        MBS.println("");
        MBS.println("");
        MBS.println(bS.getNome());
        
        MC.println("");
        MC.println("");
        MC.println(c.getNome());
        MCS.println("");
        MCS.println("");
        MCS.println(cS.getNome());
        
        
        a.setBoundary(boundary_MatrizA); b.setBoundary(boundary_MatrizB);
        aS.setBoundary(boundary_MatrizA); bS.setBoundary(boundary_MatrizB);
        
        a.preencherRandom(); b.preencherRandom();
        aS.preencherRandom(); bS.preencherRandom();
        
        MA.println("");
        MA.println(a.escreverMatriz());
        MAS.println("");
        MAS.println(aS.escreverMatriz());
        
        MB.println("");
        MB.println(b.escreverMatriz());
        MBS.println("");
        MBS.println(bS.escreverMatriz());
        
        a.printMatriz(); 
        aS.printMatriz();
        b.printMatriz();
        bS.printMatriz();
        
        MA.close(); MAS.close();
        MB.close(); MBS.close();
        
        
        long tempoP = System.currentTimeMillis();
        
        int NP = Runtime.getRuntime().availableProcessors();
        Semaphore concluido = new Semaphore(-NP+1);
        
        int totalElementos = c.linhas*c.colunas;
        
        ArrayList<int[]> elementos = new ArrayList<>();
        
        for(int i = 0; i < c.linhas; i++){
            for(int j = 0; j < c.colunas; j++){
                elementos.add(new int[]{i, j});
            }
        }
        
        int contador = totalElementos, k, aux[] = new int[NP];
        boolean flag = true;
        while(flag){
            for(k = 0; k < NP; k++){
                if(contador == 0){ flag = false; break;} aux[k]++; contador--;
            }
            if(contador == 0){ flag = false;}           
        }
        
        ArrayList<int[]> p1_ = new ArrayList<>(), p2_ = new ArrayList<>(),
                         p3_ = new ArrayList<>(), p4_ = new ArrayList<>();
        
        int param = 0, i = 0;
        for(i = 0; i < aux[0]; i++){ p1_.add(elementos.get(i));}
        
        param += aux[0];
        for(i = param; i < param+aux[1]; i++){ p2_.add(elementos.get(i));}
        
        param += aux[1];
        for(i = param; i < param+aux[2]; i++){ p3_.add(elementos.get(i));}
        
        param += aux[2];
        for(i = param; i < param+aux[3]; i++){ p4_.add(elementos.get(i));}
        
        Multiplicar p1 = new Multiplicar(a, b, c, p1_, concluido);     
        Multiplicar p2 = new Multiplicar(a, b, c, p2_, concluido);
        Multiplicar p3 = new Multiplicar(a, b, c, p3_, concluido);
        Multiplicar p4 = new Multiplicar(a, b, c, p4_, concluido);
        
        
        p1.start(); p2.start(); p3.start(); p4.start(); concluido.acquire(); 
        
        tempoP = System.currentTimeMillis() - tempoP;
        
                
        MC.println("");
        MC.println(c.escreverMatriz());
        
        c.printMatriz();
        
        long tempoS = System.currentTimeMillis();
        
        cS = Matrizes.multiplicar(aS, bS);
        
        tempoS = System.currentTimeMillis() - tempoS;
        
        MCS.println("");
        MCS.println(cS.escreverMatriz());
        
        MC.close(); MCS.close();
        
        cS.printMatriz();
        
        
        return new long[]{tempoP, tempoS};
    
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
        String info = "Nº Processadores, A(dimensão m), A(dimensão k), "
                    + "B(dimensão k), B(dimensão n), C(dimensão m), "
                    + "C(dimensão n), Boundary de A e B, Tempo Paralelo(Matriz C), "
                    + "Tempo Sequencial(Matriz C), Speed Up";
        
        String sep = ",  ";
        
        String value1S = N+sep+100+sep+100+sep+100+sep+100+
                             sep+100+sep+100+sep+1000+sep+value1[0]
                             +sep+value1[1]+sep+(value1[1]*1.0/value1[0]);
        
        String value2S = N+sep+200+sep+200+sep+200+sep+200+
                             sep+200+sep+200+sep+1000+sep+value2[0]
                             +sep+value2[1]+sep+(value2[1]*1.0/value2[0]);

        String value3S = N+sep+100+sep+200+sep+200+sep+100+
                             sep+100+sep+100+sep+1000+sep+value3[0]
                             +sep+value3[1]+sep+(value3[1]*1.0/value3[0]);
                
        String value4S = N+sep+200+sep+400+sep+400+sep+200+
                             sep+200+sep+200+sep+1000+sep+value4[0]
                             +sep+value4[1]+sep+(value4[1]*1.0/value4[0]);        
                
        System.out.println("\n\n"+inf0+"\n\n"+info+"\n\n");
        System.out.println(value1S+"\n");
        System.out.println(value2S+"\n");
        System.out.println(value3S+"\n");
        System.out.println(value4S+"\n");
    
        NumeroDeArqSpeedUp++;
        String utf = "UTF-8";
        
        String nomeSpeedUp = "src\\Paralelismo2\\RESULTADOSPEEDUP_"+
                                                      NumeroDeArqSpeedUp+".txt";
        
        File NSP = new File(nomeSpeedUp);

        PrintWriter nsp = new PrintWriter(nomeSpeedUp, utf);
        
        nsp.println("");
        nsp.println("");
        nsp.println("Resultado do teste de Speed Up:");
        nsp.println("");
        nsp.println(inf0);
        nsp.println(info);
        nsp.println(value1S);
        nsp.println(value2S);
        nsp.println(value3S);
        nsp.println(value4S);
        nsp.println("");
        nsp.println("");
        nsp.close();
        
    }
    /* n. processador, Amk, Bkn, Cmn, Boundary de A e B, SpeedUp*/
    
    public static void continuaGerandoMatrizesAleatorias() 
                                       throws InterruptedException,
                                              FileNotFoundException, 
                                              UnsupportedEncodingException{
        
        Random ck = new Random();
        while(true){
            
            execute(ck.nextInt(200), 100, 100, ck.nextInt(200), 
                                ck.nextInt(5000), ck.nextInt(5000));
        
            Thread.sleep(10000);
            
            System.out.println("\n\n\n\n");
        }
        
    }
    
    public static void main(String[] args) throws InterruptedException, 
                                                  FileNotFoundException,
                                                  UnsupportedEncodingException {
        
        valoresDefinidos();
        
        Thread.sleep(10000);
        
        continuaGerandoMatrizesAleatorias();
                  
    }
    
}