import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements ChatInterface {
    private List<String> messages;

    public ChatServer() throws RemoteException {
        messages = new ArrayList<>();
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        messages.add(message);
    }

    @Override
    public List<String> getAllMessages() throws RemoteException {
        return messages;
    }
}