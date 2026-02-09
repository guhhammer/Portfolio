package recuperador;

import dicionario.Dicionario;
import dicionario.Estatistica;
import static java.lang.System.out;
import java.util.HashMap;
import parser.Parser;
import util.Par;

public class Recuperador {

    private static String separador;
    private static Dicionario dico;
    private static double[] IDF;

    public Recuperador(String separador, Dicionario dico,
            double[] idf) {
        this.separador = separador;
        this.dico = dico;
        this.IDF = idf;
    }

    public double[] tfidf(String consulta) {

        String[] v = new Parser(separador).split(consulta.toUpperCase());

        HashMap<String, Par> Q = Estatistica.calcularFrequencia(v);

        Integer[] TF = montarLinha(Q);

        double[] TFIDF = tfidf(TF);

        return TFIDF;
    }

    public HashMap<String, Par> recupera(String consulta) {

        return null;
    }

    public static Integer[] montarLinha(HashMap<String, Par> F) {

        Integer[] L = new Integer[dico.getContador()];
        for (int i = 0; i < L.length; i++) {
            L[i] = 0;
        }

        for (Par p : F.values()) {
            int pos = dico.posicao(p.getChave());
            L[pos] = p.getValor();
        }
        return L;
    }

    public double[] tfidf(Integer[] tf) {

        double[] X = new double[tf.length];

        for (int i = 0; i < tf.length; i++) {
            X[i] = tf[i] * IDF[i];
        }
        return X;
    }

    public void mostrarVetor(double[] V) {
        out.println();
        for (String w : dico.getPalavras()) {
            out.print(w + "\t");
        }
        out.println();
        for (double v : V) {
            out.printf("%1.2f\t", v);
        }
    }
}
