
package Projeto_Bimestral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class encontrarKnotsAtD{
    
    // NOME: Gustavo Hammerschmidt.
    
    // ArrayList que salva os emails percorridos.
    private static ArrayList<String> percurso = new ArrayList<>();
   
    // ArrayList que salva os emails a uma distância d.
    private static ArrayList<String> distanciaD = new ArrayList<>();
    
    // Fila que armazena emails a serem adcionados nos ArrayLists.
    private static Queue<String> possibilidades = new LinkedList<>();
    
    // Uso: faz a busca em largura de emails em um grafo g.
    private static boolean busca(Grafo g){  
        
        // Se lista queue estiver vazia.   
        if(possibilidades.peek() == null){ return false;} 
       
        // Se email .peek() não pertencer ao percurso.
        if(!percurso.contains(possibilidades.peek())){
            
            percurso.add(possibilidades.peek()); // adiciona cabela da fila.

            MEsparsa.Knot aux = g.matriz.lista[
                                 g.findVertice(possibilidades.peek())].primeiro;
                                 // pega lista da cabeça da fila.
            
            while(aux != null){ 
                possibilidades.offer(aux.email);
                aux = aux.proximo;
            }   // adiciona a lista da cabeça da fila ao fim da fila.
            
        }
        
        possibilidades.poll(); // remove cabeça da fila.
        
        return true;  // se a lista não estiver vazia.
        
    } 
    
    // Uso: salva os nós à distância d no vetor distanciaD.
    private static boolean setDistanciaD(Grafo g, int contador, int D){
                
        boolean status = true, setOk = true; // flags de condição de parada.
        while(contador < D){ 
            status = busca(g);
            if(status == false){ setOk = false; break;}
            contador++; 
        }   // enquanto contador menor que a distância.
           
        
        String aux = ""; // String auxiliar para adicionar emails.
        while(aux != null && setOk){
            distanciaD.add(possibilidades.peek());
            aux = possibilidades.poll();
        }
                                  // remove última String que é null.
        distanciaD.remove(distanciaD.size()-1);
        
        return (setOk); // se os valores forem salvos em distanciaD. 
    
    }
    
    // Uso: função que se comunica com as de cima e define ordem de execução.
    private static ArrayList encontrarDs(Grafo g, String raiz, int D){
                    
        possibilidades.add(raiz); // adiciona String de partida: raiz.
        
        int contador = 0; // começa o contador em 0.
        boolean aux = setDistanciaD(g, contador, D);  
                                // define valores dos vetores distanciaD.
        
        return (aux == true) ? distanciaD : null; // retorna ArrayList distanciaD.
    
    }
    
    // Uso: função que retorna os emails a uma distância d de email comeco.
    public static ArrayList encontrarNosDistanciaD(Grafo g, String comeco, int D){
        
        return encontrarDs(g, comeco, D); // retorna ArrayList.
        
    }
   
    // Uso: função que imprime o Arraylist das distancias D.
    public static void printNosDistanciaD(ArrayList<String> distanciaD, 
                                                         String opcao){
        
        if(distanciaD.isEmpty()){   // se lista estiver vazia.
            System.out.println("\nNenhum nó neste ArrayList foi encontrado.\n\n");
        }
        else{
            if(opcao.equals("formatado")){
               
                String retorno = "\n\n\t";
                int contador = -1;
                for(String s : distanciaD){
                    contador++;
                    if(contador % 3 == 2){ retorno += "\n\t";}

                    retorno += s+"   ";
                }// adiciona elemento s a retorno e quebra a linha a cada 
                 // dez elementos.

                System.out.println(String.format(
                "\n\t(Encontrar Nós à Distância D)\n\tNós:\n%s\n\n\n\t"+
                "(FIM: Encontrar Nós à Distância D.)\n\n", retorno));

            }
            else if(opcao.equals("formatoArray")){
                System.out.println("Busca em Largura: "+
                                    Arrays.toString(distanciaD.toArray()));
            }
        }
    
    }    
    
}
