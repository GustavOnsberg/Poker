package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener implements Runnable {
    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(33201);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            Socket clientSocket = null;

            try {
                clientSocket = serverSocket.accept();
                Server.connectionHandlers.add(new ConnectionHandler(clientSocket));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
