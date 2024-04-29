import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatGUI {
    private JFrame frame;
    private JTextPane chatPane;
    private JTextField messageField;
    private JButton sendButton;

    public ChatGUI(ChatClient chatClient, String clientName) {

        frame = new JFrame("Chat Application :"+clientName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        chatPane = new JTextPane();
        chatPane.setContentType("text/html");
        chatPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatPane);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        messageField = new JTextField();
        bottomPanel.add(messageField);

        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    try {
                        chatClient.sendMessage(message);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    //displayMessage(message, true);
                    messageField.setText("");
                }
            }
        });
        bottomPanel.add(sendButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void displayMessage(String message, boolean isSender) {
        String alignment = isSender ? "right" : "left";
        String color = isSender ? "blue" : "red";
        String formattedMessage = String.format("<p align='%s'><font color='%s'>%s</font></p>", alignment, color,
                message);
        String currentText = chatPane.getText();
        int bodyCloseIndex = currentText.lastIndexOf("</body>");
        String newText = currentText.substring(0, bodyCloseIndex) + formattedMessage
                + currentText.substring(bodyCloseIndex);
        chatPane.setText(newText);
    }
    public void clearOutput() {
        chatPane.setText("");
    }
}