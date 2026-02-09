
package parser;

public class Parser {
    
    private String delimitador = " ";
    
    public Parser(String delimitador){
        this.delimitador = delimitador;
    }
    
    public String [] split(String txt){
        String [] v = txt.split(delimitador);
                
        return v;
    }
}
