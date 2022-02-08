
package ÁrvoreGeradoraMínima;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Busca_Profundidade{
    
    private static ArrayList<Integer> percurso = new ArrayList<>();

    private static Queue<Integer> possibilidades = new LinkedList<>();
    
    // Uso: faz busca em largura do vértice começo até o vértice fim.
    private static boolean busca(Grafo g, int fim){  
                             // Se nenhum vértice for passível de ser atingido.
        if(possibilidades.peek() == null){ return false;} 
       
        // Se o percurso já não conter o vértice atual da fila, faça:
        if(!percurso.contains(possibilidades.peek())){
            
            percurso.add(possibilidades.peek()); // adiciona à cabeça da fila.
            
            // pega todos as arestas desta cabeça.
            MEsparsa.Knot aux = g.matriz.lista[
                                 g.findVertice(possibilidades.peek())].primeiro;
            
            // as adciona, uma a uma, ao fim da fila.
            while(aux != null){ 
                possibilidades.offer(aux.adjacente);
                aux = aux.proximo;
            }
            
        }
        
        possibilidades.poll(); // remove a cabeça da lista.
        
        /* Condição de parada: faz recursão enquanto o elemento atual da 
           cabeça da fila Queue for diferente do vértice fim. Caso seja igual ao
           vértice fim, vai para a condição else e ela adcionará o vértice fim
           ao ArrayList, terminando a busca em largura.      */ 
        if(fim != (possibilidades.peek())){ busca(g, fim); } 
        else{ percurso.add(fim);}
        
        return true;
        
    } 
    
    /* Uso: faz a chamada da função busca, inicializa a lista Queue 
            possibilidades e retorna ArrayList do percurso do vértice
            começo(raiz) até o vértice fim.                    */
    private static boolean fazBusca(Grafo g, int raiz, int fim)
        throws NullPointerException{
                    
        possibilidades.clear();
        percurso.clear();
        
        possibilidades.add(raiz); 
        
        try{ 
            return busca(g, fim);
        }catch(NullPointerException npe){ npe.getSuppressed(); return false;}
       
    }
    
    /* Uso: função pública que retorna a busca em largura do vértice
            começo até o vértice fim de um grafo g.            */
    public static boolean buscaEmLargura(Grafo g){
        for(int i = 1; i < g.tamanho; i++){if(!fazBusca(g,0,i)){ return false;}}
        return true;
        
    }
    
    public static boolean buscaEmLargura(Grafo g, int i){
        if(fazBusca(g, i,i)){ 
            return false;
        }
        return true;
        
    }
        
    public static boolean isConnected(Grafo g){ 
        try{ return buscaEmLargura(g); }
        catch(NullPointerException e){ return false;}
    }
    
}
