package search;

import java.rmi.Remote;
import java.rmi.RemoteException;

/** Remote interface used by the customer console. */
public interface IConsumer extends Remote {
    String searchProduct(String name) throws RemoteException;
}
