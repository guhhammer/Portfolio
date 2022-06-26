package indexador;

import dicionario.Dicionario;
import dicionario.Estatistica;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
import diretorio.CSVFile;
import static java.lang.Math.log10;
import parser.Parser;
import util.Par;

public class Indexador {

    private Dicionario vocabu;
    private int[][] TF;
    private double[] DF;
    private double[] IDF;
    private double[][] TFIDF;
    
    public Indexador(Dicionario dik){
        this.vocabu = dik;
    }

    public void indexa(String pasta, String[] arquivos,
            String separador) {

        HashMap<String, Par> L;
        ArrayList<Integer[]> matriz = new ArrayList();

        for (String arquivo : arquivos) {

            CSVFile.abrirArquivoDeLeitura(pasta, arquivo);
            String vString = CSVFile.readTexto().toUpperCase();

            String[] v = new Parser(separador).split(vString);

            String aux = null;

            L = Estatistica.calcularFrequencia(v);
            //Estatistica.mostrar( L );
            matriz.add(montarLinha(L));
        }
        //mostrarMatriz(matriz);
        TF = tf(matriz);
        //mostrarMatriz(TF);
        DF = df(TF);
        //mostrarVetor(DF);
        IDF = idf(DF, arquivos.length);
        //mostrarVetor(IDF);
        TFIDF = tfidf(TF, IDF);
        //mostrarMatriz(TFIDF);
    }

    public int[][] getTF() {
        return TF;
    }

    public double[] getDF() {
        return DF;
    }

    public double[] getIDF() {
        return IDF;
    }

    public double[][] getTFIDF() {
        return TFIDF;
    }

    public Integer[] montarLinha(HashMap<String, Par> F) {

        Integer[] L = new Integer[vocabu.getContador()];
        for (int i = 0; i < L.length; i++) {
            L[i] = 0;
        }

        for (Par p : F.values()) {
            int pos = vocabu.posicao(p.getChave());
            L[pos] = p.getValor();
        }
        return L;
    }

    public void mostrarMatriz(ArrayList<Integer[]> M) {

        for (Integer[] linha : M) {
            out.println();
            for (Integer i : linha) {
                out.print(i + "\t");
            }
        }
    }

    public int[][] tf(ArrayList<Integer[]> M) {

        int NC = vocabu.getContador();
        int NL = M.size();

        int[][] matrizDeInteiro = new int[NL][NC];

        int i = -1, j = -1;
        for (Integer[] vInteiro : M) {
            i++;
            j = -1;
            for (Integer x : vInteiro) {
                j++;
                matrizDeInteiro[i][j] = x;
            }
        }
        return matrizDeInteiro;
    }

    public void mostrarMatriz(int[][] M) {
        for (String w : vocabu.getPalavras()) {
            out.print(w + "\t");
        }
        for (int i = 0; i < M.length; i++) {
            out.println();
            for (int j = 0; j < M[0].length; j++) {
                out.print(M[i][j] + "\t");
            }
        }
    }

    public void mostrarMatriz(double[][] M) {

        out.println();
        for (String w : vocabu.getPalavras()) {
            out.print(w + "\t");
        }
        for (int i = 0; i < M.length; i++) {
            out.println();
            for (int j = 0; j < M[0].length; j++) {
                out.printf("%1.2f\t", M[i][j]);
            }
        }
    }

    public double[] df(int[][] M) {

        double[] DF = new double[vocabu.getContador()];
        for (int i = 0; i < DF.length; i++) {
            DF[i] = 0.0;
        }
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                if (M[i][j] != 0) {
                    DF[j]++;
                }
            }
        }
        return DF;
    }

    public void mostrarVetor(double[] V) {
        out.println();
        for (double v : V) {
            out.printf("%1.2f\t", v);
        }
    }

    public double[] idf(double[] V, int N) {
        double[] idf = new double[V.length];

        for (int i = 0; i < V.length; i++) {
            idf[i] = log10(N / V[i]) / log10(2);
        }
        return idf;
    }

    public double[][] tfidf(int[][] tf,double[] idf) {

        int NL = tf.length;
        int NC = tf[0].length;

        double[][] X = new double[NL][NC];

        for (int i = 0; i < NL; i++) {
            for (int j = 0; j < NC; j++) {
                X[i][j] = tf[i][j] * idf[j];
            }
          
        }
        return X;
    }
    
    public void VetorMostrar(double[] V) {
        System.out.println();

        for (String w : vocabu.getPalavras()) {
            out.print("    (t = " + w + ") \t");
        }

        System.out.println();

        for (double v : V) {
            out.printf("      %1.2f\t", v);
        }
    }

    public void VetorMostrar(double IDF) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
