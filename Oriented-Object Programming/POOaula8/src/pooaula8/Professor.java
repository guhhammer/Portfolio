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
public class Professor {
    
    private String nomeP;
    private String sobrenomeP;
    
    public Professor(String nome, String sobrenome){
        this.nomeP = nome;
        this.sobrenomeP = sobrenome;
    }
    
    public String getnome(){ return this.nomeP;}
    public String getsobrenome(){ return this.sobrenomeP;}
    
}
