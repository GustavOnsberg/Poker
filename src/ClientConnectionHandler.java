import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnectionHandler {
    public static Socket socket;
    public static Scanner input;
    public static PrintWriter output;

    ClientConnectionHandler() throws IOException {
        socket = new Socket("localhost",33201);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream());
    }



    public void send(String message) throws IOException {
        output.println(message);
        output.flush();
    }
}
