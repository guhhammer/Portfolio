
package Dicionario;

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
}