
package TDE2;  //Grupo 14: Gustavo Hammerschmidt.

public class Warshall {
    
    
    // Warshall.
    public static void warshall_fechamento(Grafo m, boolean[][] fechamento){ 
        
        int aux = m.matriz.tamanho;
        
        for(int i = 0; i < aux;i++){
            for(int j = 0; j < aux; j++){
                fechamento[i][j] = m.matriz.lista[i].existir(j);
            }
        }   // Inicialização: M. fechamento.
        
        for(int k = 0; k < aux; k++){
            for(int i = 0; i < aux; i++){
                if(fechamento[i][k]){
                    for(int j = 0; j < aux; j++){
                        fechamento[i][j] = fechamento[i][j] || fechamento[k][j];
                    }
                }
            }
        }       // Algoritmo de Warshall.
        
    }
    
    /* 
        A mais:   implementei função existir na classe MEsparsa para
                  retornar valores boolean no primeiro loop.
    */
}
