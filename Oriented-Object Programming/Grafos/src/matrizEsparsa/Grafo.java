
package matrizEsparsa;

import matrizEsparsa.MEsparsa.*;

public class Grafo{
    
    int tamanho;
    Vertice[] vertices;
    MEsparsa matriz;
    
    public class Vertice{
        
        String nome;
        
        void setNome(String n){ this.nome = n;}
        
        String getNome(){ return nome;}
    }
    
    public Grafo(int tamanho){
        
        MEsparsa aux = new MEsparsa(tamanho);
        
        this.tamanho = tamanho;
        this.matriz = aux;
        this.vertices = new Vertice[tamanho];
       
        for(int i = 0; i < tamanho; i++){vertices[i] = new Vertice();}   
    }
    
    
    void criaAdjacencia(int i, int j, int p){this.matriz.lista[i].inserir(j, p);}
    
    void removeAdjacencia(int i, int j){this.matriz.lista[i].remover(j);}
    
    void imprimeAdjacencias(){
        for(int  i = 0; i < this.tamanho; i++){
            Knot aux = this.matriz.lista[i].primeiro;
            System.out.print(i+"º Linha: ");
            while(aux != null){
                System.out.print(" {adjacente: "+aux.adjacente+
                                 ", valor: "+aux.valor+"} ");
                aux = aux.proximo;
            }
            System.out.println("");
        }
    }
    
    void setaInformacao(int i, String v){vertices[i].setNome(v);}
  
    int adjacentes(int i, int[] adj){
        
        int count = 0,index = 0; // valor que indentifica não relação("um 0").
        for(int s = 0; s < adj.length; s++){ adj[s] = Integer.MIN_VALUE; }
        
        Knot aux = this.matriz.lista[i].primeiro;
        while(aux != null){ 
            adj[index++] = aux.adjacente; count++; aux = aux.proximo;
        }
        
        return count;
    }
    
    int mostraValor(int i, int j){return this.matriz.lista[i].mostraValor(i, j);}
    
    //Grupo 14: Gustavo Hammerschmidt, Kalebe Rodrigues.
    
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
        
        Grafo l = new Grafo(6);      // Objeto Grafo.
        
       
        l.criaAdjacencia(0, 4, 3);
        l.criaAdjacencia(2, 0, 6);   // Caminhos e Pesos.
        l.criaAdjacencia(4, 2, 4);
        l.criaAdjacencia(4, 5, 10);
        l.criaAdjacencia(2, 3, 1);
        l.criaAdjacencia(5, 2, 3);
        l.criaAdjacencia(3, 5, 1);
        l.criaAdjacencia(3, 1, 7);
        l.criaAdjacencia(1, 4, 8);
        
        l.imprimeAdjacencias();    // Imprime Matriz de Adjacência.
        
        System.out.println("\n");
        l.criaAdjacencia(0, 0, 90);
        l.imprimeAdjacencias();
        System.out.println("");      // Teste da função remover().
        l.removeAdjacencia(0, 0);
        l.imprimeAdjacencias();
                                             // Mostra valor de uma aresta. 
        System.out.print("\n\nMostra valor de (i: 2, j: 3): "+l.mostraValor(2, 3));
        System.out.print("\nMostra valor de (i: 1, j: 4): "+l.mostraValor(2, 3)+"\n");
        
        
        System.out.println("\n");
        l.setaInformacao(4, "x");
        l.setaInformacao(5, "y");    //  Teste dos Rótulos.
        System.out.println("Nome do nó "+4+": "+l.vertices[4].getNome());
        System.out.println("Nome do nó "+5+": "+l.vertices[5].getNome()+"\n\n");
       
        int[] adj = new int[6];        // Vetor das Adjacências.
        System.out.println("Número de adjacentes: "+l.adjacentes(0, adj));
        System.out.println("");
        System.out.print("Vetor adj:   ");
        for(int i = 0; i < adj.length; i++){
            if(adj[i] == Integer.MIN_VALUE){System.out.print(" inf");}
            else{ System.out.print(" "+adj[i]);}
        }
        System.out.println("\n");
        
    }
    
}
