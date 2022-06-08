package netzwerk;

import java.rmi.*;
import java.rmi.server.*;

public class RMI extends UnicastRemoteObject implements RMIInterface {
    public RMI () throws RemoteException{
        System.out.println("RMI aufgerufen");
    }

    public String sayHello(String str) throws RemoteException {
        return str;
    }
}