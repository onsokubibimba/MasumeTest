package bibimba.masumetest;

/**
 * 宝箱オブジェクト。
 *
 * Created by kenichi on 2015/03/19.
 */
public class Chest {
    private int x;
    private int y;
    private Item item;
    private int state;

    /**
     * あいていない状態。
     */
    public final int STATUS_CLOSED = 0;

    /**
     * あいている状態。
     */
    public final int STATUS_OPENED = 1;

    public Chest(int x, int y, Item item) {
        initialize(x, y, item, STATUS_CLOSED);
    }

    public Chest(int x, int y, Item item, int state) {
        initialize(x, y, item, state);
    }

    private void initialize(int x, int y, Item item, int state) {
        this.x = x;
        this.y = y;
        this.item = item;
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * 宝箱を開ける。内容のItemを返す。
     * すでにあいている場合はnullを返す
     * @return Itemオブジェクト。あいている場合はnull
     */
    public Item open() {
        if (this.state == STATUS_CLOSED) {
            this.state = STATUS_OPENED;
            return item;
        } else {
            return null;
        }
    }

    public int getState() {
        return state;
    }
}
