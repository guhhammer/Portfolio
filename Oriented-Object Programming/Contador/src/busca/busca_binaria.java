
package busca;

import java.util.Arrays;

public class busca_binaria {

    // Uso: retorna o vetor ordenado.
    public static int[] ordenar(int[] x){ Arrays.sort(x); return x;}// gemacht.
    
    // Uso: faz busca de elemento no vetor.
    public static int busca(int[] n, int pos) {
        int inf = 0, sup = n.length - 1;
        while (inf <= sup) {
            int meio = (inf + sup) / 2;
            if(pos == n[meio]){ return meio; }
            if(pos < n[meio]){ sup = meio - 1; }
            else{  inf = meio + 1; }
        }
        return (-1);
    }   // gemacht.

}
