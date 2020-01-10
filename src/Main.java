import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static MenuWindow menuWindow;
    public static Thread windowThread;

    static ClientConnectionHandler connection;



    public static void main(String [] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {


            try {
                connection = new ClientConnectionHandler();
            } catch (IOException e) {
                e.printStackTrace();
            }


        connection.send("Hi server");

        //connection.send("Hi server 2");

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");


        createMenu();



    }


    public static void createMenu(){
        menuWindow = new MenuWindow();
        menuWindow.setMinimumSize(new Dimension(1200,600));
        menuWindow.setTitle("Poker menu");
        menuWindow.setResizable(false);
        menuWindow.setVisible(true);
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
