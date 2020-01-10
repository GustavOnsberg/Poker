package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionIn implements Runnable {
    public Thread runningThis;
    public Socket socket;
    public Scanner input;
    public long connectionId = 0;

    protected BlockingQueue queue = null;

    ConnectionIn(ConnectionHandler connHandler, Socket newSocket, BlockingQueue queue) throws IOException {
        socket = newSocket;
        input = new Scanner(socket.getInputStream());
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            while(input.hasNextLine()){
                String strIn = input.nextLine();

                System.out.println(socket.getInetAddress().toString()+":"+socket.getPort()+"> "+strIn);

                try {
                    queue.put(connectionId+" "+strIn);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
