package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class NonGameConnectionsHandler implements Runnable{
    ArrayList<BlockingQueue> queues = new ArrayList<BlockingQueue>();

    public NonGameConnectionsHandler(){

    }


    @Override
    public void run() {
        while(true){
            for(int i = 0; i < Server.connectionHandlers.size(); i++){
                if (!Server.connectionHandlers.get(i).queue.isEmpty()){
                    try {
                        serverDo(Server.connectionHandlers.get(i).queue.take().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(System.currentTimeMillis() - Server.connectionHandlers.get(i).lastHeatBeat > 60000){
                    try {
                        long removedId = Server.connectionHandlers.get(i).connectionId;
                        Server.connectionHandlers.get(i).in.threadRunning = false;
                        Server.connectionHandlers.get(i).socket.close();
                        Server.connectionHandlers.remove(i);
                        System.out.println("NGCH >Connection with id "+removedId+" has benn removed due to missing heartbeat");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    i--;
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    public void serverDo(String input) throws Exception {
        String[] inputArray = input.split(" ");


        if(inputArray.length > 1){
            switch(inputArray[1]){
                case "create":
                    if(inputArray.length > 3){
                        Server.gameSessions.add(new GameSession(Server.lastGameSessionId + 1, inputArray[2],inputArray[3]));
                        Server.lastGameSessionId++;
                        Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])).out.send("gc");

                        Server.gameSessions.get(Server.gameSessions.size()-1).connectionHandlers.add(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])));
                        Server.connectionHandlers.remove(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])));
                    }
                    else if(inputArray.length > 2){
                        Server.gameSessions.add(new GameSession(Server.lastGameSessionId + 1, inputArray[2],""));
                        Server.lastGameSessionId++;
                        Server.gameSessions.get(Server.gameSessions.size()-1).runningThis = new Thread(Server.gameSessions.get(Server.gameSessions.size()-1));
                        Server.gameSessions.get(Server.gameSessions.size()-1).runningThis.start();
                        Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])).out.send("gc");

                        Server.gameSessions.get(Server.gameSessions.size()-1).connectionHandlers.add(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])));
                        Server.connectionHandlers.remove(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])));
                    }
                    break;
                case "join":
                    if(inputArray.length > 3){
                        if (inputArray[2].equals(Server.getGameSessionFromId(Long.parseLong(inputArray[1])).getPassword())){
                            Server.getGameSessionFromId(Long.parseLong(inputArray[2])).connectionHandlers.add(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])));
                            Server.connectionHandlers.remove(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])));
                            Server.getGameSessionFromId(Long.parseLong(inputArray[2])).connectionHandlers.get(Server.getGameSessionFromId(Long.parseLong(inputArray[2])).connectionHandlers.size()-1).out.send("gj");
                        }
                        else{
                            Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])).out.send("wp");
                        }
                    }
                    else if(inputArray.length > 2){
                        System.out.println(input);
                        Server.getGameSessionFromId(Long.parseLong(inputArray[2])).connectionHandlers.add(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])));
                        Server.connectionHandlers.remove(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])));
                        Server.getGameSessionFromId(Long.parseLong(inputArray[2])).connectionHandlers.get(Server.getGameSessionFromId(Long.parseLong(inputArray[2])).connectionHandlers.size()-1).out.send("gj");
                    }
                    break;
                case "gg":
                    int gamesSend = 0;
                    for(int i = 0; i < Server.gameSessions.size(); i++){
                        if(Server.gameSessions.get(i).getPassword().equals("") && gamesSend < 16){
                            Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])).out.send("gs "+Server.gameSessions.get(i).getSessionId()+" "+Server.gameSessions.get(i).getName()+" "+Server.gameSessions.get(i).connectionHandlers.size()+" "+Server.gameSessions.get(i).getMaxPlayers());
                            gamesSend++;
                        }
                    }
                    break;
                case "hb":
                    Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])).lastHeatBeat = System.currentTimeMillis();
                    break;
            }
        }
    }
}
