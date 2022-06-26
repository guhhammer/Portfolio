
package projetoimovel;

public class Casa extends Imovel {
    
    public Casa(double p){ super(p);}

    public double valorize(double p){
        preço = preço * (1.0 + p/(100*2));
        return preço;
    }
    
}
