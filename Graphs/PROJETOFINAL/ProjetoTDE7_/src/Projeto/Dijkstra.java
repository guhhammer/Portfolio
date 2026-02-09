package Projeto;

public class Dijkstra {

    private static final boolean MEMBRO = true;
    private static final boolean NAOMEMBRO = false;
    private static final int INFINITO = 999999999;

    // Uso: retorna melhor caminho entre s e t.
    public static int melhorCaminho(Grafo g, int[] caminho, int s, int t){
        int distancia[] = new int[g.tamanho];
        boolean perm[] = new boolean[g.tamanho];
        int corrente, i, k = s, dc;
        int menordist, novadist = 0;

        //inicialização
        for (i = 0; i < g.tamanho; ++i) {
            perm[i] = NAOMEMBRO;
            distancia[i] = INFINITO;
            caminho[i] = -1;
        }
        perm[s] = MEMBRO;
        distancia[s] = 0;
        corrente = s;
        while (corrente != t) {
            menordist = INFINITO;
            dc = distancia[corrente];
            
            for (i = 0; i < g.tamanho; i++) {
                
                if(!perm[i]){
                                    
                    if(g.matriz.lista[corrente].existir(i)){
                        novadist = dc + g.matriz.lista[corrente].mostraValor(corrente, i);
                    }   // Se o valor i existir -> adiciona.
                    else{ // Senão -> adiciona o valor INFINITO.
                        novadist = dc + INFINITO;
                    }

                    if(novadist < distancia[i]){
                        distancia[i] = novadist;
                        caminho[i] = corrente;
                    }

                    if(distancia[i] < menordist){
                        menordist = distancia[i];
                        k = i;
                    }

                }
            }
            corrente = k;
            perm[corrente] = MEMBRO;
        }
        return distancia[t];
    }
   
    // Uso: retorna a distância de s a t.
    public static int distancia(Grafo g, int s, int t){
        return melhorCaminho(g,new int[g.tamanho],s,t);
    }

}