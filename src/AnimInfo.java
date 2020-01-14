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

        System.out.println("Endtime1:" + this.endTime);
    }
    AnimInfo(int startX, int startY, int endX, int endY, long endTime, DataTypes.CardType card, float startFlipPos, float endFlipPos, float startSize, float endSize){
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
        new AnimInfo(startX, startY, endX, endY, System.currentTimeMillis(),  endTime, card,  startFlipPos,  endFlipPos,  startSize,  endSize);
    }
    AnimInfo(int startX, int startY, int endX, int endY, long startTime, long endTime, DataTypes.CardType card, float startFlipPos, float endFlipPos, float size){
        this.startX=startX;
        this.startY=startY;
        this.endX=endX;
        this.endY=endY;
        this.startTime=startTime+time;
        this.endTime=endTime+time;
        this.card = card;
        this.startFlipPos=startFlipPos;
        this.endFlipPos=endFlipPos;
        this.startSize=size;
        this.endSize=size;
        new AnimInfo(startX, startY, endX, endY, startTime,  endTime, card,  startFlipPos,  endFlipPos,  size,  size);

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
}
