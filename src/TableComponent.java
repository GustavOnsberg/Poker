import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class TableComponent extends JPanel {
//setup
    Image cardFront;
    Image cardBack;
    Image coin;
    private int cardW = 350; // card tile width
    private int cardH = 490; // card tile height
    public float radius = 0.8f;
    public float sizeVar = 1f;
    public float angle;
    public int playerNum;
    boolean[] cardShown;
    public ArrayList<PlayerInfo> players = new ArrayList<>();
    public ArrayList<DataTypes.CardType> communityCards = new ArrayList<>();
    public ArrayList<AnimInfo> animList = new ArrayList<>();
    private GameLogic gameLogic = new GameLogic();
    public TableComponent(){
        cardShown = new boolean[12];
        cardShown[0] = true;
        //test part________________________________________________________________________________________________________________________________________________--

        for (int i = 0; i < 6; i++) {
            cardShown[i] = true;
            players.add(new PlayerInfo(DataTypes.CardType.none, DataTypes.CardType.none,500));

        }
        players.get(4).setCard(0, DataTypes.CardType.S1);

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

//loop
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawBackground(g);
        drawPlayers(g);
        drawBoard(g, communityCards, sizeVar);
        drawAnim(animList, g);
        g.drawString(System.currentTimeMillis()+"",0,10);
    }

//draw functions
    public void drawPlayers(Graphics g){
        int posX;
        int posY;
        playerNum=0;
        float angle = (float) (Math.PI*2/(players.size()));

        for (int i = 0; i < players.size(); i++) {
            //x = sin
            posX = (int) (-Math.sin(angle*i)*getWidth()/2*radius);
            //y = -cos
            posY = (int) (Math.cos(angle*i)*getHeight()/2*radius);
            drawPlayerHand(posX+getWidth()/2, posY+getHeight()/2, g, players.get(i).card0, players.get(i).card1, players.get(i).cash, 1f, cardShown[i], i==playerNum);
        }
    }
    public void drawPlayerHand(int x, int y, Graphics g, DataTypes.CardType card0, DataTypes.CardType card1, int cash, float sizeVar, boolean isShown, boolean isYou){

        float size = sizeVar*getHeight()/2000;
        float eSize = (float) (size*(0.75-players.size()*0.02));
        if (isYou) {
            //cards
            drawCard((int) (x-cardW*size/2), y, g, card0, cardFront, size,isShown);
            drawCard((int) (x+cardW*size/2), y, g, card1, cardFront, size, isShown);
        }else {
            //cards
            drawCard((int) (x-cardW*eSize/2), y, g, card0, cardFront, eSize, isShown);
            drawCard((int) (x+cardW*eSize/2), y, g, card1, cardFront, eSize,isShown);
            //cash + cash icon
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

        g.drawImage(cardImage, (int) (x-cardW/2*cardSize*Math.abs(flipX)), (int) (y-cardH/2*cardSize*Math.abs(flipY)), (int) (x+cardW/2*cardSize*Math.abs(flipX)), (int) (y+cardH/2*cardSize*Math.abs(flipY)), thisX*cardW, thisY*cardH,  thisX*cardW+cardW, thisY*cardH+cardH, this);
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
            long endTime = element.getEndTime();
            if (endTime <= time) {
                int entityId = element.getEntityId();
                int cardId = element.getCardId();
                if (cardId != -10) {
                    PlayerInfo playerInfo = players.get(entityId);
                    DataTypes.CardType cardType = element.getCard();
                    playerInfo.setCard(cardId, cardType);
                }
                animList.remove(i);
                i--;
            }else if (element.getStartTime() <= time){
                int startX = element.getStartX();
                int endX = element.getEndX();
                int startY = element.getStartY();
                int endY = element.getEndY();
                long startT = element.getStartTime();
                long endT = element.getEndTime();
                float startF = element.getStartFlipPos();
                float endF = element.getEndFlipPos();
                float startS = element.getStartSize();
                float endS = element.getEndSize();
                double progress = (double) (time-startT)/(double) (endT-startT);
                double progressFlip = startF+(endF-startF)*progress;
                double progressSize = startS+(endS-startS)*progress;
                Image setImage;
                boolean isShown;
                if (progressFlip>0) {
                    setImage = cardFront;
                    isShown = true;
                }else{
                    setImage = cardBack;
                    isShown = false;
                }
                System.out.println("prog"+progress+"progF"+progressFlip+"progS"+progressSize);
                drawCard((int) (startX+(endX-startX)*progress), (int) (startY+(endY-startY)*progress), g, animList.get(i).card, setImage, (float) (progressSize*getHeight()/450), isShown, (float) progressFlip);
            }
        }
    }

//utility functions
    public int getPosX(int entityId, int cardId){
        if (entityId == -1) {
            float size = sizeVar*getHeight()/3000;
            return (int) (getWidth()/2+size*(2.5-cardId)*cardW);
        }else {
            float size = sizeVar*getHeight()/2000;
            float eSize = (float) (size*(0.75-players.size()*0.02));
            int offset;
            if (entityId == 0) {
                offset = (int) (-cardW*size/2+cardW*size*cardId);
            }else {
                offset = (int) (-cardW*eSize/2+cardW*eSize*cardId);
            }
            float angle = (float) (Math.PI*2/(players.size()));
            return (int) (-Math.sin(angle*entityId)*getWidth()/2*radius+getWidth()/2+offset);
        }
    }
    public int getPosY(int entityId, int cardId){
        if (entityId == -1) {
            return getHeight()/2;
        }else {
            float angle = (float) (Math.PI*2/(players.size()));
            return (int) (Math.cos(angle*entityId)*getHeight()/2*radius+getHeight()/2);
        }
    }
    public void giveCard(int startEntityId, int endEntityId, int startCardId, int endCardId, DataTypes.CardType cardType){

        animList.add(new AnimInfo(this.getPosX(startEntityId,startCardId), this.getPosY(startEntityId,startCardId),this.getPosX(endEntityId,endCardId), this.getPosY(endEntityId,endCardId), 0, 1000, cardType, -1f,1f,  0.15f,0.15f, endEntityId, endCardId));
    }
    public void takeCard(int startEntityId, int endEntityId, int startCardId, int endCardId){
        PlayerInfo playerInfo = players.get(endEntityId);
        DataTypes.CardType card = playerInfo.getCard(0);
        animList.add(new AnimInfo( this.getPosX(endEntityId,endCardId), this.getPosY(endEntityId,endCardId),this.getPosX(startEntityId,startCardId), this.getPosY(startEntityId,startCardId), 0, 1000, card, 1f,-1f,  0.15f,0.15f, endEntityId));
        playerInfo.removeCard(0);
    }
}
