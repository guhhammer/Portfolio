package dicionario;

import static java.lang.System.out;
import java.util.HashMap;
import java.util.Set;
import util.Par;

public class Estatistica {

    public static HashMap<String, Par> calcularFrequencia(String[] vetor) {

        HashMap<String, Par> L = new HashMap();

        Par aux = null;

        for (String x : vetor) {
            aux = L.get(x.trim());
            if (aux == null) {
                L.put(x.trim(), new Par(x.trim(),1));
            } else {
                aux.setValor(aux.getValor() + 1);
            }
        }
        return L;
    }

    public static void mostrar(HashMap<String, Par> HM) {
 
        Set<String> X = HM.keySet();

        for (String x : X) {
            out.println(x + " : " + HM.get(x).getValor());
        }
    }
    
    
    public static double semelhança(double[] v1, double[] v2) {

        double produto = 0.0;
        double n1 = 0.0;
        double n2 = 0.0;
        for (int i = 0; i < v1.length; i++) {
            produto += v1[i] * v2[i];
            n1 += Math.pow(v1[i], 2);
            n2 += Math.pow(v2[i], 2);
        }

        return Math.round(produto / (Math.sqrt(n1) * Math.sqrt(n2)) * 100.0);

    }
    
    public static double semelhança_erro(double[] v1) {

        double produto = 0.0;
        double n1 = 0.0;
        double n2 = 0.0;
        for (int i = 0; i < v1.length; i++) {
            produto += v1[i] * n2;
            n1 += Math.pow(v1[i], 2);
            n2 += Math.pow(n2, 2);
        }

        return Math.round(produto / (Math.sqrt(n1) * n2) * 100.0);

    }
    
    
}
