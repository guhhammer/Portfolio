package Projeto;

import Projeto.MEsparsa.*;
import java.util.List;

public class Grafo{
    
    int tamanho;
    Vertice[] vertices;
    MEsparsa matriz;
    boolean direcionado;
    
    // Uso: Classe Vértice.
    public class Vertice{
        
        String nome;
        int index;
        
        // Uso: Métodos set.
        void setNome(String n){ this.nome = n;}
        void setIndex(int n){ this.index = n;}
        
        // Uso: Métodos get.
        String getNome(){ return nome;}
        int getIndex(){ return index;}
        
    }
    
    // Uso: Construtor.
    public Grafo(int tamanho, boolean direcionado){
        
        MEsparsa aux = new MEsparsa(tamanho);
        
        this.tamanho = tamanho;
        this.matriz = aux;
        this.vertices = new Vertice[tamanho];
        this.direcionado = direcionado;
       
        for(int i = 0; i < tamanho; i++){vertices[i] = new Vertice();}  
        
        for(int i = 0; i < tamanho; i++){vertices[i].setIndex(i);} 
        
        for(int i = 0; i < tamanho; i++){vertices[i].setNome(""+i);}
        
    }
    
    // Uso: Construtor.
    public Grafo() {   }
   
    // Uso: Construtor.
    public Grafo(boolean direcionado) {  this.direcionado = direcionado;  }
    
    // Uso: cria adjacência entre i e j.
    void criaAdjacencia(int i, int j, int p){
    	if(direcionado) { this.matriz.lista[i].inserir(j, p); }
    	else {
    		this.matriz.lista[i].inserir(j, p);
            this.matriz.lista[j].inserir(i, p);
    	}
    }
    
    // Uso: remove adjacência entre i e j.
    void removeAdjacencia(int i, int j){
    	if(direcionado) {  this.matriz.lista[i].remover(j); }
    	else {
    		this.matriz.lista[i].remover(j);
            this.matriz.lista[j].remover(i);
    	}
    }
    
    // Uso: imprime adjacências do Grafo.
    void imprimeAdjacencias(){
        
    	if(this.quantidadeDeArestas() != 0) {
	       
    		for(int  i = 0; i < this.tamanho; i++){
	            Knot aux = this.matriz.lista[i].primeiro;
	            System.out.print("Vértice "+i+": ");
	            while(aux != null){
	                System.out.print(" {adjacente: "+aux.adjacente+
	                                 ", valor: "+aux.valor+"} ");
	                aux = aux.proximo;
	            }
	            System.out.println("");
	        }
    
    	}
    
    }
    
    // Uso: retorna se adjacência existe.
    boolean existe(int i, int j){ return this.matriz.lista[i].existir(j); }
    
    // Uso: seta informação(nome) no vértice.
    void setaInformacao(int i, String v){vertices[i].setNome(v);}
    
    // Uso: retorna soma dos adjacentes.
    int adjacentes(int i, int[] adj){
        
        int count = 0,index = 0; // valor que indentifica não relação("um 0").
        for(int s = 0; s < adj.length; s++){ adj[s] = Integer.MIN_VALUE; }
        
        Knot aux = this.matriz.lista[i].primeiro;
        while(aux != null){ 
            adj[index++] = aux.adjacente; count++; aux = aux.proximo;
        }
        
        return count;
    }
    
    // Uso: retorna valor da adjacência entre o knot i e j.
    int mostraValor(int i, int j){return this.matriz.lista[i].mostraValor(i, j);}
    
    // Uso: encontra vértice j.
    int findVertice(String j){
        int v = -1;
        for(int i = 0; i < tamanho; i++){if(j.equals(this.vertices[i].getNome())){ v = i; }}
        return v;
    }
    
    // Uso: encontra vértice j.
    int findVertice(int j){
        int v = -1;
        for(int i = 0; i < tamanho; i++){if(i == j){ v = i; }}
        return v;
    }
    
    // Uso: retorna quantas arestas o grafo possui.
    int quantidadeDeArestas() {
    	
    	int aux = 0;
    	for(int i = 0; i < this.tamanho; i++) { aux += this.matriz.lista[i].quantidade(); }
    	return aux;
    	
    }
    
    // Uso: retorna quantidade de Vértices.
    int quantidadeDeVertices() { return this.tamanho; }
    
    // Uso: retorna List com os componentes.
    @SuppressWarnings("rawtypes")
	List getComponentes(){ return Componentes.componentesGrafo(this); }
    
    // Uso: retorna o número de componentes do grafo.
    int quantidadeComponentes(){ return Componentes.componentesGrafo(this).size(); }
    
    // Uso: imprime apenas os componentes.
    void printOnlyComponentes(){
        if(this.quantidadeDeArestas() != 0) {
        	Componentes.printOnlyComponentes(Componentes.componentesGrafo(this));
        }
    }
    
    // Uso: imprime os componentes e a quantidade.
    void printComponentes(){ 
    	if(this.quantidadeDeArestas() != 0) {
    		Componentes.printComponentes(Componentes.componentesGrafo(this));
    	}
    }   
    
    // Uso: retorna se grafo é conexo ou não.
    boolean ehConexo(){  return (this.quantidadeComponentes() < 2); }
    
    // Uso: retorna se grafo é euleriano ou não.
    boolean ehEuleriano() {  return GrafoEuleriano.isEulerian(this);   }
    
    // Uso: retorna de grafo tem ciclo ou não.
    boolean temCiclo() { 
    	if(direcionado) {	return Ciclos.hasCycleDirected(this); }
    	else { return Ciclos.hasCycleNonDirected(this); }
    }
    
    // Uso: retorna grafo random com definição de boundary.
    Grafo random(int arestas, boolean conexo, int boundary) {
    	GerarGrafoAleatoriamente.setBoundary(boundary);
    	return GerarGrafoAleatoriamente.makeARandomGraph(this.tamanho, arestas, conexo, this.direcionado);
    }
    
    // Uso: retorna grafo random.
    Grafo random(int arestas, boolean conexo) {
    	
    	return GerarGrafoAleatoriamente.makeARandomGraph(this.tamanho, arestas, conexo, this.direcionado);
    }
    
    // Uso: salva grafo em arquivo .pajek.
    void fazerArquivoPajek(String nome) {
    	
    	try { criarArquivoPajek.writePajek(this, nome, direcionado); }
    	catch(Exception e) { e.getSuppressed(); }
    	
    }
    
    // Uso: monta grafo a partir de arquivo .pajek.
    Grafo extrairGrafoDe(String nome) {	  return lerArquivoPajek.build(nome);  }
    
    // Uso: monta grafo a partir de routes.csv (última questão).
    Grafo linhasAereas() { return lerDatabase.extractDatabase("src\\Projeto\\routes.csv", this.direcionado); }
    
    // Uso: imprime grafo da última questão (routes.csv).
    void imprimeAdjacenciasLinhasAereas() {
    	
    	if(this.quantidadeDeArestas() != 0) {
 	       
    		for(int  i = 0; i < this.tamanho; i++){
	            Knot aux = this.matriz.lista[i].primeiro;
	            System.out.print("Aeroporto "+this.vertices[i].getNome()+": ");
	            while(aux != null){
	                System.out.print(" {Aeroporto adjacente: "+this.vertices[aux.adjacente].getNome()+
	                                 ", valor: "+aux.valor+"} ");
	                aux = aux.proximo;
	            }
	            System.out.println("");
	        }
    
    	}
    	
    }
   
    // Uso: calcula o coeficiente de C.I. de cada vértice.
    void centralidadeDeIntermediacao() { BetweennessCentrality.centralidadeDeIntermediacao(this, false); }
    
    // Uso: calcula o coeficiente de C.I. de cada vértice.
    void centralidadeDeIntermediacao(boolean showStacks) {
    	BetweennessCentrality.centralidadeDeIntermediacao(this, showStacks);  
    }
    	
    // Uso: calcula o coeficiente de C.P. de cada vértice.
    void centralidadeDeProximidade() { ClosenessCentrality.centralidadeDeProximidade(this); }
    
    
    

    void ponte_aerea_distancia(String c, String d, int tam, int qntd) {
    	PontesAereas.distancia(this, c, d, tam, qntd, false);
    }
    	
    void ponte_aerea_distancia(String c, String d, int tam, int qntd, boolean s) {
    	PontesAereas.distancia(this, c, d, tam, qntd, s);
    }
    
    void ponte_aerea_distancia_e_conexoes(String c, String[] cnx, String d, int tam, int qntd) {
    	PontesAereas.distanciaEConexoes(this, c, cnx, d, tam, qntd, false);
    }
    
    void ponte_aerea_distancia_e_conexoes(String c, String[] cnx, String d, int tam, int qntd, boolean s) {
    	PontesAereas.distanciaEConexoes(this, c, cnx, d, tam, qntd, s);
    }
   
    
    

}
