package ex2;


public class professor {

    private String nomePROF;
    private String sobrenomePROF;
    
    
    public professor(String nome, String sobrenome){
        this.nomePROF = nome; this.sobrenomePROF = sobrenome;
    }
       
    
    public String toString(){
        return "Nome do Professor: "+this.nomePROF+ 
               "Sobrenome do Professor: "+this.sobrenomePROF;
    }
    
}
