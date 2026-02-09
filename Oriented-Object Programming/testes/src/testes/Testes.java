
package testes;

import java.util.Arrays;
import java.util.Random;

public class Testes {

    public class No{
    
        No proximo;
        No anterior;
        int dado;
        
        public No(int n){
            this.dado = n;
            this.anterior = null;
            this.proximo = null;
        }
    }
    
    No ultimo;
    
    public Testes(){
        this.ultimo = null;
    }
    
    
    boolean vazia(){ return (this.ultimo == null);}
    
    void insere_primeiro(int n){
        No aux = new No(n);
        
        if(this.vazia()){
            
            this.ultimo = aux;
            this.ultimo.proximo = this.ultimo;
            this.ultimo.anterior = this.ultimo;
        }
        else{
            aux.proximo = this.ultimo.proximo;
            aux.anterior = this.ultimo;
            this.ultimo.proximo = aux;
        }
    
    }
    
    
    
    void print(){
    
    No aux = this.ultimo.proximo;
    while(aux != this.ultimo){
    
        System.out.println(aux.dado);
        aux = aux.proximo;
    }
        System.out.println(aux.dado);
    
    }
    
    public static void main(String[] args) {

        Testes x = new Testes();
        
        x.insere_primeiro(5);
        x.insere_primeiro(6);
        x.insere_primeiro(8);
        x.insere_primeiro(10);
        
        x.print();
            
    }
    
}
