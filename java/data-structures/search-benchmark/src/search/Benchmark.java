package search;




import static search.BinarySearch.search;
import java.util.Scanner;


public class Benchmark {
    
    // builds the tree from an array.
    public static AvlTree buildTree(AvlTree y,int[] x){
        for(int i = 0; i < x.length; i++){  y.insert(x[i]); }
        return y;
    }
    
    // searches the tree for an array of elements.
    public static void searchTree(AvlTree tree, int[] x) throws Exception{
        for(int i = 0; i < x.length; i++){ tree.contains(tree.root,x[i]); }
    }
    
    // indexa elemento em table hash.
    public static HashTable indexKeys(HashTable h, int[] x){
        for(int i = 0; i < x.length; i++){ h.insertKey(x[i]);}
        return h;
    }
    
    // procura elemento em table hash.
    public static void searchHash(HashTable h, int[] x){
        for(int i = 0; i < x.length; i++){ h.search(x[i]);}
    }
    
    // searches an array for an array of elements.
    public static void searchArray(int[] y,int[] x){
        for(int i = 0; i < x.length; i++){ search(y, x[i]); }
    }
    
    
    public static void main(String[] args) throws Exception {
        
        long begin = System.currentTimeMillis();
        
        //////////////////////////////////////////////////
        long input = System.currentTimeMillis();
        
        Scanner s = new Scanner(System.in);        
        System.out.print("\nDigite um size para o array: ");
        int n = s.nextInt();
        System.out.println();          // user input time.
        
        long inputEnd = System.currentTimeMillis();
        ////////////////////////////////////////////////// 
        
        long processTime = System.currentTimeMillis();
     
        /////////////////////////////////////////////////
        long ArrayCreation = System.currentTimeMillis();
        
        int[] array = ArrayFactory.makeVector(n);
        int[] searchKeys = ArrayFactory.makeVector((int)(n * 1.5));
        
        long CriarArrayFim = System.currentTimeMillis();
        /////////////////////////////////////////////////
       
        /////////////////////////////////////////////////
        long Index = System.currentTimeMillis();
        HashTable Hs = new HashTable(array.length);
        Hs = indexKeys(Hs, array);                 // Index.
        long IndexarFim = System.currentTimeMillis();
        ////
        ////
        long arvC = System.currentTimeMillis();
        AvlTree tree = new AvlTree();
        tree = buildTree(tree, array);       // build.
        long arvCFim = System.currentTimeMillis();
        ////
        ////
        long Sort = System.currentTimeMillis();
        int[] x = array;                 
        x = BinarySearch.sort(x);           // sort.
        long OrdenarFim = System.currentTimeMillis();
        /////////////////////////////////////////////////
        
        /////////////////////////////////////////////////
        long hashProc = System.currentTimeMillis();
        searchHash(Hs,searchKeys);          // hash-table search.
        long hashProcFim = System.currentTimeMillis();
        ////
        ////
        long arvProc = System.currentTimeMillis();
        searchTree(tree,searchKeys);        // tree search.
        long arvProcFim = System.currentTimeMillis();
        ////
        ////
        long bbProc = System.currentTimeMillis();
        searchArray(x, searchKeys);            // array search.
        long bbProcFim = System.currentTimeMillis();
        /////////////////////////////////////////////////

        /////////////////////////////////////////////////
        long arvRec = System.currentTimeMillis();
        searchTree(tree,array);              // recover tree.
        long arvRecFim = System.currentTimeMillis();
        ////
        ////
        long hashRec = System.currentTimeMillis();
        searchHash(Hs, array);               // recover hash table.
        long hashRecFim = System.currentTimeMillis();
        ////
        ////
        long bbRec = System.currentTimeMillis();
        searchArray(x, array);                  // recover array.
        long bbRecFim = System.currentTimeMillis();
        /////////////////////////////////////////////////
        
        /////////////////////////////////////////////////
        long inputTime = inputEnd - input;
        long arrayCreationTime = CriarArrayFim - ArrayCreation;
        
        long indexTime = IndexarFim - Index;
        long treeBuildTime = arvCFim - arvC;
        long sortArrayTime = OrdenarFim - Sort;
        
        long P_tree = arvProcFim - arvProc; // time deltas.
        long P_hash = hashProcFim - hashProc;
        long P_bb = bbProcFim - bbProc;
        
        long Rec_tree = arvRecFim - arvRec; // time deltas.
        long Rec_hash = hashRecFim - hashRec;
        long Rec_bb = bbRecFim - bbRec;
        
        long processoTempoFim = System.currentTimeMillis();
        long process = processoTempoFim - processTime; // process time.
        
        long end = System.currentTimeMillis();
        long totalTime = end - begin;           // total time.
        /////////////////////////////////////////////////       
        
        System.out.println(               
        "\nInput time for the array size: "+inputTime+
        " ms ("+inputTime/1000.0+" s)\n\n"+
        "Array creation time with "+n+" elements: "+arrayCreationTime+
        " ms ("+arrayCreationTime/1000.0+" s).\n\n\n\n"+
        "Tree build time: "+treeBuildTime+" ms ("+
        treeBuildTime/1000.0+" s).\n"+
        "Hash-table indexing time: "+indexTime+" ms ("+
        indexTime/1000.0+" s).\n"+
        "Array sort time: "+sortArrayTime+" ms ("+
        sortArrayTime/1000.0+" s).\n\n\n"+       
        "Tree search time (searchKeys) over all "+"elements"
        +" of the array: "+P_tree+" ms ("+P_tree/1000.0+" s).\n"+
        "Tempo de pesquisa(searchKeys) em table hash de todos os elementos"+
        " of the array: "+P_hash+" ms ("+P_hash/1000.0+" s).\n"+
        "Tempo de pesquisa(searchKeys) em vetor de todos os elementos "+
        "of the array: "+P_bb+" ms ("+P_bb/1000.0+" s).\n\n\n"+      
        "Retrieval time of all keys in the tree over all "+
        "elements of the array: "+Rec_tree+" ms ("+Rec_tree/1000.0+
        " s).\n"+
        "Retrieval time of all keys in the hash table over all "+
        "elementos of the array: "+Rec_hash+" ms ("+Rec_hash/1000.0+
        " s).\n"+
        "Retrieval time of all keys in the array over all elements "
        + "of the array: "+Rec_bb+" ms ("+Rec_bb/1000.0+" s)."+
        "\n\n\n"+              
        "Tree build + retrieval time: "+(treeBuildTime+Rec_tree)+
        " ms ("+(treeBuildTime+Rec_tree)/1000.0+" s).\n"+
        "Hash-table indexing + retrieval time: "+(indexTime+Rec_hash)+
        " ms ("+(indexTime+Rec_hash)/1000.0+" s).\n"+
        "Array sort + retrieval time: "+(sortArrayTime+Rec_bb)+
        " ms ("+(sortArrayTime+Rec_bb)/1000.0+" s).\n\n"+
        "Sum of indexing and retrieval times: "+
        (indexTime+Rec_hash+treeBuildTime+Rec_tree+sortArrayTime+Rec_bb)+
        " ms ("+
        (indexTime+Rec_hash+treeBuildTime+Rec_tree+sortArrayTime+Rec_bb)/1000.0+
        " s).\n\n\n"+                      
        "Total process time: "+process+" ms ("+
        process/1000.0+" s).\n\n"+
        "Total execution time: "+totalTime+" ms ("+
        totalTime/1000.0+" s).\n");
        
    }

}
