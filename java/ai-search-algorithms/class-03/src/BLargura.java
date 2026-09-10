import java.util.ArrayList;

public class BLargura {

    private static ArrayList<Estado> path;
    private static int indicator;
    private static boolean pass = false;

    private static void append(Grafo g, Estado e, String ef){
        if (path.contains(g.getEstado(ef))){
            pass = true;
        }
        else if(ef.equals(e.getNome()) && !pass){
            path.add(e);
            pass = true;
        }
        else if(!path.contains(e) && !pass){
            path.add(e);
        }
    }

    private static void busca(Grafo g, String estadoFim){

        path.get(indicator).getVizinhos().forEach(estado -> append(g, estado, estadoFim));

        if(indicator == path.size() || pass){
            return;
        }

        indicator++;


        busca(g, estadoFim);

    }

    private static String stringfy(){

        String ret = " ";
        for(int i = 0; i < path.size(); i++){

            ret += path.get(i).getNome();

            if(i < path.size()-1){ ret += " -> "; }

        }

        return ret+" ";

    }

    public static String buscaLargura(Grafo g, String estadoInicio, String estadoFim){

        path = new ArrayList<>();

        indicator = 0;

        path.add(g.getEstado(estadoInicio));

        busca(g, estadoFim);

        if (stringfy().contains(" "+estadoFim+" ")){
            return stringfy();
        }

        return "Estado "+estadoFim+ " não foi encontrado!\n\tCaminhos: "+stringfy();

    }

}
