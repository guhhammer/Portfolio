
       

import java.util.Scanner;
import java.io.*;

public class WordFrequencyAvlTree {
    
    WordNode root;
    
    // tree constructor.
    public WordFrequencyAvlTree(){
        this.root = null;
    }
       
    
    // recursive entry point.
    public void insert(String x){ root = insert(x, root);}
    
    // inserts an element into the tree.
    public WordNode insert(String x, WordNode tree){
        if(tree == null){ tree = new WordNode(x);} // empty tree.
        else{
            if(x.compareTo(tree.info) == 0){  tree.freq++;} // frequency++
            if(x.compareTo(tree.info) < 0){ // go to the left branch of the tree.
                tree.left = insert(x, tree.left);// inserts when tree is null.
                if(fb(tree) == 2){
                    if(x.compareTo(tree.left.info) < 0){
                        tree = rotateLeft(tree);// rotate left
                    }
                    else{ 
                        tree = rotateRightLeft(tree);// rotate: 1. right 2. left
                    }
                }
            }       // use = in the else-if to accept duplicates 
            else if(x.compareTo(tree.info) > 0){   // ignores equal numbers;
                tree.right = insert(x, tree.right);// inserts when tree is null.
                if(fb(tree) == -2){
                    if(x.compareTo(tree.right.info) > 0){
                        tree = rotateRight(tree); // rotate right
                    }
                    else{
                        tree = rotateLeftRight(tree);// rotate: 1. left 2. right 
                    }
                }
            }
        }                
        this.updateHeight(tree);// recompute the tree height.
        return tree;
    }
    
    
    // prints whether it exists.
    void printContains(String n){
        System.out.print("\nWord \""+n+"\" in the tree? "+
        ((this.contains(this.root, n)) ? "\tYes.\n\n" : "\tNo.\n\n"));
    }
   
    // checks whether an element exists.
    boolean contains(WordNode tree, String n){
        if(this.root == null){  return false;} // isEmpty -> nenhum elemento.
        else{
            if(n.compareTo(tree.info) == 0){  return true;} // equal to the current element.
            else{
                if(n.compareTo(tree.info) < 0){  // search the left subtree.
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
    
    
    // larger of two numbers.
    int max(int a, int b){ return (a>b) ? a : b;} 
    
    // recomputes the Node height.
    void updateHeight(WordNode n){
        n.heightValue = 1 + this.max(height(n.left), height(n.right));
    }
    
    // returns the tree height.
    int height(WordNode tree){
        if(tree == null){ return -1; }
        return tree.heightValue;
    }
    
    // balance factor.
    int fb(WordNode n) { return height(n.left)-height(n.right);}  
    
    
    // rotates the tree left.
    WordNode rotateLeft(WordNode n){
        WordNode m = n.left;    // Node m is the right subtree of n.
        n.left = m.right; // left branch of tree m.
        m.right = n;        // left subtree of m points to the root.
              
        // update the Node heights.
        n.heightValue = max(height(m.left), height(m.right))+1;
        m.heightValue = max(height(m.left), height(m.right))+1;
        
        return m;
    }
    
    // rotates the tree right.
    WordNode rotateRight(WordNode m){
        WordNode n = m.right;  // Node n is the left subtree of m.
        m.right = n.left; // right branch of tree n.
        n.left = m;        // right subtree of n points to the root.
       
        // update the Node heights.
        m.heightValue = max(height(m.left), height(m.right))+1;
        n.heightValue = max(height(m.left), height(m.right))+1;
        
        return n;
    }
    
    // compound rotation:  left-right rot. .
    WordNode rotateLeftRight(WordNode n){
        n.right = this.rotateLeft(n.right);// rotate left, right branch. 
        return this.rotateRight(n); // rotate right, tree n.
    }
    
    // compound rotation:  right-left rot. .
    WordNode rotateRightLeft(WordNode m){
        m.left = this.rotateRight(m.left);// rotate right, left branch.
        return this.rotateLeft(m); // rotate left, tree n.
    }
    
    
    // prints the tree pre-order.
    void preOrder(WordNode n){
        if(n != null){
            System.out.print(n.info +", ");
            this.preOrder(n.left);  // the left branches are
            this.preOrder(n.right);   // printed first.
        }
    }
    
    // prints the tree in-order.
    void inOrder(WordNode n){
        if(n != null){
            this.inOrder(n.left);     // stacks the call and prints
            System.out.print(n.info+", "); // the result when it reaches
            this.inOrder(n.right);      // at the end of a branch.
        }
    }
    
    // prints the tree post-order.
    void postOrder(WordNode n){
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
    
    
    // returns the number of repetitions.
    int frequencyCount(String n, WordNode tree){
        if(this.root == null){  return 0;} // isEmpty -> nenhum elemento.
        else{                                     // equal to the current element.
            if(n.compareTo(tree.info) == 0){  return tree.freq;} 
            else{
                if(n.compareTo(tree.info) < 0){  // search the left subtree.
                  if(tree.left!=null){
                      return this.frequencyCount(n, tree.left);
                  }
                  else{ return 0;} // end of the tree -> no element.
                }
                else{  // search the right subtree.
                  if(tree.right!=null){
                      return this.frequencyCount(n,tree.right);}
                  else{ return 0;} // end of the tree -> no element.
                }
            }
        }
    }
    
    // prints the occurrences.
    void printOccurrences(WordFrequencyAvlTree a, String path ,String n){
        
        int counter = this.frequencyCount(n, a.root);
        
        System.out.print("\n\tNumber de Occurrences da palavra \""+n+"\" em "
                + new File(path).getName()+": "+counter+" occurrences. \n");
   
    }
    
    // sum of occurrences.
    int countRepetitions(WordFrequencyAvlTree a, String n){
        return this.frequencyCount(n, a.root);
    }
    
    // counts the number of words in the file.
    static int countWordsInFile(String path) throws FileNotFoundException{
        try{
            Scanner a = new Scanner(new File(path), "UTF-8").useDelimiter(" "); 
            int count = 0;
            while(a.hasNext()){
                a.next();
                count = count+1;
            }
            return count;
        }
        catch(FileNotFoundException f){
            return 0;
        }
    }
    
    // sanitize a palavra.
    static String sanitize(String s){
        s = s.replace(",", "");
        s = s.replace(";", "");
        s = s.replace("?", "");
        s = s.replace(".", "");
        s = s.replace(":", "");
        s = s.replace("!", "");
        s = s.replace("(", "");
        s = s.replace(")", "");
        s = s.replace("-", "");
        s = s.replace("\"", "");
        s = s.replace("\\S+", "");
        s = s.replace(System.getProperty("line.separator"), "");
        return s;
    }
    
    // retorna vetor com as words do texto.
    static String[] words(String path) throws FileNotFoundException{
        
        String[] h = new String[countWordsInFile(path)];    
        
        Scanner i = new Scanner(new File(path),"UTF-8").useDelimiter(" ");
        
        
        int j = 0;
        while(i.hasNext()){
            
            h[j] = sanitize(i.next()).toLowerCase();
            j++;    
            
        
        }
        
        return h;
    }   
    
    // inserts the words into the tree.
    void loadFromFile(String path) throws FileNotFoundException{
        String[] aux = words(path);
        for(int i = 0; i < aux.length; i++){ this.insert(aux[i]); }   
    }
    
  
    public static void main(String[] args) throws FileNotFoundException {
        
        
        
        
        WordFrequencyAvlTree tree1 = new WordFrequencyAvlTree();
        WordFrequencyAvlTree tree2 = new WordFrequencyAvlTree();
        WordFrequencyAvlTree tree3 = new WordFrequencyAvlTree();
        
        
        String path1 = "data/tree1.txt";

        String path2 = "data/tree2.txt";
        
        String path3 = "data/tree3.txt";
       
        
        tree1.loadFromFile(path1);
        tree2.loadFromFile(path2);
        tree3.loadFromFile(path3);
        
        
        // true to print the trees.
        if(true){ 
            System.out.print("\nTree 1:");
            tree1.printOrders();
            System.out.print("\nTree 2:");
            tree2.printOrders();
            System.out.print("\nTree 3:");
            tree3.printOrders();
        }
        
        
        Scanner c = new Scanner(System.in);
        System.out.print("\n\n\t\tWord search over binary trees:\n");
        
        int aux = 0;
        while(true){
            int count = 0;
            System.out.print("\n\nEnter a word to search: \n");
            System.out.print("\nWord:   ");
            String palavra = c.next();
            count = tree1.countRepetitions(tree1, palavra)+
                    tree2.countRepetitions(tree2, palavra)+
                    tree3.countRepetitions(tree3, palavra);
            System.out.println("Word \""+palavra+"\" "
                    + "repeats "+count+"times.");
            tree1.printOccurrences(tree1, path1, palavra);
            tree2.printOccurrences(tree2, path2, palavra);
            tree3.printOccurrences(tree3, path3, palavra);
            System.out.print("\nEnter 1 to quit or 0 to continue:  ");
            aux = c.nextInt();
            if(aux == 1 || aux == 1){
                System.out.print("\nQuitting.\n\n");
                break;
            }   
        }
        
        
    }
       
}
