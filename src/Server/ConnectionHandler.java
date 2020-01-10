package Server;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConnectionHandler{

    public Socket socket;
    public Scanner input;
    public PrintWriter output;

    public ConnectionIn connIn;
    public ConnectionOut connOut;

    ConnectionHandler(Socket newSocket) throws IOException {
        socket = newSocket;

        connIn = new ConnectionIn(this, socket);
        connIn.runningThis = new Thread(connIn);
        connIn.runningThis.start();

        connOut = new ConnectionOut(this, socket);
    }
}
