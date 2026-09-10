package search;




public class AvlTree{
    
    public class Node{
        
        int info;
        Node left, right;

        // Node constructor.
        public Node(int i){
            this.info = i;
            this.left = this.right = null;
        }

    }
    
    Node root;
    
    // tree constructor.
    public AvlTree(){ this.root = null; }
    
    // inserts an element into the tree.
    void insert(int x){
        if(this.root == null){ this.root = new Node(x);}
        else{ 
            Node next = this.root, hold = null;
            while(next != null){
                hold = next;
                if(x < next.info){ next = next.left;}
                else{ next = next.right;}
            }
            if(x < hold.info){ hold.left = new Node(x);}
            else{ hold.right = new Node(x);}
        }
    }
    
    // search function.  ver se usar essa ou a outra.
    boolean contains(Node tree, int n) throws Exception{
        if(this.root == null){ return false; }
        else{
            while(tree != null && n != tree.info){
               if(n < tree.info){ tree = tree.left;}
               else{ tree = tree.right; }
            }
            try{ return (n == tree.info);}
            catch(Exception e){ return false; }
        }
    }
    
}
