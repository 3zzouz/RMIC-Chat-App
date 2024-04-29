import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

//on implemente l'interface ChatInterface pour pouvoir 
//utiliser les methodes du serveur qui sont décrites dans cette interface
//on etend UnicastRemoteObject pour pouvoir appeler les
//methodes de cette classe a distance
public class ChatClient extends UnicastRemoteObject implements ChatInterface {
    private ChatInterface server;
    private ChatGUI chatGUI;
    private String name;

    public ChatClient(String host, int port, String name) throws RemoteException, NotBoundException {
        super();
        this.name = name;
        Registry registry = LocateRegistry.getRegistry(host, port);
        server = (ChatInterface) registry.lookup("ChatServer");
        server.registerClient(this);
    }

    public void sendMessage(String message) {
        try {
            String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
            String formattedMessage = this.name + " (" + timestamp + "): " + message;
            server.sendMessage(formattedMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        boolean isSender = message.startsWith(this.name);
        chatGUI.displayMessage(message, isSender);
    }

    @Override
    public void registerClient(ChatInterface client) throws RemoteException {
        // This method is not used in the client
    }

    public void setChatGUI(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;
    }
}