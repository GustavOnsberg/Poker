import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import server.ConnectionHandler;

public class Main {

    public static MenuWindow menuWindow;
    public static Socket socket;
    public static ConnectionHandler connection;
    public static BlockingQueue queue;
    public static Game game;
    public static long lastHeatBest = System.currentTimeMillis();



    public static void main(String [] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, InterruptedException {


        socket = new Socket("localhost",33201);
        connection = new ConnectionHandler(socket,0);
        queue = connection.in.queue;

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        createMenu();

        connection.out.send("Hi server");

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
            }
        }catch(Exception e){

        }
    }
}
