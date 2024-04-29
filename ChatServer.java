import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements ChatInterface {
    private List<ChatInterface> clients;

    public ChatServer() throws RemoteException {
        clients = new ArrayList<>();
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        for (ChatInterface client : clients) {
            client.receiveMessage(message);
        }
    }

    @Override
    public void registerClient(ChatInterface client) throws RemoteException {
        clients.add(client);
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        // This method is not used in the server
    }
}