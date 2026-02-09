
import java.util.ArrayList;

public class BProfundidade {

    private static ArrayList<Estado> visited;
    private static boolean pass = false;

    private static void busca(Grafo g, String estadoInicio, String estadoFim, int profundidade, int counter){

        ArrayList<Estado> estados = g.getEstado(estadoInicio).getVizinhos();

        if (profundidade == 0 ){ pass = true; }

        for(int i = 0; i < estados.size(); i++){

            Estado estado = estados.get(i);

            if (pass){ break; }

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
                busca(g, estado.getNome(), estadoFim, profundidade, counter+1);
            }
            else{
                busca(g, estado.getNome(), estadoFim, profundidade, counter+1);
            }
        }
    }

    private static void busca_b(Grafo g, String estadoInicio, String estadoFim){

        ArrayList<Estado> estados = g.getEstado(estadoInicio).getVizinhos();

        for(int i = 0; i < estados.size(); i++){

            Estado estado = estados.get(i);

            if (pass) { break; }

            if (estado.getNome().equals(estadoFim)){

                visited.add(estado);

                pass = true;

            }
            else if(!visited.contains(estado)){

                visited.add(estado);

                busca_b(g, estado.getNome(), estadoFim);

            }

        }

    }

    private static String stringfy(){

        String ret = " ";
        for(int i = 0; i < visited.size(); i++){

            ret += visited.get(i).getNome();

            if(i < visited.size()-1){ ret += " -> "; }

        }

        return ret+" ";

    }

    //Ex.6
    public static String buscaProfundidade(Grafo g, String estadoInicio, String estadoFim){

        pass = false;

        visited = new ArrayList<>();

        visited.add(g.getEstado(estadoInicio));

        busca_b(g,estadoInicio,estadoFim);

        if (stringfy().contains(" "+estadoFim+" ")){
            return stringfy();
        }

        return "Estado "+estadoFim+ " não foi encontrado!\n\tCaminhos: "+stringfy();

    }

    //Ex.7
    public static String buscaProfundidadeLimitada(Grafo g, String estadoInicio, String estadoFim, int profundidade){

        pass = false;

        visited = new ArrayList<>();

        visited.add(g.getEstado(estadoInicio));

        busca(g,estadoInicio,estadoFim, profundidade-1, 0);

        if (stringfy().contains(" "+estadoFim+" ")){
            return stringfy();
        }

        return "Estado "+estadoFim+ " não foi encontrado na profundidade "+profundidade+"!\n\tCaminhos: "+stringfy();

    }

    //Ex.8
    public static String buscaAprofundamentoIterado(Grafo g, String estadoInicio, String estadoFim){

        int profundidade = 1;

        do{

            pass = false;

            visited = new ArrayList<>();

            visited.add(g.getEstado(estadoInicio));

            busca(g,estadoInicio,estadoFim, profundidade-1, 0);

            if (stringfy().contains(" "+estadoFim+" ")){
                break;
            }

            profundidade++;

        }while (true);

        return "\nEstado "+estadoFim+" encontrado na interação "+profundidade+".\n"+stringfy()+"\n";

    }


}
