
package Projeto;

public class GrafoEuleriano {
	
	
	private static int numeroVerticesImpares; // Contador de vértices ímpares.
	 
	private static Grafo g; // Grafo de retorno.
	
	// Uso: seta o contador.
	private static void setNumV() { numeroVerticesImpares = 0; } 
	
	// Uso: seta o grafo.
	private static void setGrafo(Grafo grafo) { g = grafo; }
	 
	// Uso: conta o número de vértices com grau ímpar no grafo.
	private static void contarImpares() {
			
		int hold;
		for(int i = 0; i < g.tamanho; i++) {
			hold = g.matriz.lista[i].quantidade();
			if(hold % 2 != 0) { numeroVerticesImpares++; }
		}
		
	}
	
	// Uso: retorna o contador.
	private static int getVertices(){ return (int) Math.ceil(numeroVerticesImpares/2); }
	
	// Uso: define a ordem de execução dos métodos da classe.
	private static int steps(Grafo g) {
		
		setNumV();
		
		setGrafo(g);
		
		contarImpares(); 
		
		return getVertices();
		
	}
	
	// Uso: função de acesso público à classe.
	public static boolean isEulerian(Grafo g) { 
		
		int var = steps(g); 
		
		if(g.direcionado) { 
    		System.out.print("\n\nAtenção: Grafo é direcionado, não há verificação se é euleriano.(Retorno: false).\n\n");
    		return false;
    	}
		
		return (var == 0) ? true : ((var == 2)); 
	
	}
	
}
