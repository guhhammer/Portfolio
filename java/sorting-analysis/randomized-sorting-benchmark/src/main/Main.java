package main;

import sorting.*;
import arrays.ArrayFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static void setToZero(long[] arr){

        for(int l = 0; l < arr.length; l++){ arr[l] = 0; }

    }

    private static long[] controller(int sizes, int[] array, int repeat, String btype){

        long track, sumof, milliseconds[] = new long[repeat];
        boolean rq = false, rrq = false, rm = false, rrm = false, rs = false, rrs = false;

        long[] sortingExecutionTime = new long[6];

        // 0 -> recursive quicksort.
        // 1 -> random recursive quicksort.
        // 2 -> recursive mergesort.
        // 3 -> random recursive mergesort.
        // 4 -> recursive selectionsort.
        // 5 -> random recursive selectionsort.

        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////

        System.out.print("Recursive Quicksort [repeat="+repeat+"] [arraysize="+sizes+"] ["+btype+"] => [ ");
        setToZero(milliseconds);
        for(int r = 0; r < repeat; r++){

            if (!rq){

                try{

                    track = System.currentTimeMillis();

                    RecursiveQuicksort.quicksortWrapper(array.clone());

                    milliseconds[r] = System.currentTimeMillis() - track;

                    System.out.print(r+":("+milliseconds[r]+"ms) ");

                } catch (StackOverflowError | OutOfMemoryError e){

                    e.getSuppressed();
                    System.out.print(e.getSuppressed()); rrs = true;

                }

            }

            System.gc();

        }

        sumof = 0;
        for(int i = 0; i < milliseconds.length; i++){  sumof += milliseconds[i]; }
        sumof = (sumof / milliseconds.length);

        System.out.println("] Avg. Exec. Time = "+sumof);

        sortingExecutionTime[0] = sumof;

        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////

        System.out.print("Random Recursive Quicksort [repeat="+repeat+"] [arraysize="+sizes+"] ["+btype+"] => [ ");
        setToZero(milliseconds);
        for(int r = 0; r < repeat; r++){

            if (!rrq){

                try{

                    track = System.currentTimeMillis();

                    RecursiveQuicksort.quicksortRandomWrapper(array.clone());

                    milliseconds[r] = System.currentTimeMillis() - track;

                    System.out.print(r+":("+milliseconds[r]+"ms) ");

                } catch (StackOverflowError | OutOfMemoryError e){

                    e.getSuppressed();
                    System.out.print(e.getSuppressed()); rrs = true;

                }

            }

            System.gc();

        }

        sumof = 0;
        for(int i = 0; i < milliseconds.length; i++){  sumof += milliseconds[i]; }
        sumof = (sumof / milliseconds.length);

        System.out.println("] Avg. Exec. Time = "+sumof);

        sortingExecutionTime[1] = sumof;

        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////

        System.out.print("Recursive Mergesort [repeat="+repeat+"] [arraysize="+sizes+"] ["+btype+"] => [ ");
        setToZero(milliseconds);
        for(int r = 0; r < repeat; r++){

            if (!rm){

                try{

                    track = System.currentTimeMillis();

                    RecursiveMergesort.mergesortWrapper(array.clone());

                    milliseconds[r] = System.currentTimeMillis() - track;

                    System.out.print(r+":("+milliseconds[r]+"ms) ");

                } catch (StackOverflowError | OutOfMemoryError e){

                    e.getSuppressed();
                    System.out.print(e.getSuppressed()); rrs = true;

                }

            }

            System.gc();

        }

        sumof = 0;
        for(int i = 0; i < milliseconds.length; i++){  sumof += milliseconds[i]; }
        sumof = (sumof / milliseconds.length);

        System.out.println("] Avg. Exec. Time = "+sumof);

        sortingExecutionTime[2] = sumof;

        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////

        System.out.print("Random Recursive Mergesort [repeat="+repeat+"] [arraysize="+sizes+"] ["+btype+"] => [ ");
        setToZero(milliseconds);
        for(int r = 0; r < repeat; r++){

            if (!rrm){

                try{

                    track = System.currentTimeMillis();

                    RecursiveMergesort.mergesortRandomWrapper(array.clone());

                    milliseconds[r] = System.currentTimeMillis() - track;

                    System.out.print(r+":("+milliseconds[r]+"ms) ");

                } catch (StackOverflowError | OutOfMemoryError e){

                    e.getSuppressed();
                    System.out.print(e.getSuppressed()); rrs = true;

                }

            }

            System.gc();

        }

        sumof = 0;
        for(int i = 0; i < milliseconds.length; i++){  sumof += milliseconds[i]; }
        sumof = (sumof / milliseconds.length);

        System.out.println("] Avg. Exec. Time = "+sumof);

        sortingExecutionTime[3] = sumof;

        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////

        System.out.print("Recursive Selectionsort [repeat="+repeat+"] [arraysize="+sizes+"] ["+btype+"] => [ ");
        setToZero(milliseconds);
        for(int r = 0; r < repeat; r++){

            if (!rs && sizes < 20000){

                try{

                    track = System.currentTimeMillis();

                    RecursiveSelectionsort.selectionsortWrapper(array.clone());

                    milliseconds[r] = System.currentTimeMillis() - track;

                    System.out.print(r+":("+milliseconds[r]+"ms) ");

                } catch (StackOverflowError | OutOfMemoryError e){

                    e.getSuppressed();
                    System.out.print(e.getSuppressed()); rrs = true;

                }

            }

            System.gc();

        }

        sumof = 0;
        for(int i = 0; i < milliseconds.length; i++){  sumof += milliseconds[i]; }
        sumof = (sumof / milliseconds.length);

        System.out.println("] Avg. Exec. Time = "+sumof);

        sortingExecutionTime[4] = sumof;

        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////

        System.out.print("Random Recursive Selectionsort [repeat="+repeat+"] [arraysize="+sizes+"] ["+btype+"] => [ ");
        setToZero(milliseconds);
        for(int r = 0; r < repeat; r++){

            if (!rrs && sizes < 20000){

                try{

                    track = System.currentTimeMillis();

                    RecursiveSelectionsort.selectionsortRandomWrapper(array.clone());

                    milliseconds[r] = System.currentTimeMillis() - track;

                    System.out.print(r+":("+milliseconds[r]+"ms) ");

                } catch (StackOverflowError | OutOfMemoryError e){

                    e.getSuppressed();
                    System.out.print(e.getSuppressed()); rrs = true;

                }

            }

            System.gc();

        }

        sumof = 0;
        for(int i = 0; i < milliseconds.length; i++){  sumof += milliseconds[i]; }
        sumof = (sumof / milliseconds.length);

        System.out.println("] Avg. Exec. Time = "+sumof+"\n");

        sortingExecutionTime[5] = sumof;

        return sortingExecutionTime;

    }

    private static String getRootpath(){

        String[] aux = System.getProperty("java.class.path").replace("\\", "//").split("//");
        String rootpath = "";
        for(int i = 0; i < aux.length-3; i++){ rootpath += aux[i] + "\\\\"; }
        return rootpath;

    }

    private static void createFile(String filename){  new File(getRootpath()+filename);  }

    private static void writeFile(String filename, long[][] recipient, int i_len, int j_len){

        try {

            FileWriter myWriter = new FileWriter(getRootpath()+filename);

            String aux;
            for(int i = 0; i < i_len; i++){

                aux = "";
                for(int j = 0; j < j_len; j++){

                    aux += recipient[i][j]+" ";

                }

                myWriter.write(aux+"[BREAK] ");

            }

            myWriter.close();

            System.out.println(filename+" was written. \n");


        } catch (IOException e) {

            System.out.println(filename+" "+e.getMessage()+" -- error.\n");
            e.printStackTrace();

        }

    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        // MemoryOverflow in 90 millions, some even earlier.
        // 50 mi max - mergesort ~ 9 seconds.
        // Selectionsort hits StackOveflow when size is 20000.
        int[] sizes = new int[]{    1000, 2000, 5000,
                                    10000, 20000, 50000,
                                    100000, 200000, 500000,
                                    1000000, 2000000, 5000000,
                                    10000000, 20000000, 30000000, 40000000, 50000000, 60000000, 70000000, 80000000};

        // Store the execution time for each size and method.
        long[][] ascendingTime = new long[sizes.length][6];
        long[][] deascendingTime = new long[sizes.length][6];
        long[][] randomTime = new long[sizes.length][6];

        int[] derive;
        for(int s = 0; s < sizes.length; s++){

            derive = new ArrayFactory( sizes[s] ).getAscending();
            ascendingTime[s] = controller(sizes[s], derive, 10, "ascending");

            derive = new ArrayFactory( sizes[s] ).getDescending();
            deascendingTime[s] = controller(sizes[s], derive, 10, "descending");

            derive = new ArrayFactory( sizes[s] ).getRandom();
            randomTime[s] = controller(sizes[s], derive, 10, "random");

            derive = null;
            System.gc();

        }

        if (false){

            createFile("\\\\jtxt\\\\crescent.txt");
            writeFile("\\\\jtxt\\\\crescent.txt", ascendingTime, sizes.length, 6);

            createFile("\\\\jtxt\\\\decrescent.txt");
            writeFile("\\\\jtxt\\\\decrescent.txt", deascendingTime, sizes.length, 6);

            createFile("\\\\jtxt\\\\random.txt");
            writeFile("\\\\jtxt\\\\random.txt", randomTime, sizes.length, 6);

        }

        System.out.println("\nAscending times: ");
        for(int i = 0; i < sizes.length; i++){

            for(int j = 0; j < 6; j++){

                System.out.print(ascendingTime[i][j]+"ms ");

            } System.out.println();

        }

        System.out.println("\nDescending times: ");
        for(int i = 0; i < sizes.length; i++){

            for(int j = 0; j < 6; j++){

                System.out.print(deascendingTime[i][j]+"ms ");

            } System.out.println();

        }

        System.out.println("\nRandom times: ");
        for(int i = 0; i < sizes.length; i++){

            for(int j = 0; j < 6; j++){

                System.out.print(randomTime[i][j]+"ms ");

            } System.out.println();

        }

        long end = System.currentTimeMillis()-start;
        System.out.println("\nMain Execution Time: "+end+"ms ("+end/1000+"s) ("+end/60000+"m).\n");

    }

}
