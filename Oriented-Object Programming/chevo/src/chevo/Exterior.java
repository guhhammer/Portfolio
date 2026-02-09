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
public class Exterior {
    
    private final String cor;
    private final String rodaTipo;
    private final String vidroTipo;
    
    public Exterior(String c, String rd, String vd){
        
        this.cor = c;
        this.rodaTipo = rd;
        this.vidroTipo = vd;
        
    }
    
    public String preferenciasEXT(){
        return  "\nCor do carro: "+cor+
                "\nTipo da Roda: "+rodaTipo+
                "\nTipo de vidro: "+vidroTipo;
    }
    
    public String getCor(){return this.cor;}
    public String getRodaTipo(){return this.rodaTipo;}
    public String getVidroTipo(){return this.vidroTipo;}
    
}
