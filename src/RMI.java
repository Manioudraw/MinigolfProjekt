import java.rmi.Remote; 
import java.rmi.RemoteException;  

public interface RMI extends Remote {

void printmsg() throws RemoteException ;

}