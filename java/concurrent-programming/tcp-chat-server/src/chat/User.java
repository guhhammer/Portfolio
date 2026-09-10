package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/** Console client: connects to the server and relays the register / login / quit dialogue. */
public class User {

    public static void main(String[] args) throws IOException, InterruptedException {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = 2000;
        Scanner scan = new Scanner(System.in);

        boolean running = true;
        while (running) {
            Socket socket = new Socket(host, port);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            boolean firstMessage = true, inDialogue = true;
            Thread.sleep(2000);

            System.out.println("\n\n\n\nDo you want to register, login or quit?");
            System.out.print("Choice: ");
            String choice = scan.nextLine().toLowerCase();

            while (inDialogue) {
                if (choice.equals("register")) {
                    // the first message is always the chosen option, so the handler knows what the user wants.
                    out.writeUTF(firstMessage ? choice : scan.nextLine());
                    firstMessage = false;
                    String answer = in.readUTF();
                    System.out.print(answer);
                    if (answer.equals("User created!\n\n")) { inDialogue = false; }

                } else if (choice.equals("login")) {
                    out.writeUTF(firstMessage ? choice : scan.nextLine());
                    firstMessage = false;
                    String answer = in.readUTF();
                    System.out.println(answer);
                    if (answer.equals("\n\nAuthentication failed. Try again!\n\n")) { inDialogue = false; }
                    if (answer.equals("quit")) { inDialogue = false; }

                } else if (choice.equals("quit")) {
                    out.writeUTF(choice);
                    System.out.println(in.readUTF());
                    running = false;
                    inDialogue = false;

                } else {
                    inDialogue = false;
                }
            }
            socket.close();
        }
        System.out.println("\n\nDone!");
    }
}
