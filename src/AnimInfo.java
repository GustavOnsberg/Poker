public class AnimInfo {
    int startX;
    int startY;
    int endX;
    int endY;
    long startTime;
    long endTime;
    float startFlipPos;
    float endFlipPos;
    float startSize;
    float endSize;
    AnimInfo(int startX, int startY, int endX, int endY, long startTime, long endTime, float startFlipPos, float endFlipPos, float startSize, float endSize){
        this.startX=startX;
        this.startY=startY;
        this.endX=endX;
        this.endY=endY;
        this.startTime=startTime;
        this.endTime=endTime;
        this.startFlipPos=startFlipPos;
        this.endFlipPos=endFlipPos;
        this.startSize=startSize;
        this.endSize=endSize;
    }
    AnimInfo(int startX, int startY, int endX, int endY, long endTime, float startFlipPos, float endFlipPos, float startSize, float endSize){
        new AnimInfo(startX, startY, endX, endY, System.currentTimeMillis(),  endTime,  startFlipPos,  endFlipPos,  startSize,  endSize);
    }
    AnimInfo(int startX, int startY, int endX, int endY, long startTime, long endTime, float startFlipPos, float endFlipPos, float size){
        new AnimInfo(startX, startY, endX, endY, startTime,  endTime,  startFlipPos,  endFlipPos,  size,  size);

    }
    AnimInfo(int startX, int startY, int endX, int endY, long startTime, long endTime, boolean front, float startSize, float endSize){
        int flipPos = -1;
        if (front == true) {
            flipPos = 1;
        }
        new AnimInfo(startX, startY, endX, endY, startTime,  endTime,  flipPos,  flipPos,  startSize,  endSize);
    }


}
