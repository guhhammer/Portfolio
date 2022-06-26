

package sird;

import dicionario.Estatistica;
import dicionario.Dicionario;
import indexador.Indexador;
import recuperador.Recuperador;

import static java.lang.System.out;

import java.util.Arrays;

public class Sird {

    
    public static void TF (Indexador idx){
        int[][] TF = idx.getTF(); 
        System.out.println(" TF: \n");
        idx.mostrarMatriz(TF);
    }
    
    public static void quantia_docs(Dicionario x){
        System.out.println("\n\n Núm. de letras presentes nos documentos: "+ 
                          (x.getContador()-1));
    }
    
    public static void DF(Indexador idx){
        double[] DF = idx.getDF();
        System.out.print(" MATRIZ DF: \n");
        idx.VetorMostrar(DF);   
    }
    
    
    
    public static void main(String[] args) {

        
        String[] bd = {"doc1.txt","doc2.txt","doc3.txt", "doc4.txt", "doc5.txt", "doc6.txt"};
        // string[] dos arquivos utilizados.
        
        String pasta = "C:\\Users\\Gustavo\\Documents\\NetBeansProjects\\sird\\src\\doc\\";
        String separador = "";
        // caminho onde estão salvos os documentos.
        
        
        Dicionario vocabu = new Dicionario();  

        
        System.out.println("\n");
        vocabu.montar(separador, bd, pasta);
        vocabu.mostrar();                           //mostra palavras dicionário. 


        Indexador idx = new Indexador(vocabu);
        idx.indexa(pasta, bd, separador);          // usado para medir freq. terms

        
        
        System.out.println("\n");
        TF(idx);                       //Taxa de frequência dos termos nos docs.      

        System.out.println("\n");
        quantia_docs(vocabu);                  //número de letras no dicionário.

        System.out.println("\n");
        DF(idx);                         //frequência dos termos nos documentos.


        System.out.println("\n");
        double[] IDF = idx.getIDF();
        System.out.print(" MATRIZ IDF : \n");
        idx.VetorMostrar(IDF);            
        //frequência inversa dos termos nos documentos


        System.out.println("\n");
        double[][] TFIDF = idx.getTFIDF();
        System.out.print(" MATRIZ TFIDF : \n");
        idx.mostrarMatriz(TFIDF);   
        // matriz de frequência de termos em todos os docs

        
        String consulta = "xxxx";
        try{
        //String consulta = "aa aa ee ff bb ff ee bb";

        Recuperador r = new Recuperador(separador, vocabu, IDF);
        double[] ListFreqInv = r.tfidf(consulta);
        
        
       
        System.out.println("\n");
        System.out.print("TFIDF(Termos pesquisados =  (" + consulta+")");
        r.mostrarVetor(ListFreqInv);


        System.out.println("\n");
        System.out.println("Semelhança entre os doucmentos: ");

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 1; col++) {
                System.out.println("Documento " + 
                                   (row + 1) + ": " + 
                                (Estatistica.semelhança(TFIDF[row], ListFreqInv))+"% ;");
            }
        }

    } catch(Exception e){
            System.out.println("\n \n"+
    "Termo pesquisado não se encontra em nenhum dos arquivos procurados !"+"\n"
            +"Termo procurado: "+consulta+"\n \n");
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 1; col++) {
                System.out.println("Documento " + 
                                   (row + 1) + ": " + 
                             (Estatistica.semelhança_erro(TFIDF[row]))+"% ;");
            }
        }
            
    }
        
    
    
    
    }
            
            
}

