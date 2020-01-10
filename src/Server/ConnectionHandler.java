package server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionHandler{

    public Socket socket;

    public ConnectionIn in;
    public ConnectionOut out;

    BlockingQueue queue = new ArrayBlockingQueue(64);

    public ConnectionHandler(Socket newSocket) throws IOException {
        socket = newSocket;

        Server.nonGameConnectionsHandler.queues.add(queue);

        in = new ConnectionIn(this, socket, queue);
        in.runningThis = new Thread(in);
        in.runningThis.start();

        out = new ConnectionOut(this, socket);
    }
}
