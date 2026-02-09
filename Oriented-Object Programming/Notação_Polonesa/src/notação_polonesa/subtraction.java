
package notação_polonesa;

public class subtraction extends OperadorPrefixado{

    public subtraction(double[] ind){super(ind);}
    public subtraction(String[] ind){super(ind);}
    
     /**
     *
     * @return
     */
    @Override
    public double avaliar(){
        double subtract = 0;
        double start = valores[0];
        for(int i = 0; i < super.tamanho(); i++){
            if(i == 0){subtract = start;}
            else{subtract = subtract - valores[i];}
        }
        return subtract;
    }

    @Override
    public String toStringPolishNotation(){
        String ret;
        ret = "-( ";
        for(int i = 0; i < super.tamanho(); i++){
            ret = (ret + ((super.toStringChanger(valores[i])))+", ");
        }
        ret = ret + " )";
        
        return ret;
    }
}
