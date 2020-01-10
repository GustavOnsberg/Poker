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
    public ArrayList<PlayerInfo> players = new ArrayList<PlayerInfo>();
    private int cardW = 350; // card tile width
    private int cardH = 490; // card tile height
    boolean[] cardShown;
    ArrayList<DataTypes.CardType> comunityCards = new ArrayList<>();
    public TableComponent(){
        cardShown = new boolean[12];
        cardShown[0] = true;
        for (int i = 0; i < 6; i++) {

            players.add(new PlayerInfo(DataTypes.CardType.H1, DataTypes.CardType.H2,500));

        }

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
        int tableW = (int) (getWidth()*0.6);
        int tableH = (int) (getHeight()*0.6);
        g.setColor(Color.getHSBColor(0.1f, 1, 0.6f));
        g.fillOval((getWidth()-tableW)/2, (getHeight()-tableH)/2, tableW, tableH);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.getHSBColor(0.5f, 1, 0.6f));
        g2.drawOval((getWidth()-tableW)/2, (getHeight()-tableH)/2, tableW, tableH);

        g.setColor(Color.getHSBColor(0,1,0));

        int posX;
        int posY;
        int playerNum=0;
        float angle = (float) (Math.PI*2/(players.size()));
        float radius = 0.7f;

        for (int i = 0; i < players.size(); i++) {
            //x = sin
            posX = (int) -(Math.sin(angle*i)*getWidth()/2*radius);
            //y = -cos
            posY = (int) (Math.cos(angle*i)*getHeight()/2*radius);
            drawPlayerHand(posX+getWidth()/2, posY+getHeight()/2, g, players.get(i).card1, players.get(i).card2, players.get(i).cash, 1f, cardShown[i], i==playerNum);
        }

        drawBoard(g, comunityCards, 1);

    }


    private void drawPlayerHand(int x, int y, Graphics g, DataTypes.CardType Card1, DataTypes.CardType Card2, int cash, float sizeVar, boolean isShown, boolean isYou){

        float size = sizeVar*getHeight()/2000;
        float eSize = (float) (size*(0.75-players.size()*0.02));
        if (!isShown){
            drawCard((int) (x-cardW*eSize/2), y, g, DataTypes.CardType.S1, cardBack, eSize);
            drawCard((int) (x+cardW*eSize/2), y, g, DataTypes.CardType.S1, cardBack, eSize);
        }else if (isShown && isYou) {
            drawCard((int) (x-cardW*size/2), y, g, Card1, cardFront, size);
            drawCard((int) (x+cardW*size/2), y, g, Card2, cardFront, size);
        }else if(isShown && !isYou){
            drawCard((int) (x-cardW*eSize/2), y, g, Card1, cardFront, eSize);
            drawCard((int) (x+cardW*eSize/2), y, g, Card2, cardFront, eSize);
        }if (!isYou){
            Font font = new Font("Verdana", Font.BOLD, (int) (12*eSize*5.5));
            g.setFont(font);
            g.drawString(cash+"",(int) (x-cardW*eSize+70*eSize), (int) (y+cardH*eSize/2+55*eSize));
            g.drawImage(coin, (int) (x-cardW*eSize), (int) (y+cardH*eSize/2),(int) (70*eSize),(int) (70*eSize), this);
        }



    }
    public void drawBoard(Graphics g, ArrayList<DataTypes.CardType> comCards, float sizeVar){
        float size = sizeVar*getHeight()/3000;
        int x = (int) (getWidth()/2+getWidth()/6);
        drawCard(x, getHeight()/2, g, DataTypes.CardType.S1, cardBack, size);
        for (int i = 0; i < comCards.size(); i++) {
            drawCard(getWidth()/2, getHeight()/2, g, comCards.get(i), cardFront, size);
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
