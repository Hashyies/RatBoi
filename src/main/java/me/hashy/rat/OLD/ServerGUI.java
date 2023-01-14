package me.hashy.rat.OLD;

import java.awt.*;

import javax.swing.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;


public class ServerGUI extends JFrame {

    private JTextField command;
    private JTextArea log;
    private JButton send;

    private Server server;

    public ServerGUI() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super("Server");

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        command = new JTextField(20);
        send = new JButton("Send Command");

        send.addActionListener(e -> {
            String cmd = command.getText();

            log.append("Sending Command: " + cmd + "\n");

            //Send the command to the client
            server.sendToAllTCP(cmd);
        });

        topPanel.add(command);
        topPanel.add(send);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        log = new JTextArea();
        log.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(log);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        //Start the server
        server = new Server();
        server.start();

        //Bind the server to a port
        try {
            server.bind(50000);
            System.out.println("Server Bound to Port 50000");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Add a listener to the server
        server.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object object) {
                //Get the client output
                if(!(object instanceof String output))
                    return;

                //Print the output to the log
                log.append("Client Output:\n" + output + "\n");

            }
        });

        //Set the size and visibility of the frame

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        new ServerGUI();
    }
}