
package BUSCAS;

public class MEsparsa {
    
    // NOME: Gustavo Hammerschmidt.
    
    // Classe nó.
    public class Knot{
    
        int valor;  // quantidade de mensagens recebidas de um vértice. 
        String email; // email da pessoa.
        Knot proximo; // próximo email da lista.

        // Construtor Knot.
        public Knot(String a, int v){ 
            this.email = a;
            this.valor = v;
            this.proximo = null;
        } 
        
    }
    
    
    // Classe lista.
    public class Lista{
        
        Knot primeiro, ultimo; // ponteiros da lista.
        
        // Construtor Lista.
        public Lista(){ this.primeiro = this.ultimo = null; }

        // Uso: retorna se lista está vazia.
        boolean vazia(){return (this.primeiro == null);}
        
        // Uso: retorna o tamanho da lista.
        int size(){
            Knot aux = this.primeiro; int count = 0;
            while(aux != null){ count++; aux = aux.proximo;}
            return count;
        }
       
        // Uso: insere Knot como primeiro na Lista.
        void inserirPrimeiro(String j, int valor){
            Knot novo = new Knot(j, valor);
            if(this.vazia()){this.primeiro = novo; this.ultimo = novo;}
            else{novo.proximo = this.primeiro; this.primeiro = novo;}
        }
       
        // Uso: insere Knot depois de um outro Knot.
        void inserirDepois(Knot x, String j, int valor){
            Knot novo = new Knot(j, valor);
            novo.proximo = x.proximo; x.proximo = novo;
        }
        
        // Uso: insere Knot na Lista como último da Lista.
        void inserir(String j, int valor){
        
            if(this.vazia()){this.inserirPrimeiro(j, valor);}
            else{
                if(this.existir(j)){
                    Knot aux = this.primeiro; boolean flag = false;
                    while(aux != null && flag == false){ 
                        if(j.equals(aux.email)){aux.valor +=valor; flag =true;}
                        aux = aux.proximo;
                    }
                }   
                else{this.inserirDepois(this.ultimo, j, valor);}
            }  // Obs.: inserir equivale ao método inserir_último.

        }
        
        // Uso: remove um Knot da Lista.
        void remover(String j){
                      
            if(this.ultimo.email.equals(j)){
                Knot aux = this.primeiro;
                while(aux.proximo != this.ultimo){aux = aux.proximo;}
                aux.proximo = null;
            }
            else if(this.primeiro.email.equals(j)){
                this.primeiro = this.primeiro.proximo;
            }
            else{
                Knot aux2 = this.primeiro;
                while(!aux2.proximo.email.equals(j)){ aux2 = aux2.proximo;}
                aux2.proximo = aux2.proximo.proximo;
            }
            
        }
        
        // Uso: mostra quantidade de mensagens recebidas de um Knot.
        int mostraValor(String j){
            Knot aux = this.primeiro;
            while(aux != null){if(j.equals(aux.email)){break;} aux = aux.proximo;}
            return aux.valor;
        }
        
        // Uso: verifica se Knot existe na Lista.
        boolean existir(String j){
            Knot aux = this.primeiro; boolean flag = false;
            while(aux != null && flag == false){
                if(j.equals(aux.email)){ flag = true; } aux = aux.proximo;
            }
            return flag;
        }     
        
        // Uso: soma todas as mensagens recebidas de cada adjacência da Lista.
        int contarArestas(){
            Knot aux = this.primeiro; int contador = 0;
            while(aux != null){ contador += aux.valor; aux = aux.proximo; }
            return contador;
        }
    
    }
    
    
    private int tamanho; // tamanho da matriz.
    Lista[] lista;       // lista de Listas.
    
    // Construtor Matriz Esparsa.
    public MEsparsa(int tam){
        
        this.tamanho = tam;
        this.lista = new Lista[tam];
        
        // inicializa cada valor da lista como uma nova Lista.
        for(int i = 0; i < tamanho; i++){ lista[i] = new Lista();}
    
    }  
    
}
