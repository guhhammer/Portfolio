package BUSCAS;

import static BUSCAS.Busca_Largura.buscaEmLargura;
import static BUSCAS.Busca_Profundidade.buscaEmProfundidade;
import static BUSCAS.Busca_Largura.printCaminhoLargura;
import static BUSCAS.Busca_Profundidade.printCaminhoProfundidade;
import java.util.ArrayList;

public class Main {
    
    
    public static void main(String[] args) {
        
        String[] aux = {"a","b","c","d","e","f","g","h","i","j","k","l","m"};
        
        Grafo g = new Grafo(aux);
        
        g.matriz.lista[g.findVertice("a")].inserir("e", 1);
        g.matriz.lista[g.findVertice("a")].inserir("f", 1);
        
        g.matriz.lista[g.findVertice("b")].inserir("c", 1);
        g.matriz.lista[g.findVertice("b")].inserir("d", 1);
        
        g.matriz.lista[g.findVertice("c")].inserir("b", 1);
        g.matriz.lista[g.findVertice("c")].inserir("d", 1);
        
        g.matriz.lista[g.findVertice("d")].inserir("b", 1);
        g.matriz.lista[g.findVertice("d")].inserir("c", 1);
        g.matriz.lista[g.findVertice("d")].inserir("e", 1);
        g.matriz.lista[g.findVertice("d")].inserir("i", 1);
        
        g.matriz.lista[g.findVertice("e")].inserir("a", 1);
        g.matriz.lista[g.findVertice("e")].inserir("d", 1);
        g.matriz.lista[g.findVertice("e")].inserir("f", 1);
        g.matriz.lista[g.findVertice("e")].inserir("i", 1);
        
        g.matriz.lista[g.findVertice("f")].inserir("a", 1);
        g.matriz.lista[g.findVertice("f")].inserir("e", 1);
        g.matriz.lista[g.findVertice("f")].inserir("i", 1);
        
        g.matriz.lista[g.findVertice("g")].inserir("h", 1);
        g.matriz.lista[g.findVertice("g")].inserir("i", 1);
        
        g.matriz.lista[g.findVertice("h")].inserir("g", 1);
        g.matriz.lista[g.findVertice("h")].inserir("i", 1);
        g.matriz.lista[g.findVertice("h")].inserir("l", 1);
        
        g.matriz.lista[g.findVertice("i")].inserir("d", 1);
        g.matriz.lista[g.findVertice("i")].inserir("e", 1);
        g.matriz.lista[g.findVertice("i")].inserir("f", 1);
        g.matriz.lista[g.findVertice("i")].inserir("g", 1);
        g.matriz.lista[g.findVertice("i")].inserir("h", 1);
        g.matriz.lista[g.findVertice("i")].inserir("j", 1);
        g.matriz.lista[g.findVertice("i")].inserir("k", 1);
        g.matriz.lista[g.findVertice("i")].inserir("m", 1);
        
        g.matriz.lista[g.findVertice("j")].inserir("i", 1);
        g.matriz.lista[g.findVertice("j")].inserir("m", 1);
        
        g.matriz.lista[g.findVertice("k")].inserir("i", 1);
        g.matriz.lista[g.findVertice("k")].inserir("l", 1);
        g.matriz.lista[g.findVertice("k")].inserir("m", 1);
        
        g.matriz.lista[g.findVertice("l")].inserir("h", 1);
        g.matriz.lista[g.findVertice("l")].inserir("k", 1);
        
        g.matriz.lista[g.findVertice("m")].inserir("i", 1);
        g.matriz.lista[g.findVertice("m")].inserir("j", 1);
        g.matriz.lista[g.findVertice("m")].inserir("k", 1);
        
        
        ArrayList<String> caminho = buscaEmLargura(g, "a", "l");
        ArrayList<String> caminhoP = buscaEmProfundidade(g, "a", "l");
        printCaminhoLargura(caminho, "formatoArray");
        printCaminhoProfundidade(caminhoP, "formatoArray");
       
    }
    
}
