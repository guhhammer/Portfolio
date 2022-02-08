
package Mini_Trabalho1;

import java.util.Collections;

public class grafos {
   
    int tamanho;
    int[][] matriz;
    Vertice[] vertices;
    
    
    
    public class Vertice{
        
        String nome;
        
        void setNome(String n){ this.nome = n;}
        
        String getNome(){ return nome;}
        
    }
    
    public static int[][] matriz_adjacente(int t){ return new int[t][t];}
    
    public grafos(int tamanho){
        
        this.matriz = matriz_adjacente(tamanho);
        this.tamanho = tamanho;
        this.vertices = new Vertice[tamanho];
        
        for(int i = 0; i < tamanho; i++){
            this.vertices[i] = new Vertice();
        }
        
    }
    
    
    
    void cria_adjacencia(int i, int j, int p){ this.matriz[i][j] = p;}
    
    void remove_adjacencia(int i, int j){ this.matriz[i][j] = 0; }
    
    void imprime_matriz(){
        
        System.out.print("\n Matriz Adjacente:\n\n* |");
        for(int i = 0; i < this.tamanho; i++){System.out.print(" "+i);}
        System.out.println("\n"+
                String.join(" ", Collections.nCopies(this.tamanho+2,"-")));
        
        for(int i = 0; i < this.tamanho; i++){
            System.out.print(i+" |");
            for(int j = 0; j < this.tamanho; j++){
                System.out.print(" "+this.matriz[i][j]);
            }
            System.out.println("");
        }
        System.out.println("\n");
    }
    
    void imprime_adjacencias(){
        System.out.print("\nAdjacências:\n\nExemplo: {(index i), (index j), "
                         +"(peso p)}\n\n{  \n");
        for(int i = 0; i < this.tamanho; i++){
            for(int j = 0; j < this.tamanho; j++){
                if(this.matriz[i][j] != 0){
                    System.out.print(String.format("{%d, %d, %d}",
                                        i, j, this.matriz[i][j])+", ");
                }
            }
            System.out.println("");
        }
        System.out.print("} \n\n");
    }
    
    void seta_afirmacao(int i, String v){ vertices[i].setNome(v); }
    
    int adjacentes( int i, int[] adj){
        
        int count = 0,index = 0;
        for(int s = 0; s < adj.length; s++){ adj[s] = -1; }
        
        for(int x = 0; x < this.tamanho; x++){
            if(this.matriz[i][x] != 0){  count += 1;  adj[index++] = x; }
        }
        
        return count;
    }
    
    
    static void ver_so_adjacentes(int[] adj){
        int i = 0;
        System.out.print("Adj = { ");
        while(adj[i] != -1){ System.out.print(adj[i++]+", ");}
        System.out.print("} \n\n");    
    }
    
    // Equipe: Gustavo Hammerschmidt,  Kalebe Szlachta.
    
    public static void main(String[] args){
        /*
        Crie uma representação de grafo direcionado, ponderado e rotulado
        baseada em matriz de adjacência.
      
            Desenho do Grafo:
        
                       4      --> x (nome).
                     / | \
                    /  |  \
                   0---|---1
                   |   |   |    Obs.: há um caminho de 
                   |   |   |           x para 2 também!  Peso(4)
                   2---|---3
                    \  |  /
                     \ | /
                       5      --> y (nome).  
        
        Pesos: 
            -     0 -> x : 3
            -     2 -> 0 : 6 
            -     x -> 2 : 4
            -     x -> y : 10
            -     2 -> 3 : 1
            -     y -> 2 : 3
            -     3 -> y : 1
            -     3 -> 1 : 7
            -     1 -> x : 8
        
        */
        
        grafos GR = new grafos(6);   // Grafo.
        
        GR.cria_adjacencia(0, 4, 3);
        GR.cria_adjacencia(2, 0, 6);   // Caminhos e Pesos.
        GR.cria_adjacencia(4, 2, 4);
        GR.cria_adjacencia(4, 5, 10);
        GR.cria_adjacencia(2, 3, 1);
        GR.cria_adjacencia(5, 2, 3);
        GR.cria_adjacencia(3, 5, 1);
        GR.cria_adjacencia(3, 1, 7);
        GR.cria_adjacencia(1, 4, 8);
        
    
        GR.cria_adjacencia(0,0, 9);   // A remover ...    
        GR.imprime_matriz();
        GR.remove_adjacencia(0, 0);
        
        GR.imprime_matriz();
        GR.imprime_adjacencias();
        
        
        GR.seta_afirmacao(4, "x");
        GR.seta_afirmacao(5, "y");
        System.out.println("Nome do nó "+4+": "+GR.vertices[4].getNome());
        System.out.println("Nome do nó "+5+": "+GR.vertices[5].getNome()+"\n\n");
        
       
        
        int var = 2, l2[] = new int[GR.tamanho], total = GR.adjacentes(var,l2);
        System.out.println("Adjacentes linha "+var+": "+total);
        ver_so_adjacentes(l2);
   
    }
    
}
