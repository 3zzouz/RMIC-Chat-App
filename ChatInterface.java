import java.rmi.*;
import java.util.List;

public interface ChatInterface extends Remote {
    public void sendMessage(String message) throws RemoteException;

    public List<String> getAllMessages() throws RemoteException;

    boolean registerUser(User user) throws RemoteException;

    boolean login(String username, String password) throws RemoteException;

    boolean isLoggedIn(String username) throws RemoteException;
}