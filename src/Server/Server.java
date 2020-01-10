package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class Server {
    static ArrayList<GameSession> gameSessions = new ArrayList<GameSession>();
    static ArrayList<ConnectionHandler> connectionHandlers = new ArrayList<ConnectionHandler>();
    static Listener listener = new Listener();
    static Thread threadListner = new Thread(listener);
    static boolean running = true;
    public static NonGameConnectionsHandler nonGameConnectionsHandler = new NonGameConnectionsHandler();
    static  Thread threadNonGameConnectionsHandler = new Thread(nonGameConnectionsHandler);


    Server(){

    }

    public static void main(String [] args){
        threadListner.start();
        threadNonGameConnectionsHandler.start();
    }





    public GameSession getGameSessionFromId(long id) throws Exception{
        int lastJump = gameSessions.size();
        int lastCheck = 0;
        long lastId = 0;
        while(true){
            if (lastId < id){
                lastJump/=2;
                lastCheck+=lastJump;
                lastId = gameSessions.get(lastCheck).getSessionId();
            }
            else if(lastId > id){
                lastJump/=2;
                lastCheck-=lastJump;
                lastId = gameSessions.get(lastCheck).getSessionId();
            }
            else{
                return gameSessions.get(lastCheck);
            }

            if(lastJump == 0){
                throw new Exception("Session not found");
            }
        }
    }
}