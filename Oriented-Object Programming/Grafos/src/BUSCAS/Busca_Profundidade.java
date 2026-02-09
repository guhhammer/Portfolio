
package BUSCAS;

import BUSCAS.MEsparsa.Lista;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Busca_Profundidade{
    
    // NOME: Gustavo Hammerschmidt.
   
    // Uso: armazena o caminho do email começo até o email fim.
    private static ArrayList<String> caminho = new ArrayList<>();
    
    // Uso: faz busca em profundidade do email comeco(raiz) até o email fim.
    private static boolean busca(Grafo g, boolean[] visitados, String raiz, 
                                                                String fim){
        
        visitados[g.findVertice(raiz)] = true; // define raiz como visitado.
        caminho.add(raiz);      // adiciona raiz ao caminho.
        Lista aux = g.matriz.lista[g.findVertice(raiz)]; 
                      // pega todas as adjacências da raiz.
        
        MEsparsa.Knot auxKnot = aux.primeiro; 
                                // pega primeira adjacência da lista aux.
        
        // Se auxKnot não alcançar o email fim, retorna false.
        if(auxKnot == null){ return false; }
        
        // faz chamada recursiva para todos os elementos da lista aux.
        
        while(auxKnot != null){
            ArrayList<String> ordem = new ArrayList<>();
            if(fim.equals(auxKnot.email)){ ordem.add(fim); break; }
            Collections.sort(ordem);
            for(String a : ordem){ caminho.add(a);}
                // se o auxKnot for igual ao fim, então adiciona fim à lista.
            
            if(!visitados[g.findVertice(auxKnot.email)]){
                busca(g, visitados, auxKnot.email, fim);
            } // se auxKnot ainda não pertencer à lista dos emails visitados.
            
            auxKnot = auxKnot.proximo; // pega próximo auxKnot.
        }
        
        return true; // se o email fim for encontrado.
        
    }
    
    /* Uso: cria vetor dos email visitados, faz a chamada da função busca, 
            e retorna ArrayList do caminho do email começo(raiz) até
            o email fim.                                               */
    private static ArrayList fazBusca(Grafo g, String comeco, String fim){
        
        // cria array dos emails que são visitados.
        boolean[] visitados = new boolean[g.getTamanho()];
        
        boolean aux = busca(g, visitados, comeco, fim);  // chama função busca.
        
        return (aux == true) ? caminho : null; // retorna caminho.
        
    }
    
    /* Uso: função pública que retorna a busca em profundidade do email começo
            até o email fim de um grafo g.                                  */
    public static ArrayList buscaEmProfundidade(Grafo g, String comeco, String fim){
        
        return fazBusca(g, comeco, fim); // retorna ArrayList da busca.
     
    }
    
    // Uso: função para editar o formato de output do ArrayList percurso.
    public static void printCaminhoProfundidade(ArrayList<String> caminho,
                                                              String opcao){
        
        if(caminho == null){
            System.out.println("\nCaminho não foi encontrado.\n\n");
        }
        else{
            if(opcao.equals("formatado")){
                
                String retorno = "\n\n\t", ultimoEmail = "";
                int contador = -1;
                for(String s : caminho){
                    ultimoEmail = s;
                    contador++;
                    if(contador % 3 == 2){ retorno += "\n\t";}

                    retorno += s+"   ";
                }// adiciona elemento s a retorno e quebra a linha a cada 
                 // dez elementos.

                System.out.println(String.format(
                                   "\n\t(BUSCA EM PROFUNDIDADE)"+
                               "\n\tComeço: %s\n\tFim: %s\n\tCaminho:\n%s\n\n"+
                                   "\n\t(FIM: BUSCA EM PROFUNDIDADE.)\n\n", 
                                    caminho.get(0), ultimoEmail, retorno));
            }
            else if(opcao.equals("formatoArray")){
                System.out.println("Busca em Profundidade: "+
                                   Arrays.toString(caminho.toArray()));
            }
        }
    
    }
    
}
