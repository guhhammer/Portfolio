package search;

import java.rmi.Remote;
import java.rmi.RemoteException;

/** Remote interface used by the administrator console. */
public interface IManager extends Remote {
    String seeSearchHistory() throws RemoteException;
    int getTimeout() throws RemoteException;
    void setTimeout(int timeoutMs) throws RemoteException;
}
