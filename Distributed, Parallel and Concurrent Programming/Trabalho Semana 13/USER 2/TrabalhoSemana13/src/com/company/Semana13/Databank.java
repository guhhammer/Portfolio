package com.company.Semana13;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Databank {

    // Atualiza/Salva a Base de Dados em Arquivo Rígido.
    public static void writeBank(ArrayList<Client> clients) throws FileNotFoundException, InterruptedException {

        Server.mutex.acquire();

        File databank = new File("databank.txt");

        PrintWriter writer = new PrintWriter(databank.getName());

        for(Client c : clients){

            writer.println(String.format("{%s} {%s} {%s} {%d} {%s}",c.getNome(),
                    c.getSenha(), c.getEnderecoIP(), c.getPorta(), c.getMessageData()));

        }
        writer.close();

        Server.mutex.release();

    }


    // Extrai as informações da Base de Dados e a monta em memória.
    public static void buildInMemory(String text) throws FileNotFoundException {

        File data = new File("databank.txt");
        Scanner scan = new Scanner(data);

        while(scan.hasNextLine()){

           String[] s = scan.nextLine().split("} \\{");
           s[0] = s[0].replace("{","");
           s[s.length-1] = s[s.length-1].replace("}","");

           Server.insertClient(new Client(s[0].toString(), s[1].toString(), s[2].toString(), Integer.parseInt(s[3])));

           String[] msgs = s[s.length-1].split(",");

           for(int i = 0; i < msgs.length; i++){
               msgs[i] = msgs[i].replace("[","");
               msgs[i] = msgs[i].replace("]","");
           }

           if(msgs.length > 1) {
               int k = 0;
               String de = "", mensagem = "";
               while (k < msgs.length) {
                   Client c = Server.clients.get(Server.clients.size() - 1);
                   de = msgs[k++];
                   mensagem = msgs[k++];
                   c.novaMensagem(de, mensagem);
               }
           }
        }


    }


}
