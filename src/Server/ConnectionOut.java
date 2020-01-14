package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionOut {
    public Socket socket;
    public PrintWriter output;
    long connectionId = 0;

    ConnectionOut(ConnectionHandler connHandler, Socket newSocket) throws IOException {
        socket = newSocket;
        output = new PrintWriter(socket.getOutputStream());
    }


    public boolean send(String message) {
        try{
            output.println(message);
            output.flush();
            if(connectionId == 0)
                System.out.println("Send: "+message);
            else
                System.out.println("Send to "+connectionId+": "+message);
            return true;
        }catch(Exception e){
            if(connectionId == 0)
                System.out.println("Failed to send: "+message);
            else
                System.out.println("Failed to send to "+connectionId+": "+message);
            return false;
        }
    }
}
