package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ConnectionIn implements Runnable {
    public Thread runningThis;
    public Socket socket;
    public Scanner input;
    public long connectionId = 0;
    public  boolean threadRunning = true;

    public BlockingQueue queue = null;

    ConnectionIn(ConnectionHandler connHandler, Socket newSocket, BlockingQueue queue) throws IOException {
        socket = newSocket;
        input = new Scanner(socket.getInputStream());
        this.queue = queue;
    }

    @Override
    public void run() {
        while(threadRunning){
            while(input.hasNextLine()){
                String strIn = input.nextLine();

                if(connectionId != 0) System.out.println("Connection "+connectionId+" >"+strIn);
                else System.out.println("Server >"+strIn);

                try {
                    queue.put(connectionId+" "+strIn);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
