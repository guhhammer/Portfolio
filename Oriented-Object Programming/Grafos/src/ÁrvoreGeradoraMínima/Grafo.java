
package ÁrvoreGeradoraMínima;

import ÁrvoreGeradoraMínima.MEsparsa.*;

public class Grafo{
    
    int tamanho;
    Vertice[] vertices;
    MEsparsa matriz;
    
    // Classe Vértice.
    public class Vertice{
        
        String nome;
        int index;
        
        // Métodos set.
        void setNome(String n){ this.nome = n;}
        void setIndex(int n){ this.index = n;}
        
        // Métodos get.
        String getNome(){ return nome;}
        int getIndex(){ return index;}
        
    }
    
    // Construtor.
    public Grafo(int tamanho){
        
        MEsparsa aux = new MEsparsa(tamanho);
        
        this.tamanho = tamanho;
        this.matriz = aux;
        this.vertices = new Vertice[tamanho];
       
        for(int i = 0; i < tamanho; i++){vertices[i] = new Vertice();}  
        
        for(int i = 0; i < tamanho; i++){vertices[i].setIndex(i);} 
        
        for(int i = 0; i < tamanho; i++){vertices[i].setNome(""+i);}
        
    }
   
    // Cria adjacência não direcionada entre vértice i e j.
    void criaAdjacenciaNaoDirecionada(int i, int j, int p){
        this.matriz.lista[i].inserir(j, p);
        this.matriz.lista[j].inserir(i, p);
    }
    
    // Método especial, destinado ao uso somente pela classe MST_Kruskal.
    void criaAdjacencia(int i, int j, int p){
        this.matriz.lista[i].inserir(j, p);
    }
    
    // Remove adjacência não direcionada entre i e j.
    void removeAdjacenciaNaoDirecionada(int i, int j){
        this.matriz.lista[i].remover(j);
        this.matriz.lista[j].remover(i);
    }
    
    // Remove adjacência entre i e j.
    void removeAdjacencia(int i, int j){
        this.matriz.lista[i].remover(j);
    }
    
    // Imprime adjacências do Grafo.
    void imprimeAdjacencias(){
        
        for(int  i = 0; i < this.tamanho; i++){
            Knot aux = this.matriz.lista[i].primeiro;
            System.out.print("Vértice "+i+": ");
            while(aux != null){
                System.out.print(" {adjacente: "+aux.adjacente+
                                 ", valor: "+aux.valor+"} ");
                aux = aux.proximo;
            }
            System.out.println("");
        }
        
    }
    
    // Imprime adjacências do Grafo.
    void imprimeAdjacenciasNaoDirecionadas(){
        
        Grafo g = new Grafo(this.tamanho);
        
        for(int i = 0; i < this.tamanho; i++){
            Knot aux = this.matriz.lista[i].primeiro;
            while(aux != null){
                g.criaAdjacenciaNaoDirecionada(i, aux.adjacente, aux.valor);
                aux = aux.proximo;
            }
        }
         
        for(int  i = 0; i < this.tamanho; i++){
            Knot aux = g.matriz.lista[i].primeiro;
            System.out.print("Vértice "+i+": ");
            while(aux != null){
                System.out.print(" {adjacente: "+aux.adjacente+
                                 ", valor: "+aux.valor+"} ");
                aux = aux.proximo;
            }
            System.out.println("");
        }
        
    }
    
    // Retorna se adjacência existe.
    boolean existe(int i, int j){
        return this.matriz.lista[i].existir(j);
    }
    
    // Seta informação(nome) no vértice.
    void setaInformacao(int i, String v){vertices[i].setNome(v);}
    
    // Retorna soma dos adjacentes.
    int adjacentes(int i, int[] adj){
        
        int count = 0,index = 0; // valor que indentifica não relação("um 0").
        for(int s = 0; s < adj.length; s++){ adj[s] = Integer.MIN_VALUE; }
        
        Knot aux = this.matriz.lista[i].primeiro;
        while(aux != null){ 
            adj[index++] = aux.adjacente; count++; aux = aux.proximo;
        }
        
        return count;
    }
    
    // Retorna valor da adjacência entre o knot i e j.
    int mostraValor(int i, int j){return this.matriz.lista[i].mostraValor(i, j);}
    
    // Encontra vértice j.
    public int findVertice(int j){
        int v = -1;
        for(int i = 0; i < tamanho; i++){if(i == j){ v = i; }}
        return v;
    }
    
}