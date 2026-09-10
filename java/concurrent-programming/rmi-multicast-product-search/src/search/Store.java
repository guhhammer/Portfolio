package search;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** One store of the group: joins the multicast group, answers every search with the matching products
 *  by unicast to the server. Run several copies with different ids to simulate several stores. */
public class Store {

    private static final List<Product> stock = new ArrayList<>();

    public static List<Product> getStock() { return stock; }

    /** Products whose name contains the word, as " { {name __SEPARATOR__ price} ... } ". */
    public static String find(String word) {
        StringBuilder out = new StringBuilder(" { ");
        for (Product p : getStock()) {
            if (p.getName().toLowerCase().contains(word.toLowerCase())) {
                out.append("{").append(p.getName()).append(" __SEPARATOR__ ").append(p.getPrice()).append("} ");
            }
        }
        return out.append(" } ").toString();
    }

    public static void main(String[] args) {
        String storeId = args.length > 0 ? args[0] : "StoreA";

        getStock().add(new Product("Iphone X", 3200.0f));
        getStock().add(new Product("Iphone X", 3250.0f));
        getStock().add(new Product("Samsung S9", 3000.0f));
        getStock().add(new Product("Xiaomi 3", 1900.0f));
        getStock().add(new Product("Motorola M4", 500.0f));

        try (MulticastSocket socket = new MulticastSocket(4446)) {
            InetAddress group = InetAddress.getByName("230.0.0.2");
            socket.joinGroup(group);
            System.out.println("\n\nStore " + storeId + " is online.\n\n");

            while (true) {
                byte[] buffer = new byte[1000];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String text = new String(packet.getData(), packet.getOffset(), packet.getLength());
                System.out.println("Message received: " + text);

                String[] fragment = text.split(Server.SEPARATOR);
                System.out.println(Arrays.toString(fragment));

                String results = find(fragment[2]);
                String answer = fragment[0] + Server.SEPARATOR + fragment[1] + Server.SEPARATOR + fragment[2]
                        + Server.SEPARATOR + storeId + Server.SEPARATOR + results;

                try (DatagramSocket unicast = new DatagramSocket()) {
                    byte[] answerBytes = answer.getBytes();
                    DatagramPacket answerPacket = new DatagramPacket(answerBytes, answerBytes.length,
                            InetAddress.getByName("127.0.0.1"), 1459);
                    unicast.send(answerPacket);
                }
                System.out.println("Answer: " + answer + "\n\n");
            }
        } catch (Exception e) {
            System.err.println("Store stopped: " + e);
        }
    }
}
