
package SIRD;

import Dicionario.Dicionario;
import indexador.Indexador;
import static java.lang.System.out;
import Recuperador.Recuperador;

public class SIRD {
    
    public static void main(String[] args) {

        String [] bd = {"doc1.txt","doc2.txt","doc3.txt",
                        "doc4.txt","doc5.txt","doc6.txt"};
        
        String pasta = "C:\\Users\\PROJETO\\Documents\\SIRD\\src\\doc\\";
        String separador = " ";
        
        Dicionario dico = new Dicionario();
        dico.montar(pasta, bd, separador);
        dico.mostrar();
        
        Indexador idx = new Indexador(dico);
        idx.indexa(pasta, bd, separador);  
        
        int [][] TF = idx.getTF();
        out.println("\nTF");
        idx.mostrarMatriz(TF);
        double [] DF = idx.getDF();
        out.print("\n\nDF");
        idx.mostrarVetor(DF);
        double [] IDF = idx.getIDF();
        out.print("\n\nIDF");
        idx.mostrarVetor(IDF);
        double [][] TFIDF = idx.getTFIDF();
        out.print("\n\nTFIDF");
        idx.mostrarMatriz(TFIDF);
        
        // continuar com as funcionalidades para 
        // encontrar o documento foco
        String consulta = "aa cc";
        
        Recuperador r = new Recuperador(separador, dico, IDF);
        double [] cTFIDF = r.tfidf(consulta);
        out.print("\n\nTFIDF da consulta");
        r.mostrarVetor(cTFIDF);  
        
    }
}