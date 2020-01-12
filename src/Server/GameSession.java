package server;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class GameSession implements Runnable {

    private String name = "";
    private long sessionId;
    private String password = "";
    public Thread runningThis;


    public ArrayList<BlockingQueue> queues = new ArrayList<BlockingQueue>();
    public ArrayList<ConnectionHandler> connectionHandlers = new ArrayList<ConnectionHandler>();

    public GameSession(long sessionId,String name, String password){
        this.sessionId = sessionId;
        this.name = name;
        this.password = password;
    }


    @Override
    public void run() {
        while(true){
            for(int i = 0; i < connectionHandlers.size(); i++){
                if (!connectionHandlers.get(i).queue.isEmpty()){
                    try {
                        sessionDo(connectionHandlers.get(i).queue.take().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public long getSessionId() {
        return sessionId;
    }
    public String getPassword() {
        return password;
    }

    public void sessionDo(String input) throws Exception {
        String[] inputArray = input.split(" ");

        if(inputArray.length > 1){
            switch(inputArray[1]){
                case "chat":
                    try{
                        long fromId = Long.parseLong(inputArray[0]);
                        for(int i = 0; i < connectionHandlers.size(); i++){
                            connectionHandlers.get(i).out.send("chat "+fromId+" "+input.substring(6));
                        }
                    }catch (Exception e){}
                    break;
            }
        }
    }
}
