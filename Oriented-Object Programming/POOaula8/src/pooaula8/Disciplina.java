/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pooaula8;

import java.util.ArrayList;

public class Disciplina {
    
    private String NomeD;
    private Professor prof;
    private ArrayList<Aluno> listaAluno;
    
    public Disciplina(String nome, Professor professor){
        
        this.NomeD = nome;
        this.prof = professor;
        this.listaAluno = new ArrayList();
       
    }
    public boolean cadastra(Aluno A){ return this.listaAluno.add(A);}

    public double mediaSala(){
        double m = 0.0;
        for(int i = 0; i < listaAluno.size(); i++){
            m = m + listaAluno.get(i).media();
        }
        return m/listaAluno.size();
    }
    
    public ArrayList<String> getRelatorio(){
        
        ArrayList<String> A = new ArrayList();
        
        System.out.println("Disciplina: "+this.NomeD+
                "\nProfessor Nome: "+prof.getnome()+
                "\nProfessor Sobrenome: "+prof.getsobrenome());
        
        for(int i = 0; i < listaAluno.size(); i++){
            A.add(listaAluno.get(i).relatorio());
        }
        return A;
    }

}
