package com.company.Semana13;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Manager extends Thread{

    // Atributos da Thread Manager.
    private Socket mng;
    private DataOutputStream output;
    private DataInputStream input;

    // Construtor.
    public Manager(Socket manager) throws IOException {

        this.mng = manager;
        this.input = new DataInputStream(mng.getInputStream());
        this.output = new DataOutputStream(mng.getOutputStream());

    }

    // Execução da Thread.
    public void run(){
        try {

            // Capta as escolhas do usuário em LowerCase.
            System.out.println(mng.getLocalAddress()+" entrou. ");
            String choice = input.readUTF().toString().toLowerCase();
            System.out.println(mng.getLocalAddress()+" escolheu: "+choice+".");


            // Se o usuário escolher cadastrar:
            if (choice.equals("cadastrar")) {

                output.writeUTF("Cadastro:\nDigite seu Nome:  ");
                String nome = input.readUTF();
                output.writeUTF("\nDigite sua Senha:  ");
                String senha = input.readUTF();

                // Insere o novo cliente em ArrayList na memória.
                Server.insertClient(new Client(nome, senha, mng.getLocalAddress()+"", (int)mng.getLocalPort()));

                // Salva última alteração em memória.
                Databank.writeBank(Server.clients);

                output.writeUTF("Usuário Criado!!\n\n");


            }

            // Se o usuário escolher logar:
            else if (choice.equals("logar")) {


                output.writeUTF("Login:\nDigite seu Nome:   ");
                String nome = input.readUTF();
                output.writeUTF("\nDigite sua Senha:   ");
                String senha = input.readUTF();


                boolean registered = false;
                for (Client c : Server.clients) {
                    if (c.getNome().equals(nome)) {
                        if(c.getSenha().equals(senha)){
                            registered = true;  // Autenticação do usuário.
                            break;
                        }
                    }
                }


                // Se o usuário for autenticado:
                if (registered) {

                    Server.clientNamesOnline.add(nome);

                    while(true) {

                        // Lista de opções do serviço.
                        String show = "\n\nVocê está Online:\n" +
                                "\tOpção 1: ver usuários.\n" +
                                "\tOpção 2: enviar mensagem.\n" +
                                "\tOpção 3: ver mensagens recebidas.\n" +
                                "\tOutros: sair.\n\nDigite uma Opção:  ";

                        output.writeUTF(show);
                        String option = input.readUTF();
                        System.out.println(mng.getLocalAddress()+"("+nome+") escolheu opção: "+option+".");


                        // Se opção escolhida igual a 1:
                        if (option.equals("1")) {

                            String list = "";  // Lista dos usuários na base de dados em memória.
                            for (int i = 0; i < Server.clients.size(); i++) {
                                list += "\t" + Server.clients.get(i).getNome() +" is: ....... "+
                                        ((Server.isOnline(Server.clients.get(i).getNome())) ? "Online" : "Offline")+ "\n";
                            }
                            if (Server.clients.size() == 0) {
                                list = "\tNenhum usuário Cadastrado!!"; // se nenhum usuário cadastrado for encontrado.
                            }   // Essa condição nunca ocorre.

                            output.writeUTF("\n\nLista de Usuários:\n" + list + "\nDigite algo e pressione Enter para continuar: ");

                            input.readUTF();

                        }


                        // Se opção escolhida igual a 2:
                        else if (option.equals("2")) {


                            output.writeUTF("\n\nDigite uma mensagem:   ");
                            String mensagem = input.readUTF().toString().replace(".","")
                                    .replace("!","").replace(","," ")
                                    .replace(" ","_");
                            output.writeUTF("\n\nDigite usuário destinatário: ");
                            String destinatario = input.readUTF();


                            for (Client c : Server.clients) {
                                if (c.getNome().equals(destinatario)) {
                                    c.novaMensagem(nome, mensagem);
                                }
                            } // Cria nova mensagem para destinatário que contém o nome de quem enviou e a mensagem.

                            Databank.writeBank(Server.clients);  // Atualiza o banco de dados para incluir mensagem.

                            output.writeUTF("Mensagem enviada.\nDigite algo e pressione Enter para continuar: ");
                            input.readUTF();

                        }


                        // Se opção escolhida igual a 3:
                        else if (option.equals("3")) {

                            String lista = "";
                            for (Client c : Server.clients) {
                                if (c.getNome().equals(nome)) {
                                    lista += c.getMensagens();
                                }   // Pega todas as mensagens enviadas para o usuário atual.
                            }

                            output.writeUTF("\n\nMensagens recebidas:\n" + lista + "\n\nDigite algo e pressione Enter para continuar: ");
                            input.readUTF();

                        }


                        // Se opção escolhida não for 1 ou 2 ou 3:
                        else {

                            Server.clientNamesOnline.remove(nome);

                            output.writeUTF("sair");
                            break;  // Encerra loop while e retorna à tela de escolhas iniciais.

                        }

                    }

                    Server.clientNamesOnline.remove(nome);

                }


                // Se o usuário não for autenticado:
                else {

                    Server.clientNamesOnline.remove(nome);

                    output.writeUTF("\n\nAutenticação não confirmada. Tente Novamente!\n\n");

                }


            }

            // Se o usuário escolher sair:
            else if (choice.equals("sair")) {
                output.writeUTF("saindo da base...");
                this.stop();
            }

            // Se o usuário digitar alguma opção que não seja cadastrar, logar ou sair:
            else{ this.stop(); }


        } catch (IOException | InterruptedException e) {
            e.getSuppressed();
        }

    }


}
