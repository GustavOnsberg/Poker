import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static MenuWindow menuWindow;
    public static Thread windowThread;

    public static Socket socket;
    public static Scanner input;
    public static PrintWriter output;

    public static void main(String [] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
        socket = new Socket("82.211.202.61",33201);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream());


        output.print("Hi server\r\n");
        System.out.println("Hi server");
        output.flush();


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
