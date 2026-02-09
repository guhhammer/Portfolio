/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hojetde;
import java.util.ArrayList;
        
/**
 *
 * @author Gustavo
 */
public class empresa {
    private String nomeEmp;
    private String dataCriacao;
    // data de criação
    
    ArrayList<funcionario> listaDeFunc;
    
    public empresa(String n, String d) {
        this.nomeEmp = n;
        this.dataCriacao = d;
        this.listaDeFunc = new ArrayList();
        
                
    }
    public boolean cadastra(funcionario f){return this.listaDeFunc.add(f);}
    
    public ArrayList<String> geraRelatorio(){
        ArrayList<String> L = new ArrayList(); //variavel local
        
        for(funcionario f: this.listaDeFunc){
            L.add(f.contracheque());
        }
        return L;
    }
    
    public void imprimeRelatorio(ArrayList<String>R){
        for(String str: R){System.out.println(str);}
    }
      
    }
