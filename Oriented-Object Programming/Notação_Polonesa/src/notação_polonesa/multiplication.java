
package notação_polonesa;

public class multiplication extends OperadorPrefixado{

    public multiplication(double[] ind){super(ind);}
    public multiplication(String[] ind){super(ind);}
     
    
    /**
     *
     * @return
     */
    @Override
    public double avaliar(){
        double multiplication = 1;
        for(int i = 0; i < super.tamanho(); i++){
            multiplication = multiplication * valores[i];
        }
        return multiplication;
    }
    
    @Override
    public String toStringPolishNotation(){
        String ret;
        ret = "*( ";
        for(int i = 0; i < super.tamanho(); i++){
            ret = (ret + ((super.toStringChanger(valores[i])))+", ");
        }
        ret = ret + " )";
        
        return ret;
    }
    
}