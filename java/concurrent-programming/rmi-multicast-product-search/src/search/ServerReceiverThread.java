package search;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/** Receives the stores' unicast UDP answers on port 1459 and stores them in the search history. */
public class ServerReceiverThread extends Thread {

    @Override
    public void run() {
        try (DatagramSocket serverSocket = new DatagramSocket(1459)) {
            Thread.sleep(1000);
            while (true) {
                DatagramPacket packet = new DatagramPacket(new byte[1000], 1000);
                serverSocket.receive(packet);
                String text = new String(packet.getData(), packet.getOffset(), packet.getLength());
                String[] fragment = text.split(Server.SEPARATOR);

                Server.mutex.acquire();
                Server.history.add(new SearchRecord(fragment[0], fragment[1], fragment[2], fragment[3], fragment[4]));
                Server.mutex.release();
            }
        } catch (Exception e) {
            System.err.println("Receiver stopped: " + e.getMessage());
        }
    }
}
