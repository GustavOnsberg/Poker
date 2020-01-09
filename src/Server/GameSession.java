package Server;

public class GameSession implements Runnable {

    private long sessionId;

    public GameSession(long sessionId){
        this.sessionId = sessionId;
    }


    @Override
    public void run() {

    }


    public long getSessionId() {
        return sessionId;
    }
}
