import javax.swing.*;
import java.awt.*;

public class TableComponent extends JPanel {
    public Dimension getPreferredSize(){
        return new Dimension(800,400);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawOval(100, 100, 100, 100);
        
    }
    private int cardW = 350; // card tile width
    private int cardH = 490; // card tile height

}
