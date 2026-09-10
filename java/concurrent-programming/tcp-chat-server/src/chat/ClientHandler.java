package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/** One thread per connected user: handles register / login / quit and, once logged in,
 *  listing users, sending a message and reading received messages. */
public class ClientHandler extends Thread {

    private final Socket socket;
    private final DataOutputStream output;
    private final DataInputStream input;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            System.out.println(socket.getLocalAddress() + " joined.");
            String choice = input.readUTF().toLowerCase();
            System.out.println(socket.getLocalAddress() + " chose: " + choice + ".");

            if (choice.equals("register")) {
                output.writeUTF("Registration:\nEnter your name:  ");
                String name = input.readUTF();
                output.writeUTF("\nEnter your password:  ");
                String password = input.readUTF();

                Server.insertAccount(new Account(name, password, socket.getLocalAddress() + "", socket.getLocalPort()));
                Databank.save(Server.accounts);
                output.writeUTF("User created!\n\n");

            } else if (choice.equals("login")) {
                output.writeUTF("Login:\nEnter your name:   ");
                String name = input.readUTF();
                output.writeUTF("\nEnter your password:   ");
                String password = input.readUTF();

                boolean authenticated = false;
                for (Account a : Server.accounts) {
                    if (a.getName().equals(name) && a.getPassword().equals(password)) { authenticated = true; break; }
                }

                if (authenticated) {
                    Server.online.add(name);
                    session(name);
                    Server.online.remove(name);
                } else {
                    output.writeUTF("\n\nAuthentication failed. Try again!\n\n");
                }

            } else if (choice.equals("quit")) {
                output.writeUTF("leaving the server...");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Connection ended: " + e.getMessage());
        }
    }

    private void session(String name) throws IOException, InterruptedException {
        while (true) {
            output.writeUTF("\n\nYou are online:\n"
                    + "\tOption 1: list users.\n"
                    + "\tOption 2: send a message.\n"
                    + "\tOption 3: read received messages.\n"
                    + "\tAnything else: quit.\n\nEnter an option:  ");
            String option = input.readUTF();
            System.out.println(socket.getLocalAddress() + "(" + name + ") chose option: " + option + ".");

            if (option.equals("1")) {
                StringBuilder list = new StringBuilder();
                for (Account a : Server.accounts) {
                    list.append("\t").append(a.getName()).append(" is: ....... ")
                        .append(Server.isOnline(a.getName()) ? "Online" : "Offline").append("\n");
                }
                output.writeUTF("\n\nUsers:\n" + list + "\nType anything and press Enter to continue: ");
                input.readUTF();

            } else if (option.equals("2")) {
                output.writeUTF("\n\nEnter a message:   ");
                String message = input.readUTF().replace(".", "").replace("!", "").replace(",", " ").replace(" ", "_");
                output.writeUTF("\n\nEnter the recipient user: ");
                String recipient = input.readUTF();

                for (Account a : Server.accounts) {
                    if (a.getName().equals(recipient)) { a.addMessage(name, message); }
                }
                Databank.save(Server.accounts);
                output.writeUTF("Message sent.\nType anything and press Enter to continue: ");
                input.readUTF();

            } else if (option.equals("3")) {
                StringBuilder list = new StringBuilder();
                for (Account a : Server.accounts) {
                    if (a.getName().equals(name)) { list.append(a.formatMessages()); }
                }
                output.writeUTF("\n\nReceived messages:\n" + list + "\n\nType anything and press Enter to continue: ");
                input.readUTF();

            } else {
                output.writeUTF("quit");
                return;
            }
        }
    }
}
