
package ÁrvoreGeradoraMínima;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class MST_Kruskal {
    
    // Classe privada Kruskal usada para retornar grafo MST. 
    private static class Kruskal{

        // Grafo inicial.
        private Grafo grafo;
        private int tamanho;
        
        // Grafo de transição e retorno.
        private Grafo MSTGraph;

        private int[] vertices; // Literalemnte, os índices dos vértices do grafo.
        private boolean[] percorridos; // Vértices percorridos.
        
        // Arestas selecionadas para constroir o grafo MST.
        private ArrayList<Aresta> arestasSelected = new ArrayList<>();
        
        // Todas as arestas do grafo inicial.
        private ArrayList<Aresta> arestasAUX = new ArrayList<>();
        
        // Fila com todas as arestas.
        private Queue<Aresta> arestasQueue = new LinkedList<>();
        
        // Construtor.
        public Kruskal(Grafo g){ 
            
            this.grafo = g;
            this.tamanho = g.tamanho;
            this.vertices = new int[tamanho];
            this.percorridos = new boolean[tamanho];
            
        }

        // Inicializa os vértices.
        private void setVertices(){
            
            for(int i = 0; i < tamanho; i++){ vertices[i] = i; }
        
        }
        
        // Inicializa os percorridos.
        private void setPercorridos(){
            
            for(int i = 0; i < tamanho; i++){ percorridos[i] = false;}
        
        }
        
        // Pega todas as arestas do grafo.
        private void setArestasAUX(){
            
            for(int i = 0; i < tamanho; i++){
                MEsparsa.Knot aux = grafo.matriz.lista[i].primeiro;
                while(aux != null){
                    arestasAUX.add(new Aresta(i, aux.adjacente, aux.valor));
                    aux = aux.proximo;
                }
            }
            
        }
        
        // Ordena as arestas de arestasAUX.
        private void ordenarArestas(){
            
            Collections.sort(arestasAUX, new Aresta());
   
        }
        
        // Remove arestas duplicadas caso existão.
        private void removeDuplicates(){
            
            Aresta a, a_;
            boolean reboot = false;
            for(int i = 0; i < arestasAUX.size(); i++){
                a = arestasAUX.get(i);
                for(int j = 0; j < arestasAUX.size(); j++){
                    a_ = arestasAUX.get(j);
                    
                    if(a.getI() == a_.getJ() &&
                        a.getJ() == a_.getI() &&
                        a.getValue() == a_.getValue()){
                        arestasAUX.remove(i);
                        reboot = true;
                        break;
                    }
                    
                }
                if(reboot){ break; }
                
            }
            
            if(reboot){ removeDuplicates(); }
            
        }
        
        // Inicializa o grafo MST.
        private void setMSTGraph(){
            
            MSTGraph = new Grafo(tamanho);
            
        }
        
        // Retorna se todos os vértices foram percorridos.
        private boolean isItAllTrue(){
            
            for(int i = 0; i < percorridos.length; i++){ if(!percorridos[i]){ return false; }}
            return true;
        
        }
        
        // Inicializa a fila com os valores de arestasQueue.
        private void setQueue(){
            
            for(Aresta a : arestasAUX){ arestasQueue.offer(a); }
                    
        }
        
        // Retorna se o grafo é conexo.
        private boolean isGraphConnected(){
            
            MSTGraph = new Grafo(tamanho);
            
            if(arestasSelected.isEmpty()){
                return false;
            }
            else{
                for(Aresta a : arestasSelected){ 
                    MSTGraph.criaAdjacencia(a.getI(), a.getJ(), a.getValue());
                }
            }
            
            Grafo aux = MSTGraph;
            MSTGraph = new Grafo(tamanho);
            
            return Busca_Profundidade.isConnected(aux);
            
        }
        
        // Retorna se adicionar uma aresta gera ciclo. 
        private boolean vaiGerarCiclo(Aresta aux){
            
            MSTGraph = new Grafo(tamanho);
            
            if(arestasSelected.isEmpty()){
                return false;
            }
            else{
                for(Aresta a : arestasSelected){ 
                    MSTGraph.criaAdjacencia(a.getI(), a.getJ(), a.getValue());
                }
            }
            
            Grafo aux2 = MSTGraph;
            MSTGraph = new Grafo(tamanho);
            
            return !Busca_Profundidade.buscaEmLargura(aux2, aux.getI());
        
        }
        
        // Seleciona as arestas para o grafo MST.
        private void selectArestas(){
        
            setQueue();
                 
            boolean run1 = true;
            while(!isItAllTrue() && !isGraphConnected()){
                
                Aresta aux = arestasQueue.peek();   
                percorridos[aux.getI()] = true;
                percorridos[aux.getJ()] = true;
                arestasSelected.add(aux);
                
                if(vaiGerarCiclo(aux) && !run1){
                   arestasSelected.remove(arestasSelected.indexOf(aux));   
                }
                
                arestasQueue.poll();
                
                if(tamanho-1  == arestasSelected.size()){ break;}
                run1 = false;
                
            }
                       
        }
        
        // Preenche o grafo caso este não seja conexo.
        private void fulfill(){
        
            if(!isGraphConnected()){
                
                while(arestasSelected.size() != tamanho-1 && !arestasQueue.isEmpty()){
                    
                    arestasSelected.add(arestasQueue.peek());
                    arestasQueue.poll();
                    
                }
            }
            
        }
        
        // Retorna o grafo MST.
        private Grafo returnGraph(){
        
            for(Aresta a : arestasSelected){ 
                MSTGraph.criaAdjacencia(a.getI(), a.getJ(), a.getValue());
            }
            
            return MSTGraph;
            
        }
        
        // Função de execução da classe.
        public Grafo MST_Kruskal(){
            
            setVertices();

            setPercorridos();
            
            setArestasAUX();
     
            ordenarArestas();         
             
            removeDuplicates();
              
            setMSTGraph();
            
            selectArestas();
                        
            fulfill();

            return returnGraph();
            
        }
        
    }
    
    
    // Função para retornar a MST de um Grafo g. Ela que controla a classe Kruskal.
    public static Grafo MST_Kruskal(Grafo g){
        
        Grafo aux = g;
        
        Kruskal k = new Kruskal(aux);
        
        return k.MST_Kruskal();
    
    }
    
}
