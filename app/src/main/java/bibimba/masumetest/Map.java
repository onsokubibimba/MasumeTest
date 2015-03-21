package bibimba.masumetest;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private int movetimes = 6;

    private int movehorizonal;
    private int movevertical;
    // 座標を示すPair<Integer x, Integer y>と宝箱のマップ
    private java.util.Map<Pair<Integer, Integer>, Chest> chests;
    private final int MAX_CHESTS = 3;

    // 全てのアイテム
    private List<Item> items;
    private final int MAX_ITEMS = 4;

    private int movehorizonalmod;
    private int moveverticalmod;


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





        this.chests = new HashMap<Pair<Integer, Integer>, Chest>(MAX_CHESTS);
        this.items = new ArrayList<Item>(MAX_ITEMS);

        createMap();

        loadItems();
        generateChests();
    }

    public java.util.Map<Pair<Integer, Integer>, Chest> getChests() {
        return chests;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getBlocksize() {
        return blocksize;
    }

    public void MapMoveCalc(int toX,int toY,int fromX,int fromY) {

        movehorizonal = (fromX - toX) / movetimes;
        movevertical = (fromY - toY) / movetimes;



        int aaa = movehorizonal;
        movehorizonalmod = fromX - toX - (aaa * movetimes);



        aaa = movevertical;
        moveverticalmod = fromY - toY - (aaa * movetimes);


        /*
        switch (direction) {
            case 0:
                //if (verticalPoint == verticalBlockNum - 1) {
                //    return false;
                //}
                movehorizonal = 0;
                movevertical = -(blocksize / movetimes);
                break;


            case 1:
                //if (horizontalPoint == 0) {
                //    return false;
                //}
                movehorizonal = blocksize / movetimes;
                movevertical = 0;
                break;
            case 2:
                //if (horizontalPoint == horizontalBlockNum - 1) {
                //    return false;
                //}
                movehorizonal = -(blocksize / movetimes);
                movevertical = 0;
                break;
            case 3:
                //if (verticalPoint == 0) {
                //    return false;
                //}
                movehorizonal = 0;
                movevertical = blocksize / movetimes;
                break;
        }
        */
    }

    public boolean MapMove(int direction,int toX,int toY) {

        for (int y =0; y < verticalBlockNum; y++) {
            for (int x = 0 ; x < horizontalBlockNum; x++) {
                block[y][x].rect.offset(movehorizonal,movevertical);
            }
        }
        MOVECOUNT++;
        if (MOVECOUNT == movetimes) {
            for (int y =0; y < verticalBlockNum; y++) {
                for (int x = 0 ; x < horizontalBlockNum; x++) {
                    block[y][x].rect.offset(movehorizonalmod,moveverticalmod);
                }
            }

            MOVECOUNT = 0;
            return false;
        }
        return true;
    }

    @Override
    //public boolean canMoveMap(int fromX ,int fromY,int direction) {
    public int[] canMoveMap(int fromX ,int fromY,int direction) {

        /*
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
                //return false;
                return null;
        }
        */


        for (Integer[] data:block[fromY][fromX].canmovelistsArray) {
            if (data[2] == direction) {
                return new int[]{data[0],data[1]};
            }
        }
        return null;

        /*
        if (block[fromY][fromX].canmovelistsArray.contains(tomasume)) {
            return true;
        } else {
            //return false;
            return null;
        }
        */

        /*
        switch (direction) {
            case 0:
                if (fromY == verticalBlockNum - 1) {
                    return false;
                }
                break;


            case 1:
                if (fromX == 0) {
                    return false;
                }
                break;
            case 2:
                if (fromX == horizontalBlockNum - 1) {
                    return false;
                }
                break;
            case 3:
                if (fromY == 0) {
                    return false;
                }
                break;
        }
        return true;
        */
    }

    @Override
    //public boolean canMoveOneesan(int fromX ,int fromY,int direction) {
    public int[] canMoveOneesan(int fromX ,int fromY,int direction) {
        //Pair<Integer,Integer> tomasume;
        Integer tomasume[];

        /*
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
        */

        for (Integer[] data:block[fromY][fromX].canmovelistsArray) {
            if (data[2] == direction) {
                return new int[]{data[0],data[1]};
            }
        }
        return null;

        /*
        if (block[fromY][fromX].canmovelistsArray.contains(tomasume)) {
            return true;
        } else {
            return false;
        }
        */

        /*
        switch (direction) {
           case 0:
                if (fromY == verticalBlockNum - 1) {
                    return false;
                }
                break;


            case 1:
                if (fromX == 0) {
                    return false;
                }
                break;
            case 2:
                if (fromX == horizontalBlockNum - 1) {
                    return false;
                }
                break;
            case 3:
               if (fromY == 0) {
                    return false;
                }
                break;
        }
        */
        //return true;
    }

    private void createMap() {

        //テストで任意に切り替えてくださいモエー
        // TODO:マップは外部リソースに持ちたいね

        //ドラクエっぽいマップ
        maptype1();

        //いたストっぽいマップ
        //こっちを有効にする場合は
        //remotesisterクラスのhorizontalBlockNumとverticalBlockNumの値も合わせること
        //maptype2();

    }

    //ドラクエっぽいマップ
    private void maptype1() {
        Integer canmovelist[];
        Integer canmovelist2[];
        Integer canmovelist3[];
        Integer canmovelist4[];
        Integer canmovelist5[];
        Integer canmovelist6[];
        Integer canmovelist7[];
        Integer canmovelist8[];
        block = new Block[verticalBlockNum][horizontalBlockNum];
        for (int y = 0; y < verticalBlockNum; y++) {
            for (int x = 0; x < horizontalBlockNum; x++) {
                //のちのちマップチップのタイプを制御したい時など
                int type = rand.nextInt(2);

                int left = x * blocksize + 1;
                int top = y * blocksize + 1;
                int right = left + blocksize - 2 ;
                int bottom = top + blocksize - 2;
                block[y][x] = new Block(type,left,top,right,bottom);

                //各ブロックが移動可能な先を格納する
                //移動先リストをデータベース化すればそこから読み込ませて格納させる
                canmovelist =
                        new Integer[]{x - 1,y,directionEnum.left.directionkey()};
                block[y][x].canmovelistsArray.add(canmovelist);
                canmovelist2 =
                        new Integer[]{x + 1,y,directionEnum.right.directionkey()};
                block[y][x].canmovelistsArray.add(canmovelist2);

                canmovelist3 =
                        new Integer[]{x,y - 1,directionEnum.up.directionkey()};
                block[y][x].canmovelistsArray.add(canmovelist3);

                canmovelist4 =
                        new Integer[]{x,y + 1,directionEnum.down.directionkey()};
                block[y][x].canmovelistsArray.add(canmovelist4);

                canmovelist5 =
                        new Integer[]{x - 1,y - 1,directionEnum.leftup.directionkey()};
                block[y][x].canmovelistsArray.add(canmovelist5);
                canmovelist6 =
                        new Integer[]{x + 1,y + 1,directionEnum.rightdown.directionkey()};
                block[y][x].canmovelistsArray.add(canmovelist6);

                canmovelist7 =
                        new Integer[]{x + 1,y - 1,directionEnum.rightup.directionkey()};
                block[y][x].canmovelistsArray.add(canmovelist7);

                canmovelist8 =
                        new Integer[]{x - 1,y + 1,directionEnum.leftdown.directionkey()};
                block[y][x].canmovelistsArray.add(canmovelist8);

                if (x == 0) {
                    block[y][x].canmovelistsArray.remove(canmovelist);
                    block[y][x].canmovelistsArray.remove(canmovelist5);
                    block[y][x].canmovelistsArray.remove(canmovelist8);
                }
                if (x == horizontalBlockNum - 1) {
                    block[y][x].canmovelistsArray.remove(canmovelist2);
                    block[y][x].canmovelistsArray.remove(canmovelist6);
                    block[y][x].canmovelistsArray.remove(canmovelist7);
                }
                if (y == 0) {
                    block[y][x].canmovelistsArray.remove(canmovelist3);
                    block[y][x].canmovelistsArray.remove(canmovelist5);
                    block[y][x].canmovelistsArray.remove(canmovelist7);
                }
                if (y == verticalBlockNum - 1) {
                    block[y][x].canmovelistsArray.remove(canmovelist4);
                    block[y][x].canmovelistsArray.remove(canmovelist6);
                    block[y][x].canmovelistsArray.remove(canmovelist8);
                }
            }
        }
    }

    //いたストっぽいマップ

    private void maptype2() {

        horizontalBlockNum = 4;
        verticalBlockNum = 1;

        Integer canmovelist[];
        Integer canmovelist2[];
        Integer canmovelist3[];
        Integer canmovelist4[];
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        block = new Block[verticalBlockNum][horizontalBlockNum];
        for (int y = 0; y < verticalBlockNum; y++) {
            for (int x = 0; x < horizontalBlockNum; x++) {
                //のちのちマップチップのタイプを制御したい時など
                int type = rand.nextInt(2);

                if (x == 0 && y == 0) {
                    left = 0 * blocksize + 1;
                    top = 0 * blocksize + 1;
                    right = left + blocksize - 2 ;
                    bottom = top + blocksize - 2;
                } else if (x == 1 && y == 0) {
                    left = 3 * blocksize + 1;
                    top = 0 * blocksize + 1;
                    right = left + blocksize - 2 ;
                    bottom = top + blocksize - 2;
                } else if (x == 2 && y == 0) {
                    left = 7 * blocksize + 1;
                    top = 1 * blocksize + 1;
                    right = left + blocksize - 2 ;
                    bottom = top + blocksize - 2;
                } else if (x==3 && y==0) {
                    left = 4 * blocksize + 1;
                    top = 3 * blocksize + 1;
                    right = left + blocksize - 2 ;
                    bottom = top + blocksize - 2;
                }
                block[y][x] = new Block(type,left,top,right,bottom);
                //各ブロックが移動可能な先を格納する
                //移動先リストをデータベース化すればそこから読み込ませて格納させる
                if (x == 0 && y == 0) {
                    canmovelist =
                            new Integer[]{1,0,directionEnum.right.directionkey()};
                    block[y][x].canmovelistsArray.add(canmovelist);
                    canmovelist =
                            new Integer[]{3,0,directionEnum.rightdown.directionkey()};
                    block[y][x].canmovelistsArray.add(canmovelist);
                    canmovelist =
                            new Integer[]{3,0,directionEnum.down.directionkey()};
                    block[y][x].canmovelistsArray.add(canmovelist);
                } else if (x == 1 && y == 0) {
                    canmovelist =
                            new Integer[]{2,0,directionEnum.right.directionkey()};
                    block[y][x].canmovelistsArray.add(canmovelist);
                    canmovelist =
                            new Integer[]{0,0,directionEnum.left.directionkey()};
                    block[y][x].canmovelistsArray.add(canmovelist);
                } else if (x == 2 && y == 0) {
                    canmovelist =
                            new Integer[]{1,0,directionEnum.left.directionkey()};
                    block[y][x].canmovelistsArray.add(canmovelist);
                    canmovelist =
                            new Integer[]{1,0,directionEnum.leftup.directionkey()};
                    block[y][x].canmovelistsArray.add(canmovelist);
                    canmovelist =
                            new Integer[]{3,0,directionEnum.leftdown.directionkey()};
                    block[y][x].canmovelistsArray.add(canmovelist);
                    canmovelist =
                            new Integer[]{3,0,directionEnum.down.directionkey()};
                    block[y][x].canmovelistsArray.add(canmovelist);
                } else if (x==3 && y==0) {
                    canmovelist =
                            new Integer[]{2,0,directionEnum.rightup.directionkey()};
                    block[y][x].canmovelistsArray.add(canmovelist);
                    canmovelist =
                            new Integer[]{2,0,directionEnum.right.directionkey()};
                    block[y][x].canmovelistsArray.add(canmovelist);
                    canmovelist =
                            new Integer[]{0,0,directionEnum.leftup.directionkey()};
                    block[y][x].canmovelistsArray.add(canmovelist);
                    canmovelist =
                            new Integer[]{0,0,directionEnum.left.directionkey()};
                    block[y][x].canmovelistsArray.add(canmovelist);
                }
            }
        }
    }

    /**
     * アイテム情報をロード
     */
    private void loadItems() {
        // TODO: 将来的には外部定義ファイルから読み込む感じ
        this.items.add(new Item(0, "薬草"));
        this.items.add(new Item(1, "うまのふん"));
        this.items.add(new Item(2, "大豆"));
        this.items.add(new BagOfGold(2, "100G", 100));
        this.items.add(new BagOfGold(3, "1000G", 1000));
    }

    /**
     * 全てのアイテムの中からランダムに一つアイテムを得る。
     * @return Itemオブジェクト
     */
    private Item selectRandomItem() {
        // TODO: 将来的には確率でアイテムのレア度を分ける
        Random random = new Random();
        return this.items.get(random.nextInt(this.items.size()));
    }

    /**
     * マップ上にランダムに宝箱を配置する
     */
    private void generateChests() {
        Random random = new Random();
        for (int i = 0; i < MAX_CHESTS; i++) {
            Item item = selectRandomItem();
            int x = random.nextInt(horizontalBlockNum);
            int y = random.nextInt(verticalBlockNum);
            this.chests.put(new Pair<Integer, Integer>(x, y), new Chest(x, y, item));
        }
    }

    /**
     * 指定した座標にある宝箱を開け、中にあるアイテムを返す
     * 宝箱がない場合や多寡空箱が開いている場合はnullを返す
     * @param x マップ上のX座標
     * @param y マップ上のY座標
     * @return 宝箱の中身を示すItemオブジェクト。空の場合はnull
     */
    public Item openChest(int x, int y) {
        Chest chest = this.chests.get(new Pair<Integer, Integer>(x, y));
        Item item = null;
        if (chest != null) item = chest.open();
        return item;
    }


    void drawMap(Canvas canvas) {
        for (int y =0; y < verticalBlockNum; y++) {
            for (int x = 0 ; x < horizontalBlockNum; x++) {
                block[y][x].draw(canvas);
            }
        }
    }

    public ArrayList<Integer[]> getmovetolist(int fromX,int fromY) {
        return block[fromY][fromX].canmovelistsArray;
    }

    public int[] getBlockPosition(int X,int Y) {
        int left = block[Y][X].rect.left;
        int top = block[Y][X].rect.top;
        int right = block[Y][X].rect.right;
        int bottom = block[Y][X].rect.bottom;
        return new int[]{left,top,right,bottom};
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
        public ArrayList<Integer[]> canmovelistsArray = new ArrayList<Integer[]>();


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

    }
}