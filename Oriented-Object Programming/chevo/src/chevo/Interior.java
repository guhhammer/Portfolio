/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chevo;

/**
 *
 * @author Gustavo
 */
public class Interior {
   
    private final String bancoTipo;
    private final String bancoCor;
    private final String tapeteCor;
    
    public Interior(String banco, String Bcor, String Tcor){
        this.bancoTipo = banco;
        this.bancoCor = Bcor;
        this.tapeteCor = Tcor;
    }
    
    public String preferenciasINT(){
        return  "\nTipo de Banco: "+bancoTipo+
                "\nCor do banco: "+bancoCor+
                "\nCor do tapete: "+tapeteCor;
    }
    
    public String getBancoTipo(){return this.bancoTipo;}
    public String getBancoCor(){return this.bancoCor;}
    public String getTapeteCor(){return this.tapeteCor;}
    
    
}
