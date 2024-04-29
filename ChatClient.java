import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatClient extends UnicastRemoteObject implements ChatInterface {
    private ChatInterface server;
    private ChatGUI chatGUI;
    private String name;

    public ChatClient(String host, int port,String name) throws RemoteException, NotBoundException {
        super();
        this.name = name;
        Registry registry = LocateRegistry.getRegistry(host, port);
        server = (ChatInterface) registry.lookup("ChatServer");
        server.registerClient(this);
    }

    public void sendMessage(String message) throws RemoteException {
        String timeSent = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        message = this.name + " (" + timeSent + "): " + message;
        server.sendMessage(message);
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        chatGUI.displayMessage(message);
    }

    @Override
    public void registerClient(ChatInterface client) throws RemoteException {
        // This method is not used in the client
    }

    public void setChatGUI(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;
    }
}