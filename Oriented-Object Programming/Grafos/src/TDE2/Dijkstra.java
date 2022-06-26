
package TDE2; //Grupo 14: Gustavo Hammerschmidt.

public class Dijkstra {

    private static final boolean MEMBRO = true;
    private static final boolean NAOMEMBRO = false;
    private static final int INFINITO = 999999999;

    
    /* 
        Tirei a declaração int[] caminho de dentro da função,
        mas a inicialização do vetor ainda continua dentro da 
        função.
    */
    public static int melhorCaminho(Grafo g, int[] caminho, int s, int t){
        int distancia[] = new int[g.tamanho];
        boolean perm[] = new boolean[g.tamanho];
        int corrente, i, k = s, dc, j = 0;
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
                        novadist = dc + g.matriz.lista[corrente].mostraValor(i);
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
    
    // Imprime o caminho na ordem correta, que é a reversa.
    public static String caminhoReverso(String aux){
        
        String rev = "";
        
        for(int i = aux.length()-1; i >= 0; i--){
            rev = rev + aux.charAt(i);
        }
        
        return rev;
        
    }
    
    // Imprime a distância de s a t.
    public static void imprimeDistancia(Grafo g, int s, int t){
        System.out.println("Distância do melhor caminho:   "+
                            melhorCaminho(g,new int[g.tamanho],s,t));
    }
    
    
    // Imprime o caminho.
    public static void imprimeCaminho(Grafo g, int s, int t){
        
        int[] caminho = new int[g.tamanho];
        
        melhorCaminho(g,caminho,s,t);
       
        String path; // mudei o while para salvar os valores na String path;
        
        int i = caminho[t];
        
        path = t+" ";
        while (i != s) {
            path = path+i+" "; 
            i = caminho[i];
        }
        
        path = path+s+" ";  
              
        String pathR = caminhoReverso(path); // Reverso do caminho.
        
        System.out.println(
                String.format(("Caminho de %d a %d: \tInício ->  "+pathR+
                                                    "   <- Fim \n"),s,t));
     
    }


}
