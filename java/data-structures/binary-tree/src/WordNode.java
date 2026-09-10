
            

public class WordNode {
    
    String info;
    int heightValue;
    WordNode left;
    WordNode right;
    int freq;

    // constructor of WordNode.
    public WordNode(String i){
        this.info = i;
        this.heightValue = -1;
        this.left = null;
        this.right = null;
        this.freq = 1;
    }
}
