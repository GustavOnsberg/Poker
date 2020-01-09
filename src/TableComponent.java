import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;


public class TableComponent extends JPanel {
    Image cardFront;
    Image cardBack;
    Image coin;
    public static ArrayList<PlayerInfo> players;
    private int cardW = 350; // card tile width
    private int cardH = 490; // card tile height
    public TableComponent(){
        try {
            cardFront = ImageIO.read(getClass().getResource("/resources/graphics/decks/fronts/deck_default.png"));
            cardBack = ImageIO.read(getClass().getResource("/resources/graphics/decks/backs/card_back_heavennade.png"));
            coin = ImageIO.read(getClass().getResource("/resources/graphics/icons/icon_coin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
        g.setColor(Color.getHSBColor(0,1,0));
        try {
            drawPlayerHand(getWidth()/2, (int) (getHeight()*0.15), g, DataTypes.CardType.S1, DataTypes.CardType.S1, 500000, 0.8f,false);
            drawPlayerHand(getWidth()/2, (int) (getHeight()*0.85), g, DataTypes.CardType.C13, DataTypes.CardType.H1, 500000, 1f,true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void drawPlayerHand(int x, int y, Graphics g, DataTypes.CardType Card1, DataTypes.CardType Card2, int cash, float sizeVar, boolean isYou){

        float size = sizeVar*getHeight()/2000;
        if (isYou == true) {
            drawCard((int) (x-cardW*size/2), y, g, Card1, cardFront, size);
            drawCard((int) (x+cardW*size/2), y, g, Card2, cardFront, size);
        }else{
            drawCard((int) (x-cardW*size/2), y, g, DataTypes.CardType.S1, cardBack, size);
            drawCard((int) (x+cardW*size/2), y, g, DataTypes.CardType.S1, cardBack, size);
            Font font = new Font("Verdana", Font.BOLD, (int) (12*size*5.5));
            g.setFont(font);
            g.drawString(cash+"",(int) (x-cardW*size+70*size), (int) (y+cardH*size/2+55*size));
            g.drawImage(coin, (int) (x-cardW*size), (int) (y+cardH*size/2),(int) (70*size),(int) (70*size), this);
        }



    }
    public void drawCard(int x, int y, Graphics g, DataTypes.CardType card, Image cardImage, float cardSize){
        int thisX = card.ordinal()%13;
        int thisY = card.ordinal()/13;
        g.drawImage(cardImage, (int) (x-cardW/2*cardSize), (int) (y-cardH/2*cardSize), (int) (x+cardW/2*cardSize), (int) (y+cardH/2*cardSize), thisX*cardW, thisY*cardH,  thisX*cardW+cardW, thisY*cardH+cardH, this);
    }
    public void drawCard(int x, int y, Graphics g, DataTypes.CardType card, Image CardFront, float cardSize, float flipX){
        int thisX = card.ordinal()%13;
        int thisY = card.ordinal()/13;
        g.drawImage(CardFront, (int) (x-cardW/2*cardSize*flipX), (int) (y-cardH/2*cardSize), (int) (x+cardW/2*cardSize*flipX), (int) (y+cardH/2*cardSize), thisX*cardW, thisY*cardH,  thisX*cardW+cardW, thisY*cardH+cardH, this);
    }
    public void drawCard(int x, int y, Graphics g, DataTypes.CardType card, Image CardFront, float cardSize, float flipX, float flipY){
        int thisX = card.ordinal()%13;
        int thisY = card.ordinal()/13;
        g.drawImage(CardFront, (int) (x-cardW/2*cardSize*flipX), (int) (y-cardH/2*cardSize*flipY), (int) (x+cardW/2*cardSize*flipX), (int) (y+cardH/2*cardSize*flipY), thisX*cardW, thisY*cardH,  thisX*cardW+cardW, thisY*cardH+cardH, this);
    }
}
