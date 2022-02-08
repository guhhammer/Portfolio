
package tabela_hash_busca;


public class hash_tb {

    
    public class knot {
    
        int dado;
        knot proximo;

        public knot(int n){ this.dado = n; this.proximo = null; }

    }
    
    public class LSE {
    
        knot primeiro;
        knot ultimo;

        public LSE(){  this.primeiro = this.ultimo = null;}

        boolean vazia(){
            if(this.primeiro == null){ return true;}
            else{ return false;}
        }   

        void inserir_primeiro(int n){
            knot novo = new knot(n);
            if(vazia()){    
                this.primeiro = novo; this.ultimo =novo;
            }
            else{
                novo.proximo = this.primeiro;
                this.primeiro = novo;
            }
        }  

        knot inserir_depois(knot p, int n){
            knot novo = new knot(n);
            novo.proximo = p.proximo;
            p.proximo = novo;
            return novo;
        }   

        void inserir_ultimo(int n){
            if(this.vazia()){   this.inserir_primeiro(n);}
            else{   this.ultimo = this.inserir_depois(this.ultimo, n);}
        }   

        void inserir_ordenada(int n){
            if(this.vazia()){   this.inserir_primeiro(n);}
            else{
                if( n < this.primeiro.dado){    this.inserir_primeiro(n);}
                else{
                    if(n >= this.ultimo.dado){  this.inserir_ultimo(n);}
                    else{  
                        knot p = this.primeiro;
                        while(p.proximo.dado <  n){   p = p.proximo;}
                        this.inserir_depois(p, n);
                    }
                }
            }
        }
    
        boolean existir(int n){
            knot aux = this.primeiro;
            boolean hold = false;
            
            while(aux != null){
                if(n == aux.dado){ hold = true; break;}
                else{aux = aux.proximo;}
            }
            
            return hold;
            
        }
    
    }
    
    int tam;
    LSE tabela[];
    
    // Uso: Construtor.
    public hash_tb(int t){
        this.tam = t;
        this.tabela = new LSE[tam];

        for(int i = 0; i < tam; i++){ tabela[i] = new LSE(); }
    }
    
    // Uso: retorna hash de uma chave.
    int hash(int chave){ return (chave % this.tam);}  // gemacht.
    
    // Uso: insere chave em tabela hash.
    void inserir_ht(int chave){ tabela[hash(chave)].inserir_ordenada(chave);}// gemacht.
    
    // Uso: faz busca de chave em tabela hash
    boolean busca(int chave){ return tabela[hash(chave)].existir(chave);} //gemacht.
      
}
