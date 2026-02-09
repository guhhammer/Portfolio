
package Fila_Circular;

public class FilaC {
    
    int dado[];
    int primeiro;
    int ultimo;
    int Max;
    int tamanho;
    
    
    // Uso: construtor de Fila, define o tamanho.
    public FilaC(int m){
        this.primeiro = 0; this.ultimo = -1; this.tamanho = 0;
        this.Max = m; this.dado = new int[Max];
    }   // gemacht.
    
    // Uso: retorna se a pilha está vazia ou não.
    boolean vazia(){return (tamanho == 0) ? true : false; }   // gemacht.
    
    // Uso: retorna se a pilha está cheia ou não.
    boolean cheia(){ return (tamanho == (Max)) ? true : false;}   // gemacht.
    
    //  Uso: retorna o primeiro elemento.
    int primeiro(){ return dado[primeiro];}   // gemacht.
    
    //  Uso: retorna o último elemento.
    int ultimo(){ return dado[ultimo];}   // gemacht.
   
    //  Uso: retorna o tamanho Máximo da fila.
    int getTamanho(){ return Max;}   // gemacht.
    
    //  Uso: retorna o tamanho da fila.
    int gettamanho(){ return tamanho;}   //gemacht.
    
    // Uso: adiciona um elemento à fila circular.
    void inserir(int n){
        if(!cheia()){  // Se a pilha não estiver cheia.
            if(ultimo == Max-1){  // último volta à posição 0.
                this.tamanho++;  this.ultimo = 0; this.dado[ultimo] = n;
            } 
            else{ this.tamanho++; this.ultimo++; this.dado[ultimo] = n; }
        }                       // adiciona elemento na posição último.
    }   // gemacht.
    
    // Uso: remove um elemento da fila circular.
    void remover(){
        if(!vazia()){   // Se a pilha não estiver vazia.
            if(primeiro == Max-1){ // primeiro volta à posição 0.
                this.dado[primeiro] = '\0'; this.primeiro = 0; this.tamanho--;    
            }
            else{this.dado[primeiro] = '\0'; this.primeiro++; this.tamanho--;}
        }                //remove o primeiro elemento.
    }   // gemacht.
    
    
    
}
