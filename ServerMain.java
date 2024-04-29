import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        ChatServer server = new ChatServer();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("ChatServer", server);
        System.out.println("Server started");
    }
}