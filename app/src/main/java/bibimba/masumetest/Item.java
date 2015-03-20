package bibimba.masumetest;

/**
 * アイテム。
 * Created by kenichi on 2015/03/19.
 */
public class Item {
    private int id;
    private String name;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
