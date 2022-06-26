package ParalelismoAula1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Iniciador {
    
    public static long execute_sequencial(long tamSequencia) throws InterruptedException {
        Calculo c = new Calculo(tamSequencia);
        long T_inicio = System.currentTimeMillis();
        c.execute();
        long T_fim = System.currentTimeMillis();
        long T_exec = T_fim - T_inicio;
        return T_exec;
    }

    public static long execute_paralelo(long tamSequencia, int numTarefas) throws InterruptedException {
        long tamSequenciaPorTarefa = tamSequencia / numTarefas;
        Semaphore conclusao = new Semaphore(1 - numTarefas);
        Tarefa[] tarefa = new Tarefa[numTarefas];
        for (int i = 0; i < numTarefas; i++) {
            tarefa[i] = new Tarefa(i, tamSequenciaPorTarefa, conclusao);
        }

		//ExecutorService exec = Executors.newFixedThreadPool(numTarefas);
        long T_inicio = System.currentTimeMillis();
        for (int i = 0; i < numTarefas; i++) //exec.execute(tarefa[i]);
        {
            tarefa[i].start();
        }
        conclusao.acquire();
        long T_fim = System.currentTimeMillis();
        //exec.shutdown();
        long T_exec = T_fim - T_inicio;
        return T_exec;
    }

    public static ArrayList<String[]> graficoA(int vezes, int min, int max, int Ntarefa) throws InterruptedException{
        
        final int tarefasPorProcessador = Ntarefa;
        final int totalProcessadores = Runtime.getRuntime().availableProcessors();
        final int numTarefas = (tarefasPorProcessador) * totalProcessadores;
        
        ArrayList<String[]> ret = new ArrayList<>();
        int indice = 0;
        
        String aux = String.format("\n\nTotal de Processadores: %d"+
                                   "\nNúmero de Tarefas: %d"+
                                   "\nTarefas por processadores: %d\n\n",
                                   totalProcessadores,
                                   numTarefas,
                                   tarefasPorProcessador);
        
        System.out.println("\n\nGráfico A: \n"+aux);
        
        String[] send_ = new String[2];
        send_[0] = "\"N(2^tamanho)\""; 
        send_[1] = "\"Tempo\"";
        
        ret.add(indice, send_);
        
        String SEP = ", ";
        System.out.println("N" + SEP + "Tamanho da Sequência" +SEP+ "Tempo Paralelo" );
        for (int N = min; N <= max; N++) {
            
            long tamSequencia = 0;
            double T_exec_paralelo = 0;
            for(int i = 0; i < vezes; i++){
            
                long x =  (long) Math.pow(2,N);
                tamSequencia += x/vezes;
                T_exec_paralelo += execute_paralelo(x, numTarefas);
                
            }
            
            String[] send = new String[2];
            send[0] = N+""; 
            send[1] = (T_exec_paralelo/vezes)+"";
            
            ret.add(++indice,send);
            
            System.out.println(N + SEP + tamSequencia +
                                    SEP + T_exec_paralelo/vezes);
            
            

        }
        
        return ret;
        
    }

    public static ArrayList<String[]> graficoB(int vezes, int max, int Ntarefa) throws InterruptedException{
        
        final int tarefasPorProcessador = Ntarefa;
        final int totalProcessadores = Runtime.getRuntime().availableProcessors();
        final int numTarefas = (tarefasPorProcessador) * totalProcessadores;
        
        ArrayList<String[]> ret = new ArrayList<>();
        int indice = 0;
        
        String aux = String.format("\n\nTotal de Processadores: %d"+
                                   "\nNúmero de Tarefas: %d"+
                                   "\nTarefas por processadores: %d\n\n",
                                   totalProcessadores,
                                   numTarefas,
                                   tarefasPorProcessador);
        
        System.out.println("\n\nGráfico B: \n"+aux);
        
        String[] send_ = new String[3];
        send_[0] = "\"N(2^tamanho máximo)\"";
        send_[1] = "\"N. Processadores\"";
        send_[2] = "\"Tempo\"";
       
        ret.add(indice, send_);
        
        String SEP = ", ";
        System.out.println("N" + SEP +"Processador"+SEP + "Tamanho da Sequência" +SEP+ "Tempo Paralelo" );
        
     
        for(int processador = 1; processador < totalProcessadores+1; processador++){
           
                long tamSequencia = 0;
                double T_exec_paralelo = 0;
                for(int i = 0; i < vezes; i++){

                    long x =  (long) Math.pow(2,max);
                    tamSequencia += x/vezes;
                    T_exec_paralelo += execute_paralelo(x, 
                                        (tarefasPorProcessador) * processador);
                }
                String[] send = new String[3];
                send[0] = max+"";
                send[1] = processador+"";
                send[2] = (T_exec_paralelo/vezes)+"";
                
                ret.add(++indice, send);
                
                System.out.println(max + SEP + processador + SEP + tamSequencia + SEP + T_exec_paralelo/vezes);


        }
        
        return ret;
        
    }
    
    public static ArrayList<String[]> graficoC(int vezes, int max, int Ntarefa) throws InterruptedException{
        
        final int tarefasPorProcessador = Ntarefa;
        final int totalProcessadores = Runtime.getRuntime().availableProcessors();
        final int numTarefas = (tarefasPorProcessador) * totalProcessadores;
        
        ArrayList<String[]> ret = new ArrayList<>();
        int indice = 0;
        
        String aux = String.format("\n\nTotal de Processadores: %d"+
                                   "\nNúmero de Tarefas: %d"+
                                   "\nTarefas por processadores: %d\n\n",
                                   totalProcessadores,
                                   numTarefas,
                                   tarefasPorProcessador);
        
        System.out.println("\n\nGráfico C: \n"+aux);
        
        String[] send_ = new String[3];
        send_[0] = "\"N(2^tamanho máximo)\""; 
        send_[1] = "\"N. Processadores\"";
        send_[2] = "\"Speed UP\"";
        ret.add(indice, send_);
              
        String SEP = ", ";
        System.out.println("N(valor máximo)" + SEP + "processador" +SEP+ "Speed UP"+
                           "\n"+max+SEP+"1"+SEP+"1");
        
        String[] send__ = new String[3];
        send__[0] = max+""; 
        send__[1] = 1+"";
        send__[2] = 1+"";
        
        ret.add(++indice,send__);
        
        for(int processador = 2; processador < totalProcessadores+1; processador++){
           
                long tamSequencia = 0;
                double T_exec_paralelo = 0, T_exec_sequencial = 0;
                for(int i = 0; i < vezes; i++){

                    long x =  (long) Math.pow(2,max);
                    tamSequencia += x/vezes;
                    T_exec_sequencial += execute_sequencial(tamSequencia);
                    T_exec_paralelo += execute_paralelo(x, 
                                        (tarefasPorProcessador) * processador);
                    
                }
                
                double speedUp = (T_exec_sequencial/vezes)/(T_exec_paralelo/vezes);  
                
                String[] send = new String[3];
                send[0] = max+"";
                send[1] = processador+"";
                send[2] = speedUp+"";
                
                ret.add(++indice, send);
                
                System.out.println(max+SEP+processador+SEP+speedUp);
        
        }
        
        return ret;
        
    }
     
    public static ArrayList<String[]> graficoD(int vezes, int max, int Ntarefa) throws InterruptedException{
        
        final int tarefasPorProcessador = Ntarefa;
        final int totalProcessadores = Runtime.getRuntime().availableProcessors();
        final int numTarefas = (tarefasPorProcessador) * totalProcessadores;
        
        ArrayList<String[]> ret = new ArrayList<>();
        int indice = 0;
        
        String aux = String.format("\n\nTotal de Processadores: %d"+
                                   "\nNúmero de Tarefas: %d"+
                                   "\nTarefas por processadores: %d\n\n",
                                   totalProcessadores,
                                   numTarefas,
                                   tarefasPorProcessador);
        
        String[] send_ = new String[3];
        send_[0] = "\"N(2^tamanho máximo)\""; 
        send_[1] = "\"N. Processadores\"";
        send_[2] = "\"Eficiencia\"";
        ret.add(send_);
                     
        System.out.println("\n\nGráfico D:"+aux);
        String SEP = ", ";
        System.out.println("N" + SEP + "processador" +SEP+ "Eficiência"+
                           "\n"+max+SEP+"1"+SEP+"1" );
        
        String[] send__ = new String[3];
        send__[0] = max+""; 
        send__[1] = 1+"";
        send__[2] = 1+"";
        
        ret.add(++indice, send__);
        
        for(int processador = 2; processador < totalProcessadores+1; processador++){
           
                long tamSequencia = 0;
                double T_exec_paralelo = 0, T_exec_sequencial = 0;
                for(int i = 0; i < vezes; i++){

                    long x =  (long) Math.pow(2,max);
                    tamSequencia += x/vezes;
                    T_exec_sequencial += execute_sequencial(tamSequencia);
                    T_exec_paralelo += execute_paralelo(x, 
                                        (tarefasPorProcessador) * processador);
                    
                }
                
                double speedUp = (T_exec_sequencial/vezes)/(T_exec_paralelo/vezes);
                double eficiencia = speedUp / processador;
                
                String[] send = new String[3];
                send[0] = max+"";
                send[1] = processador+"";
                send[2] = eficiencia+"";
                
                ret.add(++indice, send);
                
                System.out.println(max+SEP+processador+SEP+eficiencia);
                
        }
        
        return ret;

    }
    
    public static String caminho = new  File("Iniciador.java")
                              .getAbsolutePath().replace("Iniciador.java", "");
    
    public static String nomeArqExcel = "Paralelismo.xlsx";
    public static String nomeArqPython = "pythonCodeCreateXLSX.py";
    
    public static Semaphore criar = new Semaphore(0),
                            executar = new Semaphore(0);
    
    
    public static int vezes = 50, min = 15, max = 18, Ntarefa = 1;
    
    
    
    public static void main(String[] args) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException, IOException {

        final int tarefasPorProcessador = 1;
        final int totalProcessadores = Runtime.getRuntime().availableProcessors();
        final int numTarefas = (tarefasPorProcessador) * totalProcessadores;
        
        System.out.println(caminho);
        
        String thispath = caminho+"src\\ParalelismoAula1\\"+nomeArqExcel;
        File xlsx = new File(thispath);
        FileWriter fileXLSX = new FileWriter(thispath);
        fileXLSX.close();
        System.out.println(xlsx.isFile());
        thispath = caminho+"src\\ParalelismoAula1\\"+nomeArqPython;
        File py = new File(thispath);
        FileWriter filePY = new FileWriter(thispath);
        filePY.close();
        System.out.println(py.isFile());
        
        
        ArrayList<String[]> a1 = graficoA(vezes, min, max, Ntarefa);
        ArrayList<String[]> b1 = graficoB(vezes, max, Ntarefa);
        ArrayList<String[]> c1 = graficoC(vezes, max,Ntarefa);
        ArrayList<String[]> d1 = graficoD(vezes, max, Ntarefa);
       
        Ntarefa = 2;
        ArrayList<String[]> a2 = graficoA(vezes, min, max, Ntarefa);
        ArrayList<String[]> b2 = graficoB(vezes, max, Ntarefa);
        ArrayList<String[]> c2 = graficoC(vezes, max,Ntarefa);
        ArrayList<String[]> d2 = graficoD(vezes, max, Ntarefa);
    
        CriarArquivoPython.escreverPythonCode(
                nomeArqExcel, a1,b1,c1,d1,a2,b2,c2,d2);
        
        
        /*
        
            Você deve executar o comando:
                - python -m pip install xlsxwriter
            no seu prompt se deseja executar este arquivo.py
        
        */
        
        ExecutePython.main(args);
        
        AbrirPlanilha.main(args);
        
    }
    
    
}

