package routes;

public class Dijkstra {

    private static final boolean MEMBER = true;
    private static final boolean NOT_MEMBER = false;
    private static final int INFINITY = 999999999;

    // shortest path from s to t; fills path with predecessors.
    public static int shortestPath(Graph g, int[] path, int s, int t){
        int distance[] = new int[g.size];
        boolean permanent[] = new boolean[g.size];
        int current, i, k = s, dc;
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
                        newDistance = dc + g.matrix.lists[current].valueOf(current, i);
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
   
    // distance from s to t.
    public static int distance(Graph g, int s, int t){
        return shortestPath(g,new int[g.size],s,t);
    }

}