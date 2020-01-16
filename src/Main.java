import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import server.ConnectionHandler;
import server.GameLogic;

public class Main {

    public static MenuWindow menuWindow;
    public static Socket socket;
    public static ConnectionHandler connection;
    public static BlockingQueue queue;
    public static Game game;
    public static long lastHeatBest = System.currentTimeMillis();



    public static void main(String [] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {



        socket = new Socket("82.211.202.61",33201); //Default IP: 82.211.202.61
        //socket = new Socket("localhost",33201); //Default IP: 82.211.202.61
        connection = new ConnectionHandler(socket,0);
        queue = connection.in.queue;

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        createMenu();

        connection.out.send("Hi server");

        Thread.sleep(1000);

        connection.out.send("gg");

        while(true){
            if (System.currentTimeMillis() - lastHeatBest > 20000){
                connection.out.send("hb");
                lastHeatBest = System.currentTimeMillis();
            }

            if (!queue.isEmpty()){
                clientDo(queue.take().toString());
            }

            Thread.sleep(10);
        }
    }


    public static void createMenu(){
        menuWindow = new MenuWindow();
        menuWindow.setMinimumSize(new Dimension(1200,600));
        menuWindow.setTitle("Poker menu");
        menuWindow.setResizable(false);
        menuWindow.setVisible(true);
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



    public static void clientDo(String input) throws IOException {
        String[] inputArray = input.split(" ");

        try{
            switch(inputArray[1]) {
                case "gc":
                    game = new Game();
                    game.runningThis = new Thread(game);
                    game.runningThis.start();
                    Main.connection.out.send("Hi game");
                    break;
                case "gj":
                    game = new Game();
                    game.runningThis = new Thread(game);
                    game.runningThis.start();
                    Main.connection.out.send("Hi game");
                    break;
                case "chat":
                    try {
                        String senderName = "Connection " + inputArray[0];
                        game.window.chatArea.append("\n\n" + senderName + ": " + input.substring(10));
                    } catch (Exception e) {
                    }
                    ;
                    break;
                case "gs":
                    menuWindow.addGame();
                    break;
                case "setup":
                    if (inputArray[2].equals("deal")) {
                        if (inputArray[3].equals("card0")) {
                            game.card0 = Integer.parseInt(inputArray[4]);
                            if (game.card0 == -1)
                                game.card0 = 52;
                        } else if (inputArray[3].equals("card1")) {
                            game.card1 = Integer.parseInt(inputArray[4]);
                            if (game.card0 == -1)
                                game.card0 = 52;
                        } else if (inputArray[2].equals("place")) {
                            game.placeAtTable = Integer.parseInt(inputArray[3]);
                            game.peopleAtTable = Integer.parseInt(inputArray[4]);
                            game.players.clear();
                            for (int i = 0; i < game.peopleAtTable; i++) {
                                game.players.add(new PlayerInfo());
                            }
                        } else if (inputArray[2].equals("dealer")) {
                            game.dealer = Integer.parseInt(inputArray[3]);
                        } else if (inputArray[2].equals("smallblind")) {
                            game.smallblind = Integer.parseInt(inputArray[3]);
                        } else if (inputArray[2].equals("bigblind")) {
                            game.bigblind = Integer.parseInt(inputArray[3]);
                        } else if (inputArray[2].equals("community")) {
                            game.communityCards[0] = Integer.parseInt(inputArray[3]);
                            game.communityCards[1] = Integer.parseInt(inputArray[4]);
                            game.communityCards[2] = Integer.parseInt(inputArray[5]);
                            game.communityCards[3] = Integer.parseInt(inputArray[6]);
                            game.communityCards[4] = Integer.parseInt(inputArray[7]);
                        }
                    }
                    break;
                case "info":
                    if (inputArray[2].equals("money")) {
                        game.players.get(Integer.parseInt(inputArray[3])).cash = Integer.parseInt(inputArray[4]);
                    }
                    break;
            }
        }catch(Exception e){

        }
    }
}
