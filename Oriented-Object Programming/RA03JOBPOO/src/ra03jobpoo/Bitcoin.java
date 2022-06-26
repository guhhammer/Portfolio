
package ra03jobpoo;

public interface Bitcoin extends Criptomoeda {
    
    public Bitcoin Criar(String cpf, String num, String numAgen, double value, 
            double Bitvalue);
    
    public double consultaSaldo();
    
    public void realizarDeposito(double x);
    
    public void realizarSaque(double x);
    
}
