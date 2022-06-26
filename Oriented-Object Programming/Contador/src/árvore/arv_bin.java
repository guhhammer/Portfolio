
package árvore;      // NOME: Gustavo Hammerschmidt.

public class arv_bin{
    
    node raiz;
    
    // Construtor: árvore.
    public arv_bin(){
        this.raiz = null;
    }   // gemacht.
       
    
    // Uso: método para utilizar-se da recursividade.
    public void inserir(int x){ raiz = inserir(x, raiz);}   // gemacht.
    
    // Uso: insere um elemento na árvore.
    public node inserir(int x, node tree){
        if(tree == null){ tree = new node(x);} // árvore vazia.
        else{
            if(x < tree.info){ // vai para braço esquerdo da árvore.
                tree.esquerda = inserir(x, tree.esquerda);//insere se tree->null.
                if(fb(tree) == 2){
                    if(x < tree.esquerda.info)
                    { tree = rotacionar_esquerda(tree); } //rot.-> esq
                    else{ tree = rotacionar_dir_esq(tree);}
                }                       //rot.-> 1. dir 2. esq
            }      
            else if(x > tree.info){//ignora números iguais;colocar = pra trocar
                tree.direita = inserir(x, tree.direita);//insere se tree->null.
                if(fb(tree) == -2){
                    if(x > tree.direita.info)
                    { tree = rotacionar_direita(tree);} //rot.-> dir
                    else{ tree = rotacionar_esq_dir(tree);}
                }                           //rot.-> 1. esq 2. dir 
            }
        }                
        this.redefinir_altura(tree);// redefine altura da árvore.
        return tree;
    }   // gemacht.
    
    
    // Uso: imprime se existe ou não.
    void existe(int n){
        System.out.print("\nElemento "+n+" está na árvore ? "+
        ((this.existir(this.raiz, n)) ? "\tSim.\n" : "\tNão.\n"));
    }   // gemacht.
    
    // Uso: verifica existência de um elemento.
    boolean existir(node tree, int n){
        if(this.raiz == null){  return false;} // vazia -> nenhum elemento.
        else{
            if(n == tree.info){  return true;} // se igual ao atual elemento.
            else{
                if(n < tree.info){  // procura na árvore esquerda.
                  if(tree.esquerda!=null){return this.existir(tree.esquerda,n);}
                  else{  return false;} // fim da árvore -> nenhum elemento.
                }
                else{  // procura na árvore direita.
                  if(tree.direita!=null){return this.existir(tree.direita,n);}
                  else{  return false;} // fim da árvore -> nenhum elemento.
                }
            }
        }
    }   // gemacht.
    
    
    // Uso: retorna o menor valor.
    static int minimo(node n){
        node aux = n;
        while(aux.esquerda != null){ aux = aux.esquerda; } 
        return aux.info;        // busca o valor mais à esquerda.
    }   // gemacht.
    
    // Uso: retorna o maior valor.
    static int maximo(node n){
        node aux = n;
        while(aux.direita != null){ aux = aux.direita; }
        return aux.info;       // busca o valor mais à direita.
    }   // gemacht.
    
    // Uso: remover o menor valor.
    node remover_minimo(node n){
        if(n != null){
            if(n.esquerda != null){
                n.esquerda = this.remover_minimo(n.esquerda);//define como null.
                return n; 
            }
            else{ return n.direita;}//define como null.
        }
        else{ return null;} // retorna null se árvore vazia.
    }   // gemacht.
    
    // Uso: remover o maior valor.
    node remover_maximo(node n){
        if(n != null){
            if(n.direita != null){
                n.direita = this.remover_maximo(n.direita);//define como null.
                return n;
            }
            else{ return n.esquerda;}//define como null.
        }
        else{ return null;} // retorna null se árvore vazia.
    }   // gemacht.
    
    
    // Uso: remove número.
    void remove(int n){
        System.out.print("\nRemover ("+n+"): ");
        if(this.existir(this.raiz,n)){
            this.remover(this.raiz, this.raiz, n);
            System.out.print("\n\tNúmero "+n+" foi removido.\n\n");
        }
        else{
            System.out.print("\n\tNúmero "+n+" não está na árvore.\n\n");
        }
    }   // gemacht.
    
    // Uso: remove elemento da árvore.
    void remover(node tree, node aux, int n){
        if(this.raiz != null){ // se a árvore não estiver vazia.
            if(this.existir(this.raiz, n) == true){ // se n existir.
                if(n == tree.info){ // se n for igual ao valor atual.
                    if(tree.esquerda == null && tree.direita == null){
                        if(aux.info <= tree.info){ 
                            aux.direita = null; this.balancear(aux, n);
                        }
                        else{ aux.esquerda = null; this.balancear(aux, n); }
                    } // if: removedor de folhas.
                    else if(tree.esquerda != null && tree.direita == null){
                        aux = tree.esquerda; this.balancear(aux, n);
                    } // removedor de 1 filho(esquerda).
                    else if(tree.esquerda == null && tree.direita != null){
                        aux = tree.direita; this.balancear(aux, n);
                    } // removedor de 1 filho(direita).
                    else{
                        tree.info = minimo(tree.direita);
                        tree.direita = this.remover_minimo(tree.direita);
                    }   // removedor de 2 filhos.
                }
                else if(n < tree.info){ this.remover(tree.esquerda,tree, n); }
                else{ this.remover(tree.direita,tree, n); }
                this.redefinir_altura(aux);
            }   
        }
    }   // gemacht.
       
    
    // Uso: maior de dois números.
    int max(int a, int b){ return (a>b) ? a : b;} // gemacht.
    
    // Uso: redefine a altura para o node.
    void redefinir_altura(node n){
        n.alturah = 1+ this.max(altura(n.esquerda), altura(n.direita));
    }   // gemacht.
   
    // Uso: retorna altura da árvore.
    int altura(node tree){
        if(tree == null){ return -1; }
        return tree.alturah;
    }   // gemacht.
    
    // Uso: fator de balanceamento.
    int fb(node n) { return altura(n.esquerda)-altura(n.direita);}   // gemacht.
    
    
    // Uso: rotaciona árvore para a esquerda.
    node rotacionar_esquerda(node n){
        node m = n.esquerda;    // nó m é sub-árvore n direita.
        n.esquerda = m.direita; // aux é galho esquerdo da árvore m.
        m.direita = n;        // sub-árvore esquerda de m aponta para raiz.
              
        // atualiza a altura dos nodes.
        n.alturah = max(altura(m.esquerda), altura(m.direita))+1;
        m.alturah = max(altura(m.esquerda), altura(m.direita))+1;
        
        return m;
    }   // gemacht.
    
    // Uso: rotaciona árvore para a direita.
    node rotacionar_direita(node m){
        node n = m.direita;  // nó n é sub-árvore m esquerda.
        m.direita = n.esquerda; // aux é galho direito da árvore n.
        n.esquerda = m;        // sub-árvore direita de n aponta para raiz.
       
        // atualiza a altura dos nodes.
        m.alturah = max(altura(m.esquerda), altura(m.direita))+1;
        n.alturah = max(altura(m.esquerda), altura(m.direita))+1;
        
        return n;
    }   // gemacht.
    
    // Uso: rotação composta:  esquerda-direita rot. .
    node rotacionar_esq_dir(node n){
        n.direita = this.rotacionar_esquerda(n.direita);//rot.->esq,braço dir. 
        return this.rotacionar_direita(n); // rot.-> dir, árvore n.
    }   // gemacht.
    
    // Uso: rotação composta:  direita-esquerda rot. .
    node rotacionar_dir_esq(node m){
        m.esquerda = this.rotacionar_direita(m.esquerda);//rot.->dir,braço esq.
        return this.rotacionar_esquerda(m); // rot.-> esq, árvore n.
    }   // gemacht.
    
    // Uso: balanceia a árvore após remoção.
    node balancear(node tree, int n){
        if(altura(tree.esquerda)+1>2 && altura(tree.direita)+1>2){
            if(fb(tree) > 1){                      //rot.-> esq
                if(n < tree.esquerda.info){tree = rotacionar_esquerda(tree);}
                else{ tree = rotacionar_dir_esq(tree);} //rot.-> 1. dir 2. esq 
            }
            else if(fb(tree) < -1){                 //rot.-> dir
                if(n > tree.direita.info){ tree = rotacionar_direita(tree);}
                else{  tree = rotacionar_esq_dir(tree);} //rot.-> 1. esq 2. dir 
            }    
            return tree;
        }      
        this.redefinir_altura(tree);
        return null;
    }   // gemacht.
    
    
    // Uso: imprime árvore em pré-ordem.
    void pré_ordem(node n){
        if(n != null){
            System.out.print(n.info +", ");
            this.pré_ordem(n.esquerda);  // os braços esquerdos são
            this.pré_ordem(n.direita);   // impressos primeiros.
        }
    }   // gemacht.
   
    // Uso: imprime árvore em in-ordem.
    void in_ordem(node n){
        if(n != null){
            this.in_ordem(n.esquerda);     // empilha a chamada e imprime
            System.out.print(n.info+", "); // o resultado quando chega
            this.in_ordem(n.direita);      // ao fim de um braço.
        }
    }   // gemacht.
    
    // Uso: imprime árvore em pós-ordem.
    void pós_ordem(node n){
        if(n != null){
            this.pós_ordem(n.esquerda);  // os braços direitos são
            this.pós_ordem(n.direita);   // impressos primeiros.
            System.out.print(n.info+", ");
        }
    }   // gemacht.
   
    // Uso: imprime árvore em pré, in e pós-ordem. 
    void imprimir_ordem(){
        if(this.raiz == null){;}
        else{
            System.out.print("\nOrdens: ");
            System.out.print("\n\n\tPré-ordem:\t { ");
            this.pré_ordem(this.raiz);
            System.out.print(" }\n\n\tIn-Ordem:\t { ");
            this.in_ordem(this.raiz);
            System.out.print(" }\n\n\tPós-ordem:\t { ");
            this.pós_ordem(this.raiz);
            System.out.print(" }\n\n");
        }
    }   // gemacht.

   
    public static void main(String[] args){
        
        // NOME: Gustavo Hammerschmidt.
        
        arv_bin tree = new arv_bin();
      
        tree.inserir(14);
        tree.inserir(15);
        tree.inserir(4);
        tree.inserir(9);
        tree.inserir(7);
        tree.inserir(18);
        tree.inserir(3);
        tree.inserir(5);
        //tree.inserir(4);
        tree.inserir(20);
        tree.inserir(17);
        tree.inserir(22);
        tree.inserir(24);
        tree.inserir(26);
        tree.inserir(21);
        tree.inserir(13);
        tree.inserir(16);
        // tree.inserir(9);
        //tree.inserir(15);
        //tree.inserir(5);         // não insere iguais.
        
        
        tree.imprimir_ordem();
        
        //tree.existe(50);
        
        System.out.println("\nMin = "+minimo(tree.raiz));
        System.out.println("Altura = "+tree.altura(tree.raiz));
        System.out.println("Max = "+tree.maximo(tree.raiz));
       
        
        tree.remove(16);
        tree.remove(3);
        tree.remove(5);
        tree.remove(17);
        tree.remove(4);
        
        tree.imprimir_ordem();
        
   
    }

}
