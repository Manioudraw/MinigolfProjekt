package netzwerk;

import java.rmi.Naming;

public class client {
    public static void main(String[] args) {
        try {
            RMIInterface stub = (RMIInterface) Naming.lookup("rmi://localhost:5000/test");  
            System.out.println(stub.sayHello("Hello World!"));  
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
