package netzwerk;

import java.rmi.Naming;

public class server {
    public static void main(String[] args) {
        try {
            RMI stub = new RMI();
            Naming.rebind("rmi://localhost:5000/test", stub);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
