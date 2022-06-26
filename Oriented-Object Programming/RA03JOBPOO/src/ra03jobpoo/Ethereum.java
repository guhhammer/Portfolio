
package ra03jobpoo;

public interface Ethereum extends Criptomoeda {
    
    public Ethereum Criar(String cpf, String num, String numAgen, double value, 
            float Ethvalue);
    
    public double consultaSaldo();
    
    public void realizarDeposito(double x);
    
    public void realizarSaque(double x);
    
}
