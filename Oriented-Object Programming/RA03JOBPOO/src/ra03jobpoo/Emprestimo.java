
package ra03jobpoo;

public interface Emprestimo {
   
    //Utilizar caso queira definir como juros compostos.
    public int setTaxaDeJuros(int tdj);    
   
    
    ///// Função de: Emprestimo     75%
    public void ligar(Cliente c, Conta cc);
    
    
    public double calcularValorAPagar();
    
    public double realizarQuitaçao();
    
    public double consultarSaldo();
    
    ///// Função de: Emprestimo
    
}
