
package contador;

public class Contador {
    
    public static int contador = 0;
    
    public Contador(){contador = 0;}
    
    public void incrementar(){contador++;}
    
    public int zerar(){return contador = 0;}
    
    public int getValor(){return contador;}

    public static void main(String[] args) {
        Contador x = new Contador();
        x.incrementar();
        System.out.println(x.getValor());
        System.out.println(x.zerar());
    }
}
