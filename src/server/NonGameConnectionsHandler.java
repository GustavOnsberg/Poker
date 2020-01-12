package server;

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
                        Server.gameSessions.get(Server.gameSessions.size()-1).queues.add(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])).queue);
                        queues.remove(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])).queue);
                        Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])).out.send("gc");

                        Server.gameSessions.get(Server.gameSessions.size()-1).connectionHandlers.add(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])));
                        Server.connectionHandlers.remove(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])));
                    }
                    else if(inputArray.length > 2){
                        Server.gameSessions.add(new GameSession(Server.lastGameSessionId + 1, inputArray[2],""));
                        Server.lastGameSessionId++;
                        Server.gameSessions.get(Server.gameSessions.size()-1).runningThis = new Thread(Server.gameSessions.get(Server.gameSessions.size()-1));
                        Server.gameSessions.get(Server.gameSessions.size()-1).runningThis.start();
                        Server.getGameSessionFromId(Server.lastGameSessionId).queues.add(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])).queue);
                        queues.remove(Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0])).queue);
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
            }
        }
    }
}
