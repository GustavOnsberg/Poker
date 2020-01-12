import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Game implements Runnable {
    public Window window;
    Thread runningThis;


    public Game() throws IOException {

    }

    @Override
    public void run() {
        try {
            createWindow(false, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public void createWindow(boolean fullscreen, Game thisGame) throws IOException {
        window = new Window(fullscreen, this);
        window.setMinimumSize(new Dimension(1100,600));
        window.setTitle("Poker");
        window.setResizable(true);
        if (fullscreen){
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            window.setUndecorated(true);
        }
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.runningThis = new Thread(window);
        window.runningThis.start();
    }



    public static void main(String [] args) throws IOException {
        Game game = new Game();
        Thread thread = new Thread(game);
        thread.start();
    }
}
