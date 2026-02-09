
package árvore;       // NOME: Gustavo Hammerschmidt.

import java.util.Scanner;
import java.io.*;

public class arv_bin_S {
    
    nodeS raiz;
    
    // Construtor: árvore.
    public arv_bin_S(){
        this.raiz = null;
    }   // gemacht.
       
    
    // Uso: método para utilizar-se da recursividade.
    public void inserir(String x){ raiz = inserir(x, raiz);}   // gemacht.
    
    // Uso: insere um elemento na árvore.
    public nodeS inserir(String x, nodeS tree){
        if(tree == null){ tree = new nodeS(x);} // árvore vazia.
        else{
            if(x.compareTo(tree.info) == 0){  tree.freq++;} // frequência++
            if(x.compareTo(tree.info) < 0){ // vai para braço esquerdo da árvore.
                tree.esquerda = inserir(x, tree.esquerda);//insere se tree->null.
                if(fb(tree) == 2){
                    if(x.compareTo(tree.esquerda.info) < 0){
                        tree = rotacionar_esquerda(tree);//rot.-> esq
                    }
                    else{ 
                        tree = rotacionar_dir_esq(tree);//rot.-> 1. dir 2. esq
                    }
                }
            }       // usar = no else if para aceitar iguais 
            else if(x.compareTo(tree.info) > 0){   // ignora números iguais;
                tree.direita = inserir(x, tree.direita);//insere se tree->null.
                if(fb(tree) == -2){
                    if(x.compareTo(tree.direita.info) > 0){
                        tree = rotacionar_direita(tree); //rot.-> dir
                    }
                    else{
                        tree = rotacionar_esq_dir(tree);//rot.-> 1. esq 2. dir 
                    }
                }
            }
        }                
        this.redefinir_altura(tree);// redefine altura da árvore.
        return tree;
    }   // gemacht.
    
    
    // Uso: imprime se existe ou não.
    void existe(String n){
        System.out.print("\nPalavra \""+n+"\" está na árvore ? "+
        ((this.existir(this.raiz, n)) ? "\tSim.\n\n" : "\tNão.\n\n"));
    }   // gemacht.
   
    // Uso: verifica existência de um elemento.
    boolean existir(nodeS tree, String n){
        if(this.raiz == null){  return false;} // vazia -> nenhum elemento.
        else{
            if(n.compareTo(tree.info) == 0){  return true;} // se igual ao atual elemento.
            else{
                if(n.compareTo(tree.info) < 0){  // procura na árvore esquerda.
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
    
    
    // Uso: maior de dois números.
    int max(int a, int b){ return (a>b) ? a : b;} // gemacht.
    
    // Uso: redefine a altura para o node.
    void redefinir_altura(nodeS n){
        n.alturah = 1 + this.max(altura(n.esquerda), altura(n.direita));
    }   // gemacht.
    
    // Uso: retorna altura da árvore.
    int altura(nodeS tree){
        if(tree == null){ return -1; }
        return tree.alturah;
    }   // gemacht.
    
    // Uso: fator de balanceamento.
    int fb(nodeS n) { return altura(n.esquerda)-altura(n.direita);}  // gemacht.
    
    
    // Uso: rotaciona árvore para a esquerda.
    nodeS rotacionar_esquerda(nodeS n){
        nodeS m = n.esquerda;    // nó m é sub-árvore n direita.
        n.esquerda = m.direita; // aux é galho esquerdo da árvore m.
        m.direita = n;        // sub-árvore esquerda de m aponta para raiz.
              
        // atualiza a altura dos nodes.
        n.alturah = max(altura(m.esquerda), altura(m.direita))+1;
        m.alturah = max(altura(m.esquerda), altura(m.direita))+1;
        
        return m;
    }   // gemacht.
    
    // Uso: rotaciona árvore para a direita.
    nodeS rotacionar_direita(nodeS m){
        nodeS n = m.direita;  // nó n é sub-árvore m esquerda.
        m.direita = n.esquerda; // aux é galho direito da árvore n.
        n.esquerda = m;        // sub-árvore direita de n aponta para raiz.
       
        // atualiza a altura dos nodes.
        m.alturah = max(altura(m.esquerda), altura(m.direita))+1;
        n.alturah = max(altura(m.esquerda), altura(m.direita))+1;
        
        return n;
    }   // gemacht.
    
    // Uso: rotação composta:  esquerda-direita rot. .
    nodeS rotacionar_esq_dir(nodeS n){
        n.direita = this.rotacionar_esquerda(n.direita);//rot.->esq,braço dir. 
        return this.rotacionar_direita(n); // rot.-> dir, árvore n.
    }   // gemacht.
    
    // Uso: rotação composta:  direita-esquerda rot. .
    nodeS rotacionar_dir_esq(nodeS m){
        m.esquerda = this.rotacionar_direita(m.esquerda);//rot.->dir,braço esq.
        return this.rotacionar_esquerda(m); // rot.-> esq, árvore n.
    }   // gemacht.
    
    
    // Uso: imprime árvore em pré-ordem.
    void pré_ordem(nodeS n){
        if(n != null){
            System.out.print(n.info +", ");
            this.pré_ordem(n.esquerda);  // os braços esquerdos são
            this.pré_ordem(n.direita);   // impressos primeiros.
        }
    }   // gemacht.
    
    // Uso: imprime árvore em in-ordem.
    void in_ordem(nodeS n){
        if(n != null){
            this.in_ordem(n.esquerda);     // empilha a chamada e imprime
            System.out.print(n.info+", "); // o resultado quando chega
            this.in_ordem(n.direita);      // ao fim de um braço.
        }
    }   // gemacht.
    
    // Uso: imprime árvore em pós-ordem.
    void pós_ordem(nodeS n){
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
    
    
    // Uso: Retorna número de repetições.
    int frequência_counter(String n, nodeS tree){
        if(this.raiz == null){  return 0;} // vazia -> nenhum elemento.
        else{                                     // se igual ao atual elemento.
            if(n.compareTo(tree.info) == 0){  return tree.freq;} 
            else{
                if(n.compareTo(tree.info) < 0){  // procura na árvore esquerda.
                  if(tree.esquerda!=null){
                      return this.frequência_counter(n, tree.esquerda);
                  }
                  else{ return 0;} // fim da árvore -> nenhum elemento.
                }
                else{  // procura na árvore direita.
                  if(tree.direita!=null){
                      return this.frequência_counter(n,tree.direita);}
                  else{ return 0;} // fim da árvore -> nenhum elemento.
                }
            }
        }
    }   // gemacht.
    
    // Uso: print das ocorrências.
    void ocorrêcias(arv_bin_S a, String path ,String n){
        
        int counter = this.frequência_counter(n, a.raiz);
        
        System.out.print("\n\tNúmero de Ocorrências da palavra \""+n+"\" em "
                + new File(path).getName()+": "+counter+" ocorrências. \n");
   
    }// gemacht.
    
    // Uso: soma de ocorrências.
    int repete_vezes(arv_bin_S a, String n){
        return this.frequência_counter(n, a.raiz);
    }   // gemacht.
    
    // Uso: conta número de palavras no txt.
    static int palavras_counter_txt(String path) throws FileNotFoundException{
        try{
            Scanner a = new Scanner(new File(path), "UTF-8").useDelimiter(" "); 
            int count = 0;
            while(a.hasNext()){
                a.next();
                count = count+1;
            }
            return count;
        }
        catch(FileNotFoundException f){
            return 0;
        }
    }   // gemacht.
    
    // Uso: formatar a palavra.
    static String formatar(String s){
        s = s.replace(",", "");
        s = s.replace(";", "");
        s = s.replace("?", "");
        s = s.replace(".", "");
        s = s.replace(":", "");
        s = s.replace("!", "");
        s = s.replace("(", "");
        s = s.replace(")", "");
        s = s.replace("-", "");
        s = s.replace("\"", "");
        s = s.replace("\\S+", "");
        s = s.replace(System.getProperty("line.separator"), "");
        return s;
    }   // gemacht.
    
    // Uso: retorna vetor com as palavras do texto.
    static String[] palavras(String path) throws FileNotFoundException{
        
        String[] h = new String[palavras_counter_txt(path)];    
        
        Scanner i = new Scanner(new File(path),"UTF-8").useDelimiter(" ");
        
        
        int j = 0;
        while(i.hasNext()){
            
            h[j] = formatar(i.next()).toLowerCase();
            j++;    
            
        
        }
        
        return h;
    }   //gemacht.
    
    // Uso: insere palavras na árvore.
    void to_arv_bin_S(String path) throws FileNotFoundException{
        String[] aux = palavras(path);
        for(int i = 0; i < aux.length; i++){ this.inserir(aux[i]); }   
    }   // gemacht.
    
  
    public static void main(String[] args) throws FileNotFoundException {
        
        
        // NOME: Gustavo Hammerschmidt.
        
        arv_bin_S tree1 = new arv_bin_S();
        arv_bin_S tree2 = new arv_bin_S();
        arv_bin_S tree3 = new arv_bin_S();
        
        
        String path1 = "C:\\Users\\Gustavo\\"
             + "Documents\\NetBeansProjects\\Contador\\"
             + "build\\classes\\árvore\\tree1.txt";

        String path2 = "C:\\Users\\Gustavo\\"
             + "Documents\\NetBeansProjects\\Contador\\"
             + "build\\classes\\árvore\\tree2.txt";
        
        String path3 = "C:\\Users\\Gustavo\\"
             + "Documents\\NetBeansProjects\\Contador\\"
             + "build\\classes\\árvore\\tree3.txt";
       
        
        tree1.to_arv_bin_S(path1);
        tree2.to_arv_bin_S(path2);
        tree3.to_arv_bin_S(path3);
        
        
        // True para ver as árvores.
        if(true){ 
            System.out.print("\nTree 1:");
            tree1.imprimir_ordem();
            System.out.print("\nTree 2:");
            tree2.imprimir_ordem();
            System.out.print("\nTree 3:");
            tree3.imprimir_ordem();
        }
        
        
        Scanner c = new Scanner(System.in);
        System.out.print("\n\n\t\tBusca de palavras em Árvores Binárias:\n");
        
        int aux = 0;
        while(true){
            int count = 0;
            System.out.print("\n\nDigite uma palavra para busca: \n");
            System.out.print("\nPalavra:   ");
            String palavra = c.next();
            count = tree1.repete_vezes(tree1, palavra)+
                    tree2.repete_vezes(tree2, palavra)+
                    tree3.repete_vezes(tree3, palavra);
            System.out.println("Palavra \""+palavra+"\" "
                    + "repete-se "+count+" vezes.");
            tree1.ocorrêcias(tree1, path1, palavra);
            tree2.ocorrêcias(tree2, path2, palavra);
            tree3.ocorrêcias(tree3, path3, palavra);
            System.out.print("\nDigite 1 para sair ou 0 para continuar:  ");
            aux = c.nextInt();
            if(aux == 1 || aux == 1){
                System.out.print("\nSaindo.\n\n");
                break;
            }   
        }
        
        
    }
       
}
