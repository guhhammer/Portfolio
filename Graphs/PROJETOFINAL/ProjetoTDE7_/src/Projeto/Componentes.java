package Projeto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Componentes {

    // ArrayList do percurso.
    private static ArrayList<Integer> componente = new ArrayList<>();
    
    // Queue das possibilidades.
    private static Queue<Integer> possibilidades = new LinkedList<>(); 
    
    // Uso: faz a busca em largura.
    private static void busca(Grafo g){
        
        if(possibilidades.peek() == null){ return; }
        
        if(!componente.contains(possibilidades.peek())){
            
            componente.add(possibilidades.peek()); 
            
            MEsparsa.Knot aux = g.matriz.lista[g.findVertice(possibilidades.peek())].primeiro;

            while(aux != null){
                possibilidades.offer(aux.adjacente);
                aux = aux.proximo;
            }
            
        }
        
        possibilidades.poll();
        
        if(possibilidades.peek() != null){ busca(g); }        
        
    }
    
    // Uso: chama a função busca e retorna o percurso em int[].
    private static int[] fazBusca(Grafo g, int raiz){
        
        possibilidades.clear();
        componente.clear();
        
        possibilidades.add(raiz);
        
        busca(g);
        
        if(componente.size() > 0){

            Arrays.sort(componente.toArray());
            int[] aux = new int[componente.size()];
            for(int i = 0; i < componente.size(); i++){ aux[i] = componente.get(i); }

            return aux;

        }
        else{
            return null;
        }
        
    }
    
    // Uso: remove percursos iguais do ArrayList de um grafo não-direcionado.
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
    
    // Uso: remove percursos repetidos do ArrayList de um grafo direcionado.
    private static void distinctDirect(ArrayList<int[]> aux){
    	
    	boolean flag = true;
    	int removed = -1;
    	for(int x = 0; x < aux.size(); x++) {
    		
    		for(int y = 0; y < aux.size(); y++) {
    			
    			if(x != y) {
    				
    				int[] x_ = aux.get(x), y_ = aux.get(y);
    				
    				for(int i = 0; i < x_.length; i++) {
    					
    					for(int j = 0; j < y_.length; j++) {
    						
    						if(x_[i] == y_[j]) { 
    							
    							removed = (x_.length >= y_.length) ? y : x;
    							flag = false;
    							
    						}
    						
    					}
	    					    				
	    				if(!flag) { break; } 
	    				
	    			}
    			 
    			}
    			
    			if(!flag) { break; }
    		
    		}
    		
    		if(!flag) { break; } 
    		
    	}
    	
    	if(!flag) { 

    		aux.remove(removed);
    		
    		distinctDirect(aux);
    		
    	} 
    	
    }
    
    // Uso: retorna Componentes de um Grafo.
    @SuppressWarnings("rawtypes")
	private static List getComponents(Grafo g){
            
        int[] vertices = new int[g.tamanho];
        for(int i = 0; i < g.tamanho; i++){
            vertices[i] = g.vertices[i].index;
        }
        
        ArrayList<int[]> aux = new ArrayList<>();
        for(int i : vertices){ 
            if(fazBusca(g, i) != null){ aux.add(fazBusca(g, i)); }
        }
        
        for(int[] i : aux){  Arrays.sort(i); } // ordena os componentes
        
        if(g.direcionado) {
        
        	distinctDirect(aux); // remove repetidos.
        	distinctDirect(aux); // remove repetidos.
        	
        }
        else {
        
        	distinct(aux); // remove repetidos.
        	distinct(aux); // remove repetidos.
        
        }
        
        return aux;
         
    }
    
    // Uso: chama a função getComponents.
    @SuppressWarnings("rawtypes")
	public static List componentesGrafo(Grafo g){  return getComponents(g);  }
    
    // Uso: imprime somente os componentes do grafo.
    @SuppressWarnings("rawtypes")
	public static void printOnlyComponentes(List l){
       
        System.out.print("\n\n\nComponentes: \n\n\t\t");
        
        for(int i = 0; i < l.size(); i++){
            System.out.print(Arrays.toString((int[]) l.get(i)));
            if(i != l.size()-1){ System.out.print(",  \n\t\t");}
            if(i == l.size()-1){ System.out.print(". \n\n");}
        }
        
        System.out.print("\n\n\n");
        
    }
    
    // Uso: imprime os componentes e quantidade. 
    @SuppressWarnings("rawtypes")
	public static void printComponentes(List l){
        
        System.out.println("\n\nQuantidade de Componentes do Grafo: "+l.size()+".");
        System.out.print("\n\n\nComponentes: \n\n\t\t");
        
        for(int i = 0; i < l.size(); i++){
            System.out.print(Arrays.toString((int[]) l.get(i)));
            if(i != l.size()-1){ System.out.print(",  \n\t\t");}
            if(i == l.size()-1){ System.out.print(". \n\n");}
        }
        
        System.out.print("\n\n\n");
        
    }
    
}
