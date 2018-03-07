package Models;

/**
 * Created by elpsychris on 03/06/2018.
 */

public class Dish {
    private int id;
    private String name;
    private String desc;
    private int quality;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
