package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class GameSession implements Runnable {

    private String name = "";
    private long sessionId;
    private String password = "";
    private int maxPlayers = 8;
    public Thread runningThis;

    GameState gameState = GameState.Setup;
    RoundState roundState = RoundState.PreFlop;
    int dealer = 0;
    int smallBlind = 0;
    int bigBlind = 0;

    GameLogic gameLogic = new GameLogic();

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
                else if(System.currentTimeMillis() - connectionHandlers.get(i).lastHeatBeat > 60000){
                    try {
                        long removedId = connectionHandlers.get(i).connectionId;
                        connectionHandlers.get(i).socket.close();
                        connectionHandlers.remove(i);
                        System.out.println("Game Session "+sessionId+" >Connection with id "+removedId+" has benn removed due to missing heartbeat");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    i--;
                }
            }





            if(gameState == GameState.Ready && connectionHandlers.size() > 1){
                for(int i = 0; i < connectionHandlers.size(); i++){
                    if(connectionHandlers.get(i).playerState == PlayerState.Folded){
                        connectionHandlers.get(i).playerState = PlayerState.Playing;
                    }
                }
                distributeButton(0);
                dealCards();
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
    public String getName() {
        return name;
    }
    public int getMaxPlayers() {
        return maxPlayers;
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

    public void distributeButton(int dealer){
        this.dealer = dealer%connectionHandlers.size();
        smallBlind = (dealer+1)%connectionHandlers.size();
        bigBlind = (dealer+2)%connectionHandlers.size();

        broadcast("setup dealer "+dealer);
        broadcast("setup smallblind "+smallBlind);
        broadcast("setup bigblind "+bigBlind);
    }

    public void dealCards(){
        for(int i = 0; i < connectionHandlers.size(); i++){
            connectionHandlers.get(i).card0 = gameLogic.takeCard();
            connectionHandlers.get(i).out.send("setup deal card0 "+connectionHandlers.get(i).card0);
        }
        for(int i = 0; i < connectionHandlers.size(); i++){
            connectionHandlers.get(i).card1 = gameLogic.takeCard();
            connectionHandlers.get(i).out.send("setup deal card1 "+connectionHandlers.get(i).card1);
        }
    }

    public void broadcast(String message){
        for(int i = 0; i < connectionHandlers.size(); i++){
            connectionHandlers.get(i).out.send(message);
        }
    }
}
