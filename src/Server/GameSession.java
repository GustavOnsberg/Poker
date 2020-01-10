package server;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class GameSession implements Runnable {

    private long sessionId;
    private String password = "";


    ArrayList<BlockingQueue> queues;

    public GameSession(long sessionId){
        this.sessionId = sessionId;
    }


    @Override
    public void run() {

    }


    public long getSessionId() {
        return sessionId;
    }
    public String getPassword() {
        return password;
    }
}
