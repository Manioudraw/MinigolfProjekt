import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;

public class Server extends Implement {
	public Server() {}
	public static void main(String[] args) {
	} {
		try {
			Implement obj = new Implement(); 
			
			RMI stub = (RMI) UnicastRemoteObject.exportObject(obj,0);
			
			Registry registry = LocateRegistry.getRegistry();
			
			registry.bind("RMI", stub);
			System.err.println("Server ready");
		}catch(Exception e) {
			System.err.println("Sever exception: " + e.toString());
			e.printStackTrace();
		
			}
		}

}
