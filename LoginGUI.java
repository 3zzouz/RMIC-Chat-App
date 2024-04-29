import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LoginGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField nameField = new JTextField(30);
        JButton joinButton = new JButton("Join");

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                if (!name.isEmpty()) {
                    try {
                        ChatClient chatClient = new ChatClient("localhost", 1099, name);
                        ChatGUI chatGUI = new ChatGUI(chatClient,name);
                        chatClient.setChatGUI(chatGUI);
                        frame.dispose();
                    } catch (RemoteException | NotBoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(nameField);
        panel.add(joinButton);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}