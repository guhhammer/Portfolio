
package ra03aula;

public class Funcionario {
    
    private float numeroDeHoras, valorHora;
    private String nome;
    
    public Funcionario(int ndh, int vh, String nom){
        this.numeroDeHoras = ndh;
        this.nome = nom;
        this.valorHora = vh;       
    }
    
    public float calcularSalario(){
        return this.numeroDeHoras * valorHora;}

    public float calcularSalario(int mes){
        return this.numeroDeHoras * valorHora * mes;
    }
 
}