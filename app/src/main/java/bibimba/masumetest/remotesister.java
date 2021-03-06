package bibimba.masumetest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by user on 2015/03/09.
 */
public class remotesister {

    private Bitmap charabitmap;
    private long rotatetiming = System.currentTimeMillis();
    private int rotateint = 0;

    private int blocksize;
    private int movesize;

    private int movesizeX;
    private int movesizeY;
    private int movesizeXmod;
    private int movesizeYmod;

    private int MOVECOUNT = 0;
    private int movetimes = 6;

    private int horizontalBlockNum;
    private int verticalBlockNum;

    public int horizontalPoint;
    public int verticalPoint;

    //お姉さんが向いている方向 0:上 1:下 2:左 3:右
    public int oneesandirection;

    private final Rect drawRect = new Rect();

    public interface Callback {
        //public int[] getDrawpoint(remotesister oneesan);
        //public boolean canMoveOneesan(int fromX ,int fromY,int direction);
        //public boolean canMoveMap(int fromX ,int fromY,int direction);

        public int[] canMoveOneesan(int fromX ,int fromY,int direction);
        public int[] canMoveMap(int fromX ,int fromY,int direction);
    }
    private final Callback callback;

    final Rect rect;

    public remotesister(Bitmap bit,int vw,int vh,int bs,Callback mapcallback) {
        charabitmap = bit;

        blocksize = bs;


/*
        horizontalBlockNum = 4;
        verticalBlockNum = 1;

        horizontalPoint=0;
        verticalPoint=0;
        */
        movesize =(blocksize) / movetimes;

        horizontalBlockNum = vw / blocksize;
        verticalBlockNum = vh / blocksize;

        if (horizontalBlockNum % 2 == 0) {
            horizontalPoint = (horizontalBlockNum / 2) - 1;
        } else {
            horizontalPoint = ((horizontalBlockNum+1) / 2) - 1;
        }

        if (verticalBlockNum % 2 == 0) {
            verticalPoint = (verticalBlockNum / 2) - 1;
        } else {
            verticalPoint = ((verticalBlockNum+1) / 2) - 1;
        }


        int rectleft = horizontalPoint * blocksize;
        int recttop = verticalPoint * blocksize;
        int rectright = (horizontalPoint+1) * blocksize;
        int rectbottom = (verticalPoint+1) * blocksize;

        rect = new Rect(rectleft,recttop,rectright,rectbottom);
        callback =mapcallback;
    }

    public void draw(Canvas canvas) {

        rotatecaluculation();

        //int[] drawpoints = callback.getDrawpoint(this);

        // 描画元の矩形イメージ（bitmap内の位置）

        //斜め対応 左上、右上は上、左下、右下は下にする
        int drawdirection;
        if (oneesandirection == directionEnum.leftup.directionkey()
                || oneesandirection == directionEnum.rightup.directionkey()) {
            drawdirection = directionEnum.up.directionkey();
        } else if (oneesandirection == directionEnum.leftdown.directionkey()
                    || oneesandirection == directionEnum.rightdown.directionkey()) {
                drawdirection = directionEnum.down.directionkey();
        } else {
                drawdirection = oneesandirection;
            }
        Rect src = new Rect(rotateint * blocksize,drawdirection * blocksize
                  ,(rotateint + 1) * blocksize,(drawdirection + 1) * blocksize);
        //Rect src = new Rect(rotateint * blocksize,oneesandirection * blocksize
        //       ,(rotateint + 1) * blocksize,(oneesandirection + 1) * blocksize);
        // 描画先の矩形イメージ（画面表示位置・サイズ）

        //rect.set(drawpoints[1],drawpoints[2],drawpoints[3],drawpoints[4]);
        drawRect.set(rect);
        canvas.drawBitmap(charabitmap,src,drawRect,null);
    }

    private void rotatecaluculation() {
        if (( System.currentTimeMillis() - rotatetiming) > 1000 ) {
            rotatetiming = System.currentTimeMillis();
            rotateint += 1;
            if (rotateint > 3 ) {
                rotateint = 0;
            }

        }
    }

    public boolean movemap(boolean isMoving,int direction) {
        oneesandirection = direction;
        if (isMoving) {
            //boolean isCanMove = callback.canMoveMap(horizontalPoint, verticalPoint, direction);
            //if (!isCanMove) {

            int[] isCanMove = callback.canMoveMap(horizontalPoint, verticalPoint, direction);
            if (isCanMove == null) {
                return false;
            }
        }
        return isMoving;
    }

    public void setmovesize(int toX,int toY) {

        movesizeX = (toX - this.rect.left) / movetimes;
        movesizeY = (toY - this.rect.top) / movetimes;


        //int aaa = Math.abs(movesizeX);
        int aaa = movesizeX;



        movesizeXmod = toX - this.rect.left - (aaa * movetimes);
        //if (movesizeX < 0) {
        //    movesizeXmod = -movesizeXmod;
        //}
        Log.v("移動","移動元X:" + this.rect.left + "移動先X:" + toX + "移動値X:" + movesizeX + "移動先余り:" + movesizeXmod );

        //aaa = Math.abs(movesizeY);
        aaa = movesizeY;
        movesizeYmod = toY - this.rect.top - (aaa * movetimes);
        //if (movesizeY < 0) {
        //    movesizeYmod = -movesizeYmod;
        //}
        Log.v("移動","移動元Y:" + this.rect.top + "移動先Y:" + toY + "移動値Y:" + movesizeY + "移動先余り:" + movesizeYmod );

    }

    //public boolean move(boolean isMoving,int direction) {
    public boolean move(int direction,int toSellX,int toSellY) {
        oneesandirection = direction;

        //boolean isCanMove = callback.canMoveOneesan(horizontalPoint,verticalPoint,direction);
        //if (!isCanMove) {

        rect.offset(movesizeX, movesizeY);

        /*
            switch (oneesandirection) {
                //0:下 1:左 2:右 3:上
                case 0:
                    //Log.v("動き","下:" + verticalPoint + " 最大:"+ (verticalBlockNum-1));
                    //if (verticalPoint == verticalBlockNum - 1) {
                    //    return false;
                    //}
                    rect.offset(0, movesize);
                    break;


                case 1:
                    //Log.v("動き","左:" + horizontalPoint);
                    //if (horizontalPoint == 0) {
                    //    return false;
                    //}
                    rect.offset(-movesize,0);
                    break;
                case 2:
                    //Log.v("動き","右:" + horizontalPoint + " 最大:"+ (horizontalBlockNum-1));
                    //if (horizontalPoint == horizontalBlockNum - 1) {
                    //    return false;
                    //}
                    rect.offset(movesize,0);
                    break;
                case 3:
                    //Log.v("動き", "上:" + verticalPoint);
                    //if (verticalPoint == 0) {
                    //    return false;
                    //}
                    rect.offset(0,-movesize);
                    break;
            }
        */
        MOVECOUNT++;
        if (MOVECOUNT == movetimes) {
            rect.offset(movesizeXmod, movesizeYmod);
            horizontalPoint = toSellX;
            verticalPoint = toSellY;
                /*
                switch (oneesandirection) {
                    //0:下 1:左 2:右 3:上
                    case 0:
                        verticalPoint++;
                        break;
                    case 1:
                        horizontalPoint--;
                        break;
                    case 2:
                        horizontalPoint++;
                        break;
                    case 3:
                        verticalPoint--;
                        break;
                }
                */

            MOVECOUNT = 0;
            return false;
        }

        return true;
    }


    //移動先可能な方向の矢印を描画する
    public void drawArrow() {

    }

    public int getHorizontalPoint() {
        return horizontalPoint;
    }

    public int getVerticalPoint() {
        return verticalPoint;
    }

    public void setHorizontalPoint(int x) {
        horizontalPoint = x;
    }

    public void setVertivalPoint(int y) {
        verticalPoint = y;
    }

    public void setDirection(int direction) {
        oneesandirection = direction;
    }
}
