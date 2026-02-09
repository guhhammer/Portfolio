
package ParalelismoRecursivo;

import static ParalelismoRecursivo.Main.*;
import java.io.IOException;

public class AbrirPythonFileEmBlocoDeNotas {
    
    
    public static void main(String[] args) throws IOException {
        
        if(choice2){
            Runtime.getRuntime().exec("cmd /c Notepad \""+caminhoPython+"\"");
        }
        
        
    }
    
    
}
