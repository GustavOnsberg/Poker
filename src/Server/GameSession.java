package server;

import java.io.IOException;
import java.util.ArrayList;

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
    int turn = 0;
    boolean limit = false;
    int pot = 0;
    int currentBet = 0;
    int peopleAtTable = 0;

    ArrayList<Integer> pots = new ArrayList<Integer>();
    ArrayList<Integer> potLimits = new ArrayList<Integer>();

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

            if(peopleAtTable != connectionHandlers.size()){
                sendPlaceAtTable();
            }





            if(gameState == GameState.Ready && connectionHandlers.size() > 1){
                for(int i = 0; i < connectionHandlers.size(); i++){
                    if(connectionHandlers.get(i).playerState == PlayerState.Folded){
                        connectionHandlers.get(i).playerState = PlayerState.Playing;
                    }
                }
                distributeButton(0);
                dealCards();
                gameState = GameState.InProgress;
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
                case "hb":
                    Server.getConnectionHandlerFromId(Long.parseLong(inputArray[0]), connectionHandlers).lastHeatBeat = System.currentTimeMillis();
                    break;
                case "command":
                    command(input);
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

    public void command(String command){
        String[] inputArray = command.split(" ");
        long gameOwner = connectionHandlers.get(0).connectionId;
        long commander = Long.parseLong(inputArray[0]);
                switch (inputArray[2]){
            case "start":
                if(commander == gameOwner)
                    startGame();
                break;
        }
    }

    public boolean startGame(){
        if(connectionHandlers.size() > 1){
            gameState = GameState.Ready;
            return true;
        }
        return false;
    }

    public void move(String move){
        String[] inputArray = move.split(" ");
        long mover = Long.parseLong(inputArray[0]);
        if(mover == connectionHandlers.get(turn).connectionId){
            switch (inputArray[2]){
                case "fold":
                    connectionHandlers.get(turn).playerState = PlayerState.Folded;
                    pot+=connectionHandlers.get(turn).bet;
                    connectionHandlers.get(turn).bet = 0;
                    broadcast("game folded "+turn);
                    connectionHandlers.get(turn).hasHadChanceToBet = true;
                    nextTurn();
                    break;
                case "call":
                    if(connectionHandlers.get(turn).money - (currentBet - connectionHandlers.get(turn).bet) > 0){
                        connectionHandlers.get(turn).money -= currentBet - connectionHandlers.get(turn).bet;
                        connectionHandlers.get(turn).bet = currentBet;
                        broadcast("game call "+turn);
                    }
                    else{
                        connectionHandlers.get(turn).bet += connectionHandlers.get(turn).money;
                        connectionHandlers.get(turn).money = 0;
                        connectionHandlers.get(turn).playerState = PlayerState.AllIn;
                        broadcast("game allin "+turn);
                    }
                    connectionHandlers.get(turn).hasHadChanceToBet = true;
                    nextTurn();
                    break;
                case "raise":
                    int raiseAmmount = Integer.parseInt(inputArray[3]);
                    if(connectionHandlers.get(turn).money - (currentBet - connectionHandlers.get(turn).bet + raiseAmmount) > 0){
                        connectionHandlers.get(turn).money -= (currentBet - connectionHandlers.get(turn).bet) + raiseAmmount;
                        connectionHandlers.get(turn).bet = currentBet + raiseAmmount;
                        currentBet = connectionHandlers.get(turn).bet;
                        broadcast("game raise "+turn+" "+currentBet);
                    }
                    else{
                        connectionHandlers.get(turn).bet += connectionHandlers.get(turn).money;
                        connectionHandlers.get(turn).money = 0;
                        connectionHandlers.get(turn).playerState = PlayerState.AllIn;
                        currentBet = connectionHandlers.get(turn).bet;
                        broadcast("game allin "+turn+" "+currentBet);
                    }
                    nextTurn();
                    break;
            }
        }
    }

    public void nextTurn(){
        boolean shouldEndBettingRound = true;
        for(int i = 0; i < connectionHandlers.size(); i++){
            shouldEndBettingRound = shouldEndBettingRound && connectionHandlers.get(i).hasHadChanceToBet;
            if(connectionHandlers.get(i).playerState == PlayerState.Playing){
                for(int j = 0; j < connectionHandlers.size(); j++){
                    if(connectionHandlers.get(j).playerState == PlayerState.Playing){
                        shouldEndBettingRound = shouldEndBettingRound && (connectionHandlers.get(i).bet == connectionHandlers.get(j).bet);
                    }
                }
            }
        }
        if(shouldEndBettingRound){
            endBettingRound();
        }


        int startTurn = turn;
        int currentTurnCheck = turn + 1;

        while(true){
            if(currentTurnCheck > connectionHandlers.size() - 1)
                currentTurnCheck = 0;

            if(startTurn == currentTurnCheck){
                endRound(turn);
            }
            else if (connectionHandlers.get(turn).playerState == PlayerState.Playing){
                turn = currentTurnCheck;
                broadcast("game turn "+turn);
                return;
            }
        }
    }

    public void endBettingRound(){

    }

    public void endRound(int winner){
        for(int i = 0; i < connectionHandlers.size(); i++){
            pot+= connectionHandlers.get(i).bet;
            connectionHandlers.get(i).bet=0;
        }
        connectionHandlers.get(winner).money+=pot;
        pot = 0;
    }

    public void sendPlaceAtTable(){
        for(int i = 0; i < connectionHandlers.size(); i++){
            connectionHandlers.get(i).out.send("setup place "+i+" "+(connectionHandlers.size()));
        }
    }
}
