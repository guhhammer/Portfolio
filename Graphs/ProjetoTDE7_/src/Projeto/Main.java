package Projeto;

public class Main {
	
    // Main.
    public static void main(String[] args) {
    	
    	
    	
        System.out.println("\n\n\nInício:\n\n");
        
   
        
        
        int vertices = 15; // Tamanho do grafo. 
        boolean direcionado = false;
        
        Grafo inicio = new Grafo(vertices, direcionado); // Criando um grafo. 
        
        // Inserção de Adjacências.
        inicio.criaAdjacencia(0, 1, 2);
        inicio.criaAdjacencia(0, 2, 1);
        inicio.criaAdjacencia(1, 3, 3);
        inicio.criaAdjacencia(1, 4, 2);
        inicio.criaAdjacencia(2, 3, 1);
        inicio.criaAdjacencia(2, 5, 2);
        inicio.criaAdjacencia(3, 4, 4);
        inicio.criaAdjacencia(3, 5, 3);
        inicio.criaAdjacencia(4, 6, 1);
        inicio.criaAdjacencia(5, 6, 3);
        inicio.criaAdjacencia(6, 7, 2);
        inicio.criaAdjacencia(6, 8, 2);
        inicio.criaAdjacencia(7, 8, 1);
        
        inicio.criaAdjacencia(9, 10, 5);
        inicio.criaAdjacencia(10, 11, 1);
        inicio.criaAdjacencia(9, 12, 3);
        inicio.criaAdjacencia(10, 12, 2);
        
        inicio.criaAdjacencia(13, 14, 5);
               
        System.out.print("Conexo: "+inicio.ehConexo()+"\n");
        System.out.print("Euleriano: "+inicio.ehEuleriano()+"\n");
        System.out.print("Cíclico: "+inicio.temCiclo()+"\n\n"); 
      
        inicio.printOnlyComponentes();  
        
        inicio.centralidadeDeIntermediacao();
        
        inicio.centralidadeDeProximidade();
     
        
        
        
   
        System.out.print("===============================================================\n\n");
        
        
        
        
        boolean direcionado_ = true;
        boolean conexo = true;
        
    	Grafo r = new Grafo(8, direcionado_).random(15, conexo);
    	
    	r.imprimeAdjacencias();
    	r.printOnlyComponentes(); 
        
    	System.out.print("Conexo: "+r.ehConexo()+"\n");
    	
    	r.fazerArquivoPajek("r"); 
    	
    	
    	
    	
    	
    	
    	Grafo novo = new Grafo().extrairGrafoDe("r.pajek");
    	
    	novo.imprimeAdjacencias();
    	
    	
    	
    	
    	
    	
    	System.out.print("\n\n===============================================================\n\n");
    	
    	
    	
    	
    	
    	
    	// Este grafo refere-se à última questão do trabalho!
    	boolean direcionado__ = false;
    	Grafo routes = new Grafo(direcionado__).linhasAereas();
    	
    	System.out.println("\n\nRoutes quantidade de vértices: "+routes.quantidadeDeVertices());
    	System.out.println("Routes quantidade de arestas: "+routes.quantidadeDeArestas()+"\n\n");
    	
    	//routes.imprimeAdjacenciasLinhasAereas();  // Imprime as adjacências de routes.
    	
        //System.out.print("Conexo: "+routes.ehConexo()+"\n");     // Stack Overflow error.
        
    	System.out.print("Euleriano: "+routes.ehEuleriano()+"\n");
        
        //System.out.print("Cíclico: "+routes.temCiclo()+"\n\n");  // Stack Overflow error.
    	
        
        
        
        
        /*
         * Siglas de Alguns Aeroportos que estão na base routes.csv:
         * 	
         * 		-- GRU : Guarulhos, São Paulo, Brasil.	
         * 		-- CWB : Curitiba, Paraná, Brasil.
         * 		-- LHR : Heathrow, Londres, Inglaterra.
         * 		-- LAX : Los Angeles, Los Angeles, Califórnia, Estados Unidos.
         * 		-- CDG : Paris, França.
         * 		-- YTZ : Toronto, Canadá.
         * 		-- YVR : Vancouver, Canadá
         * 		-- AEP : Buenos Aires, Argentina.
         * 		-- CGN : Colônia, Alemanha. 
         * 		-- HAM : Hamburgo, Alemanha.
         * 		-- BCN : Barcelona, Espanha.
         * 		-- LIS : Lisboa, Portugal.
         * 		-- MEL : Melbourne, Austrália.
         * 		-- FUK : Fukuoka, Japão.
         * 		-- HIJ : Hiroshima, Japão.
         * 		-- NRT : Tokyo, Japão.
         * 
         * */
        
        
        
        
        
        String partida = "LAX",
        	   conexoes[] = new String[] {"LAS","SAN"},
        	   conexoes2[] = new String[] {"LAS","PHX","MEX"},
        	   destino = "NRT";
        
        int distancia = 5,
        	quantidade = 20; // quantidade -> máximo de pontes aéreas a mostrar.
        
        
        System.out.println("\n\nAtenção: Próxima etapa da execução pode levar de 10 até"
        				 + "\n90 segundos dependendo da ponte aérea escolhida!!\n\n");

        try{ Thread.sleep(5000); }catch(Exception e) { e.getSuppressed(); }
        
        // INFO:  (P =>) -> Partida; e ( => D) -> Destino. Indicam começo e fim da ponte aérea.
        
        routes.ponte_aerea_distancia(partida, destino, distancia, quantidade);
        
        routes.ponte_aerea_distancia_e_conexoes(partida, conexoes, destino, distancia, (int) quantidade/conexoes.length);
        
        routes.ponte_aerea_distancia_e_conexoes(partida, conexoes2, destino, distancia, (int) quantidade/conexoes2.length);
        
        System.out.println("\n\n\nFim.\n"); 
        
        
        
        
        Grafo g = new Grafo(7,false);
        
        
        g.criaAdjacencia(0, 3, 1);
        g.criaAdjacencia(1, 3, 1);
        g.criaAdjacencia(1, 2, 1);
        
        g.criaAdjacencia(3, 4, 1);
        g.criaAdjacencia(3, 5, 1);
        g.criaAdjacencia(3, 6, 1);
        
        g.criaAdjacencia(4, 5, 1);
        
        
        g.centralidadeDeIntermediacao();
        
        g.centralidadeDeProximidade();
               
        
        System.out.println(g.ehConexo()+" "+g.ehEuleriano()+" "+g.temCiclo());
        
             
        
    }
    
}
