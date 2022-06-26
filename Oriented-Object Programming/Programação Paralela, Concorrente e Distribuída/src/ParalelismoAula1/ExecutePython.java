
package ParalelismoAula1;

import static ParalelismoAula1.Iniciador.criar;
import static ParalelismoAula1.Iniciador.executar;
import java.io.File;
import java.io.IOException;

public class ExecutePython extends Thread{
    
    public static String caminho = ParalelismoAula1.Iniciador.caminho;
    public static String nomeArqPython = ParalelismoAula1.Iniciador.nomeArqPython;
    
    public static void main(String[] args) throws IOException, InterruptedException {
        
        criar.acquire();
        
        Thread.sleep(1000);
        System.out.println("\n\n\t\tExecute in 4...");
        Thread.sleep(1000);
        System.out.println("\n\n\t\tExecute in 3...");
        Thread.sleep(1000);
        System.out.println("\n\n\t\tExecute in 2...");
        Thread.sleep(1000);
        System.out.println("\n\n\t\tExecute in 1...");
        Thread.sleep(1000);
        System.out.println("\n\n\t\tExecuting...");
        
        
        String path = caminho+"src\\ParalelismoAula1\\";
                         
        Process p = Runtime.getRuntime().exec(
			"cmd /c "+nomeArqPython, null, new File(path));
        p.waitFor();
        
        System.out.println("\n\n\t\tExecution Completed!!\n\n");
        
        executar.release();
        
    }
    
}
