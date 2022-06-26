
package Projeto_Bimestral;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class ExtrairDados{
    
    // NOME: Gustavo Hammerschmidt.
    
    /*  ArrayList que contém aqueles que enviaram e os que receberam. 
        Motivo: economia de recursos, aumento de velocidade da aplicação. */
    private static ArrayList<String[]> getNamesEmailsSalvos = new ArrayList<>();
    
    /* Arraylist que contém todos os endereços existentes e que podem ser 
    utilizados na extração de emails. Motivo: economia de tempo da aplicação.*/
    private static ArrayList<String> getEnderecosSalvos = new ArrayList<>();
    
    
    // Uso: padronizar todos os emails, retirando caractéres não importantes.
    private static String formatar(String s){
        s = s.replace(",", "");
        s = s.replace(";", "");
        s = s.replace("\"", "");
        s = s.replace("?", "");
        s = s.replace(":", "");
        s = s.replace("!", "");
        s = s.replace("(", "");
        s = s.replace(")", "");
        s = s.replace(">", "");
        s = s.replace("<", "");
        s = s.replace("=", "");
        
        return s.trim();
    }
    
    // Primeiro elemento é sempre quem envia e os demais quem recebe.
    // Uso: pega os nomes de quem envia e quem recebe de arquivos.
    private static String[] getNames(String path) throws FileNotFoundException{
        
        // Scanner de arquivo;
        Scanner scText = new Scanner(new File(path));
        
        //sender -> quem envia, holder -> segura linha do arquivo atual.
        String emails[] = null, sender="", holder;
        boolean from = true, to = true; // flags para localização, e otimização.
        while(scText.hasNextLine()){
            
            holder = scText.nextLine();
            
            if(from == true){
                
                if(holder.contains("X-From:")){
                    sender = new StringBuilder(holder).replace(0,8,"").toString();
                    scText.reset();
                    from = false;
                }   // flag evita que haja repetições da variável sender.

                if(holder.contains("From:")){
                    sender = new StringBuilder(holder).replace(0,6,"").toString();
                    scText.reset();
                    from = false;
                }
                    // StringBuilder remove o começo da linha holder.
            }
          
            if(from == false && to == true){ 
                // A ordem foi entre X-To e To foi escolhida para evitar erros.
                if(holder.contains("X-To:")){
                    emails = new StringBuilder(holder).replace(0,6,"").toString().split(",");
                    to = false;
                    break;
                }   
                // To também está contido em X-To. Por isso, ela é avaliada primeiramente.
                if(holder.contains("To:")){
                    emails = new StringBuilder(holder).replace(0,4,"").toString().split(",");
                    to = false;
                    break;
                }
            
            }
              
        }
        
        String[] aux = new String[emails.length+1];
        
        aux[0] = formatar(sender); // adiciona todos os emails ao vetor aux.
        
        for(int i = 0; i< emails.length; i++){
            aux[i+1] = formatar(emails[i]);
        }
        getNamesEmailsSalvos.add(aux);
        return aux;
        
    }  
    
    // Uso: salva todos os endereços que existem e serão utilizados para extração.
    private static void saveEnderecos(String path){
        
        // Lista as pastas das Pessoas, por exemplo, carson-m.
        File[] directories = new File(path).listFiles(File::isDirectory);
        for (File subdir : directories){ // pega pastas de cada usuário.
            
                                                    // somente esta pasta.
            String caminho = subdir.getPath()+"\\_sent_mail"; 
            File file = new File(caminho); // se a pasta existir.
            if(file.exists()){ getEnderecosSalvos.add(file.getPath()); }
            
        }
        
    }
    
    
    // Uso: Constroi os vértices do grafo.
    private static ArrayList<String> construirVertices() throws FileNotFoundException{
    
        ArrayList<String> listaEmails = new ArrayList<>(); // variável de retorno.
       
        System.out.println("Lendo Arquivos: \n\n");
        int contarArquivos = 0;
        for(String endereco : getEnderecosSalvos){
            
            File[] files = new File(endereco).listFiles();
            for(File arq : files){
                for(String email : getNames(arq.getPath())){ 
                    listaEmails.add(email);
                }
                contarArquivos++;
            }
            System.out.println("Arquivos Lidos: "+contarArquivos+". ");
            
        }
        System.out.println(String.format(
                          "\nTotal de Arquivos Lidos: %s.\n",contarArquivos));
        return listaEmails;
        
        
        
    }
    
    // Uso: Constroi os vértices do grafo sem que haja repetições e ordenados pelo nome do email.
    private static Grafo construirGrafo() throws FileNotFoundException{
        
        ArrayList<String> listaEmails = construirVertices();
        
        // Sem repetições de vértices; os coloca, ordenamente, conforme precedência dos emails.
        List noRepeat = new ArrayList<>(listaEmails).stream().distinct().collect(Collectors.toList());
        
        return new Grafo((String[]) noRepeat.stream().toArray(String[]::new));
       
    }
    
    // Uso: constroi adjacências aos vértices de um grafo.
    private static void grafoInserirEmails(Grafo g) throws FileNotFoundException{
        
        // variáveis para indicar percentual de conclusão.
        int total = getNamesEmailsSalvos.size(), contador = 0;
        
        // ArrayList para salvar a porcentagem concluída.
        ArrayList percentValues = new ArrayList<>();
        
        System.out.println("Construindo Adjacências: \n\n");
        
        for(int v = 0; v < g.getTamanho(); v++){
                // pega cada vetor para inserir as adjacências.
            for(String[] emails : getNamesEmailsSalvos){
                if(g.vertices[v].getNome().equals(emails[0])){ // quem enviou.
                    
                    for(int i = 1; i < emails.length; i++){
                        g.matriz.lista[v].inserir(emails[i], 1);
                    }  // email[i] -> quem recebeu; 1 -> o peso da mensagem.
                    
                    contador++; // calcula quantos arquivos foram lidos.
                    
                    // calcula porcentagem
                    int percentage = (int)((contador*1.0/total)*100);
                    
                    // salva valor na String percent a cada 5%.
                    String percent = ( percentage % 5 == 0) 
                                     ? String.format("Construído: %d%s.\n",
                                         percentage,"%")
                                     : "";
                    
                    // checa se o valor já foi impresso.
                    String decide = (!percentValues.contains(percentage)) 
                                    ? percent : "";
                    
                    // adiciona valor a ArrayList.
                    percentValues.add((!percentValues.contains(percentage))
                                       ? percentage : null);
                    
                    // Print da porcentagem.
                    System.out.print(decide);
                    
                }     
                
            }
        
        }
        
        System.out.println("\n\nAdjacências 100% Construídas.\n\n");
        
        
    }
    
    // Uso: extrai os dados de um arquivo path.
    public static Grafo extrairDados(String path) throws FileNotFoundException{
        
        saveEnderecos(path);
        
        Grafo aux = construirGrafo(); // Constroi grafo.
        
        grafoInserirEmails(aux); // constroi adjacências dos vértices do grafo.
     
        System.out.println("Arquivo foi Extraído.\nGrafo foi Construído.\n\n");
        return aux;  // retorna grafo.
    
    }
    
    
}
