
package ÁrvoreGeradoraMínima;

import java.util.ArrayList;

public class MST_Prim{

    // Classe privada Prim usada para retornar grafo MST.
    private static class Prim {

        private Grafo grafo; 
                    
        // Vértice de início(por padrão: 0). 
        private int selectedInitialKnot = 0, tamanho;
        
        // Variável infinito.
        private final int inf = Integer.MAX_VALUE;

        /*
            vertices-> literalmente, os índices dos vértices do grafo;
            distancia-> vetor que armazena as melhores distâncias.
            anterior-> vetor que armazena o vértice i para um vértice j: 
                       desse modo é possível adicionar arestas ao grafo.
        */
        private int[] vertices, distancia, anterior;
                        
                        // vetor que indica se vértice já pertence ao grafo.
        private boolean[] percorridos; 

        // ArrayList que contém as arestas que serão usadas no grafo MST.
        private ArrayList<Aresta> arestas = new ArrayList<>();

        // Construtor.
        public Prim(Grafo g){ 

            this.grafo = g; 
            this.tamanho = grafo.tamanho;
            this.vertices = new int[tamanho];
            this.distancia = new int[tamanho];
            this.anterior = new int[tamanho];
            this.percorridos = new boolean[tamanho];

        }

        // Define um vértice como vértice inicial.
        private void setSelectedKnot(int n){ selectedInitialKnot = n;}
        
        
        /*
            Inicializa os vetores distancia, percorridos e anterior
            (incluindo o caso especial: knot inicial).
        */
        private void setPercorridosEDistanciaEAnterior(){

            for(int i = 0; i < distancia.length; i++){
                if(i == selectedInitialKnot){
                    distancia[i] = 0;
                    percorridos[i] = true;
                    anterior[i] = 0;
                }
                else{ 
                    distancia[i] = inf;
                    percorridos[i] = false;
                    anterior[i] = -inf;
                }
            }

        }

        // Retorna se todos os vértices já foram percorridos.
        private boolean percorridosAllTrue(){

            boolean flag = true;
            for(int i = 0; i < percorridos.length; i++){
                if(percorridos[i] == false){  flag = false; break;  }
            }
            return flag;

        }
        
        
        // Seleciona arestas para o grafo MST.
        private void selectArestasToMST(){
            
            // Variável para tratar exceção(vértice inicial: selectedInitialKnot).
            boolean isItInicialPhase = true; 
            while(!percorridosAllTrue()){ // Enquanto todos os knots não forem percorridos.
                if(isItInicialPhase){ // Para a primeira etapa: exceção.
                      
                    // Variável para pegar as adjacências do vértice atual.
                    MEsparsa.Knot aux = grafo.matriz.lista[selectedInitialKnot].primeiro;
                    while(aux != null){
                        if(!percorridos[aux.adjacente]){
                            if(aux.valor < distancia[aux.adjacente]){
                                distancia[aux.adjacente] = aux.valor;
                                anterior[aux.adjacente] = selectedInitialKnot;
                            } // Define a distância se for menor e seu vértice anterior.
                        }
                        aux = aux.proximo;
                    }
                    isItInicialPhase = false; // Termina o tratamento da exceção.

                }
                else{
                    
                    // pega o vértice de menor distância na lista e seu index.
                    int menorDist = inf, index = -1;
                    for(int i = 0; i < vertices.length; i++){
                        if(!percorridos[i]){
                            if(distancia[i] < menorDist){ 
                                menorDist = distancia[i];
                                index = i;
                            }
                        }
                    }
                    
                    // Pega o peso da adjacência entre o index e seu antecessor.
                    int value = grafo.matriz.lista[anterior[index]].mostraValor(anterior[index], index);
                    
                    // Adiciona esta aresta às arestas do grafo MST.
                    arestas.add(new Aresta(anterior[index], index, value)); 
                    
                    // Seta este vértice como percorrido.
                    percorridos[index] = true;
                    
                    /*
                        Adiciona as próximas possíveis arestas ao jogo,
                        definindo melhores distâncias se elas existirem, 
                        e atualizando seus vértices anteriores.
                    
                    */
                    MEsparsa.Knot aux = grafo.matriz.lista[index].primeiro;
                    while(aux != null){
                        if(!percorridos[aux.adjacente]){
                            if(aux.valor < distancia[aux.adjacente]){
                                distancia[aux.adjacente] = aux.valor;
                                anterior[aux.adjacente] = index;
                            }
                        }
                        aux = aux.proximo;
                    }

                }
            }

        }
  
        // Constroi o Grafo MST e suas adjacências. 
        private Grafo buildMSTGraph(){

            Grafo mst = new Grafo(tamanho);

            for(Aresta a : arestas){
                mst.criaAdjacencia(a.getI(), a.getJ(), a.getValue());
            }

            return mst;
            
        }

        // Função pública que executa todas as outras em ordem.
        public Grafo MST_Prim(){

            setSelectedKnot(0); // Por padrão.

            setPercorridosEDistanciaEAnterior(); // Seta os vetores.

            selectArestasToMST(); // Seleciona arestas para o MST.

            return buildMSTGraph(); // Retorna o grafo MST.

        }

    }


    // Função para retornar a MST de um Grafo g. Ela que controla a classe Prim.
    public static Grafo MST_Prim(Grafo g){
        
        Grafo aux = g;
        
        Prim p = new Prim(aux);
        
        return p.MST_Prim();
    
    }
    
}