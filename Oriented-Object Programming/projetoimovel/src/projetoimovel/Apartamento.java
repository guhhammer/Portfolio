
package projetoimovel;

public class Apartamento extends Imovel{

    public Apartamento(double p) {
        super(p);
    }
    
    public double desvalorize(double p){
        preço = preço * (1.0 - p/100/2);
        return preço;
    }
    
}
