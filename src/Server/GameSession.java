package server;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class GameSession implements Runnable {

    private String name = "";
    private long sessionId;
    private String password = "";
    public Thread runningThis;


    public ArrayList<BlockingQueue> queues = new ArrayList<BlockingQueue>();

    public GameSession(long sessionId,String name, String password){
        this.sessionId = sessionId;
        this.name = name;
        this.password = password;
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
