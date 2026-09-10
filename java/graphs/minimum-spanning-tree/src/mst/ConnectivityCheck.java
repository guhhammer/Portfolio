
package mst;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectivityCheck{
    
    private static ArrayList<Integer> path = new ArrayList<>();

    private static Queue<Integer> frontier = new LinkedList<>();
    
    // breadth-first search from start to end.
    private static boolean search(Graph g, int end){  
                             
        if(frontier.peek() == null){ return false;} 
       
        
        if(!path.contains(frontier.peek())){
            
            path.add(frontier.peek()); 
            
            // get its adjacency list.
            SparseMatrix.Node aux = g.matrix.lists[
                                 g.findVertex(frontier.peek())].first;
            
            // enqueue every neighbour.
            while(aux != null){ 
                frontier.offer(aux.adjacent);
                aux = aux.next;
            }
            
        }
        
        frontier.poll(); // dequeue.
        
        /* Stop condition: recurse while the head of the queue differs from end;
           when it is end, append it and stop.      */ 
        if(end != (frontier.peek())){ search(g, end); } 
        else{ path.add(end);}
        
        return true;
        
    } 
    
    /* Uso: seeds the queue and runs the search.*/
    private static boolean runSearch(Graph g, int root, int end)
        throws NullPointerException{
                    
        frontier.clear();
        path.clear();
        
        frontier.add(root); 
        
        try{ 
            return search(g, end);
        }catch(NullPointerException npe){ npe.getSuppressed(); return false;}
       
    }
    
    /* Uso: true when every vertex is reachable from vertex 0.*/
    public static boolean reachesAll(Graph g){
        for(int i = 1; i < g.size; i++){if(!runSearch(g,0,i)){ return false;}}
        return true;
        
    }
    
    public static boolean reachesItself(Graph g, int i){
        if(runSearch(g, i,i)){ 
            return false;
        }
        return true;
        
    }
        
    public static boolean isConnected(Graph g){ 
        try{ return reachesAll(g); }
        catch(NullPointerException e){ return false;}
    }
    
}
