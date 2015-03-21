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
            {0, 1},  // up
            {-1, -1}, // leftup
            {1, -1}, // rightup
            {-1, 1}, // leftdown
            {1, 1}  // rightdown
    };

    public boolean isMoving;
    private boolean nowMoving;

    public static int movetype;

    private int[] movetosell;

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

        boolean rt = false;

        if (movetype == 0) {
            //こちらが有効だとおねえさんが動く
            if (isMoving) {
                if (!nowMoving) {
                    movetosell = map.canMoveOneesan(oneesan.getHorizontalPoint(),oneesan.getVerticalPoint(),direction);
                    if (movetosell == null) {
                        isMoving = false;
                    } else {
                        nowMoving = true;
                        int[] unko = map.getBlockPosition(movetosell[0],movetosell[1]);
                        oneesan.setmovesize(unko[0],unko[1]);
                    }

                } else {
                    isMoving = oneesan.move(direction,movetosell[0],movetosell[1]);
                    nowMoving = isMoving;
                }
            }


        } else {
            //こっちが有効だとマップが動く

            if (isMoving) {
                if (!nowMoving) {
                    movetosell = map.canMoveMap(oneesan.getHorizontalPoint(),oneesan.getVerticalPoint(),direction);
                    if (movetosell == null) {
                        isMoving = false;
                    } else {
                        nowMoving = true;

                        int[] unko2 = map.getBlockPosition(movetosell[0],movetosell[1]);
                        int[] unko3 = map.getBlockPosition(oneesan.getHorizontalPoint(),oneesan.getVerticalPoint());
                        map.MapMoveCalc(unko2[0],unko2[1],unko3[0],unko3[1]);
                        oneesan.setDirection(direction);
                    }
                } else {
                    boolean isMoveEnd = map.MapMove(direction,movetosell[0],movetosell[1]);

                    if (!isMoveEnd) {
                        oneesan.setHorizontalPoint(movetosell[0]);
                        oneesan.setVertivalPoint(movetosell[1]);
                        isMoving = false;
                        nowMoving = false;
                    }
                }
            }

        }


        map.drawMap(canvas);
        drawChests(canvas);
        oneesan.draw(canvas);


        if (!isMoving) {
            ArrayList<Integer[]> arpair = map.getmovetolist(oneesan.getHorizontalPoint(),oneesan.getVerticalPoint());
            arrows.drawArrow(canvas,arpair,oneesan.getHorizontalPoint(),oneesan.getVerticalPoint(),
                    oneesan.rect.left,oneesan.rect.top);
        }

    }

    private void drawChests(Canvas canvas) {
        Rect rectSrc = new Rect(0, 0, this.chestBitmaps[0].getWidth(), this.chestBitmaps[0].getHeight());
        for (Chest chest : this.map.getChests().values()) {

            //map.getBlockPosition
            int[] pos = map.getBlockPosition(chest.getX(),chest.getY());
            int x = pos[0];
            int y = pos[1];
            //int x = chest.getX() * this.map.getBlocksize();
            //int y = chest.getY() * this.map.getBlocksize();
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