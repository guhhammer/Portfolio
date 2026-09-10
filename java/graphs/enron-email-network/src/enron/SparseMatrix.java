
package enron;

public class SparseMatrix {
    
    // author: Gustavo Hammerschmidt.
    
    // node.
    public class Node{
    
        int value;  // messages sent to this address. 
        String email; // address.
        Node next; // next node.

        
        public Node(String a, int v){ 
            this.email = a;
            this.value = v;
            this.next = null;
        } 
        
    }
    
    
    // adjacency list.
    public class AdjacencyList{
        
        Node first, last; 
        
        
        public AdjacencyList(){ this.first = this.last = null; }

        // empty?
        boolean isEmpty(){return (this.first == null);}
        
        // number of nodes.
        int size(){
            Node aux = this.first; int count = 0;
            while(aux != null){ count++; aux = aux.next;}
            return count;
        }
       
        // insert at the front.
        void insertFirst(String j, int value){
            Node fresh = new Node(j, value);
            if(this.isEmpty()){this.first = fresh; this.last = fresh;}
            else{fresh.next = this.first; this.first = fresh;}
        }
       
        // insert after x.
        void insertAfter(Node x, String j, int value){
            Node fresh = new Node(j, value);
            fresh.next = x.next; x.next = fresh;
        }
        
        // append, or add to the weight if the address is already listed.
        void insert(String j, int value){
        
            if(this.isEmpty()){this.insertFirst(j, value);}
            else{
                if(this.contains(j)){
                    Node aux = this.first; boolean flag = false;
                    while(aux != null && flag == false){ 
                        if(j.equals(aux.email)){aux.value +=value; flag =true;}
                        aux = aux.next;
                    }
                }   
                else{this.insertAfter(this.last, j, value);}
            }  // insert appends at the end.

        }
        
        // remove.
        void remove(String j){
            Node aux = this.first;
            while(aux != null){
               if(this.first.email.equals(j)){
                   this.first = this.first.next; 
                   break;
               }                
               else{
                   
                   if(aux.next.email.equals(j)){
                       aux.next = aux.next.next;
                       break;
                   }
                   aux = aux.next;
                   
               }
            }
            
        }
        
        // weight stored for j.
        int valueOf(String j){
            Node aux = this.first;
            while(aux != null){if(j.equals(aux.email)){break;} aux = aux.next;}
            return aux.value;
        }
        
        // is j listed?
        boolean contains(String j){
            Node aux = this.first; boolean flag = false;
            while(aux != null && flag == false){
                if(j.equals(aux.email)){ flag = true; } aux = aux.next;
            }
            return flag;
        }     
        
        // sum of all weights.
        int countEdges(){
            Node aux = this.first; int counter = 0;
            while(aux != null){ counter += aux.value; aux = aux.next; }
            return counter;
        }
    
    }
    
    
    private int size; 
    AdjacencyList[] lists;       
    
    
    public SparseMatrix(int size){
        
        this.size = size;
        this.lists = new AdjacencyList[size];
        
        
        for(int i = 0; i < size; i++){ lists[i] = new AdjacencyList();}
    
    }  
    
}
