import java.util.ArrayList;

public class Estado {

    private ArrayList<Estado> vizinhos;
    private String nome;
    private double latitude, longitude;

    public Estado(String nome, double lat, double lon){
        this.nome = nome;
        vizinhos = new ArrayList<Estado>();
        latitude = lat;
        longitude = lon;
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

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

}
