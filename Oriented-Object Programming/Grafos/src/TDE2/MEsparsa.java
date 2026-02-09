
package TDE2;  //Grupo 14: Gustavo Hammerschmidt.

public class MEsparsa {

    // Classe Knot. 
    public class Knot{
    
        int valor;
        int adjacente;
        Knot proximo;

        public Knot(int a, int v){ 
            this.valor = v;
            this.adjacente = a;
            this.proximo = null;
        }   
    }
    
    
    public class Lista{
        
        Knot primeiro, ultimo;
        
        public Lista(){ this.primeiro = this.ultimo = null; }

        boolean vazia(){return (this.primeiro == null);}
        
        void inserirPrimeiro(int j, int valor){
            Knot novo = new Knot(j, valor);
            
            if(this.vazia()){this.primeiro = novo; this.ultimo = novo;}
            else{novo.proximo = this.primeiro; this.primeiro = novo;}
        }
        
        void inserirDepois(Knot x, int j, int valor){
            Knot novo = new Knot(j, valor);
            novo.proximo = x.proximo;
            x.proximo = novo;
        }
        
        // Obs.: inserir equivale ao método inserir_último.
        void inserir(int j, int valor){
            if(this.vazia()){this.inserirPrimeiro(j, valor);}
            else{this.inserirDepois(this.ultimo, j, valor);}
        }
        
        void remover(int j){
                      
            if(this.ultimo.adjacente == j){
                Knot aux = this.primeiro;
                while(aux.proximo != this.ultimo){aux = aux.proximo;}
                aux.proximo = null;
            }
            else if(this.primeiro.adjacente == j){
                this.primeiro = this.primeiro.proximo;
            }
            else{
                Knot aux2 = this.primeiro;
                while(aux2.proximo.adjacente != j){ aux2 = aux2.proximo;}
                aux2.proximo = aux2.proximo.proximo;
            }
            
        }
        
        int mostraValor(int j){
            Knot aux = this.primeiro;
            int hold = 0;
            while(aux != null){
                if(aux.adjacente == j){
                    hold = aux.valor;
                    break;
                }
                else{aux = aux.proximo;}
            }
        
            return hold;
        }
        
        boolean existir(int j){
            Knot aux = this.primeiro;
            boolean hold = false;
            while(aux != null){
                if(aux.adjacente == j){
                    hold = true;
                    break;
                }
                else{
                    aux = aux.proximo;
                }
            }
            return hold;
        }
        
    }
    
    int tamanho;
    Lista[] lista;
    
    public MEsparsa(int tam){
        
        this.tamanho = tam;
        this.lista = new Lista[tam];

        for(int i = 0; i < tamanho; i++){ lista[i] = new Lista();}
    
    }  
    
}
