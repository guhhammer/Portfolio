package search;




import java.util.Random;

public class ArrayFactory {
    
    // creates an array of random values.
    public static int[] makeVector(int size){
        int[] x = new int[size];
        for(int i = 0; i < x.length; i++){
            x[i] = new Random().nextInt(size*1000);// size*1000<-limit. 
        }
        return x;
    }

}
