
package tabela_hash_busca;

import static tabela_hash_busca.busca_binaria.busca;
import java.util.Scanner;


public class Tempos {
    
    // Uso: constroi a árvore a partir de um vetor.
    public static arv_bin arv_constroi(arv_bin y,int[] x){
        for(int i = 0; i < x.length; i++){  y.inserir(x[i]); }
        return y;
    }   // gemacht.
    
    // Uso: procura na árvore um vetor de elementos.
    public static void procura_tree(arv_bin tree, int[] x) throws Exception{
        for(int i = 0; i < x.length; i++){ tree.existir(tree.raiz,x[i]); }
    }   // gemacht.
    
    // Uso: indexa elemento em tabela hash.
    public static hash_tb indexar_hash(hash_tb h, int[] x){
        for(int i = 0; i < x.length; i++){ h.inserir_ht(x[i]);}
        return h;
    }   // gemacht.
    
    // Uso: procura elemento em tabela hash.
    public static void procura_hash(hash_tb h, int[] x){
        for(int i = 0; i < x.length; i++){ h.busca(x[i]);}
    }   // gemacht.
    
    // Uso: procura em um vetor um vetor de elementos.
    public static void procura_bb(int[] y,int[] x){
        for(int i = 0; i < x.length; i++){ busca(y, x[i]); }
    }   // gemacht.
    
    
    public static void main(String[] args) throws Exception {
        
        long inicio = System.currentTimeMillis();
        
        //////////////////////////////////////////////////
        long input = System.currentTimeMillis();
        
        Scanner s = new Scanner(System.in);        
        System.out.print("\nDigite um tamanho para o array: ");
        int n = s.nextInt();
        System.out.println();          // tempo do input do usuário.
        
        long inputFim = System.currentTimeMillis();
        ////////////////////////////////////////////////// 
        
        long processoTempo = System.currentTimeMillis();
     
        /////////////////////////////////////////////////
        long CriarArray = System.currentTimeMillis();
        
        int[] array = criar_array.mach_vektor(n);
        int[] chaves_busca = criar_array.mach_vektor((int)(n * 1.5));
        
        long CriarArrayFim = System.currentTimeMillis();
        /////////////////////////////////////////////////
       
        /////////////////////////////////////////////////
        long Indexar = System.currentTimeMillis();
        hash_tb Hs = new hash_tb(array.length);
        Hs = indexar_hash(Hs, array);                 // Indexar.
        long IndexarFim = System.currentTimeMillis();
        ////
        ////
        long arvC = System.currentTimeMillis();
        arv_bin tree = new arv_bin();
        tree = arv_constroi(tree, array);       // Construir.
        long arvCFim = System.currentTimeMillis();
        ////
        ////
        long Ordenar = System.currentTimeMillis();
        int[] x = array;                 
        x = busca_binaria.ordenar(x);           // Ordenar.
        long OrdenarFim = System.currentTimeMillis();
        /////////////////////////////////////////////////
        
        /////////////////////////////////////////////////
        long hashProc = System.currentTimeMillis();
        procura_hash(Hs,chaves_busca);          // Procura tbHash.
        long hashProcFim = System.currentTimeMillis();
        ////
        ////
        long arvProc = System.currentTimeMillis();
        procura_tree(tree,chaves_busca);        // Procura em árvore.
        long arvProcFim = System.currentTimeMillis();
        ////
        ////
        long bbProc = System.currentTimeMillis();
        procura_bb(x, chaves_busca);            // Procura em vetor.
        long bbProcFim = System.currentTimeMillis();
        /////////////////////////////////////////////////

        /////////////////////////////////////////////////
        long arvRec = System.currentTimeMillis();
        procura_tree(tree,array);              // Recupera árvore.
        long arvRecFim = System.currentTimeMillis();
        ////
        ////
        long hashRec = System.currentTimeMillis();
        procura_hash(Hs, array);               // Recupera tbHash.
        long hashRecFim = System.currentTimeMillis();
        ////
        ////
        long bbRec = System.currentTimeMillis();
        procura_bb(x, array);                  // Recupera vetor.
        long bbRecFim = System.currentTimeMillis();
        /////////////////////////////////////////////////
        
        /////////////////////////////////////////////////
        long inputTime = inputFim - input;
        long CATempo = CriarArrayFim - CriarArray;
        
        long IndexarTempo = IndexarFim - Indexar;
        long arvConstruir = arvCFim - arvC;
        long ordenarVet = OrdenarFim - Ordenar;
        
        long P_tree = arvProcFim - arvProc; // cálculo dos tempos.
        long P_hash = hashProcFim - hashProc;
        long P_bb = bbProcFim - bbProc;
        
        long Rec_tree = arvRecFim - arvRec; // cálculo dos tempos.
        long Rec_hash = hashRecFim - hashRec;
        long Rec_bb = bbRecFim - bbRec;
        
        long processoTempoFim = System.currentTimeMillis();
        long processo = processoTempoFim - processoTempo; // tempo de processo.
        
        long fim = System.currentTimeMillis();
        long tempoTotal = fim - inicio;           // tempo total.
        /////////////////////////////////////////////////       
        
        System.out.println(               
        "\nTempo de input do tamanho do vetor: "+inputTime+
        " milisegundos ("+inputTime/1000.0+" segundos)\n\n"+
        "Tempo de criação de array com "+n+" elementos: "+CATempo+
        " milisegundos ("+CATempo/1000.0+" segundos).\n\n\n\n"+
        "Tempo de construção da árvore: "+arvConstruir+" milisegundos ("+
        arvConstruir/1000.0+" segundos).\n"+
        "Tempo de indexação de tabela hash: "+IndexarTempo+" milisegundos ("+
        IndexarTempo/1000.0+" segundos).\n"+
        "Tempo de ordenação de vetor: "+ordenarVet+" milisegundos ("+
        ordenarVet/1000.0+" segundos).\n\n\n"+       
        "Tempo de pesquisa(chaves_busca) em árvore de todos "+"os elementos"
        +" do vetor: "+P_tree+" milisegundos ("+P_tree/1000.0+" segundos).\n"+
        "Tempo de pesquisa(chaves_busca) em tabela hash de todos os elementos"+
        " do vetor: "+P_hash+" milisegundos ("+P_hash/1000.0+" segundos).\n"+
        "Tempo de pesquisa(chaves_busca) em vetor de todos os elementos "+
        "do vetor: "+P_bb+" milisegundos ("+P_bb/1000.0+" segundos).\n\n\n"+      
        "Tempo de recuperação de todas as chaves em árvore de todos "+
        "os elementos do vetor: "+Rec_tree+" milisegundos ("+Rec_tree/1000.0+
        " segundos).\n"+
        "Tempo de recuperação de todas as chaves em tabela hash de todos os "+
        "elementos do vetor: "+Rec_hash+" milisegundos ("+Rec_hash/1000.0+
        " segundos).\n"+
        "Tempo de recuperação de todas as chaves em vetor de todos os elementos "
        + "do vetor: "+Rec_bb+" milisegundos ("+Rec_bb/1000.0+" segundos)."+
        "\n\n\n"+              
        "Tempo de construção e recuperação em árvore: "+(arvConstruir+Rec_tree)+
        " milisegundos ("+(arvConstruir+Rec_tree)/1000.0+" segundos).\n"+
        "Tempo de indexação e recuperação em tabela hash: "+(IndexarTempo+Rec_hash)+
        " milisegundos ("+(IndexarTempo+Rec_hash)/1000.0+" segundos).\n"+
        "Tempo de ordenação e recuperação em vetor: "+(ordenarVet+Rec_bb)+
        " milisegundos ("+(ordenarVet+Rec_bb)/1000.0+" segundos).\n\n"+
        "Soma dos tempos de indexação e recuperação: "+
        (IndexarTempo+Rec_hash+arvConstruir+Rec_tree+ordenarVet+Rec_bb)+
        " milisegundos ("+
        (IndexarTempo+Rec_hash+arvConstruir+Rec_tree+ordenarVet+Rec_bb)/1000.0+
        " segundos).\n\n\n"+                      
        "Tempo total de processo: "+processo+" milisegundos ("+
        processo/1000.0+" segundos).\n\n"+
        "Tempo total de execução: "+tempoTotal+" milisegundos ("+
        tempoTotal/1000.0+" segundos).\n");
        
    }

}
