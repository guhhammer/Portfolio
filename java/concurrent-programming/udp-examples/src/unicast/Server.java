package unicast;

import java.net.*;

/** Waits for one UDP question and answers it. */
public class Server {

    public static void main(String[] args) {
        try {
            System.out.print("Connecting the server ...");
            int serverPort = 4545;
            System.out.println(" on port " + serverPort);
            DatagramSocket serverSocket = new DatagramSocket(serverPort);
            DatagramPacket questionPacket = new DatagramPacket(new byte[512], 512);

            System.out.println("Receiving the question ...");
            serverSocket.receive(questionPacket);
            String questionText = new String(questionPacket.getData(), questionPacket.getOffset(), questionPacket.getLength());
            System.out.println("Question: " + questionText);

            InetAddress clientIp = questionPacket.getAddress();
            int clientPort = questionPacket.getPort();
            System.out.println("Client IP: " + clientIp);
            System.out.println("Client port: " + clientPort);

            String answerText = "White";
            byte[] answerBytes = answerText.getBytes();
            DatagramPacket answerPacket = new DatagramPacket(answerBytes, answerBytes.length, clientIp, clientPort);
            System.out.println("Sending the answer ...");
            serverSocket.send(answerPacket);
            System.out.println("Answer sent.");
            serverSocket.close();
            System.out.println("Server finished.");
        } catch (Exception exc) { exc.printStackTrace(); }
    }
}
