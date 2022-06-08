package netzwerk;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
    public String sayHello(String str) throws RemoteException;
}
