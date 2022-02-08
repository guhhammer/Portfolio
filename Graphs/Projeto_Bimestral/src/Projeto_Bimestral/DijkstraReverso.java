
package Projeto_Bimestral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DijkstraReverso{

    // NOME: Gustavo Hammerschmidt.
    
    private static final boolean Membro = true; // variável Membro.
    private static final boolean NaoMembro = false; // variável NaoMembro.
    private static final int inf = 999999999; // valor infinito.
    
    // Uso: retorna o maior caminho de s a t e sua distância.
    private static int maiorCaminho(Grafo g, int[] caminho, String s, String t){
       
        // vetor que armazena as distâncias; variável de maior Distância.
        int distancia[] = new int[g.getTamanho()], maiorDist, novaDist = 0;
        boolean perm[] = new boolean[g.getTamanho()];// vetor dos nós percorridos.
        int corrente, i, k = g.findVertice(s), dc; // variáveis.
        
        // inicialização de perm, distância e caminho.
        for(i = 0; i < g.getTamanho(); i++){
            perm[i] = NaoMembro;
            distancia[i] = -inf;  // todos começam com menos infinito. 
            caminho[i] = -1;
        }
        
        perm[g.findVertice(s)] = Membro; // email inicial vira membro.
        distancia[g.findVertice(s)] = 0; // distância dele é 0.
        corrente = g.findVertice(s);     // nó atual é o email inicial.
        while(corrente != g.findVertice(t)){ // se corrente não for email final.
            maiorDist = -inf;   // menos infinito é a maior distância por hora.
            dc = distancia[corrente]; // dc é a distância de corrente.
            
            // para todos os nós do grafo g.
            for(i = 0; i < g.getTamanho(); i++){
                
                if(!perm[i]){ // se não pertecerem ao vetor perm.
                    
                    // se existir uma adjacência entre corrente e i.
                    if(g.matriz.lista[corrente].existir(g.findEmail(i))){
                        novaDist = dc + 
                          g.matriz.lista[corrente].mostraValor(g.findEmail(i));
                    }// nova distância é a soma de dc e a adjacência de corrente a i.
                    else{ 
                        novaDist = dc + (-inf); 
                    } // se não esistir a adjacência, então novaDist é a soma 
                      // de dc e menos infinito.
                    
                    // Se a nova distância for maior que atual.
                    if(novaDist > distancia[i]){
                        distancia[i] = novaDist;
                        caminho[i] = corrente;
                    } // salva nova distância em distância.
                    
                    // distância atual for maior que a maior distância.
                    if(distancia[i] > maiorDist){
                        maiorDist = distancia[i];
                        k = i;
                    }// salva distância atual como a maior distância.
                    
                }
            
            }
            corrente = k; // próximo valor de corrente é k.
            perm[corrente] = Membro; // e ele virará membro.
            
        }
        
        return distancia[g.findVertice(t)]; // retorna valor da maior distância.
        
    }
    
    // Uso: retorna o maior caminho de começo até fim em uma String.
    private static String maiorCaminho(Grafo g, String comeco, String fim){
        
        int[] caminho = new int[g.getTamanho()]; // vetor para salvar o caminho.
        
        maiorCaminho(g, caminho, comeco, fim); // atualiza o vetor caminho.
        
        // ArrayList para salvar o caminho.
        ArrayList<String> path = new ArrayList<>(); 
        
        int aux = caminho[g.findVertice(fim)]; // começa pelo email fim.
        
        path.add((fim)); // adiciona email fim ao ArrayList.

        while(aux != g.findVertice(comeco)){// se aux for diferente do email começo.
            path.add((g.vertices[aux].getNome())); // adiciona aux no ArrayList.
            aux = caminho[aux]; // pega o próximo email do caminho.
        }
        
        path.add((comeco)); // adiciona o email começo ao ArrayList.
        
        Collections.reverse(path); // reverte o ArrayList path.
        
        String retorno = ""; // String auxiliar de retorno.
        int contador = -1; // contador para formatar a impressão da String.
        for(String s : path){ // para cada String no ArrayList path.
            contador++; if(contador % 3 == 2){ retorno += "\n\t";}
            retorno += s+"  "; // adiciona email s à String retorno
        }// adiciona elemento s a retorno e quebra a linha a cada 
         // dez elementos
        
        return retorno; // retorna String retorno.
        
    }
      
    // Uso: retorna a maior dependência acumulada entre o email s e o email t.
    private static int distanciaMaiorCaminho(Grafo g, String s, String t){
        return maiorCaminho(g, new int[g.getTamanho()], s, t);
             // dependência acumulada.
    }
    
    // Uso: função pública que retorna o maior caminho e a maior dependência.
    public static void oMaiorCaminho(Grafo g, String comeco, String fim,
                                                            String opcao){
        
        // String com o maior caminho a ser impresso na opção escolhida.
        String op = (opcao.equals("formatado")) 
                ? maiorCaminho(g, comeco, fim)
                : Arrays.toString(maiorCaminho(g, comeco, fim)
                      .replace("\n", "").replace("\t", "")
                      .trim().replaceAll("\\s+", " ").split(" "));                   
        
        // String com a maior dependência acumulada.
        String aux = String.format("\n\tDistância(Depêndencia Acumulada) "+
                                   "do Maior Caminho: %s .",
                                   distanciaMaiorCaminho(g, comeco, fim));

        // String com as informações.
        String oMaiorCaminhoInformacoes = 
            String.format(
            "\n\tO Caminho de Maior Dependência Acumulada"+
            "\n\tentre o Indivíduo %s \n\te o Indivíduo %s: \n\n"+
            "\t(Indivíduos do Caminho):\n\n\tInício:\n\n\t%s\n\n\tFim.\n\t%s"+
            "\n\n\t(Fim: Maior Dependência acumulada)\n\n",
                           comeco, fim, op, aux);
        
        System.out.println(oMaiorCaminhoInformacoes);
        
    }
    
}
