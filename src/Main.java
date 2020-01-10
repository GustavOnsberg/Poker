import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import server.ConnectionHandler;
import server.NonGameConnectionsHandler;

public class Main {

    public static MenuWindow menuWindow;
    public static Thread windowThread;
    public static Socket socket;
    public static ConnectionHandler connection;



    public static void main(String [] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {


        socket = new Socket("localhost",33201);
        connection = new ConnectionHandler(socket,0);

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        createMenu();

        connection.out.send("Hi server");

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
