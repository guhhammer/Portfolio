package Projeto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Ciclos{

	private static Grafo g;  // Grafo de retorno.
	
	private static boolean temCiclo = false; // Flag de ciclo.
 
	private static ArrayList<Integer> percurso = new ArrayList<>(); // Percurso de vértices.
	
	private static Queue<Integer> filhos = new LinkedList<Integer>(); // Filhos de um vértice.
	
	// Uso: seta o grafo.
	private static void setGrafo(Grafo gr) { g = gr; } 
	
	// Uso: seta flag de ciclo.
	private static void setCycle() { temCiclo = false; }
	
	// Uso: retorna flag de ciclo.
	private static boolean getCycle() { return temCiclo; }

	// Uso: checa se vértice possui laço.
	private static void checkSelfReference(int i) {
		
		MEsparsa.Knot aux = g.matriz.lista[i].primeiro;
		while(aux != null) {
			
			if(aux.adjacente == i) { temCiclo = true; return; }
			
			aux = aux.proximo;
			
		}
		
	}
	
	// Uso: busca de ciclo para grafo direcionado.
	private static void busca_d(int inicio) {
		
		if(filhos.peek() == inicio && percurso.size() != 0) {  temCiclo = true;  return; }
		
		if(!percurso.contains(filhos.peek())) {
			
			percurso.add(filhos.peek());
			
			MEsparsa.Knot aux = g.matriz.lista[g.findVertice(filhos.peek())].primeiro;
			
			while(aux != null) { filhos.offer(aux.adjacente); aux = aux.proximo; }
		
		}
		
		filhos.poll();
		
		if(filhos.peek() != null) { busca_d(inicio); }
		
	}
	
	// Uso: define ordem de execução de busca direcionada.
	private static boolean buscaDirecionada(Grafo g){
		
		setGrafo(g);
		
		setCycle();
		
		for(int i = 0; i < g.tamanho; i++) {
			
			checkSelfReference(i);
			
			if(getCycle()) { break; }
			
			filhos.offer(i);
			
			busca_d(i);
			
			filhos.clear();
			
			percurso.clear();
			
		}
		
		return getCycle();
		
	}
	
	// Uso: função de acesso público à classe.
	public static boolean hasCycleDirected(Grafo g) { return buscaDirecionada(g); }
	
	
	private static Queue<Integer[]> filhosN = new LinkedList<Integer[]>(); // Filhos de um vértice.
		
	// Uso: busca de ciclo para grafo não direcionado.
	private static void busca_nd(int inicio) {
		
		if(filhosN.peek()[1] == inicio && percurso.size() != 0) { temCiclo = true; return; }
					
		percurso.add(filhosN.peek()[1]);
		
		MEsparsa.Knot aux = g.matriz.lista[g.findVertice(filhosN.peek()[1])].primeiro;
		
		while(aux != null) { 
							
			if(aux.adjacente != filhosN.peek()[0]) { 
				filhosN.offer(new Integer[] { filhosN.peek()[1], aux.adjacente}); 
			}
			
			aux = aux.proximo; 
			
		}

		filhosN.poll();
		
		if(filhosN.peek() != null) { busca_nd(inicio); }
		
	}
	
	// Uso: define ordem de execução de busca não-direcionada.
	private static boolean buscaNaoDirecionada(Grafo g) {
		
		setGrafo(g);
		
		setCycle();
		
		for(int i = 0; i < 1; i++) {
			
			if(getCycle()) { break; }
			
			filhosN.offer(new Integer[] {i, i});
			
			busca_nd(i);
			
			filhosN.clear();
			
			percurso.clear();
				
		}
		
		
		return getCycle();
		
	}
	
	// Uso: função de acesso público a classe.
	public static boolean hasCycleNonDirected(Grafo g) { return buscaNaoDirecionada(g); }
	
	
}
