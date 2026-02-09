package dicionario;

import static java.lang.System.out;
import java.util.HashMap;
import java.util.Set;
import diretorio.CSVFile;
import parser.Parser;

public class Dicionario {

    private HashMap<String, Integer> dico;
    private int contador;
    
    public Dicionario() {
        this.dico = new HashMap();
        this.contador = 0;
    }

    public void montar(String separador, String[] arquivos, String pasta) {

        for (String arquivo : arquivos) {
            
            CSVFile.abrirArquivoDeLeitura(pasta, arquivo);           
            String vString = CSVFile.readTexto();
            
            String[] v = new Parser(separador).split(vString);
            
            String aux = null;
            
            for (String x : v) {
                aux = x.trim().toUpperCase();
                if (! dico.containsKey(aux)){
                    dico.put(aux,contador++);
                }
                
            }
        }
    }
    
    public void mostrar(){
        Set<String> chaves = this.dico.keySet();
        
        out.println("Dicionario: ");
        out.println("<Palavra, posicao>");
        for (String ch : chaves){
            out.println("<"+ch + "," + this.dico.get( ch )+">");
        }
    }
    
    public boolean pertence(String palavra){
        return this.dico.containsKey(palavra);
    }
    
    public int posicao(String palavra){
    
        Integer posicao = this.dico.get(palavra);
        if (posicao == null)
            posicao = -1;
        return posicao;
    }    

    public int getContador() {
        return contador;
    }
        
   public Set<String> getPalavras(){
       return this.dico.keySet();
   }
}
