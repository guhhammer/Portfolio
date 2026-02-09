package Projeto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class lerDatabase {

	/*
	 * 
	 * Site para downlaod da Database: 
	 * 
	 * 		https://www.kaggle.com/open-flights/flight-route-database
	 * 
	 * 		ou:
	 * 
	 * 		https://www.kaggle.com/open-flights/flight-route-database/download
	 * 
	 * 
	 * 	Descrição da Base de dados encontrada no site de download:
	 * 
	 * 	Routes database
	 *	As of January 2012, the OpenFlights/Airline Route Mapper Route Database contains
	 *  59036 routes between 3209 airports on 531 airlines spanning the globe.
	 *  
	 *  Content
	 *  The data is ISO 8859-1 (Latin-1) encoded.
	 *  Each entry contains the following information:
	 *  	
	 *  	Airline 2-letter (IATA) or 3-letter (ICAO) code of the airline.
	 *  	Airline ID Unique OpenFlights identifier for airline (see Airline).	
	 *  	Source airport 3-letter (IATA) or 4-letter (ICAO) code of the source airport.
	 *  	Source airport ID Unique OpenFlights identifier for source airport (see Airport)
	 *  	Destination airport 3-letter (IATA) or 4-letter (ICAO) code of the destination airport.
	 *  	Destination airport ID Unique OpenFlights identifier for destination airport (see Airport)
	 *  	Codeshare "Y" if this flight is a codeshare (that is, not operated by Airline, but another carrier), empty otherwise.
	 *  	Stops Number of stops on this flight ("0" for direct)
	 *  	Equipment 3-letter codes for plane type(s) generally used on this flight, separated by spaces
	 *  	The special value \N is used for "NULL" to indicate that no value is available.
	 *  	
	 *  Notes:
	 *  	
	 *  	Routes are directional: if an airline operates services from A to B and from B to A, 
	 *  	both A-B and B-A are listed separately.
	 *  	Routes where one carrier operates both its own and codeshare flights are listed only once.
	 *  
	 *  Acknowledgements
	 *  	This dataset was downloaded from Openflights.org under the Open Database license.
	 *  	This is an excellent resource and there is a lot more on their website, so check them out!
	 * 
	 * 
	 * */
	
	private static String pathFile;  // Caminho do arquivo.
	
	private static ArrayList<String> ids = new ArrayList<>(); // Ids de aeroportos.
	
	private static Grafo g; // Grafo de retorno.
	
	// Uso: seta caminho do arquivo.
	private static void setPath(String s) { pathFile = s; }
	
	// Uso: retorna grafo.
	private static Grafo getGrafo() { return g; }
	
	// Uso: remove ids duplicados em ArrayList ids.
	private static void removeDuplicates() {
	
		ids = new ArrayList<String>(ids.stream().distinct().collect(Collectors.toList()));
		
	}
	
	// Uso: extrai os IDS de arquivo.
	private static void extractionIDS() throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File(pathFile));
	
		boolean firstlinejump = true;
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			
			if(!firstlinejump) {
				
				ids.add(s.split(",")[2]);
				ids.add(s.split(",")[4]);
				
			}
			
			firstlinejump = false;
			
		}
		
		sc.close();

	}
	
	// Uso: Constroi grafo e nomeia seus vértices.
	private static void build_pt1(boolean direcionado) {
		
		g = new Grafo(ids.size(), direcionado); //não-direcionado.
		
		for(int i = 0; i < ids.size(); i++) {
			
			g.vertices[i].setNome(ids.get(i)+"");
			
		}
		
	}
	
	// Uso: insere as arestas do arquivo no grafo.
	private static void build_pt2() throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File(pathFile));
		
		boolean firstlinejump = true;
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			
			if(!firstlinejump) {
				
				g.criaAdjacencia(g.findVertice(s.split(",")[2]),
							     g.findVertice(s.split(",")[4]), 
							     1);
				
			}
			
			firstlinejump = false;
			
		}
		
		sc.close();
		
	}
	
	// Uso: define ordem de execução dos métodos.
	private static Grafo sequence(String path, boolean direcionado) {
		
		setPath(path);
		
		try { extractionIDS(); }catch(Exception e) { e.getSuppressed(); }
		
		removeDuplicates();
		
		build_pt1(direcionado);
		
		try { build_pt2(); }catch(Exception e) { e.getSuppressed(); }
		
		return getGrafo();
		
	}
	
	// Uso: função de acesso público à classe.
	public static Grafo extractDatabase(String path, boolean direcionado) { return sequence(path, direcionado); }	
	
}
