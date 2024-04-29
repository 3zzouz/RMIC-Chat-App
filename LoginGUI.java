import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoginGUI {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JFrame frame;

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ChatInterface server = (ChatInterface) registry.lookup("ChatServer");
            new LoginGUI(server);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LoginGUI(ChatInterface server) {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usernameField = new JTextField(30);
        passwordField = new JPasswordField(30);
        loginButton = new JButton("Login");

        registerButton = new JButton("Register");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    if (server.isLoggedIn(name)) {
                        JOptionPane.showMessageDialog(frame, "User already logged in.");
                    } else {
                        if (server.login(name, password)) {
                            frame.dispose();
                            new ChatClient(name);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Invalid username or password");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new RegistrationGUI(server);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}