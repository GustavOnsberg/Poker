package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener implements Runnable {
    long lastConnectionId = 0;
    int port = 33201;


    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listener >Socket opened on port "+port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            Socket clientSocket = null;

            try {
                System.out.println("Listener >Waiting for new connection");
                clientSocket = serverSocket.accept();
                Server.connectionHandlers.add(new ConnectionHandler(clientSocket, lastConnectionId+1));
                lastConnectionId++;
                System.out.println("Listener >New connection established with id "+lastConnectionId);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
