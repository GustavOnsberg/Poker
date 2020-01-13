import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class TableComponent extends JPanel {
    Image cardFront;
    Image cardBack;
    Image coin;
    private int cardW = 350; // card tile width
    private int cardH = 490; // card tile height
    boolean[] cardShown;
    public ArrayList<PlayerInfo> players = new ArrayList<>();
    public ArrayList<DataTypes.CardType> communityCards = new ArrayList<>();
    public ArrayList<AnimInfo> animList = new ArrayList<>();
    private GameLogic gameLogic = new GameLogic();
    public TableComponent(){
        cardShown = new boolean[12];
        cardShown[0] = true;
        //test part________________________________________________________________________________________________________________________________________________--
        animList.add(new AnimInfo(getWidth()/2, getHeight()/2, getWidth(), getHeight(), 1000, 10000, DataTypes.CardType.S13, true, 1));
        for (int i = 0; i < 6; i++) {
            cardShown[i] = true;
            players.add(new PlayerInfo(DataTypes.CardType.H4, DataTypes.CardType.H2,500));

        }
        gameLogic.NewComCards(communityCards);
        gameLogic.NewHandCards(players,3);
        //test part________________________________________________________________________________________________________________________________________________--
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
        drawBackground(g);
        drawPlayers(g);
        drawBoard(g, communityCards, 1);
        drawAnim(animList, g);
        g.drawString(System.currentTimeMillis()+"",0,10);
    }

    public void drawPlayers(Graphics g){
        int posX;
        int posY;
        int playerNum=0;
        float angle = (float) (Math.PI*2/(players.size()));
        float radius = 0.8f;

        for (int i = 0; i < players.size(); i++) {
            //x = sin
            posX = (int) -(Math.sin(angle*i)*getWidth()/2*radius);
            //y = -cos
            posY = (int) (Math.cos(angle*i)*getHeight()/2*radius);
            drawPlayerHand(posX+getWidth()/2, posY+getHeight()/2, g, players.get(i).card1, players.get(i).card2, players.get(i).cash, 1f, cardShown[i], i==playerNum);
        }
    }
    public void drawPlayerHand(int x, int y, Graphics g, DataTypes.CardType card1, DataTypes.CardType card2, int cash, float sizeVar, boolean isShown, boolean isYou){

        float size = sizeVar*getHeight()/2000;
        float eSize = (float) (size*(0.75-players.size()*0.02));
        if (isYou) {
            drawCard((int) (x-cardW*size/2), y, g, card1, cardFront, size,isShown);
            drawCard((int) (x+cardW*size/2), y, g, card2, cardFront, size, isShown);
        }else {
            drawCard((int) (x-cardW*eSize/2), y, g, card1, cardFront, eSize, isShown);
            drawCard((int) (x+cardW*eSize/2), y, g, card2, cardFront, eSize,isShown);
        }
        if (!isYou){
            Font font = new Font("Verdana", Font.BOLD, (int) (12*eSize*5.5));
            g.setFont(font);
            g.drawString(cash+"",(int) (x-cardW*eSize+70*eSize), (int) (y+cardH*eSize/2+55*eSize));
            g.drawImage(coin, (int) (x-cardW*eSize), (int) (y+cardH*eSize/2),(int) (70*eSize),(int) (70*eSize), this);
        }
    }
    public void drawBoard(Graphics g, ArrayList<DataTypes.CardType> comCards, float sizeVar){
        float size = sizeVar*getHeight()/3000;
        int x = (int) (getWidth()/2+size*2.5*cardW);
        drawCard(x, getHeight()/2, g, DataTypes.CardType.S1, cardBack, size, false);
        for (int i = 0; i < comCards.size(); i++) {
            drawCard((int) (x-(i+1)*cardW*size), getHeight()/2, g, comCards.get(i), cardFront, size, true);
        }
    }
    public void drawCard(int x, int y, Graphics g, DataTypes.CardType card, Image cardImage, float cardSize, boolean isShown){
        drawCard(x, y, g, card, cardImage, cardSize,isShown, 1, 1);
    }
    public void drawCard(int x, int y, Graphics g, DataTypes.CardType card, Image cardImage, float cardSize, boolean isShown, float flipX){
        drawCard(x, y, g, card, cardImage, cardSize, isShown, flipX, 1);
    }
    public void drawCard(int x, int y, Graphics g, DataTypes.CardType card, Image cardImage, float cardSize, boolean isShown, float flipX, float flipY){
        if (card != DataTypes.CardType.none && !isShown) {
            card = DataTypes.CardType.S1;
            cardImage = cardBack;
        }
        int thisX = card.ordinal()%13;
        int thisY = card.ordinal()/13;
        g.drawImage(cardImage, (int) (x-cardW/2*cardSize*flipX), (int) (y-cardH/2*cardSize*flipY), (int) (x+cardW/2*cardSize*flipX), (int) (y+cardH/2*cardSize*flipY), thisX*cardW, thisY*cardH,  thisX*cardW+cardW, thisY*cardH+cardH, this);
    }
    public void drawBackground(Graphics g){
        int tableW = (int) (getWidth()*0.6);
        int tableH = (int) (getHeight()*0.6);
        g.setColor(Color.getHSBColor(0.1f, 1, 0.6f));
        g.fillOval((getWidth()-tableW)/2, (getHeight()-tableH)/2, tableW, tableH);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.getHSBColor(0.5f, 1, 0.6f));
        g2.drawOval((getWidth()-tableW)/2, (getHeight()-tableH)/2, tableW, tableH);
        g.setColor(Color.getHSBColor(0,1,0));
    }
    public void drawAnim(ArrayList<AnimInfo> animList, Graphics g){
        long time = System.currentTimeMillis();
        for (int i = 0; i < animList.size(); i++) {
            AnimInfo element = animList.get(i);
            long endTime = element.endTime;
            if (endTime <= time) {
                animList.remove(i);
                i--;
            }else if (animList.get(i).startTime >= time){
                long startT = element.startTime;
                long endT = element.endTime;
                int startX = element.startX;
                int endX = element.endX;
                int startY = element.startY;
                int endY = element.endY;
                float startF = element.startFlipPos;
                float endF = element.endFlipPos;
                float startS = element.startSize;
                float endS = element.endSize;
                float progress = (time-startT)/(endT-startT);
                float progressFlip = startF+(endF-startF)*progress;
                float progressSize = startS+(endS-startS)*progress;
                Image setImage;
                boolean isShown;
                if (progressFlip>0) {
                    setImage = cardFront;
                    isShown = true;
                }else{
                    setImage = cardBack;
                    isShown = false;
                }
                drawCard((int) (startX+(endX-startX)*progress), (int) (startY+(endY-startY)*progress), g, animList.get(i).card, setImage, progressSize,isShown);
            }
        }
    }

}
