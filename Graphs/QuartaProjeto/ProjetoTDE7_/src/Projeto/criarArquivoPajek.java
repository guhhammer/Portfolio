package Projeto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class criarArquivoPajek {

	private static Grafo g;  // Grafo de retorno.
	
	private static String path; // Caminho para salvar arquivo.
	
	// Uso: seta o caminho para salvar arquivo.
	private static void setPath(String name) {	
		path = "src/Projeto/"+name+".pajek";
	}
	
	// Uso: seta o grafo.
	private static void setGrafo(Grafo gr) { g = gr;}
	
	// Uso: escreve informações em um arquivo .pajek.
	private static void writePajek(boolean direcionado) throws FileNotFoundException {
		
		File file = new File(path);
		
		PrintWriter writer = new PrintWriter(file);
		
		writer.println("*Vertices   "+g.tamanho);
		
		for(int i = 0; i < g.tamanho; i++) {
			
			writer.println(i+" \""+g.vertices[i].getNome()+"\"");
			
		}
		
		if(direcionado) { writer.println("*Arcs"); }
		else {writer.println("*Edges"); }
		
		for(int i = 0; i < g.tamanho; i++) {
			
			MEsparsa.Knot aux = g.matriz.lista[i].primeiro;
			
			while(aux != null) {
				
				writer.println(i+" "+aux.adjacente+" "+aux.valor);
				
				aux = aux.proximo;
			
			}
			
		}
		
		writer.close();	
		
	}
	
	// Uso: função de acesso público à classe que define a ordem de execução dos métodos.
	public static void writePajek(Grafo g, String name, boolean direcionado) throws FileNotFoundException {
		
		setPath(name);
		
		setGrafo(g);
		
		writePajek(direcionado);
		
	}
	
}
