
package MonitorBarbeariaDiagrama;

public class Main {
    
    public static int NUMCADEIRAS = 4;
    public static int Nclientes = 8;
    
    private static void setNclientes(int n){ Nclientes = n;}
    
    public static void main(String[] args) throws InterruptedException {
    
        Barbearia barbearia = new Barbearia(NUMCADEIRAS);
        
        Barbeiro barbeiro = new Barbeiro(barbearia);
        barbeiro.start();
        
        
        setNclientes(8);
        for(int i = 0; i < Nclientes; i++){
            Thread.sleep(2000);
            Cliente c = new Cliente(i, barbearia);
            c.start();
            Thread.sleep(2000);
        }
        
    }
    
    
    
    
}
