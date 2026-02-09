
package tabela_hash_busca;

import java.util.Random;

public class criar_array {
    
    // Uso: cria vetor com valores aleatórios.
    public static int[] mach_vektor(int tamanho){
        int[] x = new int[tamanho];
        for(int i = 0; i < x.length; i++){
            x[i] = new Random().nextInt(tamanho*1000);// tamanho*1000<-limite. 
        }
        return x;
    }   // gemacht.

}
