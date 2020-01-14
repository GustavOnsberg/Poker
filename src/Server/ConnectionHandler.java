package server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionHandler{

    public Socket socket;
    public ConnectionIn in;
    public ConnectionOut out;
    public long connectionId = 0;
    public long lastHeatBeat = System.currentTimeMillis();
    public BlockingQueue queue = new ArrayBlockingQueue(64);

    PlayerState playerState = PlayerState.Out;
    int money = 10000;
    int card0 = 53;
    int card1 = 53;
    int bet = 0;
    boolean hasHadChanceToBet = false;

    public ConnectionHandler(Socket newSocket, long connectionId) throws IOException {
        socket = newSocket;
        this.connectionId = connectionId;

        Server.nonGameConnectionsHandler.queues.add(queue);

        in = new ConnectionIn(this, socket, queue);
        in.connectionId = connectionId;
        in.runningThis = new Thread(in);
        in.runningThis.start();

        out = new ConnectionOut(this, socket);
        out.connectionId = connectionId;
    }
}
