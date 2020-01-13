import java.util.ArrayList;
import java.util.Random;

public class GameLogic {
    public void NewComCards(ArrayList comCards){
        comCards.clear();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            comCards.add(DataTypes.CardType.getRandomCard());
        }
    }
    public void NewHandCards(ArrayList players, int playerNum){
        PlayerInfo temp1 = (PlayerInfo) players.get(playerNum);
        int cash = temp1.getCash();
        PlayerInfo temp = new PlayerInfo(DataTypes.CardType.getRandomCard(), DataTypes.CardType.getRandomCard(),cash);
        players.set(playerNum,temp);
    }
    public int GetWinHand(){
        return 0;
    }
    public int GetPersonalPot(){
        return 0;
    }


}
