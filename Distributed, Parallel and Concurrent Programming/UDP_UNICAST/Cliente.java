package UDP_UNICAST;

import java.net.*;

public class Cliente {

    public static void main(String[] args) {
        try {
            System.out.print("Conectando o cliente ...");
            DatagramSocket socket_cliente = new DatagramSocket();
            System.out.println( " na porta: " +
                    socket_cliente.getLocalPort());
            InetAddress endereco_servidor = InetAddress.getByName("127.0.0.1");
            int porta_servidor = 4545;
            String pergunta_texto = "Qual a cor do cavalo?";
            byte[] pergunta_bytes = pergunta_texto.getBytes();
            DatagramPacket pergunta_pacote = new DatagramPacket(
                    pergunta_bytes, pergunta_bytes.length,
                    endereco_servidor, porta_servidor );
            DatagramPacket resposta_pacote = new DatagramPacket(new byte[512],512);
            System.out.println("Enviando pergunta ...");
            socket_cliente.send(pergunta_pacote);
            System.out.println("Pergunta enviada.");
            System.out.println("Recebendo resposta ...");
            socket_cliente.receive(resposta_pacote);
            System.out.print("Resposta: ");
            String resposta_texto = new String(resposta_pacote.getData(),
                    resposta_pacote.getOffset(),resposta_pacote.getLength());
            System.out.println(resposta_texto );
            socket_cliente.close();
            System.out.println("Cliente terminado.");
        } catch(Exception exc) { exc.printStackTrace(); }
    }

}
