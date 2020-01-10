package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionIn implements Runnable {
    public Thread runningThis;
    public Socket socket;
    public Scanner input;

    ConnectionIn(ConnectionHandler connHandler, Socket newSocket) throws IOException {
        socket = newSocket;
        input = new Scanner(socket.getInputStream());
    }

    @Override
    public void run() {
        while(true){
            while(input.hasNextLine()){
                String strIn = input.nextLine();

                System.out.println(socket.getInetAddress().toString()+":"+socket.getPort()+"> "+strIn);

            }
        }
    }
}
