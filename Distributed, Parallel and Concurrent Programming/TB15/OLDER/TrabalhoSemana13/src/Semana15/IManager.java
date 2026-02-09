package Semana15;

import java.rmi.*;


    /*
    *
    *       INTERFACE DO ADMINISTRADOR.
    *
    */


public interface IManager extends Remote{

    String seeSearchHistory() throws RemoteException;

    int verTMAX() throws RemoteException;

    void atualizarTMAX(int TMAX) throws RemoteException;

}
