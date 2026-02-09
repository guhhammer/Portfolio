
package Lista_Encadeada;

import java.lang.Math;

public class LEncadeada {
    
    knot primeiro;
    knot ultimo;
    
    // Uso: construtor de lista
    public LEncadeada(){
        this.primeiro = null;
        this.ultimo = null;
    }   // gemacht.
    
    // Uso: retorna se a lista está vazia.
    boolean vazia(){
        if(this.primeiro == null){ return true;}
        else{ return false;}
    }   // gemacht.
    
    // Uso: insere o elemento como primeiro na lista.
    void inserir_primeiro(int n){
        knot novo = new knot(n);
        if(vazia()){    
            this.primeiro = novo;
            this.ultimo =novo;
        }
        else{
            novo.proximo = this.primeiro;
            this.primeiro = novo;
        }
    }   // gemacht.
    
    // Uso: insere um elemento depois de outro na lista.
    knot inserir_depois(knot p, int n){
        knot novo = new knot(n);
        novo.proximo = p.proximo;
        p.proximo = novo;
        return novo;
    }   // gemacht.
    
    // Uso: insere um elemento como último na lista.
    void inserir_ultimo(int n){
        if(this.vazia()){   this.inserir_primeiro(n);}
        else{   this.ultimo = this.inserir_depois(this.ultimo, n);}
    }   // gemacht.
    
    // insere o elemento na lista ordenadamente.
    void inserir_ordenada(int n){
        if(this.vazia()){   this.inserir_primeiro(n);}
        else{
            if( n < this.primeiro.dado){    this.inserir_primeiro(n);}
            else{
                if(n >= this.ultimo.dado){  this.inserir_ultimo(n);}
                else{  //inserir entre dois elementos ordenadamente.
                    knot p = this.primeiro;
                    while(p.proximo.dado <  n){   p = p.proximo;}
                    this.inserir_depois(p, n);
                }
            }
        }
    }   // gemacht.
    
    
    // Uso: Mostra os elementos da Lista L.
    void mostra_lista(){
        if(vazia()){    //  se a lista estiver vazia
            System.out.println("\nLista está vazia!"
            +"\nLista Encadeada L = { }.\n");
        }
        else{
            knot aux = this.primeiro;
            System.out.print("Lista Encadeada  L = { ");
            while(aux.proximo != null){  // do 1º ao ante-penúltimo
                System.out.print(aux.dado+", ");
                aux = aux.proximo;
            }
            System.out.println(aux.dado+" }. "); // último valor 
        }
    }   // gemacht.
    
    // Uso: Retira o primeiro elemento da lista e o retorna. 
    int retira_primeiro(){
        if(this.vazia()){ return -1;}   //se vazia retorna -1.
        else{  
            if(this.primeiro == this.ultimo){  // 1 elemento.
                int aux = this.ultimo_elemento(); // or use this.primeiro.dado
                this.primeiro= null;
                this.ultimo = null;
                return aux;
            }
            else{   // 1º elemento da lista
                int aux =  this.primeiro.dado;
                this.primeiro = this.primeiro.proximo;
                return aux;
            }
        }
    }   // gemacht.
    
    // Uso: Retira o elemento depois de n e o retorna.
    int retira_depois(knot n){
        if(this.vazia()){ return -1;} // se a lista estiver vazia.
        else{
            if(this.primeiro == this.ultimo){ return -1;} // 1 elemento;
            else{  // + de 1 elemento na lista.
                int aux = n.proximo.dado;
                n.proximo = n.proximo.proximo;
                return aux;
            }
        }
    }   // gemacht.
    
    // Uso: Retira o último elemento da lista e o retorna.
    int retira_ultimo(){
        if(this.vazia()){ return -1;} // se estiver vazia.
        else{
            if(this.primeiro == this.ultimo){  // 1 elemento.
                return this.retira_primeiro();
            }
            else{   // + de 1 elemento na lista.
                knot n = this.primeiro;    
                while(n.proximo.proximo != this.ultimo.proximo){
                    n = n.proximo;
                }
                return this.retira_depois(n);
            }
        }
    }   // gemacht.
    
    // Uso: Retorna o último elemento da lista.
    int ultimo_elemento(){
        if(vazia()){ return -1;}
        else{ return this.ultimo.dado;}
    }   // gemacht.
    
    
    // Uso: retorna o tamanho de uma lista.
    int getTamanho(){
        if(vazia()){ return 0;}
        else{
            int cont = 1;
            knot aux = this.primeiro;
            while(aux.proximo != this.ultimo.proximo){
                aux = aux.proximo;
                cont++;
            }
            return cont; 
        }
   }    // gemacht.
    
    // Uso: retorna se as listas tem elementos iguais e são diferentes entre si.
    boolean check_condition(LEncadeada y){
        boolean a = true, b = true;
        knot aux = this.primeiro, aux2 = y.primeiro;
        while(aux.proximo != null){
            if(aux.dado != aux.proximo.dado){  a = false;  break; }
            aux = aux.proximo;
        }
        while(aux2.proximo != null){
            if(aux2.dado != aux2.proximo.dado){  b = false;  break; }
            aux2 = aux2.proximo;
        }
        return (a && b);
    }   // gemacht.
    
    // Uso: retorna similaridade entre as listas.
    float similaridade(LEncadeada y){
        //Retornaria NaN se as duas listas estiveressem vazias.
        if(this.vazia() && y.vazia()){ return -1.0f; }
        else{
            if(this.getTamanho()!= y.getTamanho()){ return -1.0f;}
            else{
                knot aux = this.primeiro, aux2 = y.primeiro;
                double sum_dividendo= 0, sum_xx = 0, sum_yy = 0;
                while(aux != this.ultimo.proximo){
                    sum_dividendo += (aux.dado * aux2.dado);
                    sum_xx += (aux.dado * aux.dado);
                    sum_yy += (aux2.dado * aux2.dado);
                    aux = aux.proximo;
                    aux2 = aux2.proximo;
                }
                float resposta =(float)(sum_dividendo/Math.sqrt(sum_xx*sum_yy));
                
                if(this.getTamanho() == 1 &&
                  (this.primeiro.dado == 0 && y.primeiro.dado == 0)){
                    return 1.0f;
                }
                else{
                    if(this.getTamanho() == 1 && 
                      (this.primeiro.dado == 0 || y.primeiro.dado == 0)){
                        return 0.0f;  // calculo quando há zeros
                    }
                    else{
                        if(this.getTamanho() == 1 &&
                          (this.primeiro.dado != y.primeiro.dado)){
                            return 0.0f; // 1 elemento diferente em X e Y
                        }
                        else{
                            if(this.check_condition(y)){ return 0.0f;}
                            else{             // se -1 <= resposta < 0
                                if(resposta < 0){ return -1*resposta;}
                                else{ return resposta;}
                            }
                        }   
                    }
                }
            }
        }
    }   // gemacht.
    
    // Uso: retorna interseção de duas listas.
    LEncadeada intersection(LEncadeada y){
        LEncadeada z = new LEncadeada();
        if(this.vazia() || y.vazia()){ return z;}  // retorna lista vazia: Z.
        else{
            knot a = this.primeiro;
            while(a != this.ultimo.proximo){
                knot b = y.primeiro;
                while(b != null){
                    if(a.dado == b.dado){ z.inserir_ordenada(a.dado);}
                    b = b.proximo;
                }
                a = a.proximo;
            }
            return z;
        }
    }   // gemacht. 
    
    // Uso: retorna interseção de duas listas. Veja info().
    LEncadeada intersection2(LEncadeada y){
        LEncadeada z = this.intersection(y);
        knot c = z.primeiro;
        while(c != this.ultimo.proximo){
            knot d = c; // d igual ponteiro de c atual
            //parte do ponteiro c ao fim da fila
            while(d.proximo != this.ultimo.proximo){
                if(c.dado == d.proximo.dado){z.retira_depois(d);}
                else{d = d.proximo;}
            }
            c = c.proximo;
        }
        return z;
        
    }   // gemacht.
    
    // Uso: mostra informações do código.
    static void info(){
        System.out.println(
        "\n"
        +"---------------------------------------------------------------------"
        +"\nBegin: info.\n\n\n"
        +"\t\t Nome: Gustavo Hammerschmidt.\n\n"
        +"\t Construtores: knot() e LEncadeada().\n\n"
        +"\t Funções: \n"
        +"\t\t  vazia(), inserir_primeiro(), inserir_depois(),\n"
        +"\t\t  inserir_ultimo(), inserir_ordenada, mostra_lista(),\n"
        +"\t\t  retira_primeiro(), retira_depois(), retira_ultimo(),\n"
        +"\t\t  ultimo_elemento(), getTamanho(), similaridade(),\n"
        +"\t\t  intersection(), intersection2(), check_condition()\n"
        +"\t\t  e info()."
        +"\n\n"
        +"\t Observação: \n"
        +"\t\t  -getTamanho() retorna o tamanho da lista encadeada.\n"
        +"\t\t  -info() mostra informações do código.\n"
        +"\t\t  -intersection2() retorna apenas um elemento da \n"
        +"\t\t   lista se houver um igual a ele, ou seja, repetido.\n"
        +"\t\t  -check_condition() retorna se as listas x e y são compostas\n"
        +"\t\t   somente de elementos repetidos e são diferentes entre si.\n"
        +"\n\n\nEnd: info.\n"
        +"---------------------------------------------------------------------"
        +"-\n");
    }   // gemacht.
    
    // Uso: número de knots, recursão.
    static int quantidade_calda(knot aux,int acc){      
        if(aux.proximo == null){ return acc; }
        else{ return quantidade_calda(aux.proximo, acc+1); }
    }   // gemacht.
    
    // Uso: número de knots, recursão calda.
    static int quantidade(knot aux){
        if(aux.proximo == null){ return 0; }
        else{ return 1+quantidade(aux.proximo); }
    }   // gemacht.
    
    
    public static void main(String[] args) {
        
        // Nome: Gustavo Hammerschmidt.
        info();
        
        LEncadeada lista = new LEncadeada();
        LEncadeada lista2 = new LEncadeada();
        
        lista.inserir_primeiro(10);
        lista.inserir_ultimo(18);
        lista.inserir_ultimo(14);
        knot p = lista.primeiro.proximo;
        lista.inserir_depois(p, 2);
        lista.inserir_ordenada(13);
        lista.mostra_lista();
        
        lista2.inserir_primeiro(10);
        lista2.inserir_ultimo(18);
        lista2.inserir_ultimo(15);
        lista2.inserir_ultimo(19);
        lista2.inserir_primeiro(-14);
        lista2.mostra_lista();
        
        System.out.println("\nQuantidade(rec. calda):  "+
                           quantidade_calda(lista.primeiro,0));
        System.out.println("\nQuantidade(rec.):  "+quantidade(lista.primeiro));
        System.out.println("\nSimilaridade: "+lista.similaridade(lista2));
        lista.intersection(lista2).mostra_lista();
        lista.intersection2(lista2).mostra_lista();
        
        /* 
        //mostrar que a fórmula tem defeitos pro professor:
        lista.inserir_primeiro(10);
        lista.inserir_primeiro(10);
        lista2.inserir_primeiro(15);
        lista2.inserir_primeiro(15);
        //vai retornar 1,0 mesmo as listas não tendo elementos em comum.
        System.out.println(lista.similaridade(lista2));
        Por isso, fiz a função check_condition().
        */
    }

}
