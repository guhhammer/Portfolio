package com.company.Semana13;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class Server {

    public static ArrayList<Client> clients = new ArrayList<>(); // ArrayList contendo os Clients do serviço.

    public static ArrayList<String> clientNamesOnline = new ArrayList<>();

    public static void insertClient(Client c){  clients.add(c); } // Função para adicionar clients ao ArrayList.

    public static boolean isOnline(String nome){ return clientNamesOnline.contains(nome); }

    public static Semaphore mutex;  // Para garantir que apenas uma Thread atualiza a base de dados por vez.

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(2000); // Iniciando o Server.


        System.out.println("\n\nServer is online...\n\n");
        System.out.println("Server IP Address: "+ss.getInetAddress());
        System.out.println("Server Port: "+ss.getLocalPort());

        Databank.buildInMemory("databank.txt");  // Reconstrói os Clients em memória a partir de arquivo rígido.

        mutex = new Semaphore(1);

        while(true){

            // Imprime todos os usuários cadastrados.
            System.out.println("\n\nCurrent users:\n"+Arrays.toString(Server.clients.toArray())+"\n\n");

            Socket current =  ss.accept(); // Aceita request de Users acessando o serviço.

            DataOutputStream saida = new DataOutputStream(current.getOutputStream());
            DataInputStream entrada = new DataInputStream(current.getInputStream());

            Manager m = new Manager(current);  // Cria uma Thread manager para o usuário.
            m.start();


        }



    }





}
