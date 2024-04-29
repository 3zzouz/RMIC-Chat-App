import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class ChatGUI {
    private ChatClient chatClient;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    public ChatGUI(ChatClient chatClient) {
        this.chatClient = chatClient;

        JFrame frame = new JFrame("Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea(10, 30);
        messageField = new JTextField(30);
        sendButton = new JButton("Send");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chatClient.sendMessage(messageField.getText());
                    messageField.setText("");
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(chatArea));
        panel.add(messageField);
        panel.add(sendButton);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void displayMessage(String message) {
        chatArea.append(message + "\n");
    }
}