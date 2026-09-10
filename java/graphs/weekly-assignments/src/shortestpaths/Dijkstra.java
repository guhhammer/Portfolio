
package shortestpaths; //author: Gustavo Hammerschmidt.

public class Dijkstra {

    private static final boolean MEMBER = true;
    private static final boolean NOT_MEMBER = false;
    private static final int INFINITY = 999999999;

    
    /* 
        The path array is passed in by the caller and initialised here.
    */
    public static int shortestPath(Graph g, int[] path, int s, int t){
        int distance[] = new int[g.size];
        boolean permanent[] = new boolean[g.size];
        int current, i, k = s, dc, j = 0;
        int smallestDistance, newDistance = 0;

        // initialisation
        for (i = 0; i < g.size; ++i) {
            permanent[i] = NOT_MEMBER;
            distance[i] = INFINITY;
            path[i] = -1;
        }
        permanent[s] = MEMBER;
        distance[s] = 0;
        current = s;
        while (current != t) {
            smallestDistance = INFINITY;
            dc = distance[current];
            
            for (i = 0; i < g.size; i++) {
                
                if(!permanent[i]){
                                    
                    if(g.matrix.lists[current].contains(i)){
                        newDistance = dc + g.matrix.lists[current].valueOf(i);
                    }   // edge exists -> use its weight.
                    else{ // otherwise -> infinity.
                        newDistance = dc + INFINITY;
                    }

                    if(newDistance < distance[i]){
                        distance[i] = newDistance;
                        path[i] = current;
                    }

                    if(distance[i] < smallestDistance){
                        smallestDistance = distance[i];
                        k = i;
                    }

                }
            }
            current = k;
            permanent[current] = MEMBER;
        }
        return distance[t];
    }
    
    // reverses a string (the path is built backwards).
    public static String reverse(String aux){
        
        String rev = "";
        
        for(int i = aux.length()-1; i >= 0; i--){
            rev = rev + aux.charAt(i);
        }
        
        return rev;
        
    }
    
    // prints the distance from s to t.
    public static void printDistance(Graph g, int s, int t){
        System.out.println("Shortest-path distance:   "+
                            shortestPath(g,new int[g.size],s,t));
    }
    
    
    // prints the path.
    public static void printPath(Graph g, int s, int t){
        
        int[] predecessors = new int[g.size];
        
        shortestPath(g,predecessors,s,t);
       
        String path; // the walk stores the vertices in path;
        
        int i = predecessors[t];
        
        path = t+" ";
        while (i != s) {
            path = path+i+" "; 
            i = predecessors[i];
        }
        
        path = path+s+" ";  
              
        String pathR = reverse(path); // reversed path.
        
        System.out.println(
                String.format(("Path from %d to %d: \tStart ->  "+pathR+
                                                    "   <- End \n"),s,t));
     
    }


}
