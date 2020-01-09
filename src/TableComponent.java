import javax.swing.*;
import java.awt.*;

public class TableComponent extends JPanel {
    private int cardW = 350; // card tile width
    private int cardH = 490; // card tile height
    public Dimension getPreferredSize(){
        return super.getPreferredSize();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int width = (int) (getWidth()*0.6);
        int height = (int) (getHeight()*0.6);
        g.setColor(Color.getHSBColor(0.1f, 1, 0.6f));
        g.fillOval((getWidth()-width)/2, (getHeight()-height)/2, width, height);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.getHSBColor(0.5f, 1, 0.6f));
        g2.drawOval((getWidth()-width)/2, (getHeight()-height)/2, width, height);


    }


}
