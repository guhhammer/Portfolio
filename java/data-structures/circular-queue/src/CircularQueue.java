


public class CircularQueue {
    
    int data[];
    int first;
    int last;
    int Max;
    int size;
    
    
    // constructor of Fila, sets the size.
    public CircularQueue(int m){
        this.first = 0;
        this.last = -1;
        this.Max = m;
        this.data = new int[Max];
        this.size = 0;
    }
    
    // returns whether the queue is empty.
    boolean isEmpty(){
        if(size == 0){ return true;}
        else{ return false;}
    }
    
    // returns whether the queue is full.
    boolean isFull(){
        if(size == (Max)){ return true;}
        else{ return false;}
    }
    
    // adds an element to the circular queue.
    void insert(int n){
        if(!isFull()){  // if the queue is not full.
            if(last == Max-1){  // last wraps back to position 0.
                this.size++;   // fills the queue. 
                this.last = 0;
                this.data[last] = n;
            }
            else{  // add the element at the last position.
                this.size++;  
                this.last++;
                this.data[last] = n;
            }
        }
    }
    
    // removes an element from the circular queue.
    void remove(){
        if(!isEmpty()){   // if the queue is not empty.
            if(first == Max-1){ // first wraps back to position 0.
                this.data[first] = '\0';
                this.first = 0; 
                this.size--;    // empties the queue.
            }
            else{  // remove the first element.
                this.data[first] = '\0';
                this.first++;
                this.size--;
            }
        }   
    }
    
    //  returns the first element.
    int first(){ return data[first];}
    
    //  returns the last element.
    int last(){ return data[last];}
   
    //  returns the maximum size of the queue.
    int getMaxSize(){ return Max;}
    
    //  returns the current size of the queue.
    int getSize(){ return size;}   
    
}
