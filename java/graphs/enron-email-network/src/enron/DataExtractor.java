
package enron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class DataExtractor{
    
    // author: Gustavo Hammerschmidt.
    
    /*  sender + recipients of every file read, cached so files are read once. */
    private static ArrayList<String[]> savedNameLists = new ArrayList<>();
    
    /* the _sent_mail folders found.*/
    private static ArrayList<String> savedFolders = new ArrayList<>();
    
    
    // strips punctuation from an address.
    private static String sanitize(String s){
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
    
    // the first element is the sender, the rest are recipients.
    // reads sender and recipients from one mail file.
    private static String[] readNames(String path) throws FileNotFoundException{
        
        
        Scanner scText = new Scanner(new File(path));
        
        
        String emails[] = null, sender="", line;
        boolean from = true, to = true; // stop once found.
        while(scText.hasNextLine()){
            
            line = scText.nextLine();
            
            if(from == true){
                
                if(line.contains("X-From:")){
                    sender = new StringBuilder(line).replace(0,8,"").toString();
                    scText.reset();
                    from = false;
                }   

                if(line.contains("From:")){
                    sender = new StringBuilder(line).replace(0,6,"").toString();
                    scText.reset();
                    from = false;
                }
                    // drop the header name.
            }
          
            if(from == false && to == true){ 
                // X-To is checked before To.
                if(line.contains("X-To:")){
                    emails = new StringBuilder(line).replace(0,6,"").toString().split(",");
                    to = false;
                    break;
                }   
                
                if(line.contains("To:")){
                    emails = new StringBuilder(line).replace(0,4,"").toString().split(",");
                    to = false;
                    break;
                }
            
            }
              
        }
        
        String[] aux = new String[emails.length+1];
        
        aux[0] = sanitize(sender); 
        
        for(int i = 0; i< emails.length; i++){
            aux[i+1] = sanitize(emails[i]);
        }
        savedNameLists.add(aux);
        return aux;
        
    }  
    
    // collects every user's _sent_mail folder.
    private static void collectFolders(String path){
        
        // one folder per person, e.g. carson-m.
        File[] directories = new File(path).listFiles(File::isDirectory);
        for (File subdir : directories){ 
            
                                                    // only this folder.
            String folderPath = new File(subdir, "_sent_mail").getPath(); 
            File file = new File(folderPath); 
            if(file.exists()){ savedFolders.add(file.getPath()); }
            
        }
        
    }
    
    
    // reads every file and collects the addresses.
    private static ArrayList<String> collectEmails() throws FileNotFoundException{
    
        ArrayList<String> emailList = new ArrayList<>(); 
       
        System.out.println("Reading files: \n\n");
        int fileCount = 0;
        for(String folder : savedFolders){
            
            File[] files = new File(folder).listFiles();
            for(File arq : files){
                for(String email : readNames(arq.getPath())){ 
                    emailList.add(email);
                }
                fileCount++;
            }
            System.out.println("Files read: "+fileCount+". ");
            
        }
        System.out.println(String.format(
                          "\nTotal files read: %s.\n",fileCount));
        return emailList;
        
        
        
    }
    
    // builds the graph with one vertex per distinct address.
    private static Graph buildGraph() throws FileNotFoundException{
        
        ArrayList<String> emailList = collectEmails();
        
        // distinct addresses, in order of first appearance.
        List noRepeat = new ArrayList<>(emailList).stream().distinct().collect(Collectors.toList());
        
        return new Graph((String[]) noRepeat.stream().toArray(String[]::new));
       
    }
    
    // adds one weighted edge per message (sender -> recipient).
    private static void insertEdges(Graph g) throws FileNotFoundException{
        
        // progress.
        int total = savedNameLists.size(), counter = 0;
        
        
        ArrayList percentValues = new ArrayList<>();
        
        System.out.println("Building edges: \n\n");
        
        for(int v = 0; v < g.getSize(); v++){
                
            for(String[] emails : savedNameLists){
                if(g.vertices[v].getName().equals(emails[0])){ // the sender.
                    
                    for(int i = 1; i < emails.length; i++){
                        g.matrix.lists[v].insert(emails[i], 1);
                    }  // recipient, weight 1 per message.
                    
                    counter++; 
                    
                    
                    int percentage = (int)((counter*1.0/total)*100);
                    
                    // report every 5%.
                    String percent = ( percentage % 5 == 0) 
                                     ? String.format("Built: %d%s.\n",
                                         percentage,"%")
                                     : "";
                    
                    
                    String decide = (!percentValues.contains(percentage)) 
                                    ? percent : "";
                    
                    
                    percentValues.add((!percentValues.contains(percentage))
                                       ? percentage : null);
                    
                    
                    System.out.print(decide);
                    
                }     
                
            }
        
        }
        
        System.out.println("\n\nEdges 100% built.\n\n");
        
        
    }
    
    // builds the email graph from the maildir at path.
    public static Graph extract(String path) throws FileNotFoundException{
        
        collectFolders(path);
        
        Graph aux = buildGraph(); 
        
        insertEdges(aux); 
     
        System.out.println("Data extracted.\nGraph built.\n\n");
        return aux;  
    
    }
    
    
}
