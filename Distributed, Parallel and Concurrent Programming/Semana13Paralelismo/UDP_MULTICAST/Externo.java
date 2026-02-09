package UDP_MULTICAST;

import java.net.*;

public class Externo {

    static public void main(String[] args) {
        try {
            InetAddress grupo = InetAddress.getByName( "224.0.0.1" );
            MulticastSocket s = new MulticastSocket();
            String texto = "Olá";
            DatagramPacket ola = new DatagramPacket( texto.getBytes(), texto.length(),
                    grupo, 3000 );
            System.out.println( "Enviando mensagem para o grupo ..." + texto.length() );
            s.send( ola );
            System.out.println( "Ok." );
        } catch (Exception e) { e.printStackTrace(); }
    }

}
