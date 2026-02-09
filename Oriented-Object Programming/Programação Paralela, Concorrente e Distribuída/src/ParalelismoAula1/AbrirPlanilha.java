
package ParalelismoAula1;

import static ParalelismoAula1.Iniciador.executar;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class AbrirPlanilha extends Thread{
 
    public static String caminho = ParalelismoAula1.Iniciador.caminho;
    public static String nome = Iniciador.nomeArqExcel;
    
    public static void main(String[] args) throws IOException, InterruptedException {
        
        executar.acquire();
        
        Thread.sleep(1000);
        System.out.println("\n\n\t\tOpening file .XLSX in 4...");
        Thread.sleep(1000);
        System.out.println("\n\n\t\tOpening file .XLSX in 3...");
        Thread.sleep(1000);
        System.out.println("\n\n\t\tOpening file .XLSX in 2...");
        Thread.sleep(1000);
        System.out.println("\n\n\t\tOpening file .XLSX in 1...");
        Thread.sleep(1000);
        System.out.println("\n\n\t\tOpening...");
                   
        String path = caminho.replace("\\", "\\\\")+
                      "src\\ParalelismoAula1\\"+nome;
        
        Desktop.getDesktop().open(new File(path));
        
        System.out.println("\n\n\t\tOpened Successfully!!\n\n");
        
    }
    
}
