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

    long lastReciveTime = 0;

    ConnectionHandler(Socket newSocket){
        socket = newSocket;
        lastReciveTime = System.currentTimeMillis();
    }

    public void run() {
        System.out.println(System.currentTimeMillis() - System.currentTimeMillis());
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
                    lastReciveTime = System.currentTimeMillis();
                }
            }

            //Print stuff to client
        }
    }
}
