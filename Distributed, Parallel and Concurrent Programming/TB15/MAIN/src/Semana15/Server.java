package Semana15;

import java.net.*;
import java.rmi.registry.LocateRegistry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.concurrent.Semaphore;

    /*
    *
    *     SERVIDOR QUE FAZ O INTERMÉDIO DAS COMUNICAÇÕES CLIENTE, ADMINISTRADOR E LOJA.
    *
    */

public class Server extends UnicastRemoteObject implements IConsumer, IManager{

    public int TMAX = 5000, port = 5000;

    public String addressName = "127.0.0.1";

    public static ArrayList<History> historico = new ArrayList<>();

    public UniqueID UIDCreator = new UniqueID();

    public static Semaphore mutex = new Semaphore(1);

    public Server() throws RemoteException { super(); }

    private String finder(String UID){

        String resultados = "\n";

        for(History h : historico){

            if(h.getUID().equals(UID)){
                resultados += h.formatBusca();
            }

        }

        return resultados+"\n";

    }

    @Override
    public String buscaProduto(String nome) {

        String UID = UIDCreator.getUID();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        String busca = dateFormat.format(date)+"--SEPARATOR--"+UID+"--SEPARATOR--"+nome;

        ServerThreadSENDER STM = new ServerThreadSENDER(busca);

        STM.start();

        try{ Thread.sleep(TMAX); }catch (Exception e){ e.getSuppressed(); }

        return finder(UID);

    }

    @Override
    public String seeSearchHistory() {

        String retorno = ("\n\nHistórico:\n\n");
        for(History h : historico){

            retorno += ">>"+h.formatHistory()+"\n>>\n\n";

        }

        return retorno;

    }

    @Override
    public int verTMAX() throws RemoteException {  return TMAX; }

    @Override
    public void atualizarTMAX(int TMAX) throws RemoteException { this.TMAX = TMAX; }

    public static void printer(String aux){ System.out.println(aux); }

    public static void main(String[] args) throws SocketException {

        ServerThreadRECEIVER STU = new ServerThreadRECEIVER();

        STU.start();

        String nomeObjetoRemotoClient = "ClientConsole";
        String nomeObjetoRemotoManager = "ManagerConsole";

        try {

            Server server = new Server();

            LocateRegistry.createRegistry(2522);
            //Runtime.getRuntime().exec("rmiregistry 2522");

            Naming.rebind("rmi://localhost:2522/"+nomeObjetoRemotoManager, server);
            Naming.rebind("rmi://localhost:2522/"+nomeObjetoRemotoClient, server);

            System.out.println ("\n\nServer is online.\n\n");

            while (true){

                try{ Thread.sleep(10000);} catch (Exception e){ e.getSuppressed(); }
                printer(Arrays.toString(historico.toArray()));

            }

        }
        catch (Exception e){ e.getSuppressed(); }

    }


}
