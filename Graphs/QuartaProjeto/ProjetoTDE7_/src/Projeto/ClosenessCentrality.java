package Projeto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ClosenessCentrality {

	private static Grafo g; // Grafo de uso.
	
	private static ArrayList<Integer> possibilidades = new ArrayList<>(); // fins de caminho poss�veis.
	
	private static Queue<Integer> p = new LinkedList<Integer>(); // contr�i possibilidades.
	
	// Uso: faz busca em largura de poss�veis caminhos. Somente utilizado para grafos direcionados.
	private static void busca() {
		
		if(p.peek() == null) {  return; }
		
		if(!possibilidades.contains(p.peek())) {
			
			possibilidades.add(p.peek());
			
			MEsparsa.Knot aux = g.matriz.lista[p.peek()].primeiro;
			while(aux != null) {  p.offer(aux.adjacente);  aux = aux.proximo;  }
			
		}
		
		p.poll();
		
		if(p.peek() != null) { busca();}
		
	}
	
	// Uso: encontra alcan�abilidade de um v�rtice.
	private static boolean findReachability(int inicio, int fim) {
		
		possibilidades.clear();  p.clear();
		
		p.offer(inicio);
		
		busca();
		
		for(Integer i : possibilidades) { if(i == fim) { return true; } }
		
		return false;
		
	}
	
	// Uso: seta o grafo de uso.
	private static void setGrafo(Grafo gr) { g = gr; } 
	
	// Uso: C.P. para grafo n�o-direcionado.
	private static float closenessDe_(int vertice) {
		
		int[] actual = null; // componenente do v�rtice.
		boolean flag = false;
		for(Object x_ : g.getComponentes()) { 
		   actual = (int[]) x_;
		   for(int i = 0; i < actual.length; i++) { if(actual[i] == vertice) { flag = true; }  if(flag) { break; } }
		   if(flag) { break; }
		}
	
		float soma = 0.0f; // soma das dist�ncias entre os elementos do componente e o v�rtice. 
		for(int i = 0; i < actual.length; i++) { 
			if(actual[i] != vertice) { soma += Dijkstra.distancia(g, vertice, actual[i])*1.0f; }
		}
		
		return (soma == 0.0f) ? 0.0f : (float) Math.pow(soma, -1);

	}
	
	// Uso: C.P. para grafo direcionado.
	private static float closenessDe_Directed(int vertice) {
		
		int[] actual = null; // componenente do v�rtice.
		boolean flag = false;
		for(Object x_ : g.getComponentes()) { 
		   actual = (int[]) x_;
		   for(int i = 0; i < actual.length; i++) { if(actual[i] == vertice) { flag = true; }  if(flag) { break; } }
		   if(flag) { break; }
		}
		
		float soma = 0.0f; // soma das dist�ncias entre os elementos do componente e o v�rtice. 
		for(int i = 0; i < actual.length; i++) { 
			if(actual[i] != vertice && findReachability(vertice, actual[i])) {
				soma += Dijkstra.distancia(g, vertice, actual[i])*1.0f;
			}  // a soma s� ocorre se o v�rtice actual[i] for alcan��vel.
		}
		
		return (soma == 0.0f) ? 0.0f : (float) Math.pow(soma, -1);

	}
	
	// Uso: retorna os C.P.s de todos os v�rtices do grafo.
	private static void closenessAll(boolean direcionado) {
		
		System.out.println("\n\nCentralidade de Proximidade:\n");
		for(int i = 0; i < g.tamanho; i++) {
			System.out.print(String.format("C.P. do v�rtice %d:   %f\n", i,
							(direcionado) ? closenessDe_Directed(i) : closenessDe_(i)));
		}
		System.out.println("\n\n");
	}
	
	// Uso: define a ordem de execu��o dos m�todos da classe.
	private static void steps(Grafo g) {
		
		setGrafo(g);
		
		closenessAll(g.direcionado);
		
	}
	
	// Uso: fun��o de acesso p�blico � classe.
	public static void centralidadeDeProximidade(Grafo g) { steps(g); }
		
}