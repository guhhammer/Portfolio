
package árvore;       // NOME: Gustavo Hammerschmidt.     

public class nodeS {
    
    String info;
    int alturah;
    nodeS esquerda;
    nodeS direita;
    int freq;

    // Uso: construtor de nodeS.
    public nodeS(String i){
        this.info = i;
        this.alturah = -1;
        this.esquerda = null;
        this.direita = null;
        this.freq = 1;
    }   // gemacht.
}
