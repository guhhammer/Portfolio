
package Controladoras;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public abstract class Controladora extends Thread{
    
    
    Semaphore CS, CN, PermissaoIr, Cheguei, mutex;
    long tempo = 0, tempoFim;
    int contadorTotal = 0;
    int contador;
    static int pegarProximoIndex = -1;
    
    
    ArrayList ordemChegadaNorte = new ArrayList<>();
    ArrayList ordemChegadaSul = new ArrayList<>();
    
    public static Semaphore[] listaIDSCarros = new Semaphore[Main.quantidadeNaPonte];
    
    public Controladora(Semaphore mutex, Semaphore Cheguei, Semaphore PIR,
                        Semaphore CN, Semaphore CS, int contador){
        
        this.contador = contador;
        this.mutex = mutex;
        this.Cheguei = Cheguei;
        this.PermissaoIr = PIR;
        this.CN = CN;
        this.CS = CS;
    
    }
    
    protected static void inserir(){
        if(listaIDSCarros[Carro.ID % listaIDSCarros.length] != null){
                listaIDSCarros[Carro.ID % listaIDSCarros.length] = new Semaphore(0);
        }
    }
    
    protected static boolean cheia(Semaphore[] s){
        for(int i = 0; i < s.length; i++){ if(s[i] == null){ return true;}}        
        return false;
    }
    
    protected static Semaphore remover(Semaphore[] s, int i){
        Semaphore aux = s[i]; s[i] = null;
        return aux;
    }
    
    protected static void inserir(int id){
        for(int i = 0; i < listaIDSCarros.length; i++){ 
            if(listaIDSCarros[i] == null){ 
                listaIDSCarros[id % listaIDSCarros.length] = new Semaphore(0);
            }
        }
    
    }
    
    protected static int pegarProximo(){
        
        if(pegarProximoIndex == listaIDSCarros.length){ pegarProximoIndex = 0;}
        
        return pegarProximoIndex;
        
    }
    
    protected static void controladoraAtual(){
        System.out.println(String.format("Controladora Atual: %s.", Main.CtrlAtual));
    }
    
    protected static void temPermissao(int id){
        System.out.println(String.format("\nCarro %d tem permissão para ir.", id));
    }
    
}
