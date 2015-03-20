package bibimba.masumetest;

/**
 * 金貨袋。Amountプロパティで金額を得る。
 *
 * Created by kenichi on 2015/03/19.
 */
public class BagOfGold extends Item {
    int amount;

    public BagOfGold(int id, String name, int amount) {
        super(id, name);
        this.amount = amount;
    }

    /**
     * 金貨袋の中身の金額を返す
     * @return 金額
     */
    public int getAmount() {
        return amount;
    }
}
