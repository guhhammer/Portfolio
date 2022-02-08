package psfc;

public class Main {

    public static void main(String[] args) {

        try{

            // P1. 2 contadores com tempos e contagens diferentes.
            // P2. Se pai termina antes, filho continua?

            // Resposta P1: sim, os processos executam paralelamente e o pai só encerra quando os processos terminarem.
            // Resposta P2: sim, os filhos ainda continuam se o pai terminar antes.

            boolean executar_resposta_pergunta1 = true; // false para executar a pergunta 2.

            // linha de comando: java pscf.Contador 10 0.5

            ProcessBuilder pb = new ProcessBuilder("java", "psfc.Contador", "10", "0.5");
            ProcessBuilder pb2 = new ProcessBuilder("java", "psfc.Contador", "20", "0.7");

            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb2.redirectOutput(ProcessBuilder.Redirect.INHERIT);

            Process cont = pb.start();
            Process cont2 = pb2.start();

            if(executar_resposta_pergunta1){
                //Thread.sleep(5500);
                cont.waitFor();
                cont2.waitFor();
            }
            else{

            }

            System.out.println("Pai terminando.");

        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

}