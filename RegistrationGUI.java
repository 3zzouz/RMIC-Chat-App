import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.FlowLayout;

public class RegistrationGUI {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JFrame frame;

    public RegistrationGUI(ChatInterface server) {
        frame = new JFrame("Registration");
        frame.setLayout(new FlowLayout());
        usernameField = new JTextField(30);
        passwordField = new JPasswordField(30);
        frame.add(new JLabel("Username:"));
        frame.add(usernameField);
        frame.add(new JLabel("Password:"));
        frame.add(passwordField);
        registerButton = new JButton("Register");
        frame.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    if (username.equals("") || password.equals("")) {
                        JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.");
                    } else {
                        if (password.length() < 8) {
                            JOptionPane.showMessageDialog(frame, "Password must be at least 8 characters long.");
                        } else {
                            if (server.isLoggedIn(username)) {
                                JOptionPane.showMessageDialog(frame, "User already logged in.");
                            } else {
                                if (server.registerUser(new User(username, password))) {
                                    JOptionPane.showMessageDialog(frame, "Registration successful. Please login.");
                                    frame.dispose();
                                    new LoginGUI(server);
                                } else {
                                    JOptionPane.showMessageDialog(frame,
                                            "Username already taken. Please choose another.");
                                }
                            }
                        }
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        frame.pack();
        frame.setVisible(true);
    }
}