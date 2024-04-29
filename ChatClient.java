import java.util.List;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClient {
    private ChatInterface server;
    private ChatGUI chatGUI;
    private String name;
    private int lastDisplayedMessageIndex = 0;

    public ChatClient(String name) throws RemoteException, NotBoundException {
        this.name = name;
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        this.server = (ChatInterface) registry.lookup("ChatServer");
        setChatGUI(new ChatGUI(this, name));
        this.startFetchingMessages();
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

    public void setChatGUI(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;
    }

    public void getAllMessages() {
        List<String> messages;
        try {
            messages = server.getAllMessages();
            for (int i = lastDisplayedMessageIndex; i < messages.size(); i++) {
                String msg = messages.get(i);
                boolean isSender = msg.startsWith(this.name);
                chatGUI.displayMessage(msg, isSender);
            }
            lastDisplayedMessageIndex = messages.size();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void startFetchingMessages() {
        new Thread(() -> {
            while (true) {
                this.getAllMessages();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}