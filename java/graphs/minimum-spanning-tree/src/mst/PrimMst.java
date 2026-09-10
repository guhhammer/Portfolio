
package mst;

import java.util.ArrayList;

public class PrimMst{

    // Prim's algorithm.
    private static class Prim {

        private Graph graph; 
                    
        // start vertex (0 by default).
        private int startVertex = 0, size;
        
        // infinity.
        private final int INF = Integer.MAX_VALUE;

        /*
            vertices: indices; distance: best known distances;
            previous: the vertex each distance came from, so edges can be added to the tree.
        */
        private int[] vertices, distance, previous;
                        
                        // already in the tree?
        private boolean[] visited; 

        // edges of the tree.
        private ArrayList<Edge> edges = new ArrayList<>();

        // constructor.
        public Prim(Graph g){ 

            this.graph = g; 
            this.size = graph.size;
            this.vertices = new int[size];
            this.distance = new int[size];
            this.previous = new int[size];
            this.visited = new boolean[size];

        }

        
        private void setStartVertex(int n){ startVertex = n;}
        
        
        /*
            initialises the arrays (the start vertex is a special case).
        */
        private void initialise(){

            for(int i = 0; i < distance.length; i++){
                if(i == startVertex){
                    distance[i] = 0;
                    visited[i] = true;
                    previous[i] = 0;
                }
                else{ 
                    distance[i] = INF;
                    visited[i] = false;
                    previous[i] = -INF;
                }
            }

        }

        
        private boolean allVisited(){

            boolean flag = true;
            for(int i = 0; i < visited.length; i++){
                if(visited[i] == false){  flag = false; break;  }
            }
            return flag;

        }
        
        
        // greedily selects edges.
        private void selectEdges(){
            
            // the start vertex is handled first.
            boolean initialPhase = true; 
            while(!allVisited()){ // until every vertex is in the tree.
                if(initialPhase){ 
                      
                    
                    SparseMatrix.Node aux = graph.matrix.lists[startVertex].first;
                    while(aux != null){
                        if(!visited[aux.adjacent]){
                            if(aux.value < distance[aux.adjacent]){
                                distance[aux.adjacent] = aux.value;
                                previous[aux.adjacent] = startVertex;
                            } // relax.
                        }
                        aux = aux.next;
                    }
                    initialPhase = false; 

                }
                else{
                    
                    // closest vertex not yet in the tree.
                    int smallestDistance = INF, index = -1;
                    for(int i = 0; i < vertices.length; i++){
                        if(!visited[i]){
                            if(distance[i] < smallestDistance){ 
                                smallestDistance = distance[i];
                                index = i;
                            }
                        }
                    }
                    
                    // weight of the edge from its predecessor.
                    int value = graph.matrix.lists[previous[index]].valueOf(previous[index], index);
                    
                    // add it to the tree.
                    edges.add(new Edge(previous[index], index, value)); 
                    
                    
                    visited[index] = true;
                    
                    /*
                        relax the edges out of the new vertex.
                    
                    */
                    SparseMatrix.Node aux = graph.matrix.lists[index].first;
                    while(aux != null){
                        if(!visited[aux.adjacent]){
                            if(aux.value < distance[aux.adjacent]){
                                distance[aux.adjacent] = aux.value;
                                previous[aux.adjacent] = index;
                            }
                        }
                        aux = aux.next;
                    }

                }
            }

        }
  
        // builds the tree graph.
        private Graph buildMst(){

            Graph mst = new Graph(size);

            for(Edge a : edges){
                mst.addEdge(a.getI(), a.getJ(), a.getValue());
            }

            return mst;
            
        }

        // runs the run in order.
        public Graph run(){

            setStartVertex(0); // default.

            initialise(); 

            selectEdges(); 

            return buildMst(); // builds the tree graph.

        }

    }


    // minimum spanning tree of g by Prim's algorithm.
    public static Graph prim(Graph g){
        
        Graph aux = g;
        
        Prim p = new Prim(aux);
        
        return p.run();
    
    }
    
}