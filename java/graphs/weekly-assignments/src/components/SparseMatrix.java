
package components;

public class SparseMatrix {

    // node of an adjacency list.
    public class Node{
    
        int value;
        int adjacent;
        Node next;
        
        // constructor.
        public Node(int a, int v){ 
            this.value = v;
            this.adjacent = a;
            this.next = null;
        }   
    }
    
    // adjacency list of one vertex.
    public class AdjacencyList{
        
        Node first, last;
        
        // constructor.
        public AdjacencyList(){ this.first = this.last = null; }

        // returns whether the list is empty.
        boolean isEmpty(){return (this.first == null);}
        
        // inserts a node at the front.
        void insertFirst(int j, int value){
            Node fresh = new Node(j, value);
            
            if(this.isEmpty()){this.first = fresh; this.last = fresh;}
            else{fresh.next = this.first; this.first = fresh;}
        }
        
        // inserts a node after x.
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
        
        // removes the node for j.
        void remove(int j){
             
            if(this.first.adjacent == j){
                this.first = this.first.next;
            }
            else if(this.last.adjacent == j){
                Node aux = this.first;
                while(aux.next != this.last){aux = aux.next;}
                aux.next = null;
            }
            else{
                Node other = this.first;
                while(other.next.adjacent != j){ other = other.next;}
                other.next = other.next.next;
            }
            
        }
        
        // returns the weight stored for j.
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
        
        // returns the number of nodes. 
        int count(){
            Node aux = this.first;
            int count = 0;
            while(aux != null){ count++; aux = aux.next; }
            return count;
        }
        
        // returns whether j is in the list.
        boolean contains(int j){
            Node aux = this.first;
            boolean hold = false;
            while(aux != null){
                if(aux.adjacent == j){
                    hold = true;
                    break;
                }
                else{
                    aux = aux.next;
                }
            }
            return hold;
        }
        
    }
    
    int size;
    AdjacencyList[] lists;
    
    // constructor.
    public SparseMatrix(int size){
        
        this.size = size;
        this.lists = new AdjacencyList[size];

        for(int i = 0; i < size; i++){ lists[i] = new AdjacencyList();}
    
    }  
    
}
