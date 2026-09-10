package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

/** Multi-threaded TCP messaging server: accepts users on port 2000, one ClientHandler thread each, with accounts
 *  and messages persisted to databank.txt. Concurrent Programming course, PUCPR (2020). */
public class Server {

    public static final List<Account> accounts = new ArrayList<>();
    public static final List<String> online = new ArrayList<>();
    public static final Semaphore mutex = new Semaphore(1);   // one thread writes the databank at a time

    public static void insertAccount(Account a) { accounts.add(a); }

    public static boolean isOnline(String name) { return online.contains(name); }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
        System.out.println("\n\nServer is online...\n\n");
        System.out.println("Server IP address: " + serverSocket.getInetAddress());
        System.out.println("Server port: " + serverSocket.getLocalPort());

        Databank.load();

        while (true) {
            System.out.println("\n\nCurrent users:\n" + Arrays.toString(accounts.toArray()) + "\n\n");
            Socket current = serverSocket.accept();
            new ClientHandler(current).start();
        }
    }
}
