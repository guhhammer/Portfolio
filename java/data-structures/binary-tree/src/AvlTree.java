
      

public class AvlTree{
    
    Node root;
    
    // tree constructor.
    public AvlTree(){
        this.root = null;
    }
       
    
    // recursive entry point.
    public void insert(int x){ root = insert(x, root);}
    
    // inserts an element into the tree.
    public Node insert(int x, Node tree){
        if(tree == null){ tree = new Node(x);} // empty tree.
        else{
            if(x < tree.info){ // go to the left branch of the tree.
                tree.left = insert(x, tree.left);// inserts when tree is null.
                if(fb(tree) == 2){
                    if(x < tree.left.info)
                    { tree = rotateLeft(tree); } // rotate left
                    else{ tree = rotateRightLeft(tree);}
                }                       // rotate: 1. right 2. left
            }      
            else if(x > tree.info){// ignores equal numbers; use = to allow duplicates
                tree.right = insert(x, tree.right);// inserts when tree is null.
                if(fb(tree) == -2){
                    if(x > tree.right.info)
                    { tree = rotateRight(tree);} // rotate right
                    else{ tree = rotateLeftRight(tree);}
                }                           // rotate: 1. left 2. right 
            }
        }                
        this.updateHeight(tree);// recompute the tree height.
        return tree;
    }
    
    
    // prints whether it exists.
    void printContains(int n){
        System.out.print("\nElement "+n+" in the tree? "+
        ((this.contains(this.root, n)) ? "\tYes.\n" : "\tNo.\n"));
    }
    
    // checks whether an element exists.
    boolean contains(Node tree, int n){
        if(this.root == null){  return false;} // isEmpty -> nenhum elemento.
        else{
            if(n == tree.info){  return true;} // equal to the current element.
            else{
                if(n < tree.info){  // search the left subtree.
                  if(tree.left!=null){return this.contains(tree.left,n);}
                  else{  return false;} // end of the tree -> no element.
                }
                else{  // search the right subtree.
                  if(tree.right!=null){return this.contains(tree.right,n);}
                  else{  return false;} // end of the tree -> no element.
                }
            }
        }
    }
    
    
    // returns the smallest value.
    static int min(Node n){
        Node aux = n;
        while(aux.left != null){ aux = aux.left; } 
        return aux.info;        // busca o valor mais à left.
    }
    
    // returns the largest value.
    static int max(Node n){
        Node aux = n;
        while(aux.right != null){ aux = aux.right; }
        return aux.info;       // busca o valor mais à right.
    }
    
    // remove o menor valor.
    Node removeMin(Node n){
        if(n != null){
            if(n.left != null){
                n.left = this.removeMin(n.left);// set to null.
                return n; 
            }
            else{ return n.right;}// set to null.
        }
        else{ return null;} // return null if the tree is empty.
    }
    
    // remove o maior valor.
    Node removeMax(Node n){
        if(n != null){
            if(n.right != null){
                n.right = this.removeMax(n.right);// set to null.
                return n;
            }
            else{ return n.left;}// set to null.
        }
        else{ return null;} // return null if the tree is empty.
    }
    
    
    // removes a number.
    void remove(int n){
        System.out.print("\nRemover ("+n+"): ");
        if(this.contains(this.root,n)){
            this.remove(this.root, this.root, n);
            System.out.print("\n\tNumber "+n+" was removed.\n\n");
        }
        else{
            System.out.print("\n\tNumber "+n+" is not in the tree.\n\n");
        }
    }
    
    // removes an element from the tree.
    void remove(Node tree, Node aux, int n){
        if(this.root != null){ // if the tree is not empty.
            if(this.contains(this.root, n) == true){ // se n contains.
                if(n == tree.info){ // if n equals the current value.
                    if(tree.left == null && tree.right == null){
                        if(aux.info <= tree.info){ 
                            aux.right = null; this.rebalance(aux, n);
                        }
                        else{ aux.left = null; this.rebalance(aux, n); }
                    } // leaf remover.
                    else if(tree.left != null && tree.right == null){
                        aux = tree.left; this.rebalance(aux, n);
                    } // removedor de 1 filho(left).
                    else if(tree.left == null && tree.right != null){
                        aux = tree.right; this.rebalance(aux, n);
                    } // removedor de 1 filho(right).
                    else{
                        tree.info = min(tree.right);
                        tree.right = this.removeMin(tree.right);
                    }   // remover with two children.
                }
                else if(n < tree.info){ this.remove(tree.left,tree, n); }
                else{ this.remove(tree.right,tree, n); }
                this.updateHeight(aux);
            }   
        }
    }
       
    
    // larger of two numbers.
    int max(int a, int b){ return (a>b) ? a : b;} 
    
    // recomputes the Node height.
    void updateHeight(Node n){
        n.heightValue = 1+ this.max(height(n.left), height(n.right));
    }
   
    // returns the tree height.
    int height(Node tree){
        if(tree == null){ return -1; }
        return tree.heightValue;
    }
    
    // balance factor.
    int fb(Node n) { return height(n.left)-height(n.right);}
    
    
    // rotates the tree left.
    Node rotateLeft(Node n){
        Node m = n.left;    // Node m is the right subtree of n.
        n.left = m.right; // left branch of tree m.
        m.right = n;        // left subtree of m points to the root.
              
        // update the Node heights.
        n.heightValue = max(height(m.left), height(m.right))+1;
        m.heightValue = max(height(m.left), height(m.right))+1;
        
        return m;
    }
    
    // rotates the tree right.
    Node rotateRight(Node m){
        Node n = m.right;  // Node n is the left subtree of m.
        m.right = n.left; // right branch of tree n.
        n.left = m;        // right subtree of n points to the root.
       
        // update the Node heights.
        m.heightValue = max(height(m.left), height(m.right))+1;
        n.heightValue = max(height(m.left), height(m.right))+1;
        
        return n;
    }
    
    // compound rotation:  left-right rot. .
    Node rotateLeftRight(Node n){
        n.right = this.rotateLeft(n.right);// rotate left, right branch. 
        return this.rotateRight(n); // rotate right, tree n.
    }
    
    // compound rotation:  right-left rot. .
    Node rotateRightLeft(Node m){
        m.left = this.rotateRight(m.left);// rotate right, left branch.
        return this.rotateLeft(m); // rotate left, tree n.
    }
    
    // rebalances the tree after removal.
    Node rebalance(Node tree, int n){
        if(height(tree.left)+1>2 && height(tree.right)+1>2){
            if(fb(tree) > 1){                      // rotate left
                if(n < tree.left.info){tree = rotateLeft(tree);}
                else{ tree = rotateRightLeft(tree);} // rotate: 1. right 2. left 
            }
            else if(fb(tree) < -1){                 // rotate right
                if(n > tree.right.info){ tree = rotateRight(tree);}
                else{  tree = rotateLeftRight(tree);} // rotate: 1. left 2. right 
            }    
            return tree;
        }      
        this.updateHeight(tree);
        return null;
    }
    
    
    // prints the tree pre-order.
    void preOrder(Node n){
        if(n != null){
            System.out.print(n.info +", ");
            this.preOrder(n.left);  // the left branches are
            this.preOrder(n.right);   // printed first.
        }
    }
   
    // prints the tree in-order.
    void inOrder(Node n){
        if(n != null){
            this.inOrder(n.left);     // stacks the call and prints
            System.out.print(n.info+", "); // the result when it reaches
            this.inOrder(n.right);      // at the end of a branch.
        }
    }
    
    // prints the tree post-order.
    void postOrder(Node n){
        if(n != null){
            this.postOrder(n.left);  // the right branches are
            this.postOrder(n.right);   // printed first.
            System.out.print(n.info+", ");
        }
    }
   
    // prints the tree in pre-, in- and post-order. 
    void printOrders(){
        if(this.root == null){;}
        else{
            System.out.print("\nTraversals: ");
            System.out.print("\n\n\tPre-order:\t { ");
            this.preOrder(this.root);
            System.out.print(" }\n\n\tIn-Ordem:\t { ");
            this.inOrder(this.root);
            System.out.print(" }\n\n\tPost-order:\t { ");
            this.postOrder(this.root);
            System.out.print(" }\n\n");
        }
    }

   
    public static void main(String[] args){
        
        
        
        AvlTree tree = new AvlTree();
      
        tree.insert(14);
        tree.insert(15);
        tree.insert(4);
        tree.insert(9);
        tree.insert(7);
        tree.insert(18);
        tree.insert(3);
        tree.insert(5);
        //tree.insert(4);
        tree.insert(20);
        tree.insert(17);
        tree.insert(22);
        tree.insert(24);
        tree.insert(26);
        tree.insert(21);
        tree.insert(13);
        tree.insert(16);
        // tree.insert(9);
        //tree.insert(15);
        //tree.insert(5);         // does not insert duplicates.
        
        
        tree.printOrders();
        
        //tree.printContains(50);
        
        System.out.println("\nMin = "+min(tree.root));
        System.out.println("Altura = "+tree.height(tree.root));
        System.out.println("Max = "+tree.max(tree.root));
       
        
        tree.remove(16);
        tree.remove(3);
        tree.remove(5);
        tree.remove(17);
        tree.remove(4);
        
        tree.printOrders();
        
   
    }

}
