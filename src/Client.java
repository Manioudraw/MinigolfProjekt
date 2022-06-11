import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	private Client() {}
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry(null);
			
			RMI stub = (RMI) registry.lookup("RMI");
			
			stub.printmsg();
			
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
		
	}
}
