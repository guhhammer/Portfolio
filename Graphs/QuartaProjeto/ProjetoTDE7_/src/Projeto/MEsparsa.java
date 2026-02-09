package Projeto;

public class MEsparsa {

    // Classe Knot.
    public class Knot{
    
        int valor;
        int adjacente;
        Knot proximo; 
        
        // Construtor.
        public Knot(int a, int v){ 
            this.valor = v;
            this.adjacente = a;
            this.proximo = null;
        }   
    }
    
    // Classe Lista.
    public class Lista{
        
        Knot primeiro, ultimo;
        
        // Construtor.
        public Lista(){ this.primeiro = this.ultimo = null; }

        // Retorna se lista está vazia.
        boolean vazia(){return (this.primeiro == null);}
        
        // Insere Knot como primeiro da lista.
        void inserirPrimeiro(int j, int valor){
            Knot novo = new Knot(j, valor);
            
            if(this.vazia()){this.primeiro = novo; this.ultimo = novo;}
            else{novo.proximo = this.primeiro; this.primeiro = novo;}
        }
        
        // Insere knot depois de um knot na lista.
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
        
        // Remove knot da lista.
        void remover(int j){
             
            if(this.primeiro.adjacente == j){
                this.primeiro = this.primeiro.proximo;
            }
            else if(this.ultimo.adjacente == j){
                Knot aux = this.primeiro;
                while(aux.proximo != this.ultimo){aux = aux.proximo;}
                aux.proximo = null;
            }
            else{
                Knot aux2 = this.primeiro;
                while(aux2.proximo.adjacente != j){ aux2 = aux2.proximo;}
                aux2.proximo = aux2.proximo.proximo;
            }
            
        }
        
        // Mostra valor de knot na lista.
        int mostraValor(int i, int j){
            Knot aux = lista[i].primeiro;
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
        
        // Retorna quantidade de knots na lista. 
        int quantidade(){
            Knot aux = this.primeiro;
            int count = 0;
            while(aux != null){ count++; aux = aux.proximo; }
            return count;
        }
        
        // Retorna se knot j existe.
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
    
    // Construtot da classe MEsparsa.
    public MEsparsa(int tam){
        
        this.tamanho = tam;
        this.lista = new Lista[tam];

        for(int i = 0; i < tamanho; i++){ lista[i] = new Lista();}
    
    }  
    
}
