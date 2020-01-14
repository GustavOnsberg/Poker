public class AnimInfo {
    int startX;
    int startY;
    int endX;
    int endY;
    long startTime;
    long endTime=1;
    DataTypes.CardType card;
    float startFlipPos;
    float endFlipPos;
    float startSize;
    float endSize;
    long time = System.currentTimeMillis();
    int entityId = -10;
    int cardId = -10;
    AnimInfo(int startX, int startY, int endX, int endY, long startTime, long endTime, DataTypes.CardType card, float startFlipPos, float endFlipPos, float startSize, float endSize, int entityId, int cardId){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.startTime = startTime+time;
        this.endTime = endTime+time;
        this.card = card;
        this.startFlipPos = startFlipPos;
        this.endFlipPos = endFlipPos;
        this.startSize = startSize;
        this.endSize = endSize;
        this.entityId = entityId;
        this.cardId = cardId;
    }
    AnimInfo(int startX, int startY, int endX, int endY, long startTime, long endTime, DataTypes.CardType card, float startFlipPos, float endFlipPos, float startSize, float endSize, int entityId){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.startTime = startTime+time;
        this.endTime = endTime+time;
        this.card = card;
        this.startFlipPos = startFlipPos;
        this.endFlipPos = endFlipPos;
        this.startSize = startSize;
        this.endSize = endSize;
        this.entityId = entityId;
    }
    AnimInfo(int startX, int startY, int endX, int endY, long startTime, long endTime, DataTypes.CardType card, float startFlipPos, float endFlipPos, float startSize, float endSize){
        this.startX=startX;
        this.startY=startY;
        this.endX=endX;
        this.endY=endY;
        this.startTime=startTime+time;
        this.endTime=endTime+time;
        this.card = card;
        this.startFlipPos=startFlipPos;
        this.endFlipPos=endFlipPos;
        this.startSize=startSize;
        this.endSize=endSize;
    }

    public int getStartX() { return this.startX; }
    public int getStartY() { return this.startY; }
    public int getEndX() { return this.endX; }
    public int getEndY() { return this.endY; }
    public long getStartTime() { return this.startTime; }
    public long getEndTime() { return this.endTime; }
    public DataTypes.CardType getCard() { return this.card; }
    public float getStartFlipPos() { return this.startFlipPos; }
    public float getEndFlipPos() { return this.endFlipPos; }
    public float getStartSize() { return this.startSize; }
    public float getEndSize() { return this.endSize; }
    public int getEntityId() { return this.entityId; }
    public int getCardId() { return this.cardId; }
}
