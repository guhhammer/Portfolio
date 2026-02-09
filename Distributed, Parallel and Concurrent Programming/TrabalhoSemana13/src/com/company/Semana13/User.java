package com.company.Semana13;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class User {


    public static void main(String[] args) throws IOException, InterruptedException {

        String host = "10.150.96.247";  // Endereço deste user.
        int porta = 2000; // Porta deste user.

        Scanner scan = new Scanner(System.in);

        boolean on = true, firstMessage, on2; // Condições de laço de repetição.
        while(on) {

            Socket s = new Socket(host, porta); // Cria socket.

            DataOutputStream saida = new DataOutputStream(s.getOutputStream());
            DataInputStream entrada = new DataInputStream(s.getInputStream());

            firstMessage = true; on2 = true;

            Thread.sleep(2000); // Espera 2 segundos.

            System.out.println("\n\n\n\nDeseja cadastrar, logar ou sair?");
            System.out.print("Choice: ");  // Opção inicial.
            String choose = scan.nextLine().toLowerCase();

            while (on2) {

                // Se a escolha for cadastrar:
                if (choose.equals("cadastrar")) {

                    // O primeiro envio é sempre a opção escolhida. Desta forma o manager sabe o que o usuário quer.
                    saida.writeUTF((firstMessage) ? choose : scan.nextLine()); firstMessage = false;

                    String answer = entrada.readUTF();
                    System.out.print(answer);
                    if(answer.equals("Usuário Criado!!\n\n")){  on2 = false; }

                }

                // Se a escolha for logar:
                else if (choose.equals("logar")) {
                    // O primeiro envio é sempre a opção escolhida. Desta forma o manager sabe o que o usuário quer.
                    saida.writeUTF((firstMessage) ? choose : scan.nextLine()); firstMessage = false;

                    String answer = entrada.readUTF();
                    System.out.println(answer);
                    if(answer.equals("\n\nAutenticação não confirmada. Tente Novamente!\n\n")){ on2 = false; }
                    if(answer.equals("sair")){ on2 = false; }

                }

                // Se a escolha for sair:
                else if (choose.equals("sair")) {

                    // O primeiro envio é sempre a opção escolhida. Desta forma o manager sabe o que o usuário quer.
                    saida.writeUTF(choose);

                    System.out.println(entrada.readUTF());
                    on = false; on2 = false;

                }

                // Qualquer escolha que não as acima.
                else{ on2 = false; }

            }

        }

        System.out.println("\n\nTerminado!");

    }



}
