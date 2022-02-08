package Semana15;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


    /*
    *
    *   THREAD QUE ENVIA MENSAGENS ATRAVÉS DE MULTICASTING PARA AS LOJAS DO GRUPO.
    *
    */


public class ServerThreadSENDER extends Thread{

    private String nome;

    public ServerThreadSENDER(String nome){ this.nome = nome; }

    public void sender(){

        try {

            InetAddress grupo = InetAddress.getByName("230.0.0.2");

            MulticastSocket mcs = new MulticastSocket();

            DatagramPacket search = new DatagramPacket(this.nome.getBytes(), this.nome.length(), grupo, 4446);

            mcs.send(search);
            System.out.println(search+"");

        }
        catch(Exception e){ e.getSuppressed(); }

    }

    public void run(){

        sender();

        try{ Thread.sleep(100); }catch (Exception e){ e.getSuppressed(); }

        this.stop();

    }

}
