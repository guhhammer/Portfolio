/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hojetde;

/**
 *
 * @author Gustavo
 */
public class funcionario {
    
    // nome funcionário
    private String nome;
    private String sobrenome;
    // nome funcionário
    
    // data de nascimento
    private String nasc;
    
    // número de horas trabalhadas
    private double horas;
    // número de horas trabalhadas

    private float valorHora;
    
    public funcionario(String n, String sn, String dia, int hora, float valor ){
            nome = n; sobrenome = sn;nasc = dia ; 
            horas = hora; valorHora = valor;}
    
        public double calcularsalario(int horas){
            return (double)horas*7.8;
        }
    
        public String contracheque(){
            return "Nome: "+this.nome + "\nSobrenome: " + this.sobrenome 
                    + "\nData de Nascimento:  "+ this.nasc +
                    "\nHoras: "+this.horas + 
                    "\n Salário: "+this.valorHora;
        }
}
