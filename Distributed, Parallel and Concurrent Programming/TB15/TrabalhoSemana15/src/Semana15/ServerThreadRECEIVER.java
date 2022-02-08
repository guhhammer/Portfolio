package Semana15;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

    /*
    *
    *       THREAD QUE RECEBE MENSAGENS UNICAST DAS LOJAS E AS ARMAZENA NO HISTÓRICO.
    *
    */

public class ServerThreadRECEIVER extends Thread{

    public ServerThreadRECEIVER(){
    }

    public synchronized void receiver(){

        try {

            DatagramSocket socket_servidor = new DatagramSocket(1459);

            try{ Thread.sleep(1000); }catch (Exception e){ e.getSuppressed(); }

            while(true) {

                DatagramPacket resposta_pacote = new DatagramPacket(new byte[1000], 1000);

                socket_servidor.receive(resposta_pacote);

                String resposta_texto = new String(resposta_pacote.getData(),
                        resposta_pacote.getOffset(), resposta_pacote.getLength());

                String sep = "--SEPARATOR--";
                String[] fragment = resposta_texto.split(sep);

                Server.mutex.acquire();

                Server.historico.add(new History(fragment[0], fragment[1], fragment[2],fragment[3], fragment[4]));

                Server.mutex.release();

            }

        }
        catch (Exception e){ e.getSuppressed(); }

    }

    public void run(){

        receiver();

    }

}
