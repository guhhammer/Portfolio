
package shortestpaths;  //author: Gustavo Hammerschmidt.

public class Warshall {
    
    
    // Warshall.
    public static void transitiveClosure(Graph m, boolean[][] closure){ 
        
        int aux = m.matrix.size;
        
        for(int i = 0; i < aux;i++){
            for(int j = 0; j < aux; j++){
                closure[i][j] = m.matrix.lists[i].contains(j);
            }
        }   // initialise the closure matrix.
        
        for(int k = 0; k < aux; k++){
            for(int i = 0; i < aux; i++){
                if(closure[i][k]){
                    for(int j = 0; j < aux; j++){
                        closure[i][j] = closure[i][j] || closure[k][j];
                    }
                }
            }
        }       // Warshall's algorithm.
        
    }
    
    /* 
        Extra: contains() was added to SparseMatrix to fill the first loop with booleans.
    */
}
