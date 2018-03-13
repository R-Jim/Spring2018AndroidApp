package model;

import java.util.List;

/**
 * Created by elpsychris on 03/06/2018.
 */

public class Order {
    private int id;
    private List<Dish> dish;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Dish> getDish() {
        return dish;
    }

    public void setDish(List<Dish> dish) {
        this.dish = dish;
    }
}
