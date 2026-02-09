package pscf;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try{
            // linha de comando: java pscf.Contador 10 0.5
            ProcessBuilder pb = new ProcessBuilder("java", "pscf.Contador", "10", "0.5");

            Process cont = pb.start();

        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

}
