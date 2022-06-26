
package ra03jobpoo;

public interface Poupança {
    
        
    public Poupança criar
        (String num, String numAgen, double value,
                                  int dd, int mm,int aaaa, double tdCorr);       
    /// Métodos get e set:
        
    public int getDIA();
    
    public int getMES();        
            
    public int getANO();

    public String getDataDeAniversario();
    
    //dia, mes, ano atual: assinatura
    public int timeCalculator(int ddA, int mmA, int aaaaA);
    /// Métodos get e set.
    
    ///// Função de: Poupança    100%
    public double corrigirSaldo(int dd, int mm, int aaaa);
    ///// Função de: Poupança.
    
    public double consultaSaldo();
    
    public void realizarDeposito(double x);
    
    public void realizarSaque(double x);
    
}
