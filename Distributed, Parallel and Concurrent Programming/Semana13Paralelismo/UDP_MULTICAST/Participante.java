package UDP_MULTICAST;

import java.net.*;

public class Participante {

    static public void main(String[] args) {
        try {
            InetAddress grupo = InetAddress.getByName("224.0.0.1");
            MulticastSocket s = new MulticastSocket(3000);
            System.out.println("Entrando no grupo ...");
            s.joinGroup(grupo);
            System.out.println("Ok.");
            // Lê 5 mensagens enviadas ao grupo:
            for(int i = 0; i < 5; i++) {
                byte[] mensagem_bytes = new byte[1000];
                DatagramPacket mensagem_pacote =
                        new DatagramPacket(mensagem_bytes, mensagem_bytes.length);
                System.out.print("Aguardando mensagem "+i+" ...");
                s.receive(mensagem_pacote);
                System.out.println("Ok.");
                byte[] dados_bytes = new byte[mensagem_pacote.getLength()];
                System.arraycopy(mensagem_pacote.getData(),
                        0, dados_bytes, 0, dados_bytes.length);
                String dados_texto = new String(mensagem_pacote.getData(),
                        mensagem_pacote.getOffset(),mensagem_pacote.getLength());
                System.out.println("Mensagen recebida: "+ dados_texto);
            }

            System.out.println("Saindo do grupo ...");
            s.leaveGroup(grupo);
            System.out.println("Ok.");
        } catch(Exception exc) { exc.printStackTrace(); }
    }

}
