package bibimba.masumetest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Pair;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by user on 2015/03/09.
 */
public class gamewindow extends SurfaceView implements SurfaceHolder.Callback{

    private Map map;


    private SurfaceHolder holder;
    private Bitmap charabitmap;
    private Bitmap[] chestBitmaps;

    private static final Paint PAINT = new Paint();

    private static final int CHARA_BLOCK_SIZE = 32;
    public int direction = 0;
    private final int[][] DIRECTION_MOVEMENT = {
            {0, -1}, // down
            {-1, 0}, // left
            {1, 0}, // right
            {0, 1}  // up
    };

    public boolean isMoving;

    public static int movetype;

    //private Handler.Callback callback;
    //public void setCallback(Handler.Callback callback) {
    //    this.callback =callback;
    //}


    private int vw;
    private int vh;

    private int rectleft;
    private int recttop;
    private int rectright;
    private int rectbottom;

    private remotesister oneesan;
    private Arrows arrows;


    private final int SLEEPTIME = 50; // ミリセカンド

    public gamewindow(Context context,SurfaceView sv) {
        super(context);
        holder = sv.getHolder();
        holder.addCallback(this);

    }

    public gamewindow(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);


    }

    private DrawThread drawThread;
    private class DrawThread extends Thread {
        private boolean isFinished;

        @Override
        public void run() {
            while (!isFinished) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    drawGame(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }

                try {
                    sleep(SLEEPTIME);

                } catch (InterruptedException e) {

                }
            }
        }
    }

    public void startDrawThread() {
        stopDrawThread();
        drawThread = new DrawThread();
        drawThread.start();
    }

    public boolean stopDrawThread() {
        if (drawThread == null) {
            return false;
        }
        drawThread.isFinished = true;
        drawThread = null;
        return true;
    }

    public void drawGame(Canvas canvas) {

        canvas.drawColor(Color.WHITE);



        vw = canvas.getWidth();
        vh = canvas.getHeight();

        if ( map == null) {
            map = new Map(vw,vh,CHARA_BLOCK_SIZE);
        }
        //map.drawMap(canvas);

        if (oneesan == null) {
            //テスト画像読み込み
            charabitmap = BitmapFactory.decodeResource(getResources(),R.drawable.masutest);

            //oneesan = new remotesister(charabitmap,rectleft,recttop,rectright,rectbottom);
            oneesan = new remotesister(charabitmap,vw,vh,CHARA_BLOCK_SIZE,map);
        }

        if (arrows == null) {
            arrows = new Arrows(getContext());

        }

        if (this.chestBitmaps == null) {
            this.chestBitmaps = new Bitmap[2];
            this.chestBitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.chest_closed);
            this.chestBitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.chest_opened);
        }

        boolean rt;

        if (movetype == 0) {
            //こちらが有効だとおねえさんが動く
            rt = oneesan.move(isMoving,direction);
        } else {
            //こっちが有効だとマップが動く
            rt = oneesan.movemap(isMoving,direction);
            isMoving = rt;
            if (isMoving) {
                rt = map.MapMove(isMoving,direction);
                if (!rt) {
                    switch (direction) {
                        //0:下 1:左 2:右 3:上
                        case 0:
                            oneesan.verticalPoint++;
                            break;
                        case 1:
                            oneesan.horizontalPoint--;
                            break;
                        case 2:
                            oneesan.horizontalPoint++;
                            break;
                        case 3:
                            oneesan.verticalPoint--;
                            break;
                    }
                }
            }
        }

        isMoving = rt;

        map.drawMap(canvas);
        drawChests(canvas);
        oneesan.draw(canvas);

        if (!isMoving) {
            ArrayList<Pair> arpair = map.getmovetolist(oneesan.horizontalPoint,oneesan.verticalPoint);
            arrows.drawArrow(canvas,arpair,oneesan.horizontalPoint,oneesan.verticalPoint,
                    oneesan.rect.left,oneesan.rect.top);


        }

    }

    private void drawChests(Canvas canvas) {
        Rect rectSrc = new Rect(0, 0, this.chestBitmaps[0].getWidth(), this.chestBitmaps[0].getHeight());
        for (Chest chest : this.map.getChests().values()) {
            int x = chest.getX() * this.map.getBlocksize();
            int y = chest.getY() * this.map.getBlocksize();
            Rect rectDest = new Rect(x, y, x + this.map.getBlocksize(), y + this.map.getBlocksize());
            canvas.drawBitmap(this.chestBitmaps[chest.getState()], rectSrc, rectDest, null);
        }
    }

    /**
     * 宝箱を開ける。
     * 本処理は移動イベント発生時に呼ばれるため、おねえさんの座標は移動前のままである。
     * 宝箱の有無について実際に調べる座標は移動後のものである必要があり、
     * そのために移動方向に基づく座標の補正配列DIRECTION_MOVEMENTを用いている。
     * @return 宝箱があり、あいていなかった場合、内容のItem。それ以外の場合null
     */
    public Item openChest() {
        if (this.isMoving) {
            int[] movement = DIRECTION_MOVEMENT[this.direction];
            // FIXME: どうもY方向移動時、座標計算が誤っている模様。
            return this.map.openChest(this.oneesan.getHorizontalPoint() + movement[0], this.oneesan.getVerticalPoint() + movement[1]);
        } else {
            return null;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startDrawThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int width,int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDrawThread();
    }
}
