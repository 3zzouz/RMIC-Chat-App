import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements ChatInterface {
    private List<String> messages;
    private List<User> users = new ArrayList<>();

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

    @Override
    public boolean registerUser(User user) throws RemoteException {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                return false; // Username already taken
            }
        }
        users.add(user);
        return true;
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                u.setLoggedIn(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isLoggedIn(String username) throws RemoteException {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u.isLoggedIn();
            }
        }
        return false;
    }

    @Override
    public void logout(String username) throws RemoteException {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                u.setLoggedIn(false);
                break;
            }
        }
    }
}