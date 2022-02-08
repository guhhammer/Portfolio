
package ParalelismoRecursivo;

import java.io.IOException;
import static ParalelismoRecursivo.Main.*;

public class FecharExcel {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        
        if(choice){
            Process p = Runtime.getRuntime().exec("cmd /c taskkill /F /IM excel.exe");
            p.waitFor();
        }
        
        Thread.sleep(2000);
        warten.release();
    }
   
}
