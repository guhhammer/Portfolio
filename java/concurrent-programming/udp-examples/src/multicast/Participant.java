package multicast;

import java.net.*;

/** Joins the multicast group, reads five messages and leaves. */
public class Participant {

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName("224.0.0.1");
            MulticastSocket socket = new MulticastSocket(3000);
            System.out.println("Joining the group ...");
            socket.joinGroup(group);
            System.out.println("Ok.");
            for (int i = 0; i < 5; i++) {
                byte[] messageBytes = new byte[1000];
                DatagramPacket messagePacket = new DatagramPacket(messageBytes, messageBytes.length);
                System.out.print("Waiting for message " + i + " ...");
                socket.receive(messagePacket);
                System.out.println("Ok.");
                String messageText = new String(messagePacket.getData(), messagePacket.getOffset(), messagePacket.getLength());
                System.out.println("Message received: " + messageText);
            }
            System.out.println("Leaving the group ...");
            socket.leaveGroup(group);
            System.out.println("Ok.");
        } catch (Exception exc) { exc.printStackTrace(); }
    }
}
