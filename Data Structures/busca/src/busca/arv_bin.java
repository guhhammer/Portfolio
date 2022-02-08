
package busca;

public class arv_bin{
    
    node raiz;
    
    // Construtor: árvore.
    public arv_bin(){
        this.raiz = null;
    }   // gemacht.
    
    // Uso: insere elemento na árvore.
    void inserir(int x){
        if(this.raiz == null){ this.raiz = new node(x);}
        else{ 
            node next = this.raiz, hold = null;
            
            while(next != null){
                hold = next;
                if(x < next.info){ next = next.esquerda;}
                else{ next = next.direita;}
            }
            
            if(x < hold.info){ hold.esquerda = new node(x);}
            else{ hold.direita = new node(x);}
        }
    }   // gemacht.
    
    // Uso: função de busca.  ver se usar essa ou a outra.
    boolean existir(node tree, int n) throws Exception{
        if(this.raiz == null){ return false; }
        else{
            while(tree != null && n != tree.info){
               if(n < tree.info){ tree = tree.esquerda;}
               else{ tree = tree.direita; }
            }
            try{ return (n == tree.info);}
            catch(Exception e){ return false; }
        }
    }   // gemacht.
    
    // Uso: imprime árvore em in-ordem.
    void in_ordem(node n){
        if(n != null){
            this.in_ordem(n.esquerda);     
            System.out.print(n.info+", "); 
            this.in_ordem(n.direita);      
        }
    }   // gemacht.
   
    // Uso: imprime árvore em in-ordem. 
    void imprimir_ordem(){
        if(this.raiz == null){;}
        else{
            System.out.print("\n\n\tIn-Ordem:\t { ");
            this.in_ordem(this.raiz);
            System.out.print(" }\n\n");
        }
    }   // gemacht.
    
}
