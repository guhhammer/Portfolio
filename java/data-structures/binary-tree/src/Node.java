
       

public class Node{
        
    int info;
    int heightValue;
    Node left;
    Node right;

    // Node constructor.
    public Node(int i){
        this.info = i;
        this.heightValue = -1;
        this.left = null;
        this.right = null;
    }
    
}