package multicast;

import java.net.*;

/** Sends one datagram to the multicast group 224.0.0.1:3000. */
public class Sender {

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName("224.0.0.1");
            MulticastSocket socket = new MulticastSocket();
            String text = "Hello";
            DatagramPacket hello = new DatagramPacket(text.getBytes(), text.length(), group, 3000);
            System.out.println("Sending message to the group ..." + text.length());
            socket.send(hello);
            System.out.println("Ok.");
        } catch (Exception e) { e.printStackTrace(); }
    }
}
