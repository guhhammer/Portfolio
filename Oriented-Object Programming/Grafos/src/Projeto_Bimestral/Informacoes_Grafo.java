
package Projeto_Bimestral;

import java.util.ArrayList;
import java.util.Arrays;

public class Informacoes_Grafo {
    
    // NOME: Gustavo Hammerschmidt.
    
    private String email; // nome do email do indivíduo.
    private int soma;     // soma de envio ou recebimento de emails.
    
    // Construtor Informacoes_Grafo.
    private Informacoes_Grafo(String email, int soma){
        this.email = email; this.soma = soma;
    }
    
    // Uso: pega o email.
    private String getEmail(){ return this.email;}
    
    // Uso: pega a soma.
    private int getSoma(){ return this.soma;}
    
    /* ArrayList para salvar os usuários que mais enviaram emails.*/
    private static ArrayList<Informacoes_Grafo> osQueMaisEnviam = new ArrayList<>();
    
    // Uso: retorna o número de vértices de um grafo g.
    private static int numeroVertices(Grafo g){ return g.getTamanho(); }
    
    // Uso: retorna o número de arestas de um grafo g.
    private static int numeroDeArestas(Grafo g){ return g.getNumeroArestas(); }
    
    
    // GRAU DE SAÍDA ........................................................
    
    // Uso: Adiciona indivíduos que mais enviaram emails ao ArrayList osQueMaisEnviam.
    private static void os20QueMaisEnviam(Grafo g, int quantidade){
        
        for(int i = 0; i < g.getTamanho(); i++){
            
            // Número de mensagens que o indivíduo i mandou.
            int valor = g.matriz.lista[i].contarArestas();
            
            // adiciona individuos até quantidade.
            if(osQueMaisEnviam.size() < quantidade){
                
                Informacoes_Grafo envia = 
                        new Informacoes_Grafo(g.vertices[i].getNome(), valor);
                osQueMaisEnviam.add(envia);
                
            }  
            else{
                                       // caso o ArrayList esteja cheio.
                Informacoes_Grafo envia = 
                        new Informacoes_Grafo(g.vertices[i].getNome(), valor);
                boolean flag = false; 
                for(int j = 0; j < osQueMaisEnviam.size(); j++){
                    
                    if(valor > osQueMaisEnviam.get(j).getSoma()
                                                        && flag == false){
                        osQueMaisEnviam.remove(j);
                        osQueMaisEnviam.add(envia);
                        flag = true;
                    }
                    
                } // filtra e adiciona só os que mais enviaram emails.
                
                
            }
       
        }
        
    }
    
    // Uso: encontra os que mais enviaram emails.
    private static String osQueEnviamMais(Grafo g, int quantidade){
                                // encontra os (quantidade) que mais enviaram.
        os20QueMaisEnviam(g, quantidade);  
        
        int[] valores = new int[quantidade];
        for(int i = 0; i < osQueMaisEnviam.size(); i++){
            valores[i] = osQueMaisEnviam.get(i).getSoma();
        }  // pega os valores do ArrayList.
               
        Arrays.sort(valores);  // ordena os valores.
        
        String adder = "";  // String de retorno.
        
        for(int i = valores.length-1; i >=0; i--){
            String hold = "";//adiciona à String os (quantidade) que mais enviaram.
            boolean flag = true;
            for(int j = 0; j < osQueMaisEnviam.size(); j++){
                if(valores[i] == osQueMaisEnviam.get(j).getSoma() && flag == true){
                    hold = osQueMaisEnviam.get(j).getEmail();
                    osQueMaisEnviam.remove(j);
                    flag = false;
                }
            }
            adder += String.format(
                    "%dº\tEmail: %s \n\tQuantidade de Emails Enviados: %d \n\n", 
                                          (valores.length-i),hold,valores[i]);
            
        }

        return adder;
        
    }
    
    
    // GRAU DE SAÍDA ........................................................
    
    
    // GRAU DE ENTRADA ......................................................
    
    // Uso: Constroi vetor com a quantidade de mensagens que cada um recebeu.
    private static int[] individuos_arr(Grafo g){
        
        int[] individuos = new int[g.getTamanho()]; 
        for(int i = 0; i < individuos.length; i++){
            for(int j = 0; j < g.getTamanho(); j++){
                if(g.matriz.lista[j].existir(g.vertices[i].getNome())){
                    individuos[i] += g.matriz.lista[j].mostraValor(g.vertices[i].getNome());
                }
            }  // Se na lista j existir o email: vertices[i], adiciona o seu 
        }      // valor no vetor indivíduos.
        return individuos;
        
    }
    
    
    // Uso: pega o maior valor de um array.
    private static int maior(int[] arr){
    
        int maiorValor = arr[0], index = 0;
        for(int i = 1; i < arr.length; i++){
            if(maiorValor < arr[i]){ maiorValor = arr[i]; index = i;}
        }
        
        return index;
        
    }
    
    
    // Uso: retorna vetor com os índices dos (quantidade) maiores elementos.
    private static int[] os20QueMaisRecebem(Grafo g, int quantidade){
    
        int[] individuos = individuos_arr(g); // vetor de indivíduos.
        int[] quantidadeIndividuos = new int[quantidade]; // vetor com a quantidade.
        for(int i = 0; i < quantidadeIndividuos.length; i++){
            if("".equals(g.vertices[maior(individuos)].getNome())){
                individuos[maior(individuos)] = 0;
            } // Se não for mensagem sem destinatário.
            
            quantidadeIndividuos[i] = maior(individuos); // adiciona maior índice.
            individuos[maior(individuos)] = 0;   
        }
        
        return quantidadeIndividuos;
     
    }
    
    
    // Uso: encontra os que mais receberam emails.
    private static String osQueRecebemMais(Grafo g, int quantidade){
    
                                // encontra os (quantidade) que mais recebem.
        int[] valores = os20QueMaisRecebem(g, quantidade);  
        
        int[] individuos = individuos_arr(g); // vetor dos indivíduos.
       
        int[] aux = new int[quantidade];   // auxiliar salva os pesos.
        for(int i = 0; i < valores.length; i++){
            aux[i] = individuos[valores[i]];
        }
        
        String adder = "";  // String de retorno.
        for(int i = 0; i < aux.length; i++){
            String hold = "";
            for(int j = 0; j < valores.length; j++){
                if(aux[i] == individuos[valores[j]]){
                    hold = g.vertices[valores[i]].getNome();
                          //adiciona à String os (quantidade) que mais enviaram.
                    individuos[valores[j]] = 0; // evita repetições.
                }
            }
            adder += String.format(
                    "%dº\tEmail: %s\n\tQuantidade de Emails Recebidos: %d \n\n", 
                                (i+1),hold,aux[i]);
        }

        return adder;
        
    }
    
    
    // GRAU DE ENTRADA ......................................................
    
  
    public static void informacoesSobreOGrafo(Grafo g, int quantidade){
        
        System.out.println("\nInformações do Grafo: \n\n");
        
        System.out.println(
                      String.format("Número de Vértices do grafo: %d. \n\n",
                                                          numeroVertices(g)));
    
        System.out.println(
                      String.format("Número de Arestas do grafo: %d. \n\n",
                                                         numeroDeArestas(g)));
        
        System.out.println(
                      String.format("Os %d que mais enviaram emails:\n\n%s", 
                                 quantidade, osQueEnviamMais(g, quantidade)));
    
        System.out.println(
                      String.format("Os %d que mais receberam emails: \n\n%s",
                                quantidade, osQueRecebemMais(g, quantidade)));
        
        System.out.println("(FIM: Informações do Grafo.)\n\n");
        
    }
    
    
}
