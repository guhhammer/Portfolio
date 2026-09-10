


public class Merge {
    
    
    // collects all elements of the queue.
    static CircularQueue compact(CircularQueue n){
        
        int k = 0;
        CircularQueue vAux = new CircularQueue(n.getSize());
        while(n.first+k <= n.getMaxSize()-1){
            vAux.insert(n.data[n.first+k]);
            k++;
        }
        
        k = 0;
        while(k <= n.last){   
            vAux.insert(n.data[k]);
            k++;
        }
        return vAux;
    }
    
    
    //  Coloca em sortQueue crescente os elementos da fila.
    static CircularQueue sortQueue(CircularQueue n){
        
        //Pode-se fazer tanto no main quanto aqui, o uso do compact().
        int tam = compact(n).getMaxSize();
        int aux;
        
        // Ordena os elements.
        for(int i = 0; i < tam; i++){
            for(int j = i+1; j < tam; j++){
                if(n.data[i] > n.data[j]){
                    aux = n.data[i];
                    n.data[i] = n.data[j];
                    n.data[j] = aux;
                }
            }
        }  
        return n;
    }

    
    //   Concatena em sortQueue crescente duas filas.
    public static CircularQueue merge(CircularQueue A, CircularQueue B){
        
        CircularQueue C = new CircularQueue(A.getMaxSize()+B.getMaxSize());
 
        while(!A.isEmpty() && !B.isEmpty()){
            if(A.first() < B.first()){
                C.insert(A.first());
                A.remove();
            }
            else{
                C.insert(B.first());
                B.remove();
            }
        }
        
        while(!A.isEmpty()){
            C.insert(A.first());
            A.remove();
        }
        
        while(!B.isEmpty()){
            C.insert(B.first());
            B.remove();
        }
        
        return C; 
    }

    
    //  information about the algorithm.
    public static void help(){
        System.out.println(
        "CircularQueue + Merge. "+
        "CircularQueue: isEmpty(), isFull(), insert(), remove(), first(), last(), getMaxSize(), getSize(). "+
        "Merge: compact(), sortQueue(), merge(), printSorted(), printMerge(), printQueueExample(). "+
        "compact() keeps only the elements from first to last; merge() joins two queues in ascending order.");
    }


    public static void printQueueExample(){
        
        CircularQueue A = new CircularQueue(3);
        System.out.println(" -queueExample(): \n");
        A.insert(1);
        System.out.println("Primeiro elemento: "+A.first()+
                           "\nÚltimo elemento: "+A.last()+"\n");
        A.insert(2);
        System.out.println("Primeiro elemento: "+A.first()+
                           "\nÚltimo elemento: "+A.last()+"\n");
        A.insert(3);
        System.out.println("Primeiro elemento: "+A.first()+
                           "\nÚltimo elemento: "+A.last()+"\n");
        
        System.out.println("A.isEmpty(): "+A.isEmpty()+
                           "\nA.isFull(): "+A.isFull());
        
        A.remove();
        System.out.println("\nPrimeiro elemento: "+A.first()+
                           "\nÚltimo elemento: "+A.last()+"\n");
        A.remove();
        System.out.println("Primeiro elemento: "+A.first()+
                           "\nÚltimo elemento: "+A.last()+"\n");
        A.remove();
        System.out.println("Primeiro elemento: "+A.first()+
                           "\nÚltimo elemento: "+A.last()+"\n");
        
        System.out.println("A.isEmpty(): "+A.isEmpty()+
                           "\nA.isFull(): "+A.isFull()+"\n\n");
    }
   
    
    //  printPerson vetor ordenado.
    public static void printSorted(CircularQueue n){
        
        System.out.println(" -printSorted():\n");
        System.out.print("A["+(n.getMaxSize())+"] = { ");
        if(n.getMaxSize() != 0){
        for(int i = 0; i < n.getMaxSize(); i++){
            if(i == n.getMaxSize()-1){
                System.out.println(n.data[i]+" }. ");
            }
            else{
                System.out.print(n.data[i]+", ");
            }
        }}
        else{System.out.println(" }. ");}
        
        System.out.print("\nA["+(n.getMaxSize())+"] (Sorted queue) = { ");
        if(n.getMaxSize() != 0){
        for(int i = 0; i < n.getMaxSize(); i++){
            if(i == n.getMaxSize()-1){
                System.out.println(sortQueue(n).data[i]+" }. \n");
            }
            else{
                System.out.print(sortQueue(n).data[i]+", ");
            }
        }}
        else{System.out.println(" }. \n");
                }
    }
    
    
    //  printPerson vetor resultante do Merge.
    public static void printMerge(CircularQueue c){
        System.out.println(" -printMerge(): \n");
        int tam = c.getMaxSize();
        System.out.print("C["+tam+"] (Merged queue) = { ");
        if(tam != 0){
        for(int i = 0; i < tam; i++){
            if(i == tam-1){
                System.out.println(c.data[i]+" }. \n");
            }
            else{
                System.out.print(c.data[i]+", ");
            }
        }}
        else{System.out.println(" }. \n");}
    }
    
    
    //  example queue.
    public static CircularQueue f(){
        CircularQueue a = new CircularQueue(10);
        a.insert(5);
        a.insert(3);
        a.insert(10);
        a.insert(4);
        a.insert(9);
        a.insert(05);
        a.insert(45);
        a.remove();
        a.remove();
        a.remove();
        a.insert(54543);
        a.insert(8);
        a.remove();
        a.remove();
        return a;
    }
   
    //  example queue.
    public static CircularQueue g(){
        CircularQueue a = new CircularQueue(5);
        a.insert(9);
        a.insert(6);
        a.insert(3);
        a.insert(04);
        a.insert(21);
        return a;
    }
    
    
    public static void main(String[] args) {
        
        // team: Gustavo Hammerschmidt.
                
        help();
        
        //printQueueExample();
        
        
        CircularQueue a = compact(f());
        CircularQueue b = compact(g());
        
        printSorted(a);
        printSorted(b);
         
        printMerge(merge(sortQueue(a),sortQueue(b)));
        
    }

    
}