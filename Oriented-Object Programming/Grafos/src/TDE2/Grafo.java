
package TDE2;   //Grupo 14: Gustavo Hammerschmidt.

import TDE2.MEsparsa.*;         // Importação dos algoritmos e da estrutura.
import static TDE2.Warshall.warshall_fechamento;
import static TDE2.Dijkstra.imprimeCaminho;
import static TDE2.Dijkstra.imprimeDistancia;

public class Grafo{
    
    
    int tamanho;
    Vertice[] vertices;  // Construtor, classe Vertice e atributos.
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
    
    
    
    
    /*
        CriaAdjacencia, removeAdjacencia, imprimeAdjacencia, 
        setaInformacao, adjacentes e mostraValor são as seis funções
        do Mini-trabalho anterior.
    
    */
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
    
    int mostraValor(int i, int j){return this.matriz.lista[i].mostraValor(j);}
    
    
    
    // Função usada para imprimir matriz de fechamento.
    static void imprime_matriz(boolean[][] m){
        System.out.print("\n\t");
        for(int i = 0; i < m.length; i++){
            System.out.print(i+"    ");
        }
        System.out.println("");
        for(int i = 0; i < m.length; i++){
            System.out.print(i+":   ");
            for(int j = 0; j < m.length; j++){
                System.out.print(" "+m[i][j]);
            }
            System.out.println("\n");
        }
        System.out.println("");
    }
   
    
    
    
    //Grupo 14: Gustavo Hammerschmidt.
    
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
        
       /*
        l.criaAdjacencia(0, 4, 3);
        l.criaAdjacencia(2, 0, 6);   // Caminhos e Pesos.
        l.criaAdjacencia(4, 2, 4);
        l.criaAdjacencia(4, 5, 10);
        l.criaAdjacencia(2, 3, 1);
        l.criaAdjacencia(5, 2, 3);
        l.criaAdjacencia(3, 5, 1);
        l.criaAdjacencia(3, 1, 7);
        l.criaAdjacencia(1, 4, 8);
        */
        
        
        l.criaAdjacencia(0, 1, 2);
        l.criaAdjacencia(2, 1, 2);
        
        l.criaAdjacencia(0, 2, 6);
        l.criaAdjacencia(2, 0, 6);  
        
        l.criaAdjacencia(2, 4, 3);
        l.criaAdjacencia(4, 2, 3);
        
        l.criaAdjacencia(4, 5, 1);
        l.criaAdjacencia(5, 4, 1);
        
        l.criaAdjacencia(5, 3, 1);
        l.criaAdjacencia(3, 5, 1);
        
        l.criaAdjacencia(1, 2, 4);
        l.criaAdjacencia(2, 1, 4);
        
        l.criaAdjacencia(1, 3, 8);
        l.criaAdjacencia(3, 1, 8);
        
        l.criaAdjacencia(0, 5, 12);
        l.criaAdjacencia(5, 0, 12);
        
        l.criaAdjacencia(1, 5, 3);
        l.criaAdjacencia(1, 5, 3);
       
        
        System.out.println("");
        l.imprimeAdjacencias();    // Imprime Matriz de Adjacência.
        
       
        
        /* 
        
            Teste das 6 funções anteriores: estão comentadas 
            pois não são o foco específico deste trabalho.
            
        */
        
        /*
        
                                            // Mostra valor de uma aresta. 
        System.out.print("\n\nMostra valor de (i: 2, j: 3): "+
                                                        l.mostraValor(2, 3));
        
        System.out.print("\nMostra valor de (i: 1, j: 4): "+
                                                    l.mostraValor(2, 3)+"\n");
        
        
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
        
        */
        
        Grafo marshall = new Grafo(6);
        
        marshall.criaAdjacencia(0, 1, 1);
        marshall.criaAdjacencia(1, 2, 1);
        marshall.criaAdjacencia(1, 3, 1);
        marshall.criaAdjacencia(2, 4, 1);
        marshall.criaAdjacencia(4, 3, 1);
        marshall.criaAdjacencia(5, 2, 1);
        marshall.criaAdjacencia(5, 0, 1);
        
        
        // Criação de uma matriz de fechamento.
        boolean[][] fechamento = new boolean[l.tamanho][l.tamanho];
        
        // Algoritmo de Warshall que atualiza a matriz de fechamento.
        warshall_fechamento(marshall, fechamento);
        
        // Comando que imprime a matriz de fechamento do Algoritmo de Warshall.
        imprime_matriz(fechamento);
        
        
        // Os comandos abaixo referem-se ao algoritmo de Dijkstra.
        
        int pontoInicial = 0;
        int pontoFinal = 5;
        
        
        // Imprime o melhor caminho de a a b.
        imprimeCaminho(l, pontoInicial, pontoFinal);
        
        // Imprime a distância do melhor caminho.
        imprimeDistancia(l, pontoInicial, pontoFinal);
        System.out.println("");
        
    }
    
}
