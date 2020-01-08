import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static Window window;
    public static Thread windowThread;

    public static void main(String [] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");


        createWindow(false);

    }



    public static void createWindow(boolean fullscreen) throws IOException {
        window = new Window(fullscreen);
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
