
package enron;

import java.util.ArrayList;
import java.util.Arrays;

public class GraphStatistics {
    
    // author: Gustavo Hammerschmidt.
    
    private String email; // email address.
    private int sum;     // messages sent or received.
    
    
    private GraphStatistics(String email, int sum){
        this.email = email; this.sum = sum;
    }
    
    // pega o email.
    private String getEmail(){ return this.email;}
    
    // pega a sum.
    private int getSum(){ return this.sum;}
    
    /* top senders.*/
    private static ArrayList<GraphStatistics> topSenders = new ArrayList<>();
    
    // number of vertices.
    private static int vertexCount(Graph g){ return g.getSize(); }
    
    // number of edges.
    private static int edgeCount(Graph g){ return g.getEdgeCount(); }
    
    
    // OUT-DEGREE ........................................................
    
    // collects the people who sent the most emails.
    private static void collectTopSenders(Graph g, int count){
        
        for(int i = 0; i < g.getSize(); i++){
            
            // messages sent by i.
            int value = g.matrix.lists[i].countEdges();
            
            // fill up first.
            if(topSenders.size() < count){
                
                GraphStatistics sender = 
                        new GraphStatistics(g.vertices[i].getName(), value);
                topSenders.add(sender);
                
            }  
            else{
                                       // list full: replace a smaller one.
                GraphStatistics sender = 
                        new GraphStatistics(g.vertices[i].getName(), value);
                boolean flag = false; 
                for(int j = 0; j < topSenders.size(); j++){
                    
                    if(value > topSenders.get(j).getSum()
                                                        && flag == false){
                        topSenders.remove(j);
                        topSenders.add(sender);
                        flag = true;
                    }
                    
                } 
                
                
            }
       
        }
        
    }
    
    // report of the top senders.
    private static String topSendersReport(Graph g, int count){
                                
        collectTopSenders(g, count);  
        
        int[] values = new int[count];
        for(int i = 0; i < topSenders.size(); i++){
            values[i] = topSenders.get(i).getSum();
        }  
               
        Arrays.sort(values);  // sort.
        
        String report = "";  
        
        for(int i = values.length-1; i >=0; i--){
            String hold = "";
            boolean flag = true;
            for(int j = 0; j < topSenders.size(); j++){
                if(values[i] == topSenders.get(j).getSum() && flag == true){
                    hold = topSenders.get(j).getEmail();
                    topSenders.remove(j);
                    flag = false;
                }
            }
            report += String.format(
                    "%d.\tEmail: %s \n\tEmails sent: %d \n\n", 
                                          (values.length-i),hold,values[i]);
            
        }

        return report;
        
    }
    
    
    // OUT-DEGREE ........................................................
    
    
    // IN-DEGREE ......................................................
    
    // messages received by each vertex.
    private static int[] receivedCounts(Graph g){
        
        int[] individuals = new int[g.getSize()]; 
        for(int i = 0; i < individuals.length; i++){
            for(int j = 0; j < g.getSize(); j++){
                if(g.matrix.lists[j].contains(g.vertices[i].getName())){
                    individuals[i] += g.matrix.lists[j].valueOf(g.vertices[i].getName());
                }
            }  // sum the weights of the edges into vertices[i]
        }      
        return individuals;
        
    }
    
    
    // index of the largest value.
    private static int indexOfMax(int[] arr){
    
        int maiorValor = arr[0], index = 0;
        for(int i = 1; i < arr.length; i++){
            if(maiorValor < arr[i]){ maiorValor = arr[i]; index = i;}
        }
        
        return index;
        
    }
    
    
    // indices of the largest receivers.
    private static int[] topReceiverIndices(Graph g, int count){
    
        int[] individuals = receivedCounts(g); 
        int[] topIndices = new int[count]; 
        for(int i = 0; i < topIndices.length; i++){
            if("".equals(g.vertices[indexOfMax(individuals)].getName())){
                individuals[indexOfMax(individuals)] = 0;
            } // skip the empty address.
            
            topIndices[i] = indexOfMax(individuals); 
            individuals[indexOfMax(individuals)] = 0;   
        }
        
        return topIndices;
     
    }
    
    
    // report of the top receivers.
    private static String topReceiversReport(Graph g, int count){
    
                                
        int[] values = topReceiverIndices(g, count);  
        
        int[] individuals = receivedCounts(g); 
       
        int[] aux = new int[count];   
        for(int i = 0; i < values.length; i++){
            aux[i] = individuals[values[i]];
        }
        
        String report = "";  
        for(int i = 0; i < aux.length; i++){
            String hold = "";
            for(int j = 0; j < values.length; j++){
                if(aux[i] == individuals[values[j]]){
                    hold = g.vertices[values[i]].getName();
                          
                    individuals[values[j]] = 0; // avoid repeats.
                }
            }
            report += String.format(
                    "%d.\tEmail: %s\n\tEmails received: %d \n\n", 
                                (i+1),hold,aux[i]);
        }

        return report;
        
    }
    
    
    // IN-DEGREE ......................................................
    
  
    public static void printStatistics(Graph g, int count){
        
        System.out.println("\nGraph statistics: \n\n");
        
        System.out.println(
                      String.format("Vertices: %d. \n\n",
                                                          vertexCount(g)));
    
        System.out.println(
                      String.format("Edges: %d. \n\n",
                                                         edgeCount(g)));
        
        System.out.println(
                      String.format("Top %d senders:\n\n%s", 
                                 count, topSendersReport(g, count)));
    
        System.out.println(
                      String.format("Top %d receivers: \n\n%s",
                                count, topReceiversReport(g, count)));
        
        System.out.println("(END: graph statistics.)\n\n");
        
    }
    
    
}
