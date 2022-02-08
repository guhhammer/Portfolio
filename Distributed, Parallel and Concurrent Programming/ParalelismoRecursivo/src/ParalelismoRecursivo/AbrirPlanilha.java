
package ParalelismoRecursivo;

import static ParalelismoRecursivo.Main.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


public class AbrirPlanilha {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        
        executar.acquire();
        
        System.out.println("\n\n\t\tOpening .Xlsx File...");
        Thread.sleep(1000);
        System.out.print("\n\t\tOpening in 4...");
        Thread.sleep(1000);
        System.out.print(" 3...");
        Thread.sleep(1000);
        System.out.print(" 2...");
        Thread.sleep(1000);
        System.out.print(" 1...");
        Thread.sleep(1000);
        System.out.println("\n\t\tOpening...");
                   
        String path = caminhoMain.replace("\\", "\\\\")+
                      "src\\ParalelismoRecursivo\\"+nomeArqExcel;
        
        Desktop.getDesktop().open(new File(path));
        
        System.out.println("\n\t\tOpened Successfully!!\n\n");
        
        abrir.release();
        
    }
    
}
