package bibimba.masumetest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by user on 2015/03/14.
 */
public class Arrows {

    private static final int CHARA_BLOCK_SIZE = 32;

    private final Paint paint = new Paint();
    private Bitmap bitmap[] = new Bitmap[4];
    //final Rect rect;

    private long rotatetiming = System.currentTimeMillis();
    private int rotateint = 0;

    public Arrows(Context context) {
        //0:下 1:左 2:右 3:上
        this.bitmap[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowdown);
        this.bitmap[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowleft);
        this.bitmap[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowright);
        this.bitmap[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowup);

    }

    public void drawArrow(Canvas canvas,ArrayList<Pair> arp,int X,int Y,int oneesanleft,int oneesantop) {

        int rectleft;
        int recttop;

        rotatecaluculation();

        if (rotateint==0) {
                  /*
        for (Pair test:arp) {
            Log.v("ペア値", " toＸ:" + test.first + " toＹ:" + test.second);
        }
        */

            //下
            Pair<Integer,Integer> pa = Pair.create(X,Y + 1);
            if (arp.contains(pa)) {
                rectleft = oneesanleft;
                recttop = oneesantop + CHARA_BLOCK_SIZE;
                canvas.drawBitmap(this.bitmap[0],rectleft,recttop,paint);
            }

            //左
            pa = Pair.create(X - 1,Y);
            if (arp.contains(pa)) {
                rectleft = oneesanleft - CHARA_BLOCK_SIZE;
                recttop = oneesantop;
                canvas.drawBitmap(this.bitmap[1],rectleft,recttop,paint);
            }

            //右
            pa = Pair.create(X + 1,Y);
            if (arp.contains(pa)) {
                rectleft = oneesanleft + CHARA_BLOCK_SIZE;
                recttop = oneesantop;
                canvas.drawBitmap(this.bitmap[2],rectleft,recttop,paint);
            }

            //上
            pa = Pair.create(X,Y - 1);
            if (arp.contains(pa)) {
                rectleft = oneesanleft;
                recttop = oneesantop - CHARA_BLOCK_SIZE;
                canvas.drawBitmap(this.bitmap[3],rectleft,recttop,paint);
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
