package diretorio;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class CSVFile {

    private static String caminho, nomeDoArquivo;
    private static BufferedReader reader;

 
    public static void abrirArquivoDeLeitura(String pCaminho,
            String pNome) {
        caminho = pCaminho;
        nomeDoArquivo = pNome;
        abrirArquivoDeLeitura();
    }
    
    
    private static void abrirArquivoDeLeitura() {
        try {
            InputStreamReader arquivoInStream;
            FileInputStream entradaInStream;
            entradaInStream = new FileInputStream(caminho + nomeDoArquivo);
            arquivoInStream = new InputStreamReader(entradaInStream);
            reader = new BufferedReader(arquivoInStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

     public static String readLine() {

        String linha = null;
        try {
            linha = reader.readLine();
        } catch (Exception e) {
            System.out.println("readLine: " + e);
        }

        return linha;
    }

    public static String readTexto() {
        String texto = "";
        String linha = "";
        try {
            while ((linha = readLine()) != null) {
                texto += linha + "\n";
            }
        } catch (Exception e) {
            System.out.println("readTexto: " + e);
        }
        return texto;
    }
}
