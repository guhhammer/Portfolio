package ParalelismoRecursivo;

import static ParalelismoRecursivo.MergeSortSequencial.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main { 
    
    
    /*
        
            Necessário baixar a biblioteca xlsxwriter!!
        
            Use o seguinte comando para baixá-la:
                
                python -m pip install xlsxwriter
       
    */
    
    // multiplier aumenta o boundary do random().nextInt(boundary).
    public static int[] criarArrayRandom(int n, int multiplier) {
        int[] aux = new int[n];
        for (int i = 0; i < aux.length; i++) {
            aux[i] = new Random().nextInt(n * multiplier);
        }
        return aux;
    }

    public static ArrayList<double[]> testar50vezes(int leftLimit, int rightLimit)
            throws InterruptedException {

        ArrayList<double[]> aux = new ArrayList<>();

        String info = "N, tempo sequencial, tempo paralelo, Speed Up:\n\n";

        System.out.println(info);
        for (int i = leftLimit; i < rightLimit + 1; i++) {

            int[] x = criarArrayRandom((int) Math.pow(2, i), 10);

            long tempoSequencialTotal = 0, tempoParaleloTotal = 0;
            for (int j = 0; j < 50; j++) {
                long tempoSequencial = System.currentTimeMillis();
                merge_sorted_sequencial(x.clone());
                tempoSequencialTotal += System.currentTimeMillis() - tempoSequencial;
              
                Semaphore s = new Semaphore(0);
                MergeSortParalelo mp = new MergeSortParalelo(x.clone(),
                        0, x.length - 1, s, 0);
                long tempoParalelo = System.currentTimeMillis();
                mp.start();
                s.acquire();
                tempoParaleloTotal += System.currentTimeMillis() - tempoParalelo;
            }
            double  elem1 =  (tempoSequencialTotal / 50.0),
                    elem2 =  (tempoParaleloTotal / 50.0);

            aux.add(new double[]{i, elem1, elem2});

            System.out.println(i +", " + elem1 + ", " + elem2 + ", "+elem1/elem2+"\n");

        }

        return aux;

    }

    public static Semaphore warten = new Semaphore(1),
                            mainRelease = new Semaphore(1),
                            criar = new Semaphore(0),
                            executar = new Semaphore(0),
                            abrir = new Semaphore(0);

    
    public static String caminhoMain = new File("Main.java")
            .getAbsolutePath().replace("Main.java", "");

    public static String nomeArqExcel = "ParalelismoRecursivo.xlsx";
    public static String nomeArqPython = "pythonCodeCreateXlsx.py";
    public static ArrayList values = new ArrayList<>();
    
    public static final int leftLimit = 15;
    public static final int rightLimit = 26;
    
    public static boolean choice = true;
    public static boolean choice2 = true;
    
    public static String caminhoPython = "";
    
    
    public static void main(String[] args) 
            throws InterruptedException, IOException {

        /*
        
            Necessário baixar a biblioteca xlsxwriter!!
        
            Use o seguinte comando para baixá-la:
                
                python -m pip install xlsxwriter
       
        */
        /*
        
            Para que o arquivo .XLSX seja escrito, 
            é necessário fechá-lo.
            
            Atribua o valor true à variável choice, logo abaixo,
            para fechar todas as abas do excel abertas no seu computador.
            
            Por default, o valor será true. Caso não queira fechar suas 
            planilhas, mude o valor para falso, mas não se esqueça de 
            fechar o arquivo: ParalelismoRecursivo.xlxs se quiser executar
            este código .java.
        
        */
        choice = true;
        FecharExcel.main(args);
        warten.acquire();
        
        
        String thispath = caminhoMain + "src\\ParalelismoRecursivo\\" + nomeArqExcel;
        File xlsx = new File(thispath);
        FileWriter fileXLSX = new FileWriter(thispath);
        fileXLSX.close();
        System.out.println("\n\nArquivo Excel Criado?   "+
                        ((xlsx.isFile() == true) ? "Sim!" : "Não!"));
        caminhoPython = caminhoMain + "src\\ParalelismoRecursivo\\" + nomeArqPython;
        File py = new File(caminhoPython);
        FileWriter filePY = new FileWriter(caminhoPython);
        filePY.close();
        System.out.println("Arquivo Python Criado?   "+
                        ((py.isFile() == true) ? "Sim!\n\n" : "Não!\n\n"));
        
        System.out.println("Acquiring-Values-for-Graphic Phase: \n\n");
        
        values = testar50vezes(leftLimit, rightLimit);
       
        System.out.println("\n\nValues Acquired!!\n\n1st Phase ended!!\n\n");
           
        System.out.println("Starting Make-Graphic Phase:\n\n");
        fazerArquivoPython.main(args);
        
        /*
            Abrir Código Python Automaticamente:
                Defina choice2 como true para abrir o código python gerado no 
                bloco de notas!!
        
            Default: true;
        */
        choice2 = true;
        AbrirPythonFileEmBlocoDeNotas.main(args);
        
        
        ExecutePython.main(args);
        AbrirPlanilha.main(args);
        
        abrir.acquire();
        System.out.println("\n\nMake-Graphic Phase Ended"
                         + "\n\nPhases Concluded!!\n\n");
       
    }

}
