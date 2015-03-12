package bibimba.masumetest;

/**
 * Created by user on 2015/03/11.
 */
public class UnitBase {
    private int blocksize;
    private int horizontalBlockNum;
    private int verticalBlockNum;
    public UnitBase(int w,int h,int bs) {
        blocksize = bs;
        horizontalBlockNum = w / blocksize;
        verticalBlockNum = h / blocksize;
    }
}
