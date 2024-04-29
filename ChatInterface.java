import java.rmi.*;
import java.util.List;

public interface ChatInterface extends Remote {
    public void sendMessage(String message) throws RemoteException;
    public List<String> getAllMessages() throws RemoteException;
}