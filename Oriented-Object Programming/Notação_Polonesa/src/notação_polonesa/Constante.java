
package notação_polonesa;


public class Constante extends Operando{
 
    private final double konstant;
    
    public Constante(double v){super(v); this.konstant = v;}
    
    @Override
    public double getValor(){return this.konstant;}
    
    @Override
    public String toString(){return ""+konstant;}
    
}