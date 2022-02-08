
package Projeto_Bimestral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Busca_Largura{
    
    // NOME: Gustavo Hammerschmidt.
    
    // Uso: armazena o caminho do email começo até o email fim.
    private static ArrayList<String> percurso = new ArrayList<>();
   
    // Uso: administra a busca em largura e salva os emails em percurso.
    private static Queue<String> possibilidades = new LinkedList<>();
    
    // Uso: faz busca em largura do email começo até o email fim.
    private static boolean busca(Grafo g, String fim){  
                             // Se nenhum email for passível de ser atingido.
        if(possibilidades.peek() == null){ return false;} 
       
        // Se o percurso já não conter o email atual da fila, faça:
        if(!percurso.contains(possibilidades.peek())){
            
            percurso.add(possibilidades.peek()); // adiciona a cabeça da fila.
            
            // pega todos as arestas desta cabeça.
            MEsparsa.Knot aux = g.matriz.lista[
                                 g.findVertice(possibilidades.peek())].primeiro;
            
            // as adciona, uma a uma, ao fim da fila.
            while(aux != null){ 
                possibilidades.offer(aux.email);
                aux = aux.proximo;
            }
            
        }
        
        possibilidades.poll(); // remove a cabeça da lista.
        
        /* Condição de parada: faz recursão enquanto o elemento atual da 
           cabeça da fila Queue for diferente da String fim. Caso seja igual à
           String fim, vai para a condição else e ela adcionará a String fim ao
           ArrayList, terminando a busca em largura.                         */ 
        if(!fim.equals(possibilidades.peek())){ busca(g, fim); } 
        else{ percurso.add(fim);}
        
        return true;
        
    } 
    
    /* Uso: faz a chamada da função busca, inicializa a lista Queue 
            possibilidades e retorna ArrayList do percurso do email
            começo(raiz) até o email fim.                          */
    private static ArrayList fazBusca(Grafo g, String raiz, String fim){
                    
        // inicializa lista de possibilidades com o email inicial.
        possibilidades.add(raiz); 
        
        boolean aux = busca(g, fim);  // chama função busca.
        
        return (aux == true) ? percurso : null; // retorna percurso.
    
    }
    
    /* Uso: função pública que retorna a busca em largura do email começo
            até o email fim de um grafo g.                              */
    public static ArrayList buscaEmLargura(Grafo g, String comeco, String fim){
        
        return fazBusca(g, comeco, fim); // retorna ArrayList da busca.
        
    }
    
    // Uso: função para editar o formato de output do ArrayList percurso.
    public static void printCaminhoLargura(ArrayList<String> percurso, 
                                                         String opcao){
        
        if(percurso == null){
            System.out.println("\nCaminho não foi encontrado.\n\n");
        }
        else{
            if(opcao.equals("formatado")){
                
                String retorno = "\n\n\t", ultimoEmail = "";
                int contador = -1;
                for(String s : percurso){
                    ultimoEmail = s;
                    contador++;
                    if(contador % 3 == 2){ retorno += "\n\t";}

                    retorno += s+"   ";
                }// adiciona elemento s a retorno e quebra a linha a cada 
                 // dez elementos.

                System.out.println(String.format(
                                   "\n\t(BUSCA EM LARGURA)"+
                               "\n\tComeço: %s\n\tFim: %s\n\tCaminho:\n%s\n\n"+
                                   "\n\t(FIM: BUSCA EM LARGURA.)\n\n", 
                                    percurso.get(0), ultimoEmail, retorno));
        
            }
            else if(opcao.equals("formatoArray")){
                System.out.println("Busca em Largura: "+
                                    Arrays.toString(percurso.toArray()));
            }
        }
    
    }    
    
}
