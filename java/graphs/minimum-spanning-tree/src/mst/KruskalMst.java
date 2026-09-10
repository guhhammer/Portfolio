
package mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class KruskalMst {
    
    // Kruskal's algorithm.
    private static class Kruskal{

        // input graph.
        private Graph graph;
        private int size;
        
        // the tree being built.
        private Graph mst;

        private int[] vertices; // vertex indices.
        private boolean[] visited; // visited vertices.
        
        // edges selected for the tree.
        private ArrayList<Edge> selectedEdges = new ArrayList<>();
        
        // every edge of the input graph.
        private ArrayList<Edge> allEdges = new ArrayList<>();
        
        // edge queue.
        private Queue<Edge> edgeQueue = new LinkedList<>();
        
        // constructor.
        public Kruskal(Graph g){ 
            
            this.graph = g;
            this.size = g.size;
            this.vertices = new int[size];
            this.visited = new boolean[size];
            
        }

        
        private void setVertices(){
            
            for(int i = 0; i < size; i++){ vertices[i] = i; }
        
        }
        
        
        private void resetVisited(){
            
            for(int i = 0; i < size; i++){ visited[i] = false;}
        
        }
        
        // collects the edges.
        private void collectEdges(){
            
            for(int i = 0; i < size; i++){
                SparseMatrix.Node aux = graph.matrix.lists[i].first;
                while(aux != null){
                    allEdges.add(new Edge(i, aux.adjacent, aux.value));
                    aux = aux.next;
                }
            }
            
        }
        
        // sorts them by weight.
        private void sortEdges(){
            
            Collections.sort(allEdges, new Edge());
   
        }
        
        // drops the reverse copy of each undirected edge.
        private void removeDuplicates(){
            
            Edge a, a_;
            boolean reboot = false;
            for(int i = 0; i < allEdges.size(); i++){
                a = allEdges.get(i);
                for(int j = 0; j < allEdges.size(); j++){
                    a_ = allEdges.get(j);
                    
                    if(a.getI() == a_.getJ() &&
                        a.getJ() == a_.getI() &&
                        a.getValue() == a_.getValue()){
                        allEdges.remove(i);
                        reboot = true;
                        break;
                    }
                    
                }
                if(reboot){ break; }
                
            }
            
            if(reboot){ removeDuplicates(); }
            
        }
        
        
        private void resetMst(){
            
            mst = new Graph(size);
            
        }
        
        
        private boolean allVisited(){
            
            for(int i = 0; i < visited.length; i++){ if(!visited[i]){ return false; }}
            return true;
        
        }
        
        
        private void fillQueue(){
            
            for(Edge a : allEdges){ edgeQueue.offer(a); }
                    
        }
        
        // is the tree built so far connected?
        private boolean isGraphConnected(){
            
            mst = new Graph(size);
            
            if(selectedEdges.isEmpty()){
                return false;
            }
            else{
                for(Edge a : selectedEdges){ 
                    mst.addEdge(a.getI(), a.getJ(), a.getValue());
                }
            }
            
            Graph aux = mst;
            mst = new Graph(size);
            
            return ConnectivityCheck.isConnected(aux);
            
        }
        
        // would adding the edge create a cycle?
        private boolean createsCycle(Edge aux){
            
            mst = new Graph(size);
            
            if(selectedEdges.isEmpty()){
                return false;
            }
            else{
                for(Edge a : selectedEdges){ 
                    mst.addEdge(a.getI(), a.getJ(), a.getValue());
                }
            }
            
            Graph other = mst;
            mst = new Graph(size);
            
            return !ConnectivityCheck.reachesItself(other, aux.getI());
        
        }
        
        // greedily selects edges.
        private void selectEdges(){
        
            fillQueue();
                 
            boolean run1 = true;
            while(!allVisited() && !isGraphConnected()){
                
                Edge aux = edgeQueue.peek();   
                visited[aux.getI()] = true;
                visited[aux.getJ()] = true;
                selectedEdges.add(aux);
                
                if(createsCycle(aux) && !run1){
                   selectedEdges.remove(selectedEdges.indexOf(aux));   
                }
                
                edgeQueue.poll();
                
                if(size-1  == selectedEdges.size()){ break;}
                run1 = false;
                
            }
                       
        }
        
        // adds edges until the tree is connected.
        private void fill(){
        
            if(!isGraphConnected()){
                
                while(selectedEdges.size() != size-1 && !edgeQueue.isEmpty()){
                    
                    selectedEdges.add(edgeQueue.peek());
                    edgeQueue.poll();
                    
                }
            }
            
        }
        
        // builds the tree graph.
        private Graph buildMst(){
        
            for(Edge a : selectedEdges){ 
                mst.addEdge(a.getI(), a.getJ(), a.getValue());
            }
            
            return mst;
            
        }
        
        // runs the run in order.
        public Graph run(){
            
            setVertices();

            resetVisited();
            
            collectEdges();
     
            sortEdges();         
             
            removeDuplicates();
              
            resetMst();
            
            selectEdges();
                        
            fill();

            return buildMst();
            
        }
        
    }
    
    
    // minimum spanning tree of g by Kruskal's algorithm.
    public static Graph kruskal(Graph g){
        
        Graph aux = g;
        
        Kruskal k = new Kruskal(aux);
        
        return k.run();
    
    }
    
}
