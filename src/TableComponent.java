import javax.swing.*;
import java.awt.*;

public class TableComponent extends JPanel {
    public Dimension getPreferredSize(){
        return new Dimension(800,400);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }
}
