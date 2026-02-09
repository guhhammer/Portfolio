
package ra03jobpoo;

public interface Criptomoeda extends ContaCorrente{
    
       
    public double converterSaldo(double coin, double x);
    
    public double consultaSaldoEmReais(double coin);
    
    public double consultaSaldo();
    
    public double getValue();
    
    public void realizarDeposito(double x);
    
    public void realizarSaque(double x);
    
    
    
}
