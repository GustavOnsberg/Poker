package server;

import java.util.ArrayList;

public class Server {
    static public ArrayList<GameSession> gameSessions = new ArrayList<GameSession>();
    static public ArrayList<ConnectionHandler> connectionHandlers = new ArrayList<ConnectionHandler>();
    static Listener listener = new Listener();
    static Thread threadListner = new Thread(listener);
    static boolean running = true;
    public static NonGameConnectionsHandler nonGameConnectionsHandler = new NonGameConnectionsHandler();
    static  Thread threadNonGameConnectionsHandler = new Thread(nonGameConnectionsHandler);
    public static long lastGameSessionId = 0;


    Server(){

    }

    public static void main(String [] args){
        threadListner.start();
        threadNonGameConnectionsHandler.start();
    }





    public static GameSession getGameSessionFromId(long id) throws Exception{
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

            if(lastId == id){
                return gameSessions.get(lastCheck);
            }

            if(lastJump == 0){
                throw new Exception("Session not found");
            }
        }
    }


    public static ConnectionHandler getConnectionHandlerFromId(long id, ArrayList<ConnectionHandler> connHandlers) throws Exception{
        int lastJump = connHandlers.size();
        int lastCheck = 0;
        long lastId = 0;
        while(true){
            if (lastId < id){
                lastJump/=2;
                lastCheck+=lastJump;
                lastId = connHandlers.get(lastCheck).connectionId;
            }
            else if(lastId > id){
                lastJump/=2;
                lastCheck-=lastJump;
                lastId = connHandlers.get(lastCheck).connectionId;
            }

            if(lastId == id){
                return connHandlers.get(lastCheck);
            }

            if(lastJump == 0){
                throw new Exception("Connection not found");
            }
        }
    }

    public static ConnectionHandler getConnectionHandlerFromId(long id) throws Exception{
        return getConnectionHandlerFromId(id, connectionHandlers);
    }
}
