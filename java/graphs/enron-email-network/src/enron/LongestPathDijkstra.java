
package enron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LongestPathDijkstra{

    // author: Gustavo Hammerschmidt.
    
    private static final boolean MEMBER = true; // member flag.
    private static final boolean NOT_MEMBER = false; // non-member flag.
    private static final int INF = 999999999; // infinity.
    
    // longest path from s to t (Dijkstra with the comparison reversed); fills path with predecessors.
    private static int longestPath(Graph g, int[] path, String s, String t){
       
        // distances and the largest distance so far.
        int distance[] = new int[g.getSize()], largestDistance, newDistance = 0;
        boolean permanent[] = new boolean[g.getSize()];// settled vertices.
        int current, i, k = g.findVertex(s), dc; 
        
        // initialisation.
        for(i = 0; i < g.getSize(); i++){
            permanent[i] = NOT_MEMBER;
            distance[i] = -INF;  // start at minus infinity. 
            path[i] = -1;
        }
        
        permanent[g.findVertex(s)] = MEMBER; // the start is settled.
        distance[g.findVertex(s)] = 0; // at distance 0.
        current = g.findVertex(s);     // current vertex.
        while(current != g.findVertex(t)){ // until the end is reached.
            largestDistance = -INF;   // reset the best candidate.
            dc = distance[current]; // distance of the current vertex.
            
            // for every vertex.
            for(i = 0; i < g.getSize(); i++){
                
                if(!permanent[i]){ // not settled yet.
                    
                    // edge current -> i exists.
                    if(g.matrix.lists[current].contains(g.emailOf(i))){
                        newDistance = dc + 
                          g.matrix.lists[current].valueOf(g.emailOf(i));
                    }
                    else{ 
                        newDistance = dc + (-INF); 
                    } 
                    
                    // longer than the known one.
                    if(newDistance > distance[i]){
                        distance[i] = newDistance;
                        path[i] = current;
                    } 
                    
                    // best candidate so far.
                    if(distance[i] > largestDistance){
                        largestDistance = distance[i];
                        k = i;
                    }
                    
                }
            
            }
            current = k; // move to k.
            permanent[current] = MEMBER; // and settle it.
            
        }
        
        return distance[g.findVertex(t)]; // the largest distance.
        
    }
    
    // the longest path as a formatted string.
    private static String longestPath(Graph g, String start, String end){
        
        int[] predecessors = new int[g.getSize()]; // predecessors.
        
        longestPath(g, predecessors, start, end); // fill it.
        
        
        ArrayList<String> path = new ArrayList<>(); 
        
        int aux = predecessors[g.findVertex(end)]; // walk back from the end.
        
        path.add((end)); 

        while(aux != g.findVertex(start)){// until the start.
            path.add((g.vertices[aux].getName())); 
            aux = predecessors[aux]; 
        }
        
        path.add((start)); 
        
        Collections.reverse(path); // start to end.
        
        String out = ""; 
        int counter = -1; // three per line.
        for(String s : path){ 
            counter++; if(counter % 3 == 2){ out += "\n\t";}
            out += s+"  "; 
        }// three per line.
         // dez elementos
        
        return out; 
        
    }
      
    // the largest accumulated dependency between s and t.
    private static int longestDistance(Graph g, String s, String t){
        return longestPath(g, new int[g.getSize()], s, t);
             
    }
    
    // public entry point: prints the longest path and its accumulated dependency.
    public static void printLongestPath(Graph g, String start, String end,
                                                            String option){
        
        // the path in the chosen format.
        String op = (option.equals("formatado")) 
                ? longestPath(g, start, end)
                : Arrays.toString(longestPath(g, start, end)
                      .replace("\n", "").replace("\t", "")
                      .trim().replaceAll("\\s+", " ").split(" "));                   
        
        
        String aux = String.format("\n\tDistance (accumulated dependency) "+
                                   "of the longest path: %s .",
                                   longestDistance(g, start, end));

        
        String oMaiorCaminhoInformacoes = 
            String.format(
            "\n\tThe path of largest accumulated dependency"+
            "\n\tbetween %s \n\tand %s: \n\n"+
            "\t(people on the path):\n\n\tStart:\n\n\t%s\n\n\tEnd.\n\t%s"+
            "\n\n\t(End: largest accumulated dependency)\n\n",
                           start, end, op, aux);
        
        System.out.println(oMaiorCaminhoInformacoes);
        
    }
    
}
