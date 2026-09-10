package chat;

import java.util.ArrayList;
import java.util.List;

/** In-memory record of a registered user: credentials, last address, and the messages received.
 *  Persisted to databank.txt by Databank. */
public class Account {

    private final String name, password, ipAddress;
    private final int port;
    private final List<String[]> messages = new ArrayList<>();

    public Account(String name, String password, String ipAddress, int port) {
        this.name = name;
        this.password = password;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public String getName() { return name; }

    public String getPassword() { return password; }

    public String getIpAddress() { return ipAddress; }

    public int getPort() { return port; }

    public String formatMessages() {
        if (messages.isEmpty()) { return "\tNo messages!\n"; }
        StringBuilder out = new StringBuilder();
        for (String[] m : messages) { out.append("\tFrom: ").append(m[0]).append(" | Message: ").append(m[1]).append(" \n"); }
        return out.toString();
    }

    /** Serialises the messages as [[from,text],[from,text]] for the databank file. */
    public String messageData() {
        if (messages.isEmpty()) { return "[]"; }
        StringBuilder data = new StringBuilder("[");
        for (int i = 0; i < messages.size(); i++) {
            data.append("[").append(messages.get(i)[0]).append(",").append(messages.get(i)[1]).append("]");
            if (i != messages.size() - 1) { data.append(","); }
        }
        return data.append("]").toString();
    }

    public void addMessage(String from, String message) { messages.add(new String[]{from, message}); }

    @Override
    public String toString() { return "Client: " + name + " | IP: " + ipAddress + " | Port: " + port; }
}
