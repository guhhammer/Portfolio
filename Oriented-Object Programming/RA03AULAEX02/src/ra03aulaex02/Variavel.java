
package ra03aulaex02;

public class Variavel extends Operando{
    
    private final String nome;
    
    public Variavel(double v, String nom){super(v); this.nome = nom;}
    
    @Override
    public double getValor(){return super.getValor();}
        
    @Override
    public String toString(){return nome;}
   
}