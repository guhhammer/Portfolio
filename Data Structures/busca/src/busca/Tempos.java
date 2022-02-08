
package busca;

import static busca.busca_binaria.busca;
import java.util.Scanner;

public class Tempos {   
    
    // Uso: constroi a árvore a partir de um vetor.
    public static arv_bin arv_constroi(arv_bin y,int[] x){
        for(int i = 0; i < x.length; i++){  y.inserir(x[i]); }
        return y;
    }   // gemacht.
    
    // Uso: proura na árvore um vetor de elementos.
    public static void procura_tree(arv_bin tree, int[] x) throws Exception{
        for(int i = 0; i < x.length; i++){ tree.existir(tree.raiz,x[i]); }
    }   // gemacht.
    
    // Uso: procura em um vetor um vetor de elementos.
    public static void procura_bb(int[] y,int[] x){
        for(int i = 0; i < x.length; i++){ busca(y, x[i]); }
    }   // gemacht.
    
         // ver como otimizar tempo da árvore.passar next como parâmetro
    public static void main(String[] args) throws InterruptedException, Exception{
        
        long inicio = System.currentTimeMillis();
        //////////////////////////////////////////////////
        long input = System.currentTimeMillis();
        Scanner s = new Scanner(System.in);        
        System.out.print("\nDigite um tamanho para o array: ");
        int n = s.nextInt();
        System.out.println();          // tempo do input do usuário.
        long inputFim = System.currentTimeMillis();
        long processoTempo = System.currentTimeMillis();
        //////////////////////////////////////////////////
        long CriarArray = System.currentTimeMillis();
        int[] array = criar_array.mach_vektor(n);
        int[] x = array;                  // tempo de criação do array.
        long CriarArrayFim = System.currentTimeMillis();
        /////////////////////////////////////////////////
        long Ordenar = System.currentTimeMillis();
        x = busca_binaria.ordenar(x);// tempo de ordenação de um array.
        long OrdenarFim = System.currentTimeMillis();
        /////////////////////////////////////////////////
        long arvC = System.currentTimeMillis();
        arv_bin tree = new arv_bin();
        tree = arv_constroi(tree, array);// tempo de construção da árvore.
        long arvCFim = System.currentTimeMillis();
        /////////////////////////////////////////////////
        long arvProc = System.currentTimeMillis();
        procura_tree(tree,array);// Procura em árvore.
        long arvProcFim = System.currentTimeMillis();
        /////////////////////////////////////////////////
        long bbProc = System.currentTimeMillis();
        procura_bb(x, array);// Procura em vetor.
        long bbProcFim = System.currentTimeMillis();
        /////////////////////////////////////////////////
        long inputTime = inputFim - input;
        long CATempo = CriarArrayFim - CriarArray;
        long OrdenarTempo = OrdenarFim - Ordenar;
        long arvConstruir = arvCFim - arvC;
        long P_tree = arvProcFim - arvProc; // cálculo dos tempos.
        long P_bb = bbProcFim - bbProc;
        long processoTempoFim = System.currentTimeMillis();
        long processo = processoTempoFim - processoTempo; // tempo de processo.
        long fim = System.currentTimeMillis();
        long tempoTotal = fim - inicio;           // tempo toatal.
        /////////////////////////////////////////////////
        
        System.out.println(
        "\nTempo de input do tamanho do vetor: "+inputTime+
        " milisegundos ("+inputTime/1000.0+" segundos)\n\n"+
        "Tempo de criação de array com "+n+" elementos: "+CATempo+
        " milisegundos ("+CATempo/1000.0+" segundos).\n\n\n\n"+
        "Tempo de Ordenação em vetor: "+OrdenarTempo+" milisegundos ("+
        OrdenarTempo/1000.0+" segundos).\n\n"+
        "Tempo de Construção da árvore: "+arvConstruir+" milisegundos ("+
        arvConstruir/1000.0+" segundos).\n\n\n\n"+
        "Tempo de pesquisa em árvore de todos "+"os elementos do vetor: "+
        P_tree+" milisegundos ("+P_tree/1000.0+" segundos).\n\n"+
        "Tempo de pesquisa em vetor de todos os elementos do vetor: "+
        P_bb+" milisegundos ("+P_bb/1000.0+" segundos).\n\n\n\n"+
        "Tempo de ordenação e pesquisa em vetor: "+(OrdenarTempo+P_bb)+
        " milisegundos ("+(OrdenarTempo+P_bb)/1000.0+" segundos).\n\n"+
        "Tempo de construção e pesquisa em árvore: "+(arvConstruir+P_tree)+
        " milisegundos ("+(arvConstruir+P_tree)/1000.0+" segundos).\n\n\n\n"+
        "Tempo total de processo: "+processo+" milisegundos ("+
        processo/1000.0+" segundos).\n\n"+
        "Tempo total de execução: "+tempoTotal+" milisegundos ("+
        tempoTotal/1000.0+" segundos).\n");
        
    }
  
}
