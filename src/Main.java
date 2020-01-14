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



    public static void clientDo(String input){
        String[] inputArray = input.split(" ");

        try{
            switch(inputArray[1]){
                case "gc":
                    game = new Game();
                    game.runningThis = new Thread(game);
                    game.runningThis.start();
                    break;
                case "gj":
                    game = new Game();
                    game.runningThis = new Thread(game);
                    game.runningThis.start();
                    break;
                case "chat":
                    try{
                        String senderName = "Connection "+inputArray[0];
                        game.window.chatArea.append("\n\n"+senderName+": "+input.substring(10));
                    }catch (Exception e){};
                    break;
                case "gs":
                    menuWindow.addGame();
                    break;
            }
        }catch(Exception e){

        }
    }
}
