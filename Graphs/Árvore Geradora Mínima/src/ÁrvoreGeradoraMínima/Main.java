package ÁrvoreGeradoraMínima;

import static ÁrvoreGeradoraMínima.MST_Kruskal.MST_Kruskal;
import static ÁrvoreGeradoraMínima.MST_Prim.MST_Prim;

public class Main {
    
    // Main.
    public static void main(String[] args) {

        System.out.println("\n\n\nInício:\n\n");

        int vertices = 9; // Tamanho do grafo.

        Grafo inicio = new Grafo(vertices); // Criando um grafo.
        
        // Inserção de Adjacências.
        inicio.criaAdjacencia(0, 1, 2);
        inicio.criaAdjacencia(0, 2, 1);
        inicio.criaAdjacencia(1, 3, 3);
        inicio.criaAdjacencia(1, 4, 2);
        inicio.criaAdjacencia(2, 3, 1);
        inicio.criaAdjacencia(2, 5, 2);
        inicio.criaAdjacencia(3, 4, 4);
        inicio.criaAdjacencia(3, 5, 3);
        inicio.criaAdjacencia(4, 6, 1);
        inicio.criaAdjacencia(5, 6, 3);
        inicio.criaAdjacencia(6, 7, 2);
        inicio.criaAdjacencia(6, 8, 2);
        inicio.criaAdjacencia(7, 8, 1);
        
        // Não direcionado -> aresta(0,1,2) && aresta(1,0,2).
        String[] c = new String[]{"Direcionadas", "Não Direcionadas"};
        
        int choice = 1;
       
        System.out.print("Adjacências("+c[choice]+") do Grafo Início:\n");
        // Imprime as adjacências do grafo início.
        if(c[0].equals(c[choice])){  inicio.imprimeAdjacencias(); }
        else{  inicio.imprimeAdjacenciasNaoDirecionadas(); }
        
        // Grafo MST pelo Algoritmo de Kruskal.
        Grafo MST_Kruskal = MST_Kruskal(inicio);

        System.out.print("\n\n\nAdjacências("+c[choice]+") do Grafo MST_Kruskal:\n");
        // Imprime as adjacências do grafo MST_Kruskal.
        if(c[0].equals(c[choice])){  MST_Kruskal.imprimeAdjacencias(); }
        else{  MST_Kruskal.imprimeAdjacenciasNaoDirecionadas(); }
        
                
        // Grafo MST pelo Algoritmo de Prim.
        Grafo MST_Prim = MST_Prim(inicio);

        System.out.println("");

        System.out.print("\n\n\nAdjacências("+c[choice]+") do Grafo MST_Prim:\n");
        // Imprime as adjacências do grafo MST_Prim.
        if(c[0].equals(c[choice])){  MST_Prim.imprimeAdjacencias(); }
        else{  MST_Prim.imprimeAdjacenciasNaoDirecionadas(); }
        
        System.out.println("\n\n\nFim.\n");

    }

}
