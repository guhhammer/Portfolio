
package ProjetoTDE7;

public class GrafoEuleriano{
    
    private int verticesImpares = 0;
        
    private Grafo g;
    
    private void setGrafo(Grafo g){ this.g = g;}
    
    private void contarImpares(){
        
        for(int i = 0; i < g.tamanho; i++){
            
            if(g.matriz.lista[i].quantidade() % 2 == 0){ verticesImpares++; }
            
        }
        
    }
    
    private int getVerticesImpares(){ return verticesImpares; }
   
    private int steps(Grafo g){
        
        setGrafo(g);
        
        contarImpares();
       
        return getVerticesImpares();
        
    }
    
    
    public boolean isEulerian(Grafo g){
        
        int vertices = steps(g);
        
        return (vertices == 0) ? true : ((vertices == 2));
        
                
    }
    
    
}
