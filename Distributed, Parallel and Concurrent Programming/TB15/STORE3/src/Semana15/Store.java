package Semana15;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

    /*
    *
    *       CLASSE LOJA DE UMA DAS LOJAS DO GRUPO.
    *
    */

public class Store {

    private static ArrayList<Product> estoque = new ArrayList<>();

    public static ArrayList<Product> getEstoque() {
        return estoque;
    }

    public static String finder(String palavra){
        String ss = " { ";
        for(Product p : getEstoque()){
            if(p.getName().toLowerCase().split(palavra.toLowerCase()).length > 1){
                ss += "{"+p.getName()+" __SEPARATOR__ "+p.getPrice()+"} ";
            }
        }

        return (ss.length() == 0)  ? "{NADA __SEPARATOR__ NADA}" : ss + " } ";

    }

    private static String IDStore = "StoreC";

    public static void main(String[] args) throws UnknownHostException {

        getEstoque().add(new Product("Iphone X",5000.0f));
        getEstoque().add(new Product("Iphone 8",2300.0f));
        getEstoque().add(new Product("Samsung S11", 3000.0f));
        getEstoque().add(new Product("Samsung S10",1800.0f));
        getEstoque().add(new Product("Mac PRO", 4600.0f));

        try {

            InetAddress grupo = InetAddress.getByName("230.0.0.2");

            MulticastSocket mcs = new MulticastSocket(4446);

            mcs.joinGroup(grupo);

            System.out.println("\n\nStore is online.\n\n");

            while(true){

                byte[] msgBytes = new byte[1000];
                DatagramPacket msgPacote = new DatagramPacket(msgBytes, msgBytes.length);

                mcs.receive(msgPacote);

                byte[] dadosBytes = new byte[msgPacote.getLength()];

                System.arraycopy(msgPacote.getData(),
                        0, dadosBytes, 0, dadosBytes.length);

                String dados_texto = new String(msgPacote.getData(),
                        msgPacote.getOffset(),msgPacote.getLength());

                System.out.println("Mensagen recebida: "+ dados_texto);


                String sep = "--SEPARATOR--";
                String[] fragmenter = dados_texto.split(sep);

                System.out.println(Arrays.toString(fragmenter));

                String pesquisa = finder(fragmenter[2]);
                String resposta_texto = fragmenter[0]+sep+fragmenter[1]+sep+fragmenter[2]+sep+IDStore+sep+pesquisa;

                DatagramSocket socket_cliente = new DatagramSocket();

                InetAddress IP_cliente = InetAddress.getByName("127.0.0.1");
                int porta_cliente = 1459;

                byte[] resposta_bytes = resposta_texto.getBytes();

                DatagramPacket resposta_pacote = new DatagramPacket(
                        resposta_bytes, resposta_bytes.length,
                        IP_cliente, porta_cliente);

                socket_cliente.send(resposta_pacote);

                System.out.println("Resposta: "+resposta_texto+"\n\n");

                socket_cliente.close();

            }


        }
        catch(Exception e){
            e.getSuppressed();
        }


    }


}
