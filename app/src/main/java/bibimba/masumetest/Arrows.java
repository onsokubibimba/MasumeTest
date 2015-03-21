package bibimba.masumetest;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by user on 2015/03/14.
 */
public class Arrows {

    private static final int CHARA_BLOCK_SIZE = 32;

    private final Paint paint = new Paint();
    private Bitmap bitmap[] = new Bitmap[8];
    //final Rect rect;

    private long rotatetiming = System.currentTimeMillis();
    private int rotateint = 0;

    public Arrows(Context context) {
        //0:下 1:左 2:右 3:上 4:左上 5:右上 6:左下 7:右下
        this.bitmap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowdown);
        this.bitmap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowleft);
        this.bitmap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowright);
        this.bitmap[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowup);
        this.bitmap[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowleftup);
        this.bitmap[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowrightup);
        this.bitmap[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowleftdown);
        this.bitmap[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowrightdown);


    }


    //
    public void drawArrow(Canvas canvas,ArrayList<Integer[]> arp,int X,int Y,int oneesanleft,int oneesantop) {

        int rectleft;
        int recttop;

        ArrayList<Integer> directions = new ArrayList<Integer>();
        for (Integer[] data:arp) {
            directions.add(data[2]);
        }

        rotatecaluculation();

        if (rotateint==0) {

            //下
            if (directions.contains(directionEnum.down.directionkey())) {
            //    Pair<Integer,Integer> pa = Pair.create(X,Y + 1);
            //    if (arp.contains(pa)) {
                rectleft = oneesanleft;
                recttop = oneesantop + CHARA_BLOCK_SIZE;
                canvas.drawBitmap(this.bitmap[0],rectleft,recttop,paint);
            }

            //左
            if (directions.contains(directionEnum.left.directionkey())) {
            //pa = Pair.create(X - 1,Y);
            //if (arp.contains(pa)) {
                rectleft = oneesanleft - CHARA_BLOCK_SIZE;
                recttop = oneesantop;
                canvas.drawBitmap(this.bitmap[1],rectleft,recttop,paint);
            }

            //右
            if (directions.contains(directionEnum.right.directionkey())) {
            //pa = Pair.create(X + 1,Y);
            //if (arp.contains(pa)) {
                rectleft = oneesanleft + CHARA_BLOCK_SIZE;
                recttop = oneesantop;
                canvas.drawBitmap(this.bitmap[2],rectleft,recttop,paint);
            }

            //上
            if (directions.contains(directionEnum.up.directionkey())) {
            //pa = Pair.create(X,Y - 1);
            //if (arp.contains(pa)) {
                rectleft = oneesanleft;
                recttop = oneesantop - CHARA_BLOCK_SIZE;
                canvas.drawBitmap(this.bitmap[3],rectleft,recttop,paint);
            }

            //4:左上
            if (directions.contains(directionEnum.leftup.directionkey())) {
            //pa = Pair.create(X - 1,Y - 1);
            //if (arp.contains(pa)) {
                rectleft = oneesanleft - CHARA_BLOCK_SIZE;
                recttop = oneesantop - CHARA_BLOCK_SIZE;
                canvas.drawBitmap(this.bitmap[4],rectleft,recttop,paint);
            }

            //5:右上
            if (directions.contains(directionEnum.rightup.directionkey())) {
            //pa = Pair.create(X + 1,Y - 1);
            //if (arp.contains(pa)) {
                rectleft = oneesanleft + CHARA_BLOCK_SIZE;
                recttop = oneesantop - CHARA_BLOCK_SIZE;
                canvas.drawBitmap(this.bitmap[5],rectleft,recttop,paint);
            }

            //6:左下
            if (directions.contains(directionEnum.leftdown.directionkey())) {
            //pa = Pair.create(X - 1,Y + 1);
            //if (arp.contains(pa)) {
                rectleft = oneesanleft - CHARA_BLOCK_SIZE;
                recttop = oneesantop + CHARA_BLOCK_SIZE;
                canvas.drawBitmap(this.bitmap[6],rectleft,recttop,paint);
            }

            //7:右下
            if (directions.contains(directionEnum.rightdown.directionkey())) {
            //pa = Pair.create(X + 1,Y + 1);
            //if (arp.contains(pa)) {
                rectleft = oneesanleft + CHARA_BLOCK_SIZE;
                recttop = oneesantop + CHARA_BLOCK_SIZE;
                canvas.drawBitmap(this.bitmap[7],rectleft,recttop,paint);
            }

        }


    }

    private void rotatecaluculation() {
        if (( System.currentTimeMillis() - rotatetiming) > 1500 ) {
            rotatetiming = System.currentTimeMillis();
            rotateint += 1;
            if (rotateint > 1 ) {
                rotateint = 0;
            }

        }
    }
}
