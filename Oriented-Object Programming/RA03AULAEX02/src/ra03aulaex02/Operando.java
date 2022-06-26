
package ra03aulaex02;

public abstract class Operando extends Expression {
   
    private final double valor;
   
    public Operando(double v){super(v); this.valor = v;}
    
    @Override
    public double avaliar(){return valor;}

}