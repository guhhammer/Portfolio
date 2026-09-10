package arrays;

import java.util.Random;

public class ArrayFactory {

    private int size;
    private int[] ascending, descending, random;

    public ArrayFactory(int size){

        this.size = size;
        this.ascending = initAscending();
        this.descending = initDescending();
        this.random = initRandom();

    }

    private int[] initAscending(){

        int[] arr = new int[this.size];

        for(int i = 0; i < this.size; i++) {  arr[i] = i+1;  }

        return arr;

    }

    private int[] initDescending(){

        int[] arr = new int[this.size];

        for(int i = 0; i < this.size; i++) {  arr[i] = this.size-i;  }

        return arr;

    }

    private int[] initRandom(){

        Random r = new Random();
        int[] arr = new int[this.size];

        for(int i = 0; i < this.size; i++){

            arr[i] = r.nextInt(  2 * this.size);

        }

        return arr;

    }

    public int[] getAscending() { return ascending.clone(); }

    public int[] getDescending() { return descending.clone(); }

    public int[] getRandom() { return random.clone(); }
    
}
