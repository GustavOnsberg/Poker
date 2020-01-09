import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Game implements Runnable {
    public static Window window;
    public static Thread windowThread;
    public static Socket socket;
    public static Scanner input;
    public static PrintWriter output;

    public Game() throws IOException {
        socket = new Socket("82.211.202.61",33201);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream());


        output.print("Hi server");
        output.flush();
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
        window.setMinimumSize(new Dimension(1000,600));
        window.setTitle("Poker");
        window.setResizable(true);
        if (fullscreen){
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            window.setUndecorated(true);
        }
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Thread windowThread = new Thread(window);

        windowThread.start();
    }
}
