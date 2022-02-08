package Projeto;

import java.util.ArrayList;
import java.util.Random;
 
public class GerarGrafoAleatoriamente {

	private static int nKnots, nArestas, boundary = 100000; // Número de vértices, arestas e limite de random.
	private static boolean conexo, direcionado;   // Conexo e direcionado.
	private static Grafo gRandom;  // Grafo de retorno.
	
	// Uso: inicializa todas as variáveis.
	private static void setAll(int k, int a, boolean c, boolean d){
		nKnots = k;  nArestas = a;  conexo = c;  direcionado = d;
	}
	
	// Uso: define o limite do boundary do Random().nextInt().
	public static void setBoundary(int b) { boundary = b; }
	
	// Uso: retorna o grafo.
	private static Grafo getGraph() { return gRandom; }

	// Uso: constroi o grafo.
	private static void build() {
		
		gRandom = new Grafo(nKnots, direcionado);
		
		Random rd = new Random();
		
		if(conexo) {
			
			ArrayList<Integer> arestas_ = new ArrayList<>();
			
			for(int i = 0; i < nKnots; i++) { arestas_.add(i); }
			
			int vertice_i = rd.nextInt(arestas_.size()), vertice_j, index;
			arestas_.remove(vertice_i);
			
			while(!arestas_.isEmpty()) {
				index = rd.nextInt(arestas_.size());
				vertice_j = arestas_.get(index);
				arestas_.remove(index);
				gRandom.criaAdjacencia(vertice_i, vertice_j, rd.nextInt(boundary));
				vertice_i = vertice_j;
			}
			
			for(int i = nKnots-1; i < nArestas; i++) {
				
				gRandom.criaAdjacencia(rd.nextInt(nKnots), rd.nextInt(nKnots), rd.nextInt(boundary));
				
			}
			
			if(!gRandom.ehConexo()) { build(); }
			
		}
		else {
			
			int breakpoint = nArestas;
			while(breakpoint > 0) {
				
				gRandom.criaAdjacencia(rd.nextInt(nKnots), rd.nextInt(nKnots), rd.nextInt(boundary));
				breakpoint--;
			}
			
			if(nArestas == 1) { return; }
			if(gRandom.ehConexo()) { build(); }
	
		}
		
	}
			
	// Uso: função de acesso público à classe que define a ordem de execução e trata exceções.
	public static Grafo makeARandomGraph(int nKnots, int nArestas, boolean conexo, boolean direcionado){
		
		if(nKnots == 0 || nArestas == 0) { 
			String s = (nKnots == 0) ? "\n\nAtenção: Grafo possui 0 vértices,"
					+ " automaticamente será conexo.\n\n" : "";
			System.out.print(s);
			return new Grafo(nKnots, direcionado);
		}
		
		if(nKnots-1 > nArestas) {  
		
			System.out.print("\n\nAtenção: número de arestas menor do que nós;"
						   + " seu grafo será automaticamente desconexo!\n\n");
			setAll(nKnots, nArestas, false, direcionado);
			
		}
		else { setAll(nKnots, nArestas, conexo, direcionado); }
		
		if(!conexo && nArestas > nKnots*2) {
			
			System.out.print("\n\nAtenção: número de arestas é muito alto "
					+ "para o grafo ser não conexo.\n"
					+ "(2x mais arestas do que nKnots.)\n"
					+ "Seu Grafo será inicializado com 0 arestas.\n\n");
			
			nArestas = (int)(nKnots);
			
			return new Grafo(nKnots, direcionado);
			
		} 
		
		
		build(); 
		
		return getGraph();
		
	}
	
	
}
