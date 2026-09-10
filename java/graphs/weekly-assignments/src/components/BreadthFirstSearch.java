
package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearch {

    // the component found so far.
    private static ArrayList<Integer> component = new ArrayList<>();
    
    // queue of vertices to visit.
    private static Queue<Integer> frontier = new LinkedList<>();
    
    // breadth-first search.
    private static void search(Graph g){
        
        if(frontier.peek() == null){ return; }
        
        if(!component.contains(frontier.peek())){
            
            component.add(frontier.peek()); 
            
            SparseMatrix.Node aux = g.matrix.lists[g.findVertex(frontier.peek())].first;

            while(aux != null){
                frontier.offer(aux.adjacent);
                aux = aux.next;
            }
            
        }
        
        frontier.poll();
        
        if(frontier.peek() != null){ search(g); }        
        
    }
    
    // runs the search and returns the component as int[].
    private static int[] runSearch(Graph g, int root){
        
        frontier.clear();
        component.clear();
        
        frontier.add(root);
        
        search(g);
        
        if(component.size() > 1){

            Arrays.sort(component.toArray());
            int[] aux = new int[component.size()];
            for(int i = 0; i < component.size(); i++){ aux[i] = component.get(i); }

            return aux;

        }
        else{
            return null;
        }
        
    }
    
    // removes duplicate components.
    private static void distinct(ArrayList<int[]> aux){
        
        int pass = 0;
        boolean flag = false;
        for(int i = 0; i < aux.size(); i++){
            for(int j = 0; j < aux.size(); j++){
                if(i != j){
                    if(Arrays.equals(aux.get(i), aux.get(j))){
                        aux.remove(aux.get(j));
                        flag = true;
                        break;
                    }
                }
            }
            pass++;
            if(flag){ break; }
        }
        if(pass == aux.size()-1){ return; }
        if(flag){ distinct(aux); }
        
    }
    
    // returns the components of the graph.
    private static List getComponents(Graph g){
            
        int[] vertices = new int[g.size];
        for(int i = 0; i < g.size; i++){
            vertices[i] = g.vertices[i].index;
        }
        
        ArrayList<int[]> aux = new ArrayList<>();
        for(int i : vertices){ 
            if(runSearch(g, i) != null){ aux.add(runSearch(g, i)); }
        }
        
        for(int[] i : aux){  Arrays.sort(i); } // sort the components
        
        distinct(aux); // remove duplicates.
        distinct(aux); // remove duplicates.
        
        return aux;
         
    }
    
    // public entry point.
    public static List componentsOf(Graph g){  return getComponents(g);  }
    
    // prints only the components.
    public static void printOnlyComponents(List l){
       
        System.out.print("\n\n\nComponents: \n\n\t\t");
        
        for(int i = 0; i < l.size(); i++){
            System.out.print(Arrays.toString((int[]) l.get(i)));
            if(i != l.size()-1){ System.out.print(",  \n\t\t");}
            if(i == l.size()-1){ System.out.print(". \n\n");}
        }
    
    }
    
    // prints the components and their count.
    public static void printComponents(List l){
        
        System.out.println("\n\nNumber of components: "+l.size()+".");
        System.out.print("\n\n\nComponents: \n\n\t\t");
        
        for(int i = 0; i < l.size(); i++){
            System.out.print(Arrays.toString((int[]) l.get(i)));
            if(i != l.size()-1){ System.out.print(",  \n\t\t");}
            if(i == l.size()-1){ System.out.print(". \n\n");}
        }
        
    }
    
}
