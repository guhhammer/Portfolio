package search;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

/** The broker between customers, the administrator and the stores: customers and the administrator talk to it over
 *  Java RMI; each search is multicast to the stores over UDP and their unicast answers are collected for TIMEOUT ms.
 *  Concurrent Programming course, PUCPR (2020). */
public class Server extends UnicastRemoteObject implements IConsumer, IManager {

    public static final String SEPARATOR = "--SEPARATOR--";
    public static final int RMI_PORT = 2522;

    private int timeoutMs = 5000;
    public static final List<SearchRecord> history = new ArrayList<>();
    public static final Semaphore mutex = new Semaphore(1);
    private final UniqueId ids = new UniqueId();

    public Server() throws RemoteException { super(); }

    private String resultsFor(String uid) {
        StringBuilder results = new StringBuilder("\n");
        for (SearchRecord r : history) {
            if (r.uid.equals(uid)) { results.append(r.formatSearch()); }
        }
        return results.append("\n").toString();
    }

    @Override
    public String searchProduct(String name) {
        String uid = ids.next();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String request = dateFormat.format(new Date()) + SEPARATOR + uid + SEPARATOR + name;

        new ServerSenderThread(request).start();
        try { Thread.sleep(timeoutMs); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return resultsFor(uid);
    }

    @Override
    public String seeSearchHistory() {
        StringBuilder out = new StringBuilder("\n\nHistory:\n\n");
        for (SearchRecord r : history) { out.append(">>").append(r.formatHistory()).append("\n>>\n\n"); }
        return out.toString();
    }

    @Override
    public int getTimeout() { return timeoutMs; }

    @Override
    public void setTimeout(int timeoutMs) { this.timeoutMs = timeoutMs; }

    public static void main(String[] args) {
        new ServerReceiverThread().start();
        try {
            Server server = new Server();
            LocateRegistry.createRegistry(RMI_PORT);
            Naming.rebind("rmi://localhost:" + RMI_PORT + "/ManagerConsole", server);
            Naming.rebind("rmi://localhost:" + RMI_PORT + "/ClientConsole", server);
            System.out.println("\n\nServer is online.\n\n");

            while (true) {
                Thread.sleep(10000);
                System.out.println(Arrays.toString(history.toArray()));
            }
        } catch (Exception e) {
            System.err.println("Server stopped: " + e);
        }
    }
}
