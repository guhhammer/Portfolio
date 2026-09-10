package unicast;

import java.net.*;

/** Sends one question to the UDP server and prints the answer. */
public class Client {

    public static void main(String[] args) {
        try {
            System.out.print("Connecting the client ...");
            DatagramSocket clientSocket = new DatagramSocket();
            System.out.println(" on port: " + clientSocket.getLocalPort());

            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            int serverPort = 4545;

            String questionText = "What colour is the horse?";
            byte[] questionBytes = questionText.getBytes();
            DatagramPacket questionPacket = new DatagramPacket(questionBytes, questionBytes.length, serverAddress, serverPort);
            DatagramPacket answerPacket = new DatagramPacket(new byte[512], 512);

            System.out.println("Sending the question ...");
            clientSocket.send(questionPacket);
            System.out.println("Question sent.");
            System.out.println("Receiving the answer ...");
            clientSocket.receive(answerPacket);
            System.out.print("Answer: ");
            String answerText = new String(answerPacket.getData(), answerPacket.getOffset(), answerPacket.getLength());
            System.out.println(answerText);
            clientSocket.close();
            System.out.println("Client finished.");
        } catch (Exception exc) { exc.printStackTrace(); }
    }
}
