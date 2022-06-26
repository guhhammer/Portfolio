
package ra03jobpoo;

public interface ContaCorrente {
    
    public ContaCorrente criar(String num, String numAgen, double value);
    
    public double consultaSaldo();
    
    public void realizarDeposito(double x);
    
    public void realizarSaque(double x);
    
}
