package Server;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionHandler implements Runnable{

    public Thread runningThis;
    public Socket socket;
    public Scanner input;
    public PrintWriter output;

    ConnectionHandler(Socket newSocket){
        socket = newSocket;
    }

    public void run() {
        try {
            input = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            output = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            while(input.hasNextLine()) {
                String strIn = input.nextLine();
                if (strIn.equals("")){
                    break;
                }
                else{
                    //do stuff
                }
            }

            //Print stuff to client
        }
    }
}
