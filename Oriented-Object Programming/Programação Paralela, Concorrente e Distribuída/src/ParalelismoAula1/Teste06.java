package ParalelismoAula1;

import java.util.concurrent.Semaphore;

public class Teste06 {

    public static void main(String[] args) {

        int[] primo = new int[8]; // vetor para 8 valores inteiros
        primo[0] = 1;
        primo[1] = 2;
        primo[2] = 3;
        primo[3] = 5;
        primo[4] = 7;
        primo[5] = 11;
        primo[6] = 13;
        primo[7] = 17;

        Semaphore A = new Semaphore(0);
        Semaphore B = new Semaphore(0);
        Quadrado q1 = new Quadrado(primo, 0, 3, A);
        Quadrado q2 = new Quadrado(primo, 4, 7, B);
        q1.start();
        q2.start();

        try {
            A.acquire(); // espera q1 terminar
            B.acquire(); // espera q2 terminar

            System.out.println("Depois dos quadrados:");
            for (int i = 0; i < 8; i++) {
                System.out.println("primo[" + i + "] = " + primo[i]);
            }

            int hash = 0;
            for (int i = 0; i < 4; i++) {
                hash = hash + primo[i] * primo[7 - i];
            }
            System.out.println("hash = " + hash);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
