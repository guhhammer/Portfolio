
package Projeto_Bimestral;

import static Projeto_Bimestral.Busca_Largura.buscaEmLargura;
import static Projeto_Bimestral.Busca_Largura.printCaminhoLargura;
import static Projeto_Bimestral.Busca_Profundidade.buscaEmProfundidade;
import static Projeto_Bimestral.Busca_Profundidade.printCaminhoProfundidade;
import static Projeto_Bimestral.DijkstraReverso.oMaiorCaminho;
import static Projeto_Bimestral.ExtrairDados.extrairDados;
import static Projeto_Bimestral.Informacoes_Grafo.informacoesSobreOGrafo;
import static Projeto_Bimestral.encontrarKnotsAtD.encontrarNosDistanciaD;
import static Projeto_Bimestral.encontrarKnotsAtD.printNosDistanciaD;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Main {
    
    // NOME: Gustavo Hammerschmidt.
    
    // ver problema dos emails nulos.
    
    public static void main(String[] args) throws FileNotFoundException {
        
        System.out.println("\n\n\n\n");
        System.out.println("COMEÇO\n\n");
        
        // A String path deve receber o caminho da base de dados maildir.
        String path = "C:\\Users\\Gustavo\\Desktop\\maildir";
        
        
        // Extração das Informações da Base/Construção do Grafo:
        
        Grafo g = extrairDados(path);
        
        // Extração das Informações da Base/Construção do Grafo.
    
        
        // Informações do Grafo:
        
        informacoesSobreOGrafo(g, 20);
 
        // Informações do Grafo.
        
        
        // Busca em Profundidade e Busca em Largura:
        
        String comeco = g.vertices[0].getNome();
        String fim = g.vertices[3].getNome();
        
        ArrayList<String> bProfundidade = buscaEmProfundidade(g, comeco, fim);
        
        ArrayList<String> bLargura = buscaEmLargura(g, comeco, fim);
        
        
        // Opções: 
        // formatado-> imprime ArrayList conforme programado pelo desenvolvedor.
        // formatoArray-> imprime ArrayList com a função Arrays.toString(...).
        String opcaoProfundidade = "formatado", opcaoLargura="formatado";
        printCaminhoProfundidade(bProfundidade, opcaoProfundidade);
        printCaminhoLargura(bLargura, opcaoLargura);
         
        // Busca em Profundidade e Busca em Largura.
        
        
        // Encontrar Nós à distância D:
        
        String comecoDistanciaD = g.vertices[2].getNome();
        int intDistanciaD = 1;
        
        ArrayList<String> distanciaD = 
                    encontrarNosDistanciaD(g, comecoDistanciaD, intDistanciaD);
        
        
        // Opções: 
        // formatado-> imprime ArrayList conforme programado pelo desenvolvedor.
        // formatoArray-> imprime ArrayList com a função Arrays.toString(...).
        String opcaoDistanciaD = "formatado";
        printNosDistanciaD(distanciaD, opcaoDistanciaD);
        
        // Encontrar Nós à distância D.
        
        
        // Encontrar Maior Distância entre dois Nós(emails):
        
        String comecoDijkstra = g.vertices[0].getNome();
        String fimDijsktra = g.vertices[3].getNome();
        
        
        // Opções: 
        // formatado-> imprime ArrayList conforme programado pelo desenvolvedor.
        // formatoArray-> imprime ArrayList com a função Arrays.toString(...).
        String opcaoDijkstra = "formatado";
        oMaiorCaminho(g, comecoDijkstra, fimDijsktra, opcaoDijkstra);
        
        // Encontrar Maior Distância entre dois Nós(emails).
        
        
        System.out.println("FIM\n");
        
    }

}
