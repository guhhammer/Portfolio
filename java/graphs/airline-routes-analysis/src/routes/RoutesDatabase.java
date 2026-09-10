package routes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RoutesDatabase {

	/*
	 * 
	 * Dataset download: 
	 * 
	 * 		https://www.kaggle.com/open-flights/flight-route-database
	 * 
	 * 		ou:
	 * 
	 * 		https://www.kaggle.com/open-flights/flight-route-database/download
	 * 
	 * 
	 * 	Dataset description from the download page:
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
	
	private static String path;  // file path.
	
	private static ArrayList<String> codes = new ArrayList<>(); // airport codes.
	
	private static Graph g; // graph.
	
	// sets the file path.
	private static void setPath(String s) { path = s; }
	
	// returns the graph.
	private static Graph getGrafo() { return g; }
	
	// removes duplicate codes.
	private static void removeDuplicates() {
	
		codes = new ArrayList<String>(codes.stream().distinct().collect(Collectors.toList()));
		
	}
	
	// reads the airport codes from the file.
	private static void extractIds() throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File(path));
	
		boolean firstlinejump = true;
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			
			if(!firstlinejump) {
				
				codes.add(s.split(",")[2]);
				codes.add(s.split(",")[4]);
				
			}
			
			firstlinejump = false;
			
		}
		
		sc.close();

	}
	
	// creates the graph and labels the vertices.
	private static void createVertices(boolean directed) {
		
		g = new Graph(codes.size(), directed); // undirected by default.
		
		for(int i = 0; i < codes.size(); i++) {
			
			g.vertices[i].setName(codes.get(i)+"");
			
		}
		
	}
	
	// inserts the routes as edges.
	private static void insertEdges() throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File(path));
		
		boolean firstlinejump = true;
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			
			if(!firstlinejump) {
				
				g.addEdge(g.findVertex(s.split(",")[2]),
							     g.findVertex(s.split(",")[4]), 
							     1);
				
			}
			
			firstlinejump = false;
			
		}
		
		sc.close();
		
	}
	
	// runs the run in order.
	private static Graph run(String path, boolean directed) {
		
		setPath(path);
		
		try { extractIds(); }catch(Exception e) { e.getSuppressed(); }
		
		removeDuplicates();
		
		createVertices(directed);
		
		try { insertEdges(); }catch(Exception e) { e.getSuppressed(); }
		
		return getGrafo();
		
	}
	
	// public entry point.
	public static Graph load(String path, boolean directed) { return run(path, directed); }	
	
}
