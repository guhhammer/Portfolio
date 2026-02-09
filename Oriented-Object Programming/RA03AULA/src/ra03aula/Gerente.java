
package ra03aula;

public class Gerente extends Funcionario {

    private float desempenho = 0.02f;
    
    public Gerente(int ndh, int vh, String nom) {
        super(ndh, vh, nom);
    }
   
    @Override
    public float calcularSalario(){
        return super.calcularSalario()*(1+desempenho);
    }
    
    @Override
    public float calcularSalario(int mes){
        return super.calcularSalario()*(1+desempenho)*mes;
    }

    
    
    
    
    
}
