
package ex2;

public class aluno {
    
    private String nomeAl;
    private String sobrenomeAl;
    private double n1;
    private double n2;
    private double n3;
    private double n4;
    
    private double media;
    
    public aluno(String nome, String sobrenome){
        this.nomeAl = nome; this.sobrenomeAl = sobrenome;
    }
    
    public void setn1(double nota1){this.n1 = nota1;}
    public void setn2(double nota2){this.n1 = nota2;}
    public void setn3(double nota3){this.n1 = nota3;}
    public void setn4(double nota4){this.n1 = nota4;}
    public String getNomeAL() {return this.nomeAl; }
    public String getSobrenomeAL() {return this.sobrenomeAl; }
    public double getn1() {return this.n1; }
    public double getn2() {return this.n2; }
    public double getn3() {return this.n3; }
    public double getn4() {return this.n4; }
    
    public void media(double nota1, double nota2, 
            double nota3, double nota4){
            this.media = ( nota1 + nota2 +nota3 + nota4)/4.0 ;
    }
    
    public double getMedia(){return this.media;}
    
    public String getHistorico(){
        return "Nome do aluno: "+this.getNomeAL()+
               "\nSobrenome do aluno: "+this.getSobrenomeAL()+
               "\nNota 1: "+this.getn1()+
               "\nNota 2: "+this.getn2()+
               "\nNota 3: "+this.getn3()+
               "\nNota 4: "+this.getn4()+
               "\nMédia: "+this.getMedia();
        
    }
    
}
