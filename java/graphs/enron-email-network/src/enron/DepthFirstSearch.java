
package enron;

import enron.SparseMatrix.AdjacencyList;
import java.util.ArrayList;
import java.util.Arrays;

public class DepthFirstSearch{
    
    // author: Gustavo Hammerschmidt.
   
    // the path from the start email to the end email.
    private static ArrayList<String> path = new ArrayList<>();
    
    // depth-first search from root to end.
    private static boolean search(Graph g, boolean[] visited, String root, 
                                                                String end){
        
        visited[g.findVertex(root)] = true; // mark the root visited.
        path.add(root);      // add it to the path.
        AdjacencyList aux = g.matrix.lists[g.findVertex(root)]; 
                      // its adjacency list.
        
        SparseMatrix.Node auxKnot = aux.first; 
                                // first neighbour.
        
        // no neighbours: dead end.
        if(auxKnot == null){ return false; }
        
        // recurse into every unvisited neighbour.
        while(auxKnot != null){
            if(end.equals(auxKnot.email)){ path.add(end); break; }
                // found the end.
            
            if(!visited[g.findVertex(auxKnot.email)]){
                search(g, visited, auxKnot.email, end);
            } // unvisited neighbour.
            
            auxKnot = auxKnot.next; // next neighbour.
        }
        
        return true; // end reached.
        
    }
    
    /* Uso: runs the search with a fresh visited array and returns the path.*/
    private static ArrayList runSearch(Graph g, String start, String end){
        
        // visited emails.
        boolean[] visited = new boolean[g.getSize()];
        
        boolean aux = search(g, visited, start, end);  // search.
        
        return (aux == true) ? path : null; // return the path.
        
    }
    
    /* Uso: public entry point.*/
    public static ArrayList depthFirst(Graph g, String start, String end){
        
        return runSearch(g, start, end); // the path.
     
    }
    
    // prints the path in the chosen format.
    public static void printDepthFirstPath(ArrayList<String> path,
                                                              String option){
        
        if(path == null){
            System.out.println("\nPath not found.\n\n");
        }
        else{
            if(option.equals("formatado")){
                
                String out = "\n\n\t", lastEmail = "";
                int counter = -1;
                for(String s : path){
                    lastEmail = s;
                    counter++;
                    if(counter % 3 == 2){ out += "\n\t";}

                    out += s+"   ";
                }// three emails per line.

                System.out.println(String.format(
                                   "\n\t(DEPTH-FIRST SEARCH)"+
                               "\n\tStart: %s\n\tEnd: %s\n\tPath:\n%s\n\n"+
                                   "\n\t(END: DEPTH-FIRST SEARCH.)\n\n", 
                                    path.get(0), lastEmail, out));
            }
            else if(option.equals("formatoArray")){
                System.out.println("Depth-first search: "+
                                   Arrays.toString(path.toArray()));
            }
        }
    
    }
    
}
