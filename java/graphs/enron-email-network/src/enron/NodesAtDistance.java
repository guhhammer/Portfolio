
package enron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class NodesAtDistance{
    
    // author: Gustavo Hammerschmidt.
    
    // visited emails.
    private static ArrayList<String> path = new ArrayList<>();
   
    // emails found at distance d.
    private static ArrayList<String> atDistance = new ArrayList<>();
    
    // queue of the search.
    private static Queue<String> frontier = new LinkedList<>();
    
    // one breadth-first step.
    private static boolean search(Graph g){  
        
        
        if(frontier.peek() == null){ return false;} 
       
        
        if(!path.contains(frontier.peek())){
            
            path.add(frontier.peek()); 

            SparseMatrix.Node aux = g.matrix.lists[
                                 g.findVertex(frontier.peek())].first;
                                 
            
            while(aux != null){ 
                frontier.offer(aux.email);
                aux = aux.next;
            }   
            
        }
        
        frontier.poll(); 
        
        return true;  
        
    } 
    
    // advances d run and keeps whatever is left in the queue.
    private static boolean collectAtDistance(Graph g, int counter, int D){
                
        boolean status = true, setOk = true; 
        while(counter < D){ 
            status = search(g);
            if(status == false){ setOk = false; break;}
            counter++; 
        }   
           
        
        String aux = ""; 
        while(aux != null && setOk){
            atDistance.add(frontier.peek());
            aux = frontier.poll();
        }
                                  // drop the trailing null.
        atDistance.remove(atDistance.size()-1);
        
        return (setOk); 
    
    }
    
    // runs the run in order.
    private static ArrayList find(Graph g, String root, int D){
                    
        frontier.add(root); 
        
        int counter = 0; 
        boolean aux = collectAtDistance(g, counter, D);  
                                
        
        return (aux == true) ? atDistance : null; 
    
    }
    
    // public entry point: the emails at distance d from start.
    public static ArrayList nodesAtDistance(Graph g, String start, int D){
        
        return find(g, start, D); 
        
    }
   
    // prints the result.
    public static void printNodesAtDistance(ArrayList<String> atDistance, 
                                                         String option){
        
        if(atDistance.isEmpty()){   
            System.out.println("\nNo node found.\n\n");
        }
        else{
            if(option.equals("formatado")){
               
                String out = "\n\n\t";
                int counter = -1;
                for(String s : atDistance){
                    counter++;
                    if(counter % 3 == 2){ out += "\n\t";}

                    out += s+"   ";
                }// three emails per line.

                System.out.println(String.format(
                "\n\t(Nodes at distance D)\n\tNodes:\n%s\n\n\n\t"+
                "(END: nodes at distance D.)\n\n", out));

            }
            else if(option.equals("formatoArray")){
                System.out.println("Breadth-first search: "+
                                    Arrays.toString(atDistance.toArray()));
            }
        }
    
    }    
    
}
