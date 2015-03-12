package bibimba.masumetest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.provider.Telephony;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by user on 2015/03/09.
 */
public class gamewindow extends SurfaceView implements SurfaceHolder.Callback{

    private Map map;


    private SurfaceHolder holder;
    private Bitmap charabitmap;

    private static final Paint PAINT = new Paint();

    private static final int CHARA_BLOCK_SIZE = 32;
    public int direction = 0;

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

        boolean rt;

        if (movetype == 0) {
            //こちらを有効にするとおねえさんが動く
            rt = oneesan.move(isMoving,direction);
        } else {
            //以下を有効にするとマップが動く
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
        oneesan.draw(canvas);


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
