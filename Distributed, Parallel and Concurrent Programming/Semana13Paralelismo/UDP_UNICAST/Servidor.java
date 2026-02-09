package UDP_UNICAST;

import java.net.*;

public class Servidor {

    public static void main(String[] args) {
        try {
            System.out.print("Concectando o servidor ...");
            int porta_servidor = 4545;
            System.out.println(" na porta " + porta_servidor );
            DatagramSocket socket_servidor = new DatagramSocket(porta_servidor );
            DatagramPacket pergunta_pacote = new DatagramPacket(new byte[512], 512);
            System.out.println("Recebendo a pergunta ...");
            socket_servidor.receive(pergunta_pacote);
            String pergunta_texto = new String(pergunta_pacote.getData(),
                    pergunta_pacote.getOffset(),pergunta_pacote.getLength());
            System.out.println("Pergunta: " + pergunta_texto);
            InetAddress IP_cliente = pergunta_pacote.getAddress();
            int porta_cliente = pergunta_pacote.getPort();
            System.out.println("IP do cliente: " + IP_cliente);
            System.out.println("Porta do cliente: " + porta_cliente);
            String resposta_texto = "Branco";
            byte[] resposta_bytes = resposta_texto.getBytes();
            DatagramPacket resposta_pacote = new DatagramPacket(
                    resposta_bytes, resposta_bytes.length,
                    IP_cliente, porta_cliente);
            System.out.println("Enviando a resposta ...");
            socket_servidor.send(resposta_pacote);
            System.out.println("Resposta enviada.");
            socket_servidor.close();
            System.out.println("Servidor terminado.");
        } catch(Exception exc) {
            exc.printStackTrace();
        }
    }


}
