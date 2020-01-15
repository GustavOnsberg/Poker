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
    Image gameButtons;
    private int cardW = 350; // card tile width
    private int cardH = 490; // card tile height
    private int buttonS = 240; //game button height and width
    public float radius = 0.8f;
    public float sizeVar = 1f;
    public float animSize = 0.15f;
    public DataTypes.CardType[] cards = DataTypes.CardType.values();
    public float angle;
    public int playerNum;
    boolean[] cardShown;
    public DataTypes.CardType[] communityCards = new DataTypes.CardType[5];
    public ArrayList<PlayerInfo> players = new ArrayList<>();
    public ArrayList<AnimInfo> animList = new ArrayList<>();
    public Color[]  buttonColors = new  Color[3];
    public String[] buttonStrings = new String[3];
    private GameLogic gameLogic = new GameLogic();
    public TableComponent(){
        buttonColors[0] = Color.WHITE;
        buttonStrings[0] = "D";
        buttonColors[1] = Color.BLUE;
        buttonStrings[1] = "S";
        buttonColors[2] = Color.YELLOW;
        buttonStrings[2] = "B";
        cardShown = new boolean[12];
        cardShown[0] = true;
        //test part________________________________________________________________________________________________________________________________________________--

        for (int i = 0; i < communityCards.length; i++) {
            communityCards[i] = DataTypes.CardType.none;
        }

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
        for (int i = 0; i < Main.game.players.size(); i++) {

        }
        super.paintComponent(g);
        drawTable(g);
        drawPlayers(g);
        drawBoard(g, communityCards, sizeVar);
        drawAnim(animList, g);
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
            //cash
            g.drawString(cash+"",(int) (x-cardW*eSize+70*eSize), (int) (y+cardH*eSize/2+55*eSize));
            //icon
            g.drawImage(coin, (int) (x-cardW*eSize), (int) (y+cardH*eSize/2),(int) (70*eSize),(int) (70*eSize), this);
        }

    }
    public void drawBoard(Graphics g, DataTypes.CardType[] comCards, float sizeVar){
        float size = sizeVar*getHeight()/3000;
        int x = (int) (getWidth()/2+size*2.5*cardW);
        drawCard(x, getHeight()/2, g, DataTypes.CardType.S1, cardBack, size, false);
        for (int i = 0; i < 5; i++) {
            drawCard((int) (x-(i+1)*cardW*size), getHeight()/2, g, comCards[i], cardFront, size, true);
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
    public void drawButton(int x, int y, Graphics g, DataTypes.GameButton buttonType ){
        float size = getHeight()/200f;
        g.setColor(buttonColors[buttonType.ordinal()]);
        g.fillOval((int) (x-10*size),(int) (y-10*size), (int) (20*size), (int) (20*size));
        g.setColor(Color.BLACK);
        Font font = new Font("Verdana", Font.BOLD, (int) (15*size));
        g.setFont(font);
        g.drawString(buttonStrings[buttonType.ordinal()],(int) (x-size*6), (int) (y+size*5));
    }
    public void drawGameButton(Graphics g, int playerNum, DataTypes.GameButton buttonType){
        drawButton(this.getPosX(playerNum, -1),this.getPosY(playerNum,-1),g, buttonType);
    }
    public void drawAnim(ArrayList<AnimInfo> animList, Graphics g){
        long time = System.currentTimeMillis();
        for (int i = 0; i < animList.size(); i++) {
            AnimInfo element = animList.get(i);
            long endTime = element.getEndTime();
            if (endTime <= time) {
                int entityId = element.getEntityId();
                int cardId = element.getCardId();
                DataTypes.CardType cardType = element.getCard();
                if (cardId != -10) {
                    if (entityId > -1) {
                        PlayerInfo playerInfo = players.get(entityId);
                        playerInfo.setCard(cardId, cardType);
                    }else if(cardId>0){
                        communityCards[cardId-1] = cardType;
                    }
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
    public void drawTable(Graphics g){
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

//utility functions
    public int getPosX(int entityId, int cardId){
        if (entityId < 0) {
            float size = sizeVar*getHeight()/3000;
            return (int) (getWidth()/2+size*(2.5-cardId)*cardW);
        }else {

            float size = sizeVar*getHeight()/2000;
            float eSize = (float) (size*(0.75-players.size()*0.02));
            float angle = (float) (Math.PI*2/(players.size()));

            if (cardId == -1) {
                return (int) (-Math.sin(angle*entityId)*getWidth()/2*radius*0.7+getWidth()/2);
            }else {
                int offset;
                if (entityId == 0) {
                    offset = (int) (-cardW*size/2+cardW*size*cardId);
                }else {
                    offset = (int) (-cardW*eSize/2+cardW*eSize*cardId);
                }

                return (int) (-Math.sin(angle*entityId)*getWidth()/2*radius+getWidth()/2+offset);
            }
        }
    }
    public int getPosY(int entityId, int cardId){
        if (entityId < 0) {
            return getHeight()/2;
        }else {
            float angle = (float) (Math.PI*2/(players.size()));
            if (cardId == -1) {
                return (int) (Math.cos(angle*entityId)*getHeight()/2*radius*0.7+getHeight()/2);
            }
            return (int) (Math.cos(angle*entityId)*getHeight()/2*radius+getHeight()/2);
        }
    }
    public void giveCard(int startEntityId, int endEntityId, int startCardId, int endCardId, boolean isShown, DataTypes.CardType cardType){
        float endSize = animSize;
        float startSize = animSize;
        float endFlip = 1f;
        if (endEntityId == 0) { endSize = animSize*1.5f; }
        if (startEntityId == 0) { startSize = animSize*1.5f; }
        if (!isShown){ endFlip = -1f;}
        animList.add(new AnimInfo(this.getPosX(startEntityId,startCardId), this.getPosY(startEntityId,startCardId),this.getPosX(endEntityId,endCardId), this.getPosY(endEntityId,endCardId), 0, 1000, cardType, -1f,endFlip,  startSize,endSize, endEntityId, endCardId));
    }
    public void takeCard(int startEntityId, int endEntityId, int startCardId, int endCardId, boolean isShown){
        DataTypes.CardType card = DataTypes.CardType.none;
        if (endEntityId > -1) {
            PlayerInfo playerInfo = players.get(endEntityId);
            card = playerInfo.getCard(endCardId);
            playerInfo.removeCard(endCardId);
        }else {
            card = communityCards[startCardId];
            communityCards[startCardId] = DataTypes.CardType.none;
        }
        float endSize = animSize;
        float startSize = animSize;
        float endFlip = 1f;
        if (endEntityId == 0) { endSize = animSize*1.5f; }
        if (startEntityId == 0) { startSize = animSize*1.5f; }
        if (!isShown){ endFlip = -1f;}
        animList.add(new AnimInfo( this.getPosX(endEntityId,endCardId), this.getPosY(endEntityId,endCardId),this.getPosX(startEntityId,startCardId), this.getPosY(startEntityId,startCardId), 0, 1000, card, endFlip,-1f,  endSize, startSize, endEntityId));

    }
}
