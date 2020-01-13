public class AnimInfo {
    int startX;
    int startY;
    int endX;
    int endY;
    long startTime;
    long endTime;
    DataTypes.CardType card;
    float startFlipPos;
    float endFlipPos;
    float startSize;
    float endSize;
    AnimInfo(int startX, int startY, int endX, int endY, long startTime, long endTime, DataTypes.CardType card, float startFlipPos, float endFlipPos, float startSize, float endSize){
        this.startX=startX;
        this.startY=startY;
        this.endX=endX;
        this.endY=endY;
        this.startTime=startTime+System.currentTimeMillis();
        this.endTime=endTime+System.currentTimeMillis();
        this.card = card;
        this.startFlipPos=startFlipPos;
        this.endFlipPos=endFlipPos;
        this.startSize=startSize;
        this.endSize=endSize;
    }
    AnimInfo(int startX, int startY, int endX, int endY, long endTime, DataTypes.CardType card, float startFlipPos, float endFlipPos, float startSize, float endSize){
        new AnimInfo(startX, startY, endX, endY, System.currentTimeMillis(),  endTime, card,  startFlipPos,  endFlipPos,  startSize,  endSize);
    }
    AnimInfo(int startX, int startY, int endX, int endY, long startTime, long endTime, DataTypes.CardType card, float startFlipPos, float endFlipPos, float size){
        new AnimInfo(startX, startY, endX, endY, startTime,  endTime, card,  startFlipPos,  endFlipPos,  size,  size);

    }
    AnimInfo(int startX, int startY, int endX, int endY, long startTime, long endTime, DataTypes.CardType card, boolean front, float startSize, float endSize){
        int flipPos = -1;
        if (front == true) {
            flipPos = 1;
        }
        new AnimInfo(startX, startY, endX, endY, startTime,  endTime, card,  flipPos,  flipPos,  startSize,  endSize);
    }
    AnimInfo(int startX, int startY, int endX, int endY, long startTime, long endTime, DataTypes.CardType card, boolean front, float size){

        new AnimInfo( startX,  startY,  endX,  endY,  startTime,  endTime, card,  front,  size,  size);
    }

}
