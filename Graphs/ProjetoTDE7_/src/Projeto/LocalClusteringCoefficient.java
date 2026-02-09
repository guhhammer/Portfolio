package Projeto;

import java.util.ArrayList;

public class LocalClusteringCoefficient {

	
	
	private static Grafo g;
	
	
	private static void setGrafo(Grafo gr) { g = gr;}
	
	
	private static float coefficient(int vertice) {
		
		ArrayList<Integer> neighbourhood = new ArrayList<>();
		
		MEsparsa.Knot aux = g.matriz.lista[vertice].primeiro;
		
		while(aux != null) {
			if(!neighbourhood.contains(aux.adjacente)) { neighbourhood.add(aux.adjacente); }
			aux = aux.proximo;
		}
		
		int Ki = neighbourhood.size();
		
		int count = 0;
		
		for(int i = 0; i < neighbourhood.size(); i++) {
			
			MEsparsa.Knot aux2 = g.matriz.lista[neighbourhood.get(i)].primeiro;
			
			while(aux2 != null) {
				
				if(neighbourhood.contains(aux2.adjacente) && aux2.adjacente != vertice) { count++; }
				
				aux2 = aux2.proximo;
				
			}
			
		}
		
		if(g.direcionado) { return (Ki*(Ki-1)*1.0f == 0.0f) ? 0.0f : count/(Ki*(Ki-1)*1.0f); }
		else { return ((Ki*(Ki-1))/2*1.0f == 0.0f) ? 0.0f : (count)/(Ki*(Ki-1)*1.0f); }
		
	}
	
	
	private static void showCoefficients() {
		
		
		System.out.println("\n\nLocal Clustering Coefficient:\n");
		
		for(int i = 0; i < g.tamanho; i++) {
			
			System.out.println("Vértice "+i+":  "+coefficient(i));
			
		}
		
		System.out.println("\n\n");
		
	}

	
	public static void localClusteringCoefficient(Grafo g) {
		
		setGrafo(g);
		
		showCoefficients();
		
	}
	
	
	private static float a_coefficient() {
		
		float sum = 0.0f;
		for(int i = 0; i < g.tamanho; i++) { sum += coefficient(i);}
		
		return (1.0f/g.tamanho)*sum;
		
	}
	
	public static void averageClusteringCoefficient(Grafo g) {
		
		setGrafo(g);
		
		System.out.println("\n\nCoeficiente de clusterização médio do grafo:  "+a_coefficient()+"\n\n");
		
	}
	
	
	
	
	public static void main(String[] args) {
	    
        int vertices = 15; // Tamanho do grafo. 
        boolean direcionado = false;
        
        Grafo inicio = new Grafo(vertices, direcionado); // Criando um grafo. 
        
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
        
        inicio.criaAdjacencia(9, 10, 5);
        inicio.criaAdjacencia(10, 11, 1);
        inicio.criaAdjacencia(9, 12, 3);
        inicio.criaAdjacencia(10, 12, 2);
        
        inicio.criaAdjacencia(13, 14, 5);
		localClusteringCoefficient(inicio);
     
		
		averageClusteringCoefficient(inicio);
		
	}
	
	
}
