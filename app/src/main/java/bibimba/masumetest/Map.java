package bibimba.masumetest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by user on 2015/03/11.
 */
public class Map implements remotesister.Callback{

    private int blocksize;
    private int horizontalBlockNum;
    private int verticalBlockNum;
    private Block[][] block;

    private int movesize;
    private int MOVECOUNT = 0;
    private int movetimes = 4;

    private int movehorizonal;
    private int movevertical;



    private Handler.Callback callback;
    public void setCallback(Handler.Callback callback) {
        this.callback =callback;
    }

    Random rand = new Random(0);

    public Map(int w,int h,int bs) {

        blocksize = bs;
        horizontalBlockNum = w / blocksize;
        verticalBlockNum = h / blocksize;

        horizontalBlockNum = horizontalBlockNum+3;
        verticalBlockNum=verticalBlockNum+3;

        createMap();

    }


    //方向はEnumに変換すればいいと思うけどめんどくさい
    private void MapMoveCalc(int direction) {
        switch (direction) {
            //0:下 1:左 2:右 3:上
            case 0:
                //Log.v("動き","下:" + verticalPoint + " 最大:"+ (verticalBlockNum-1));
                //if (verticalPoint == verticalBlockNum - 1) {
                //    return false;
                //}
                movehorizonal = 0;
                movevertical = -(blocksize / movetimes);
                break;


            case 1:
                //Log.v("動き","左:" + horizontalPoint);
                //if (horizontalPoint == 0) {
                //    return false;
                //}
                movehorizonal = blocksize / movetimes;
                movevertical = 0;
                break;
            case 2:
                //Log.v("動き","右:" + horizontalPoint + " 最大:"+ (horizontalBlockNum-1));
                //if (horizontalPoint == horizontalBlockNum - 1) {
                //    return false;
                //}
                movehorizonal = -(blocksize / movetimes);
                movevertical = 0;
                break;
            case 3:
                //Log.v("動き", "上:" + verticalPoint);
                //if (verticalPoint == 0) {
                //    return false;
                //}
                movehorizonal = 0;
                movevertical = blocksize / movetimes;
                break;
        }
    }

    public boolean MapMove(boolean isMoving,int direction) {
        //if (!isMoving) {
        //    return false;
        //}
        if (MOVECOUNT == 0) {
            MapMoveCalc(direction);
        }
        for (int y =0; y < verticalBlockNum; y++) {
            for (int x = 0 ; x < horizontalBlockNum; x++) {
                block[y][x].rect.offset(movehorizonal,movevertical);
            }
        }
        MOVECOUNT++;
        if (MOVECOUNT == movetimes) {
            MOVECOUNT = 0;
            return false;
        }
        return isMoving;
    }

    @Override
    public boolean canMoveMap(int fromX ,int fromY,int direction) {

        Pair<Integer,Integer> tomasume;
        switch (direction) {
            case 0:
                tomasume = Pair.create(fromX,fromY + 1);
                break;
            case 1:
                tomasume = Pair.create(fromX - 1,fromY);
                break;
            case 2:
                tomasume = Pair.create(fromX + 1,fromY);
                break;
            case 3:
                tomasume = Pair.create(fromX,fromY - 1);
                break;
            default:
                tomasume = Pair.create(fromX,fromY);
                return false;
        }

/*
        Log.v("ペア値","fromＸ:" + fromX + " fromＹ:" + fromY + " toＸ:" + tomasume.first + " toＹ:" + tomasume.second);
        for (Pair test:block[fromY][fromX].canmovelistsArray) {
            Log.v("ペア値"," toＸ:" + test.first + " toＹ:" + test.second);
        }
*/
        if (block[fromY][fromX].canmovelistsArray.contains(tomasume)) {
            return true;
        } else {
            return false;
        }

        /*
        switch (direction) {
            //0:下 1:左 2:右 3:上
            case 0:
                //Log.v("動き","下:" + verticalPoint + " 最大:"+ (verticalBlockNum-1));
                if (fromY == verticalBlockNum - 1) {
                    return false;
                }
                break;


            case 1:
                //Log.v("動き","左:" + horizontalPoint);
                if (fromX == 0) {
                    return false;
                }
                break;
            case 2:
                //Log.v("動き","右:" + horizontalPoint + " 最大:"+ (horizontalBlockNum-1));
                if (fromX == horizontalBlockNum - 1) {
                    return false;
                }
                break;
            case 3:
                //Log.v("動き", "上:" + verticalPoint);
                if (fromY == 0) {
                    return false;
                }
                break;
        }
        return true;
        */
    }

    @Override
    public boolean canMoveOneesan(int fromX ,int fromY,int direction) {

        Pair<Integer,Integer> tomasume;
        switch (direction) {
            case 0:
                tomasume = Pair.create(fromX,fromY + 1);
                break;
            case 1:
                tomasume = Pair.create(fromX - 1,fromY);
                break;
            case 2:
                tomasume = Pair.create(fromX + 1,fromY);
                break;
            case 3:
                tomasume = Pair.create(fromX,fromY - 1);
                break;
            default:
                tomasume = Pair.create(fromX,fromY);
                return false;
        }

/*
        Log.v("ペア値","fromＸ:" + fromX + " fromＹ:" + fromY + " toＸ:" + tomasume.first + " toＹ:" + tomasume.second);
        for (Pair test:block[fromY][fromX].canmovelistsArray) {
            Log.v("ペア値"," toＸ:" + test.first + " toＹ:" + test.second);
        }
*/
        if (block[fromY][fromX].canmovelistsArray.contains(tomasume)) {
            return true;
        } else {
            return false;
        }

        /*
        switch (direction) {
            //0:下 1:左 2:右 3:上
            case 0:
                //Log.v("動き","下:" + verticalPoint + " 最大:"+ (verticalBlockNum-1));
                if (fromY == verticalBlockNum - 1) {
                    return false;
                }
                break;


            case 1:
                //Log.v("動き","左:" + horizontalPoint);
                if (fromX == 0) {
                    return false;
                }
                break;
            case 2:
                //Log.v("動き","右:" + horizontalPoint + " 最大:"+ (horizontalBlockNum-1));
                if (fromX == horizontalBlockNum - 1) {
                    return false;
                }
                break;
            case 3:
                //Log.v("動き", "上:" + verticalPoint);
                if (fromY == 0) {
                    return false;
                }
                break;
        }
        */
        //return true;
    }

    private void createMap() {

        block = new Block[verticalBlockNum][horizontalBlockNum];
        for (int y = 0; y < verticalBlockNum; y++) {
            for (int x = 0; x < horizontalBlockNum; x++) {
                //仮
                int type = rand.nextInt(2);

                int left = x * blocksize + 1;
                int top = y * blocksize + 1;
                int right = left + blocksize - 2 ;
                int bottom = top + blocksize - 2;
                block[y][x] = new Block(type,left,top,right,bottom);

                //各ブロックが移動可能な先を格納する
                //移動先リストをデータベース化すればそこから読み込ませて格納させる
                Pair<Integer,Integer> canmovelist = Pair.create(x-1,y);
                block[y][x].canmovelistsArray.add(canmovelist);
                Pair<Integer,Integer> canmovelist2 = Pair.create(x,y-1);
                block[y][x].canmovelistsArray.add(canmovelist2);
                Pair<Integer,Integer> canmovelist3 = Pair.create(x+1,y);
                block[y][x].canmovelistsArray.add(canmovelist3);
                Pair<Integer,Integer> canmovelist4 = Pair.create(x,y+1);
                block[y][x].canmovelistsArray.add(canmovelist4);

                if (x == 0) {
                    block[y][x].canmovelistsArray.remove(canmovelist);
                }
                if (x == horizontalBlockNum - 1) {
                    block[y][x].canmovelistsArray.remove(canmovelist3);
                }
                if (y == 0) {
                    block[y][x].canmovelistsArray.remove(canmovelist2);
                }
                if (y == verticalBlockNum - 1) {
                    block[y][x].canmovelistsArray.remove(canmovelist4);
                }


            }
        }
    }

    void drawMap(Canvas canvas) {
        for (int y =0; y < verticalBlockNum; y++) {
            for (int x = 0 ; x < horizontalBlockNum; x++) {
                block[y][x].draw(canvas);
            }
        }
    }

    public ArrayList<Pair> getmovetolist(int fromX,int fromY) {
        return block[fromY][fromX].canmovelistsArray;
    }

    static class Block {

        private static final int TYPE_A = 0;
        private static final int TYPE_B = 1;

        private static final Paint PAINT_A = new Paint();
        private static final Paint PAINT_B = new Paint();

        static {
            PAINT_A.setColor(Color.GREEN);
            PAINT_B.setColor(Color.BLUE);
        }
        public ArrayList<Pair> canmovelistsArray = new ArrayList<Pair>(3);


        private final int type;
        final Rect rect;

        private Block(int type,int left,int top,int right,int bottom) {
            this.type = type;
            rect = new Rect(left,top,right,bottom);

        }

        private Paint getPaint() {
            switch (type) {
                case TYPE_A:
                    return PAINT_A;
                case TYPE_B:
                    return PAINT_B;
            }
            return null;
        }

        private void draw(Canvas canvas) {
            canvas.drawRect(rect,getPaint());
        }

        public void setcanmovetolist() {

        }

    }
}
