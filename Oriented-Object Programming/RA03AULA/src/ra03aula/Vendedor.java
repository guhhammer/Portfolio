package ra03aula;

public class Vendedor extends Funcionario {
    
    private float comissao = 0.03f;
    
    public Vendedor(int ndh, int vh, String nom) {
        super(ndh, vh, nom);
    }
    
    @Override
    public float calcularSalario(){
        return super.calcularSalario()*(1+comissao);
    }
    
    @Override
    public float calcularSalario(int mes){
        return super.calcularSalario()*(1 + comissao)* mes; 
    }
     
}
