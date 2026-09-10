package search;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/** Multicasts one search request to every store in the group 230.0.0.2:4446. */
public class ServerSenderThread extends Thread {

    private final String message;

    public ServerSenderThread(String message) { this.message = message; }

    @Override
    public void run() {
        try (MulticastSocket socket = new MulticastSocket()) {
            InetAddress group = InetAddress.getByName("230.0.0.2");
            DatagramPacket search = new DatagramPacket(message.getBytes(), message.length(), group, 4446);
            socket.send(search);
            System.out.println("Sent search: " + message);
        } catch (Exception e) {
            System.err.println("Could not multicast the search: " + e.getMessage());
        }
    }
}
