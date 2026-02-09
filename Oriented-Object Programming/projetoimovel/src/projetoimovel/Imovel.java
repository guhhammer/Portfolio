
package projetoimovel;

public abstract class Imovel {
    
    protected double preço;
    
    public Imovel(double p){ this.preço = p;}
    
    public double valorize(double p){
        preço = preço * (1.0 + p/100);
        return preço;
    }
    
    public double desvalorize(double p){ 
        preço = preço * (1.0 - p/100);
        return preço;
    }
    
}
