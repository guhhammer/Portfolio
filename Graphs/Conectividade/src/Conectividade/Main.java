
package Conectividade;

public class Main {
    
    // Main.
    public static void main(String[] args) {
        
        System.out.println("\n\n\nInício:\n\n");

        int vertices = 15; // Tamanho do grafo. 

        Grafo inicio = new Grafo(vertices); // Criando um grafo.
        
        // Inserção de Adjacências.
        inicio.criaAdjacenciaNaoDirecionada(0, 1, 2);
        inicio.criaAdjacenciaNaoDirecionada(0, 2, 1);
        inicio.criaAdjacenciaNaoDirecionada(1, 3, 3);
        inicio.criaAdjacenciaNaoDirecionada(1, 4, 2);
        inicio.criaAdjacenciaNaoDirecionada(2, 3, 1);
        inicio.criaAdjacenciaNaoDirecionada(2, 5, 2);
        inicio.criaAdjacenciaNaoDirecionada(3, 4, 4);
        inicio.criaAdjacenciaNaoDirecionada(3, 5, 3);
        inicio.criaAdjacenciaNaoDirecionada(4, 6, 1);
        inicio.criaAdjacenciaNaoDirecionada(5, 6, 3);
        inicio.criaAdjacenciaNaoDirecionada(6, 7, 2);
        inicio.criaAdjacenciaNaoDirecionada(6, 8, 2);
        inicio.criaAdjacenciaNaoDirecionada(7, 8, 1);
        
        inicio.criaAdjacenciaNaoDirecionada(9, 10, 5);
        inicio.criaAdjacenciaNaoDirecionada(10, 11, 1);
        inicio.criaAdjacenciaNaoDirecionada(9, 12, 3);
        inicio.criaAdjacenciaNaoDirecionada(10, 12, 2);
        
        inicio.criaAdjacenciaNaoDirecionada(13, 14, 5);
               
         
        
        // Imprime os componentes e a quantidade.
        inicio.printComponentes(); 
        
        
        
        System.out.println("\n\n\nFim.\n");
        
    }
    
}
