package Projeto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class PontesAereas {

	private static ArrayList<Object[]> percursos = new ArrayList<>(); // Todos os percursos possíveis.
	
	private static Stack<Integer> percurso = new Stack<>(); // Percurso de i a j.

	private static Grafo g; // Grafo de uso.
	
	// Uso: seta o grafo.
	private static void setGrafo(Grafo gr) { g = gr; }
	
	// Uso: encontra todos os caminhos entre i e j. Para grafo direcionado e não-direcionado.
	private static void find(int f, int distancia, int limitador, boolean show, 
									boolean switchConnections, String[] connections) {
		
		if(filter(percursos).size() == limitador) { return; }
		
		if(percurso.peek() != f) { 
			
			MEsparsa.Knot aux = g.matriz.lista[percurso.peek()].primeiro;
			boolean jump = false;
			while(aux != null) {
				
				if(!percurso.contains(aux.adjacente)) {
					
					percurso.push(aux.adjacente);
					
					if(percurso.size() == distancia+1 && percurso.peek() != f) {
						percurso.pop(); jump = true;
					}
					
					
					if(show) {System.out.println(Arrays.deepToString(percurso.toArray()));}
					
					if(!jump) { find(f, distancia, limitador, show, switchConnections, connections); }
					
					jump = false;
					
				}
				
				aux = aux.proximo;
		
			}
			
			if(aux == null) { percurso.pop(); if(show) {System.out.println(Arrays.deepToString(percurso.toArray()));}}
			
		}
		else {
			percursos.add(percurso.toArray());
			
			if(switchConnections) { percursos = filter_(percursos, connections); }
			else { percursos = filter(percursos); }
			
			percurso.pop();
			if(show) {System.out.println(Arrays.deepToString(percurso.toArray()));}
		}

	}
		
	// Uso: define a ordem de execução dos métodos.
	private static ArrayList<Object[]> step(Grafo g, int i, int f, int distancia, int limitador, 
								boolean show, boolean switchConnections, String[] connections) {
			
		percursos.clear();
		percurso.clear();
		
		setGrafo(g);
		
		if(i == f) { return new ArrayList<>(); }
		
		percurso.push(i);
		
		find(f, distancia, limitador, show, switchConnections, connections); 
		
		return percursos;
		
	}
	
	// Uso: transforma array de objetos em string.
	private static String stringfyMe(Object[] aux) {
		
		String route = "\tP =>\t  ";
		for(int i = 0; i < aux.length; i++) {
			
			route += g.vertices[(int)aux[i]].getNome();
		
			if(i != aux.length-1) { route += " -> "; }
			
		}
		
		return route+"\t=> D."; 
		
	}
	
	// Uso: imprime uma quantidade(qntd) de objetos de um array de objetos aux.
	private static void basicPrint(ArrayList<Object[]> aux, int qntd) {
		
		int select = (qntd <= aux.size()) ? qntd : aux.size();
		
		if(qntd == 0 || aux.size() == 0) { 
			
			System.out.println("\n\nNenhuma Ponte Aérea encontrada!\n\n"); return;
		
		}
	
		for(int i = 0; i < select; i++) { System.out.println(stringfyMe(aux.get(i))); }
		System.out.println("\n");
		
	}

	// Uso: verifica se array de objetos contém int j.
	private static boolean contains(Object[] aux, int j) {
		
		for(int i = 0; i < aux.length; i++) { if(j == (int) aux[i]) { return true; }} return false;
		
	}
	
	// Uso: filtra objetos repetidos em ArrayList.
	private static ArrayList<Object[]> filter(ArrayList<Object[]> aux){
		
		ArrayList<Object[]> ret = new ArrayList<>();
		
		boolean check;
		for(int x = 0; x < aux.size(); x++) {
			
			check = true;
			for(int y = 0; y < ret.size(); y++) {
				
				if(x != y) { if(Arrays.equals(aux.get(x), ret.get(y))) { check = false; } }
				
			}
			
			if(check) { ret.add(aux.get(x)); }
			
		}
		
		return ret;

	}
	
	// Uso: filtra objetos repetidos e seleciona aqueles que possuam todos os objetos de conexões.
	private static ArrayList<Object[]> filter_(ArrayList<Object[]> aux, String[] conexoes){
		
		aux = filter(aux);
		
		ArrayList<Object[]> retorno = new ArrayList<>();
		
		boolean passed;
		for(int i = 0; i < aux.size(); i++) {
			passed = true;
			for(int j = 0; j < conexoes.length; j++) {
				passed = (contains(aux.get(i), g.findVertice(conexoes[j])));
				if(!passed) { break;}
			}
			if(passed) { retorno.add(aux.get(i)); }
		}
		return retorno;
		
	}

	// Uso: função de acesso público à classe que imprime linha aérea.
	public static void distancia(Grafo g, String comeco, String destino, int distancia, 
												int qntdDeCaminhosAMostrar, boolean show) {
		
		setGrafo(g);
		
		System.out.println(String.format("\nLinhas Aéreas de distância (até) %d: \n",distancia));
		
		basicPrint(filter(step(g, g.findVertice(comeco), g.findVertice(destino),
					distancia, qntdDeCaminhosAMostrar, show, false, new String[]{})), qntdDeCaminhosAMostrar);
		
	}
	
	// Uso: função de acesso público à classe que imprime linha aérea com conexôes.
	public static void distanciaEConexoes(Grafo g,String comeco, String[] conexoes, String destino, 
										int distancia, int qntdDeCaminhosAMostrar, boolean show) {
		
		setGrafo(g);
		System.out.println(String.format("\nLinhas Aéreas com "
									   + "as conexões( %s ) de distância (até) %d: \n",
									   Arrays.toString(conexoes).replace("[","").replace("]", ""),
									   distancia));
		
		basicPrint(filter_(step(g, g.findVertice(comeco), g.findVertice(destino),
				distancia, qntdDeCaminhosAMostrar, show, true, conexoes), conexoes), qntdDeCaminhosAMostrar);
		
	}
	
}
