package makeArrays;

import java.util.Random;

public class ArrayBag {

    private int size;
    private int[] crescent, decrescent, randomarray;

    public ArrayBag(int size){

        this.size = size;
        this.crescent = initCrescent();
        this.decrescent = initDecrescent();
        this.randomarray = initRandomarray();

    }

    private int[] initCrescent(){

        int[] arr = new int[this.size];

        for(int i = 0; i < this.size; i++) {  arr[i] = i+1;  }

        return arr;

    }

    private int[] initDecrescent(){

        int[] arr = new int[this.size];

        for(int i = 0; i < this.size; i++) {  arr[i] = this.size-i;  }

        return arr;

    }

    private int[] initRandomarray(){

        Random r = new Random();
        int[] arr = new int[this.size];

        for(int i = 0; i < this.size; i++){

            arr[i] = r.nextInt(  2 * this.size);

        }

        return arr;

    }

    public int[] getCrescent() { return crescent.clone(); }

    public int[] getDecrescent() { return decrescent.clone(); }

    public int[] getRandomarray() { return randomarray.clone(); }
    
}
