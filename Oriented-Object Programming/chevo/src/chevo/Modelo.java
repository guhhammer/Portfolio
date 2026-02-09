package chevo;

import java.util.ArrayList;

public class Modelo {
    
    private final String motor;
    private final String marca;
    private final String ano;
    private final String cavalos;
    private final String nome;
    private Exterior ext;
    private Interior inte;
    private ArrayList<Exterior> prefEXT; 
    private ArrayList<Interior> prefINT; 
    
    public Modelo(String Mt, String Mc, String ANO, String cav, String nm){
        
        this.motor = Mt; 
        this.marca = Mc;
        this.ano = ANO;
        this.cavalos = cav;
        this.nome = nm;
        this.prefEXT = new ArrayList();
        
    }
    
    public boolean cadastraPrefEXT(Exterior E){ return prefEXT.add(E);}
    public boolean cadastraPrefINT(Interior I){ return prefINT.add(I);}
    
    public String getMotor(){ return this.motor;}
    public String getMarca(){ return this.marca;}
    public String getAno(){ return this.ano;}
    public String getCavalos(){ return this.cavalos;}
    public String getNome(){ return this.nome;}
    
    public ArrayList<String> Imprime(){
        
        ArrayList<String> D = new ArrayList();
        
        System.out.println("Motor: "); //to do. Stopped here
        
        
        for(int i = 0; i < prefEXT.size(); i++){
            D.add(prefEXT.get(i).preferenciasEXT());
        }
        
        for(int i = 0; i < prefINT.size(); i++){
            D.add(prefINT.get(i).preferenciasINT());
        }
        
        return D;
    } 
    
}
