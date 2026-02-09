
package MonitorAula1;

public class Main {
    
    
    public static void main(String[] args) {
    
        Barbearia b = new Barbearia();
        Barbeiro bb = new Barbeiro(b);
        
        bb.start();
        
        for(int i = 0; i < 30; i++){
            Cliente c = new Cliente(b, i);
            c.start();
        }
        
    }
    
    
}
