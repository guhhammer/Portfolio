import java.util.ArrayList;

public class BHeuristica {

    private static ArrayList<Estado> visited;
    private static boolean pass = false;


    // Valor de referência passado. Delta(x -> [...] -> Fim).
    // Função estimativa de distância entre a e b(b == fim).
    public static double h(Estado x, Estado fim){

        double delta_x = ( x.getLatitude() - fim.getLatitude() ),
               delta_y = ( x.getLongitude() - fim.getLongitude() );

        double delta_z = Math.sqrt(delta_x * delta_x + delta_y * delta_y);

        return delta_z;

    }

    // Valor de referência passado. Delta(Start -> [...] -> x).
    // Função estimativa de distância entre a e b(a == inicio).
    public static double g(Estado x, Estado inicio){  return h(x, inicio);  }


    // Valor h(n) para busca gulosa.
    private static Estado least_distant(ArrayList<Estado> estados, Estado goal){

        double lowest = Double.MAX_VALUE;

        Estado hold = null;

        for(int i = 0; i < estados.size(); i++){

            double h_i = h(estados.get(i), goal);

            if(!visited.contains(estados.get(i)) && (h_i < lowest)){
                lowest = h_i;
                hold = estados.get(i);
            }

        }

        return hold;

    }


    // Função Auxiliar.
    private static double average(double a, double b){  return (a+b)/2; }


    // Valor de h(n) e g(n) |- f(n) = g(n) + h(n) |- função estrela.
    private static Estado least_distant_g_h(ArrayList<Estado> estados, Estado inicio, Estado fim){

        double lowest_h = Double.MAX_VALUE, lowest_g = Double.MAX_VALUE;
        Estado hold = null;

        for(int i = 0; i < estados.size(); i++){

            double h_i = h(estados.get(i), fim), g_i = g(estados.get(i), inicio);

            if(!visited.contains(estados.get(i)) && (average(g_i, h_i) < average(lowest_g, lowest_h))){

                lowest_g = g_i;
                lowest_h = h_i;
                hold = estados.get(i);

            }

        }

        return hold;

    }


    // Busca Gulosa.
    private static void busca(Grafo g, String estadoInicio, String estadoFim){

        ArrayList<Estado> estados = g.getEstado(estadoInicio).getVizinhos();

        for(int i = 0; i < estados.size(); i++){

            Estado estado = least_distant(estados, g.getEstado(estadoFim));

            if (pass || estado == null) { break; }

            if (estado.getNome().equals(estadoFim)){

                visited.add(estado);

                pass = true;

            }
            else if(!visited.contains(estado)){

                visited.add(estado);

                busca(g, estado.getNome(), estadoFim);

            }

        }

    }


    // Busca A*.
    private static void busca_a(Grafo g, String estadoInicio, String estadoFim){

        ArrayList<Estado> estados = g.getEstado(estadoInicio).getVizinhos();

        for(int i = 0; i < estados.size(); i++){

            Estado estado = least_distant_g_h(estados, g.getEstado(estadoInicio), g.getEstado(estadoFim));

            if (pass || estado == null) { break; }

            if (estado.getNome().equals(estadoFim)){

                visited.add(estado);

                pass = true;

            }
            else if(!visited.contains(estado)){

                visited.add(estado);

                busca_a(g, estado.getNome(), estadoFim);

            }

        }

    }


    // Busca A* iterativo.
    private static void busca_a_i(Grafo g, String estadoInicio, String estadoFim, int profundidade, int counter){

        ArrayList<Estado> estados = g.getEstado(estadoInicio).getVizinhos();

        if (profundidade == 0){ pass = true; }

        for(int i = 0; i < estados.size(); i++){

            Estado estado = least_distant_g_h(estados, g.getEstado(estadoInicio), g.getEstado(estadoFim));

            if (pass || estado == null){ break; }

            if (estado.getNome().equals(estadoFim)){
                visited.add(estado);
                pass = true;
            }
            else if(counter+1 == profundidade){
                if(!visited.contains(estado)){  visited.add(estado); }
                continue;
            }
            else if(!visited.contains(estado)){
                visited.add(estado);
                busca_a_i(g, estado.getNome(), estadoFim, profundidade, counter+1);
            }
            else{
                busca_a_i(g, estado.getNome(), estadoFim, profundidade, counter+1);
            }
        }
    }


    // Retorna visitados como string.
    private static String stringfy(){

        String ret = " ";
        for(int i = 0; i < visited.size(); i++){

            ret += visited.get(i).getNome();

            if(i < visited.size()-1){ ret += " -> "; }

        }

        return ret+" ";

    }


    // Call Gulosa.
    public static String buscaGulosa(Grafo g, String estadoInicio, String estadoFim){

        pass = false;

        visited = new ArrayList<>();

        visited.add(g.getEstado(estadoInicio));

        busca(g,estadoInicio,estadoFim);

        if (stringfy().contains(" "+estadoFim+" ")){
            return stringfy();
        }

        return "Estado "+estadoFim+ " não foi encontrado!\n\tCaminhos: "+stringfy();

    }


    // Call A*.
    public static String A_estrela(Grafo g, String estadoInicio, String estadoFim){

        pass = false;

        visited = new ArrayList<>();

        visited.add(g.getEstado(estadoInicio));

        busca_a(g,estadoInicio,estadoFim);

        if (stringfy().contains(" "+estadoFim+" ")){
            return stringfy();
        }

        return "Estado "+estadoFim+ " não foi encontrado!\n\tCaminhos: "+stringfy();

    }


    // Call A* iterativo.
    public static String A_estrela_iterativo(Grafo g, String estadoInicio, String estadoFim){

        int profundidade = 1;

        do{

            pass = false;

            visited = new ArrayList<>();

            visited.add(g.getEstado(estadoInicio));

            busca_a_i(g,estadoInicio,estadoFim, profundidade-1, 0);

            if (stringfy().contains(" "+estadoFim+" ")){
                break;
            }

            profundidade++;

        }while (true);

        return "\n\t\tEstado "+estadoFim+" encontrado na interação "+profundidade+".\n\t\t\t"+stringfy()+"\n";


    }


}
