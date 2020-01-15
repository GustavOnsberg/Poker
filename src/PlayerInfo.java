public class PlayerInfo {
    public DataTypes.CardType card0;
    public DataTypes.CardType card1;
    public int cash;

    PlayerInfo(DataTypes.CardType card0, DataTypes.CardType card1, int cash){
        this.card0 = card0;
        this.card1 = card1;
        this.cash = cash;
    }
    PlayerInfo(){
        this.card0 = DataTypes.CardType.none;
        this.card1 = DataTypes.CardType.none;
        this.cash = 0;
    }
    public void setCard(int cardId, DataTypes.CardType card){
        if (cardId == 0) {
            this.card0 = card;
        } else {
            this.card1 = card;
        }
    }
    public void removeCard(int cardId){
        if (cardId == 0) {
            this.card0 = DataTypes.CardType.none;
        } else {
            this.card1 = DataTypes.CardType.none;
        }
    }
    public int getCash() {
        return cash;
    }
    public DataTypes.CardType getCard(int cardId){
        if (cardId == 0) {
            return this.card0;
        }else {
            return this.card1;
        }
    }


}
