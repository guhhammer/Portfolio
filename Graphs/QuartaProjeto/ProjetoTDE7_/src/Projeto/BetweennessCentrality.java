package Projeto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class BetweennessCentrality{
	
	private static ArrayList<Object[]> percursos = new ArrayList<>(); // Todos os percursos poss�veis.
	
	private static Stack<Integer> percurso = new Stack<>(); // Percurso de i a j.

	private static Grafo g; // Grafo de uso.
	
	// Uso: seta o grafo.
	private static void setGrafo(Grafo gr) { g = gr; }
	
	// Uso: encontra todos os caminhos entre i e j. Para grafo direcionado e n�o-direcionado.
	private static void find(int f, boolean show) {
		
		if(percurso.peek() != f) {
			
			MEsparsa.Knot aux = g.matriz.lista[percurso.peek()].primeiro;
			while(aux != null) {
				
				if(!percurso.contains(aux.adjacente)) {
					
					percurso.push(aux.adjacente);
					
					if(show) {System.out.println(Arrays.deepToString(percurso.toArray()));}
					
					find(f, show);
					
				}
				
				aux = aux.proximo;
			}
			if(aux == null) { percurso.pop(); if(show) {System.out.println(Arrays.deepToString(percurso.toArray()));}}
			
		}
		else {
			percursos.add(percurso.toArray());
			percurso.pop();
			if(show) {System.out.println(Arrays.deepToString(percurso.toArray()));}
		}
	}
	
	// Uso: define a ordem de execu��o dos m�todos.
	private static ArrayList<Object[]> step(Grafo g, int i, int f, boolean show) {
			
		percursos.clear();
		percurso.clear();
		
		setGrafo(g);
		
		if(i == f) { return new ArrayList<>(); }
		
		percurso.push(i);
		
		find(f, show);
		
		return percursos;
		
	}
	
	// Uso: retorna os caminhos geod�sicos.
	private static ArrayList<Object[]> geodesicos(ArrayList<Object[]> aux){
		
		ArrayList<Object[]> retorno = new ArrayList<>();
		
		if(aux != null) {
		
			ArrayList<Integer> sizes = new ArrayList<>();
			
			for(Object[] a : aux) { sizes.add(a.length); }
			
			int smallest = 999999999;
			for(Integer i : sizes) { if(i < smallest) { smallest = i; } }
			
			for(Object[] a : aux) { if(a.length == smallest) { retorno.add(a); } }
		
		}
		
		return retorno;
		
	}
	
	// Uso: retorna valor do intermed�rio n em lista de geod�sicos.
	public static float intermediario(int n, ArrayList<Object[]> aux) {
		
		float appereance = 0.0f, size = (float) aux.size();
		
		for(Object[] a : aux) {	
			
			if(a.length < 3) { break; }
			
			for(int i = 1; i <= a.length-1; i++) { if( (int) a[i] == n) { appereance++;} }
			
		}
		
		return appereance/size;
	
	}
	
	// Uso: retorna o C.I. de um v�rtice.
	public static float betweennessDe_(Grafo g, int vertice, boolean showStack) {
		
		// C�lculo de Centralidade de Intermedia��o.
		float soma = 0.0f, temp;
		String str = "";
		for(int i = 0; i < g.tamanho; i++) {
			
			for(int j = 0; j < g.tamanho; j++) {
				
				if(i != j) {
					
					temp = intermediario(vertice, geodesicos(step(g, i, j, showStack)));
					str = temp+"";
					
					soma += (str.matches("[-+]?[0-9]*\\.?[0-9]+")) ? temp : 0.0f;
				
				}
				
			}
			
		}
		
		soma = (float) soma/2;
		
		float ci = soma/((g.tamanho-1)*(g.tamanho-2));
		
		return (g.direcionado) ? ci : 2*ci;
		
	}
	
	// Uso: retorna os C.I.s dos v�rtices de um grafo.
	public static void centralidadeDeIntermediacao(Grafo g, boolean showStacks) {
		
		System.out.println("\n\nCentralidade de Intermedia��o:\n");
		for(int i = 0; i < g.tamanho; i++) {
			
			System.out.print(String.format("C.I. do v�rtice %s:   %f\n",
							                       i, betweennessDe_(g, i, showStacks)));
			
		}
		System.out.println("\n\n");
		
	}
	
}