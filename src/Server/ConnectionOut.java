package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionOut {
    public Socket socket;
    public PrintWriter output;

    ConnectionOut(ConnectionHandler connHandler, Socket newSocket) throws IOException {
        socket = newSocket;
        output = new PrintWriter(socket.getOutputStream());
    }


    public boolean send(String message) {
        try{
            output.println(message);
            output.flush();
            System.out.println("Send: "+message);
            return true;
        }catch(Exception e){
            System.out.println("Failed to send: "+message);
            return false;
        }
    }
}
