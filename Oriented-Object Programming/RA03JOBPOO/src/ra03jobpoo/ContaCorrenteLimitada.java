
package ra03jobpoo;

public interface ContaCorrenteLimitada {
    
    
    public ContaCorrenteLimitada criar
        (String num, String numAgen, double value,double lim, double tdJuro);
    
    ///// Função de: Conta Corrente Limitada    100%
    public double setTaxaJuroCCL(double num);
    
    public double getLimite();
    
    public double aumentarLimite(double auml); 
    
    public double reduzirLimite(double redl);
    
    public double consultaSaldo();
    
    public void realizarDeposito(double x);
    
    public void realizarSaque(double x);
    
    public double getTaxaJuroCCl();
    ///// Função de: Conta Corrente Limitada

}
