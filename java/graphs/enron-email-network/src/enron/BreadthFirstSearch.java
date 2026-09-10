
package enron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch{
    
    // author: Gustavo Hammerschmidt.
    
    // the path from the start email to the end email.
    private static ArrayList<String> path = new ArrayList<>();
   
    // queue that drives the breadth-first search.
    private static Queue<String> frontier = new LinkedList<>();
    
    // breadth-first search from start to end.
    private static boolean search(Graph g, String end){  
                             // nothing left to visit.
        if(frontier.peek() == null){ return false;} 
       
        // if the head of the queue is not on the path yet:
        if(!path.contains(frontier.peek())){
            
            path.add(frontier.peek()); // add it to the path.
            
            // get its adjacency list.
            SparseMatrix.Node aux = g.matrix.lists[
                                 g.findVertex(frontier.peek())].first;
            
            // enqueue every neighbour.
            while(aux != null){ 
                frontier.offer(aux.email);
                aux = aux.next;
            }
            
        }
        
        frontier.poll(); // dequeue.
        
        /* Stop condition: recurse while the head of the queue differs from end;
           when it is end, append it and stop.                              */ 
        if(!end.equals(frontier.peek())){ search(g, end); } 
        else{ path.add(end);}
        
        return true;
        
    } 
    
    /* Uso: seeds the queue with the root and returns the path from start to end.*/
    private static ArrayList runSearch(Graph g, String root, String end){
                    
        // seed the queue with the start email.
        frontier.add(root); 
        
        boolean aux = search(g, end);  // search.
        
        return (aux == true) ? path : null; // return the path.
    
    }
    
    /* Uso: public entry point.*/
    public static ArrayList breadthFirst(Graph g, String start, String end){
        
        return runSearch(g, start, end); // the path.
        
    }
    
    // prints the path in the chosen format.
    public static void printBreadthFirstPath(ArrayList<String> path, 
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
                                   "\n\t(BREADTH-FIRST SEARCH)"+
                               "\n\tStart: %s\n\tEnd: %s\n\tPath:\n%s\n\n"+
                                   "\n\t(END: BREADTH-FIRST SEARCH.)\n\n", 
                                    path.get(0), lastEmail, out));
        
            }
            else if(option.equals("formatoArray")){
                System.out.println("Breadth-first search: "+
                                    Arrays.toString(path.toArray()));
            }
        }
    
    }    
    
}
