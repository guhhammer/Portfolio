
package Fila_Circular;

public class FilaC {
    
    int dado[];
    int primeiro;
    int ultimo;
    int Max;
    int tamanho;
    
    
    // Uso: construtor de Fila, define o tamanho.
    public FilaC(int m){
        this.primeiro = 0;
        this.ultimo = -1;
        this.Max = m;
        this.dado = new int[Max];
        this.tamanho = 0;
    }   // gemacht.
    
    // Uso: retorna se a pilha está vazia ou não.
    boolean vazia(){
        if(tamanho == 0){ return true;}
        else{ return false;}
    }   // gemacht.
    
    // Uso: retorna se a pilha está cheia ou não.
    boolean cheia(){
        if(tamanho == (Max)){ return true;}
        else{ return false;}
    }   // gemacht.
    
    // Uso: adiciona um elemento à fila circular.
    void inserir(int n){
        if(!cheia()){  // Se a pilha não estiver cheia.
            if(ultimo == Max-1){  // último volta à posição 0.
                this.tamanho++;   // enche a fila. 
                this.ultimo = 0;
                this.dado[ultimo] = n;
            }
            else{  // adiciona elemento na posição último.
                this.tamanho++;  
                this.ultimo++;
                this.dado[ultimo] = n;
            }
        }
    }   // gemacht.
    
    // Uso: remove um elemento da fila circular.
    void remover(){
        if(!vazia()){   // Se a pilha não estiver vazia.
            if(primeiro == Max-1){ // primeiro volta à posição 0.
                this.dado[primeiro] = '\0';
                this.primeiro = 0; 
                this.tamanho--;    // esvazia a fila.
            }
            else{  // remove o primeiro elemento.
                this.dado[primeiro] = '\0';
                this.primeiro++;
                this.tamanho--;
            }
        }   
    }   // gemacht.
    
    //  Uso: retorna o primeiro elemento.
    int primeiro(){ return dado[primeiro];}   // gemacht.
    
    //  Uso: retorna o último elemento.
    int ultimo(){ return dado[ultimo];}   // gemacht.
   
    //  Uso: retorna o tamanho Máximo da fila.
    int getTamanho(){ return Max;}   // gemacht.
    
    //  Uso: retorna o tamanho da fila.
    int gettamanho(){ return tamanho;}   //gemacht.
    
}
