


import java.lang.Math;

public class LinkedList {
    
    Node first;
    Node last;
    Node Lista;
    
    // constructor of lista
    public LinkedList(){
        this.Lista = null;
        this.first = null;
        this.last = null;
    }
    
    
    // returns whether the list is empty.
    boolean isEmpty(){
        if(this.first == null){ return true;}
        else{ return false;}
    }
    
    // inserts the element as the first of the list.
    void insertFirst(int n){
        Node fresh = new Node(n);
        if(isEmpty()){    
            this.first = fresh;
            this.last =fresh;
        }
        else{
            fresh.next = this.first;
            this.first = fresh;
        }
    }
    
    // inserts an element after another in the list.
    Node insertAfter(Node p, int n){
        Node fresh = new Node(n);
        fresh.next = p.next;
        p.next = fresh;
        return fresh;
    }
    
    // inserts an element as the last of the list.
    void insertLast(int n){
        if(this.isEmpty()){   this.insertFirst(n);}
        else{   this.last = this.insertAfter(this.last, n);}
    }
    
    // inserts the element into the list in order.
    void insertSorted(int n){
        if(this.isEmpty()){   this.insertFirst(n);}
        else{
            if( n < this.first.data){    this.insertFirst(n);}
            else{
                if(n >= this.last.data){  this.insertLast(n);}
                else{  //insert entre dois elementos ordenadamente.
                    Node p = this.first;
                    while(p.next.data <  n){   p = p.next;}
                    this.insertAfter(p, n);
                }
            }
        }
    }
    
    
    // prints the elements of the list L.
    void printList(){
        if(isEmpty()){    //  se a lista estiver isEmpty
            System.out.println("\nList is empty!"
            +"\nLista Encadeada L = { }.\n");
        }
        else{
            Node aux = this.first;
            System.out.print("Lista Encadeada  L = { ");
            while(aux.next != null){  // from the first to the second-to-last
                System.out.print(aux.data+", ");
                aux = aux.next;
            }
            System.out.println(aux.data+" }. "); // last value 
        }
    }
    
    // removes and returns the first element. 
    int removeFirst(){
        if(this.isEmpty()){ return -1;}   //se isEmpty retorna -1.
        else{  
            if(this.first == this.last){  // one element.
                int aux = this.lastElement(); // or use this.first.data
                this.first= null;
                this.last = null;
                return aux;
            }
            else{   // 1º elemento da lista
                int aux =  this.first.data;
                this.first = this.first.next;
                return aux;
            }
        }
    }
    
    // removes and returns the element after n.
    int removeAfter(Node n){
        if(this.isEmpty()){ return -1;} // se a lista estiver isEmpty.
        else{
            if(this.first == this.last){ return -1;} // one element;
            else{  // more than one element in the list.
                int aux = n.next.data;
                n.next = n.next.next;
                return aux;
            }
        }
    }
    
    // removes and returns the last element.
    int removeLast(){
        if(this.isEmpty()){ return -1;} // se estiver isEmpty.
        else{
            if(this.first == this.last){  // one element.
                return this.removeFirst();
            }
            else{   // more than one element in the list.
                Node n = this.first;    
                while(n.next.next != this.last.next){
                    n = n.next;
                }
                return this.removeAfter(n);
            }
        }
    }
    
    // returns the last element of the list.
    int lastElement(){
        if(isEmpty()){ return -1;}
        else{ return this.last.data;}
    }
    
    
    // returns the size of the list.
    int getMaxSize(){
        if(isEmpty()){ return 0;}
        else{
            int cont = 1;
            Node aux = this.first;
            while(aux.next != this.last.next){
                aux = aux.next;
                cont++;
            }
            return cont; 
        }
   } 
    
    // returns whether the lists hold only repeated elements and differ from each other.
    boolean checkCondition(LinkedList y){
        boolean a = true, b = true;
        Node aux = this.first, aux2 = y.first;
        while(aux.next != null){
            if(aux.data != aux.next.data){  a = false;  break; }
            aux = aux.next;
        }
        while(aux2.next != null){
            if(aux2.data != aux2.next.data){  b = false;  break; }
            aux2 = aux2.next;
        }
        return (a && b);
    }
    
    // retorna similarity entre as listas.
    float similarity(LinkedList y){
        // would return NaN if both lists were empty.
        if(this.isEmpty() && y.isEmpty()){ return -1.0f; }
        else{
            if(this.getMaxSize()!= y.getMaxSize()){ return -1.0f;}
            else{
                Node aux = this.first, aux2 = y.first;
                double sum_dividendo= 0, sum_xx = 0, sum_yy = 0;
                while(aux != this.last.next){
                    sum_dividendo += (aux.data * aux2.data);
                    sum_xx += (aux.data * aux.data);
                    sum_yy += (aux2.data * aux2.data);
                    aux = aux.next;
                    aux2 = aux2.next;
                }
                float resposta =(float)(sum_dividendo/Math.sqrt(sum_xx*sum_yy));
                
                if(this.getMaxSize() == 1 &&
                  (this.first.data == 0 && y.first.data == 0)){
                    return 1.0f;
                }
                else{
                    if(this.getMaxSize() == 1 && 
                      (this.first.data == 0 || y.first.data == 0)){
                        return 0.0f;  // case with zeros
                    }
                    else{
                        if(this.getMaxSize() == 1 &&
                          (this.first.data != y.first.data)){
                            return 0.0f; // one differing element in X and Y
                        }
                        else{
                            if(this.checkCondition(y)){ return 0.0f;}
                            else{             // when -1 <= answer < 0
                                if(resposta < 0){ return -1*resposta;}
                                else{ return resposta;}
                            }
                        }   
                    }
                }
            }
        }
    }
    
    // returns the intersection of two lists.
    LinkedList intersection(LinkedList y){
        LinkedList z = new LinkedList();
        if(this.isEmpty() || y.isEmpty()){ return z;}  // retorna lista isEmpty: Z.
        else{
            Node a = this.first;
            while(a != this.last.next){
                Node b = y.first;
                while(b != null){
                    if(a.data == b.data){ z.insertSorted(a.data);}
                    b = b.next;
                }
                a = a.next;
            }
            return z;
        }
    } 
    
    // returns the intersection of two lists. Veja info().
    LinkedList intersectionDedup(LinkedList y){
        LinkedList z = this.intersection(y);
        Node c = z.first;
        while(c != this.last.next){
            Node d = c; // d equals the current c pointer
            //parte do ponteiro c ao end da fila
            while(d.next != this.last.next){
                if(c.data == d.next.data){z.removeAfter(d);}
                else{d = d.next;}
            }
            c = c.next;
        }
        return z;
        
    }
    
    // prints information about the code.
    static void info(){
        System.out.println(
        "\n"
        +"---------------------------------------------------------------------"
        +"\nBegin: info.\n\n\n"
        +"\t\t Name: Gustavo Hammerschmidt.\n\n"
        +"\t Construtores: Node() e LinkedList().\n\n"
        +"\t Functions: \n"
        +"\t\t  isEmpty(), insertFirst(), insertAfter(),\n"
        +"\t\t  insertLast(), insertSorted, printList(),\n"
        +"\t\t  removeFirst(), removeAfter(), removeLast(),\n"
        +"\t\t  lastElement(), getMaxSize(), similarity(),\n"
        +"\t\t  intersection(), intersectionDedup(), checkCondition()\n"
        +"\t\t  e info()."
        +"\n\n"
        +"\t Note: \n"
        +"\t\t  -getMaxSize() retorna o size da lista encadeada.\n"
        +"\t\t  -info() prints information about the code.\n"
        +"\t\t  -intersectionDedup() retorna apenas um elemento da \n"
        +"\t\t   lista se houver um igual a ele, ou seja, repetido.\n"
        +"\t\t  -checkCondition() returns whether lists x and y hold\n"
        +"\t\t   only repeated elements and differ from each other.\n"
        +"\n\n\nEnd: info.\n"
        +"---------------------------------------------------------------------"
        +"-\n");
    }
    
    
    
    public static void main(String[] args) {
        
        
        info();
        
        LinkedList lista = new LinkedList();
        LinkedList lista2 = new LinkedList();
             
        lista.insertFirst(10);
        lista.insertLast(18);
        lista.insertLast(14);
        Node p = lista.first.next;
        lista.insertAfter(p, 2);
        lista.insertSorted(15);
        lista.printList();
        
        lista2.insertFirst(10);
        lista2.insertLast(15);
        lista2.insertLast(15);
        lista2.insertLast(19);
        lista2.insertFirst(-14);
        lista2.printList();
        
        System.out.println("\nSimilaridade: "+lista.similarity(lista2));
        lista.intersection(lista2).printList();
        lista.intersectionDedup(lista2).printList();
        
        /* 
        // show the professor that the formula has flaws:
        lista.insertFirst(10);
        lista.insertFirst(10);
        lista2.insertFirst(15);
        lista2.insertFirst(15);
        // would return 1.0 even when the lists share no elements.
        System.out.println(lista.similarity(lista2));
        That is why checkCondition() was written.
        */
    }

}
