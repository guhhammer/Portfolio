package person;


public class pessoa {

    
    
    private String nome;
    private String sobrenome;
    private int idade;
    private double salario;
    
    public pessoa(String n, String sn, int i, double s){
        nome = n;
        sobrenome = sn;
        idade = i;
        salario = s;
    }
    
    public String getNome(pessoa p){
        return p.nome;
    }
    
    public String getSobre(pessoa p){
        return p.sobrenome;
    }
    
    public int getIdade(pessoa p){
        return p.idade;
    }
    
    public double getSalario(pessoa p){
        return p.salario;
    }
    
    public void imprimir(pessoa p){
        System.out.println("Nome: "+getNome(p)+
                           " \nSobrenome: "+getSobre(p)+
                           " \nIdade: "+getIdade(p)+
                           "\nSalário: "+getSalario(p));
    }
    
}
