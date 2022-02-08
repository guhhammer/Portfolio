package Semana15;

import java.rmi.Remote;
import java.rmi.RemoteException;


     /*
     *
     *      INTERFACE DO CONSUMIDOR.
     *
     */

public interface IConsumer extends Remote {

    String buscaProduto(String nome) throws RemoteException;

}
