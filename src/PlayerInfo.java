public class PlayerInfo {
    public DataTypes.CardType card1;
    public DataTypes.CardType card2;
    public int cash;

    PlayerInfo(DataTypes.CardType card1, DataTypes.CardType card2, int cash){
        this.card1 = card1;
        this.card2 = card2;
        this.cash = cash;
    }

    public int getCash() {
        return cash;
    }


}
