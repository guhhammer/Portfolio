/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pooaula8;

/**
 *
 * @author Gustavo
 */
public class Aluno {
    
    private String nomeA;
    private String sobrenomeA;
    
    private double n1;
    private double n2;
    private double n3;
    private double n4;
    
    public Aluno(String nome, String sobrenome, double nota1, 
            double nota2, double nota3, double nota4){
        
        this.nomeA = nome;
        this.sobrenomeA = sobrenome;
        
        this.n1 = nota1;
        this.n2 = nota2;
        this.n3 = nota3;
        this.n4 = nota4;
        
    }
    
    public double media(){
        return (this.n1+this.n2+this.n3+this.n4)/4;
    }
    
    public String relatorio(){
        return  "\nNome: "+this.nomeA+
                "\nSobrenome: "+this.sobrenomeA+
                "\nNota 1: "+this.n1+
                "\nNota 2: "+this.n2+
                "\nNota 3: "+this.n3+
                "\nNota 4: "+this.n4+
                "\nMédia: "+media()+
                "\n";
    }
    
}
