package bibimba.masumetest;

/**
 * Created by user on 2015/03/14.
 */
public enum directionEnum {
    //0:下 1:左 2:右 3:上
    down(0,"down"),
    left(1,"left"),
    right(2,"right"),
    up(3,"up"),
    leftup(4,"leftup"),
    rightup(5,"rightup"),
    leftdown(6,"leftdown"),
    rightdown(7,"rightdown");

    private final int directionkey;
    private final String directionname;
    private directionEnum(final int directionkey,final String directionname) {
        this.directionkey = directionkey;
        this.directionname = directionname;
    }
    public int directionkey() {
        return directionkey;
    }
    public String directionname() {
        return directionname;
    }


}
