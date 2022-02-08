package psfc;

public class Main {

    public static void main(String[] args) {

        try{

            // P1. 2 contadores com tempos e contagens diferentes.
            // P2. Se pai termina antes, filho continua?

            // linha de comando: java pscf.Contador 10 0.5
            ProcessBuilder pb = new ProcessBuilder("java", "psfc.Contador", "10", "0.5");
            ProcessBuilder pb2 = new ProcessBuilder("java", "psfc.Contador", "20", "0.4");

            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb2.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            Process cont = pb.start();
            Process cont2 = pb2.start();


            //Thread.sleep(5500);
            cont.waitFor();
            cont2.waitFor();

            System.out.println("Pai terminando.");

        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

}