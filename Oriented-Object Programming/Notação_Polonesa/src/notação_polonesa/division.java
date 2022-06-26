
package notação_polonesa;

public class division extends OperadorPrefixado{

    public division(double[] ind){super(ind);}
    public division(String[] ind){super(ind);}

     /**
     *
     * @return
     */
    @Override
    public double avaliar(){
        
        double division = 0;
        double start = valores[0];
        for(int i = 0; i < super.tamanho(); i++){
            if (i == 0){division = start;}
            else{division = division /valores[i];}
        }
        return division;
    }
    
    @Override
    public String toStringPolishNotation(){
        String ret;
        ret = "/( ";
        for(int i = 0; i < super.tamanho(); i++){
            ret = (ret + ((super.toStringChanger(valores[i])))+", ");
        }
        ret = ret + " )";
        
        return ret;
    }
       
}

