
package notação_polonesa;

public class addition extends OperadorPrefixado{

    public addition(double[] ind){super(ind);}
    public addition(String[] ind){super(ind);}


    /**
     *
     * @return
     */
    @Override
    public double avaliar(){
        double soma = 0;
        for(int i = 0; i < super.tamanho(); i++){
            soma = soma + valores[i];
        }
        return soma;
    }
     
    @Override
    public String toStringPolishNotation(){
        String ret;
        ret = "+( ";
        for(int i = 0; i < super.tamanho(); i++){
            ret = (ret + ((super.toStringChanger(valores[i])))+", ");
        }
        ret = ret + " )";
        
        return ret;
    }
}