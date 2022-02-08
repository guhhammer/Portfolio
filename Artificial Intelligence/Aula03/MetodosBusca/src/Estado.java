import java.util.ArrayList;

public class Estado {

    private ArrayList<Estado> vizinhos;
    private String nome;

    public Estado(String nome){
        this.nome = nome;
        vizinhos = new ArrayList<Estado>();
    }

    public String getNome(){
        return this.nome;
    }

    public ArrayList<Estado> getVizinhos(){
        return this.vizinhos;
    }

    public void addEstado(Estado e){
        vizinhos.add(e);
    }

}
