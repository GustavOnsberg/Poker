import javax.swing.*;
import java.awt.*;

public class Main {

    public static Window window;
    public static Thread windowThread;

    public static void main(String [] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");


        createWindow(false);

    }



    public static void createWindow(boolean fullscreen){
        window = new Window();
        window.isFullscreen = fullscreen;
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
