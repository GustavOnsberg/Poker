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


    public void send(String message) throws IOException {
        output.println(message);
        output.flush();
    }
}
