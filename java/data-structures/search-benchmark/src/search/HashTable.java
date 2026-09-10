package search;





public class HashTable {

    
    public class Node {
    
        int data;
        Node next;

        public Node(int n){ this.data = n; this.next = null; }

    }
    
    public class LSE {
    
        Node first;
        Node last;

        public LSE(){  this.first = this.last = null;}

        boolean isEmpty(){
            if(this.first == null){ return true;}
            else{ return false;}
        }   

        void insertFirst(int n){
            Node fresh = new Node(n);
            if(isEmpty()){    
                this.first = fresh; this.last =fresh;
            }
            else{
                fresh.next = this.first;
                this.first = fresh;
            }
        }  

        Node insertAfter(Node p, int n){
            Node fresh = new Node(n);
            fresh.next = p.next;
            p.next = fresh;
            return fresh;
        }   

        void insertLast(int n){
            if(this.isEmpty()){   this.insertFirst(n);}
            else{   this.last = this.insertAfter(this.last, n);}
        }   

        void insertSorted(int n){
            if(this.isEmpty()){   this.insertFirst(n);}
            else{
                if( n < this.first.data){    this.insertFirst(n);}
                else{
                    if(n >= this.last.data){  this.insertLast(n);}
                    else{  
                        Node p = this.first;
                        while(p.next.data <  n){   p = p.next;}
                        this.insertAfter(p, n);
                    }
                }
            }
        }
    
        boolean contains(int n){
            Node aux = this.first;
            boolean hold = false;
            
            while(aux != null){
                if(n == aux.data){ hold = true; break;}
                else{aux = aux.next;}
            }
            
            return hold;
            
        }
    
    }
    
    int tam;
    LSE table[];
    
    // Construtor.
    public HashTable(int t){
        this.tam = t;
        this.table = new LSE[tam];

        for(int i = 0; i < tam; i++){ table[i] = new LSE(); }
    }
    
    // retorna hash de uma key.
    int hash(int key){ return (key % this.tam);}  
    
    // insere key em table hash.
    void insertKey(int key){ table[hash(key)].insertSorted(key);}
    
    // faz search de key em table hash
    boolean search(int key){ return table[hash(key)].contains(key);} 
      
}
