
package ParalelismoRecursivo;

import static ParalelismoRecursivo.Main.*;
import java.io.File;
import java.io.IOException;


public class ExecutePython{
    
    public static void main(String[] args) throws IOException, InterruptedException {
        
        criar.acquire();
        
        System.out.println("\n\n\t\tExecuting Python File...");
        Thread.sleep(1000);
        System.out.print("\n\t\tExecuting in 4...");
        Thread.sleep(1000);
        System.out.print(" 3...");
        Thread.sleep(1000);
        System.out.print(" 2...");
        Thread.sleep(1000);
        System.out.print(" 1...");
        Thread.sleep(1000);
        System.out.println("\n\t\tExecuting...");
        
        String path = caminhoMain+"src\\ParalelismoRecursivo\\";
                         
        Process p = Runtime.getRuntime().exec(
			"cmd /c "+nomeArqPython, null, new File(path));
        p.waitFor();
        
        System.out.println("\n\t\tExecution Completed!!\n\n");
        
        executar.release();
        
    }
    
}
