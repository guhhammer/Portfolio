package Projeto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class lerArquivoPajek {
	
	private static Grafo g; // Grafo g.
	
	// Uso: l� arquivo .pajek.
	private static void reader(File f) throws FileNotFoundException {
		
		
		Scanner sc = new Scanner(f);
		
		
		String numVertices = "";
		boolean direcionado = true;
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			
			if(s.startsWith("*Vertices")) {  numVertices = s.replace("*Vertices", "").trim(); }
			if(s.startsWith("*Arcs")) { direcionado = true; }
			if(s.startsWith("*Edges")) { direcionado = false; }
			
		} // Pega o n�mero de v�rtices e se � direcionado ou n�o.
		
		sc.close();
		sc = new Scanner(f);
		
		g = new Grafo(Integer.parseInt(numVertices), direcionado); // Inicia o grafo.
		
		ArrayList<String[]> names = new ArrayList<>();
		boolean jump = false;
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			
			if(s.startsWith("*Vertices")) { jump = true; }
			if(s.startsWith("*Arcs") || s.startsWith("*Edges")) { break; }
						
			if(!jump) {
		
				names.add(s.replaceAll("\\s{2,}", " ").split(" "));			
	
			}
			
			jump = false;
			
		} // Pega os nomes dos v�rtices.
		
		for(String[] n : names) {
			g.vertices[Integer.parseInt(n[0])].setNome(n[1].replaceAll("\"", ""));
		}  // Nomeia os v�rtices.
		
		sc.close();
		sc = new Scanner(f);
		
		ArrayList<String[]> arestas = new ArrayList<>();
		boolean pass = true;
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			
			if(s.startsWith("*Arcs") || s.startsWith("*Edges")) {   pass = false; s = sc.nextLine(); }
						
			if(!pass) {
		
				arestas.add(s.replaceAll("\\s{2,}", " ").split(" "));
				
			}
			
		}  // Pega as arestas do arquivo.
		
		for(String[] a : arestas) {
			
			g.criaAdjacencia(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]));
		
		}  // Adiciona as arestas do arquivo.
		
		sc.close();
		
	}
	
	// Uso: chama reader() se caminho existir.
	private static Grafo buildFromThis(String nome) {
		
		if(nome.endsWith(".pajek")) {
			
			File file = new File(nome);
		
			if(file.isFile()) {
				
				try{ reader(file); }catch(Exception e) { e.getSuppressed(); }
			
				return g;
		
			}
			else {
				
				System.out.print("\nAten��o: arquivo n�o foi encontrado. Seu grafo iniciar� nulo.\n\n");
				
				return null;
			}
			
		}
		
		System.out.print("\nAten��o: arquivo n�o � do formanto .pajek.\nSeu grafo iniciar� nulo.\n\n");
		
		return null;
	
	}
	
	// Uso: fun��o de acesso p�blico � classe.
	public static Grafo build(String nome) {
		
		if(new File("src\\Projeto\\"+nome).exists()) {
			return buildFromThis(("src\\Projeto\\"+nome));
		}
		else {
			return buildFromThis(nome);
		}
		
	}

	 
}
