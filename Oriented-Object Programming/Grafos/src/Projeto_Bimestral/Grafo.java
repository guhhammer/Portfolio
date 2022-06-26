
package Projeto_Bimestral;

public class Grafo {
    
    // NOME: Gustavo Hammerschmidt.
    
    private int tamanho; // tamanho do Grafo.
    Vertice[] vertices;  // lista de Vértices.
    MEsparsa matriz;     // matriz esparsa
    
    
    // Classe vértice.
    public class Vertice{
        
        private String nome; // nome do vértice.
        
        // Uso: define valor para a variável nome.
        public void setNome(String n){ this.nome = n;}
        
        // Uso: pega valor da variável nome.
        public String getNome(){ return nome;}
        
    }
    
    
    // Construtor Grafo.
    public Grafo(String[] nomes){
               
        this.tamanho = nomes.length;
        this.matriz = new MEsparsa(tamanho);
        this.vertices = new Vertice[tamanho];
        
        // inicializa cada vértice da lista de vértices.
        for(int i = 0; i < tamanho; i++){vertices[i] = new Vertice();}
        
        // dá, a cada vértice, o nome correspondente no vetor nomes.
        for(int i = 0; i < tamanho; i++){vertices[i].setNome(nomes[i]);}
        
    }
    
    // Uso: retorna tamanho(número de vértices) do grafo .
    public int getTamanho(){ return this.tamanho;}
   
    // Uso: encontra vértice com nome igual ao email.
    public int findVertice(String email){
        int v = -1;
        for(int i = 0; i < tamanho; i++){
            if(email.equals(vertices[i].getNome())){  v = i; }
        }
        return v;
    }
    
    // Uso: encontra email com vértice igual a s.
    public String findEmail(int s){ return this.vertices[s].getNome(); }
    
    // Uso: pega o número de arestas do grafo inteiro.
    private int numeroArestas(){
        int contador = 0;
        for(int i = 0; i < this.getTamanho(); i++){
            contador += this.matriz.lista[i].contarArestas();
        }                     // conta as arestas de cada vértice do grafo.
        return contador;
    }
    
    // Uso: retorna o número de Arestas do grafo.
    public int getNumeroArestas(){ return this.numeroArestas();}
    
    
}
