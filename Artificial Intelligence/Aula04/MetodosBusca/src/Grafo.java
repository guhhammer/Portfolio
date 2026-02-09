import java.util.ArrayList;

public class Grafo {

    private ArrayList<Estado> estados;

    public Grafo(){
        estados = new ArrayList<Estado>();
    }

    public void add(Estado e){
        estados.add(e);
    }

    public Estado getEstado(String nome){
        for(Estado e : estados){
            if(e.getNome().equals(nome)) return e;
        }
        return null;
    }

}
