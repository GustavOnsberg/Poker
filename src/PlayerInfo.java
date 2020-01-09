import java.util.ArrayList;

public class PlayerInfo {
    DataTypes.CardType card1;
    DataTypes.CardType card2;
    int cash;

    PlayerInfo(DataTypes.CardType card1, DataTypes.CardType card2, int cash){
        this.card1 = card1;
        this.card2 = card2;
        this.cash = cash;
    }

    public void AddPlayer(DataTypes.CardType card1, DataTypes.CardType card2, int cash){
        TableComponent.players.add(new PlayerInfo(card1,card2,cash));
    }
}
