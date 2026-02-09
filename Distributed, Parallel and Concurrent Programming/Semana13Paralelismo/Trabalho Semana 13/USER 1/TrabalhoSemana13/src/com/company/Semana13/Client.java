package com.company.Semana13;

import java.util.ArrayList;

public class Client {

    /*
    *
    *       ESTA CLASSE É USADA PARA SE TER UMA REPRESENTAÇÃO EM MEMÓRIA DO CLIENTE
    *       E FACILITAR TRANSFERÊNCIA DE DADOS ENTRE CLIENTES. TAMBÉM AUXILIA NO
    *       ARMAZENAMENTO DE CLIENTES EM ARQUIVO RÍGIDO.
    *
    * */

    // Atributos de Cliente.
    private String nome, senha, enderecoIP;
    private int porta;
    private ArrayList<String[]> mensagens;

    // Construtor.
    public Client(String nome, String senha, String enderecoIP, int porta){

        this.nome = nome; this.senha = senha; this.enderecoIP = enderecoIP; this.porta = porta;
        this.mensagens = new ArrayList<>();

    }

    // Métodos get():
    public String getNome(){ return this.nome; }

    public String getSenha(){ return this.senha; }

    public String getEnderecoIP(){ return this.enderecoIP; }

    public int getPorta(){ return  this.porta; }

    public String getMensagens(){
        String ret = "";
        for(String[] m : mensagens){ ret += "\tDe: "+m[0]+" | Mensagem: "+m[1]+" \n";}
        if(mensagens.isEmpty()){ ret = "\tNenhuma mensagem!\n"; }
        return ret;
    }

    public String getMessageData(){

        if(this.mensagens.isEmpty()){ return "[]";}

        String data = "[";
        for(int i = 0; i < this.mensagens.size(); i++){
            data += "["+this.mensagens.get(i)[0]+","+this.mensagens.get(i)[1]+"]";
            if( i != this.mensagens.size()-1){ data+=","; }
        }

        return data+"]";

    }


    // Método para criação de nova mensagem.
    public void novaMensagem(String de, String mensagem){   mensagens.add(new String[]{de, mensagem});  }

    // Método para representação de Cliente(Usuário) no kernel da Classe Server.
    public String toString(){
        return "Client: "+this.nome+" | IP: "+enderecoIP+" | Porta: "+this.porta;
    }


}
