
package sparsematrix;

public class SparseMatrix {

    public class Node{
    
        int value;
        int adjacent;
        Node next;

        public Node(int a, int v){ 
            this.value = v;
            this.adjacent = a;
            this.next = null;
        }   
    }
    
    public class AdjacencyList{
        
        Node first, last;
        
        public AdjacencyList(){ this.first = this.last = null; }

        boolean isEmpty(){return (this.first == null);}
        
        void insertFirst(int j, int value){
            Node fresh = new Node(j, value);
            
            if(this.isEmpty()){this.first = fresh; this.last = fresh;}
            else{fresh.next = this.first; this.first = fresh;}
        }
        
        void insertAfter(Node x, int j, int value){
            Node fresh = new Node(j, value);
            fresh.next = x.next;
            x.next = fresh;
        }
        
        // insert appends at the end.
        void insert(int j, int value){
            if(this.isEmpty()){this.insertFirst(j, value);}
            else{this.insertAfter(this.last, j, value);}
        }
        
        void remove(int j){
                      
            if(this.last.adjacent == j){
                Node aux = this.first;
                while(aux.next != this.last){aux = aux.next;}
                aux.next = null;
            }
            else if(this.first.adjacent == j){
                this.first = this.first.next;
            }
            else{
                Node other = this.first;
                while(other.next.adjacent != j){ other = other.next;}
                other.next = other.next.next;
            }
            
        }
        
        int valueOf(int i, int j){
            Node aux = lists[i].first;
            int hold = 0;
            while(aux != null){
                if(aux.adjacent == j){
                    hold = aux.value;
                    break;
                }
                else{aux = aux.next;}
            }
        
            return hold;
        }
    
    }
    
    int size;
    AdjacencyList[] lists;
    
    public SparseMatrix(int size){
        
        this.size = size;
        this.lists = new AdjacencyList[size];

        for(int i = 0; i < size; i++){ lists[i] = new AdjacencyList();}
    
    }  
    
}
